package gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import org.jfree.chart.*;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import dto.*;
import controller.*;

public class ReportMensileFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    public ReportMensileFrame(Controller controller) {
        super("Report Mensile - UninaFoodLab");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Logo.png")));

        // HEADER
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(0, 102, 204, 180));
        headerPanel.setPreferredSize(new Dimension(1000, 100));

        ImageIcon logo = new ImageIcon(getClass().getResource("/Logo.png"));
        Image originalImage = logo.getImage();
        int newWidth = 100;
        int newHeight = (int) (newWidth / ((double) originalImage.getWidth(null) / originalImage.getHeight(null)));
        Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(scaledImage));
        headerPanel.add(logoLabel, BorderLayout.WEST);

        JLabel titleLabel = new JLabel("Report Mensile", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        ImageIcon icon = new ImageIcon(getClass().getResource("/Back.png"));
        Image img = icon.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);
        JLabel Back = new JLabel(new ImageIcon(img));
        headerPanel.add(Back, BorderLayout.EAST);
        Back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    controller.chiudiReportMensileFrame();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                Back.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });

        getContentPane().add(headerPanel, BorderLayout.NORTH);

        // MAIN PANEL
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));
        getContentPane().add(mainPanel, BorderLayout.CENTER);


        // STATISTICHE
        JPanel dataPanel = new JPanel(new GridBagLayout());
        dataPanel.setBackground(Color.WHITE);
        dataPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;

        Chef chef = controller.getChef();
        int totaleCorsi = chef.getCorso().size();
        int sessioniOnline = 0;
        int sessioniInPresenza = 0;
        int totaleRicette = 0;
        int maxRicette = 0;
        int minRicette = Integer.MAX_VALUE;
        int sessioniConRicette = 0;

        LinkedList<String> categorie = new LinkedList<>();
        int[] corsiPerCategoria;

        for (Corso corso : chef.getCorso()) {
            String categoria = corso.getCategoria().toLowerCase();
            if (!categorie.contains(categoria)) categorie.add(categoria);
        }

        corsiPerCategoria = new int[categorie.size()];

        for (Corso corso : chef.getCorso()) {
            for (int i = 0; i < categorie.size(); i++) {
                if (corso.getCategoria().toLowerCase().equals(categorie.get(i).toLowerCase())) {
                    corsiPerCategoria[i]++;
                }
            }
            for (Sessione sessione : corso.getSessioni()) {
                if (sessione instanceof Sessione_online) {
                    sessioniOnline++;
                } else if (sessione instanceof Sessione_in_presenza) {
                    int numRicette = ((Sessione_in_presenza) sessione).getRicette().size();
                    sessioniInPresenza++;
                    totaleRicette += numRicette;
                    maxRicette = Math.max(maxRicette, numRicette);
                    if (numRicette > 0) {
                        minRicette = Math.min(minRicette, numRicette);
                        sessioniConRicette++;
                    }
                }
            }
        }

        double mediaRicette = sessioniConRicette > 0 ? (double) totaleRicette / sessioniConRicette : 0;
        if (minRicette == Integer.MAX_VALUE) minRicette = 0;

        String[][] reportData = {
                {"Numero totale corsi", String.valueOf(totaleCorsi)},
                {"Sessioni online", String.valueOf(sessioniOnline)},
                {"Sessioni in presenza", String.valueOf(sessioniInPresenza)},
                {"Media ricette per sessione in presenza", String.format("%.2f", mediaRicette)},
                {"Massimo ricette per sessione in presenza", String.valueOf(maxRicette)},
                {"Minimo ricette per sessione in presenza", String.valueOf(minRicette)}
        };

        for (int i = 0; i < reportData.length; i++) {
            JLabel titleLabel1 = new JLabel(reportData[i][0] + ":");
            titleLabel1.setFont(new Font("Arial", Font.BOLD, 15));
            gbc.gridx = 0;
            gbc.gridy = i;
            dataPanel.add(titleLabel1, gbc);

            JLabel valueLabel = new JLabel(reportData[i][1]);
            valueLabel.setFont(new Font("Arial", Font.PLAIN, 15));
            gbc.gridx = 1;
            dataPanel.add(valueLabel, gbc);
        }

        mainPanel.add(dataPanel, BorderLayout.NORTH);

        // PANELLO GRAFICI
        JPanel chartsPanel = new JPanel();
        chartsPanel.setLayout(new BoxLayout(chartsPanel, BoxLayout.Y_AXIS));
        chartsPanel.setBackground(new Color(245, 245, 245));
        chartsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // GRAFICO 1
        DefaultPieDataset pieDataset = new DefaultPieDataset();
        pieDataset.setValue("Online", sessioniOnline);
        pieDataset.setValue("In Presenza", sessioniInPresenza);
        JFreeChart pieChart = ChartFactory.createPieChart("Distribuzione Sessioni", pieDataset, true, true, false);
        ChartPanel pieChartPanel = new ChartPanel(pieChart);
        pieChartPanel.setPopupMenu(null);
        chartsPanel.add(wrapChartInPanel(pieChartPanel, "Distribuzione Sessioni"));

        // GRAFICO 2
        DefaultCategoryDataset categoryDataset = new DefaultCategoryDataset();
        for (int i = 0; i < categorie.size(); i++) {
            categoryDataset.addValue(corsiPerCategoria[i], "Corsi", categorie.get(i));
        }
        JFreeChart categoryChart = ChartFactory.createBarChart("Corsi per Categoria", "Categoria", "Numero Corsi",
                categoryDataset, PlotOrientation.VERTICAL, false, true, false);
        ChartPanel categoryChartPanel = new ChartPanel(categoryChart);
        categoryChartPanel.setPopupMenu(null);
        chartsPanel.add(wrapChartInPanel(categoryChartPanel, "Corsi per Categoria"));
        CategoryPlot catPlot = categoryChart.getCategoryPlot();
        BarRenderer catRenderer = (BarRenderer) catPlot.getRenderer();
        catRenderer.setMaximumBarWidth(0.05);


        // GRAFICO 3
        DefaultCategoryDataset recipeDataset = new DefaultCategoryDataset();
        recipeDataset.addValue(minRicette, "Ricette", "Minimo");
        recipeDataset.addValue(mediaRicette, "Ricette", "Media");
        recipeDataset.addValue(maxRicette, "Ricette", "Massimo");
        JFreeChart recipeChart = ChartFactory.createBarChart("Statistiche Ricette", "Metrica", "Numero Ricette", recipeDataset, PlotOrientation.VERTICAL, false, true, false);
        ChartPanel recipeChartPanel = new ChartPanel(recipeChart);
        chartsPanel.add(wrapChartInPanel(recipeChartPanel, "Statistiche Ricette"));
        recipeChartPanel.setPopupMenu(null);
        CategoryPlot recipePlot = recipeChart.getCategoryPlot();
        BarRenderer recipeRenderer = (BarRenderer) recipePlot.getRenderer();
        recipeRenderer.setMaximumBarWidth(0.05);


        // ScrollPane
        JScrollPane scrollPane = new JScrollPane(chartsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

    }


    // Metodo helper per impacchettare ogni grafico in un pannello titolato
    private JPanel wrapChartInPanel(ChartPanel chartPanel, String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                title,
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14)
        ));
        panel.add(chartPanel, BorderLayout.CENTER);
        panel.setPreferredSize(new Dimension(900, 250));
        panel.setBackground(Color.WHITE);
        return panel;
    }
}

