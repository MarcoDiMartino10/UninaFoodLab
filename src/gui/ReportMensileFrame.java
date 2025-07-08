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
	
	// Attributi
    private static final long serialVersionUID = 1L;

    // Costruttore
    public ReportMensileFrame(Controller controller) {
        
    	super("Report Mensile - UninaFoodLab");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Logo.png")));

        // Header Panel
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
                	dispose();
                	controller.apriHomepageFrame();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                Back.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });

        getContentPane().add(headerPanel, BorderLayout.NORTH);

        // Main
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));
        getContentPane().add(mainPanel, BorderLayout.CENTER);


        // Pannello con le statistiche
        JPanel statistichePannello = new JPanel(new GridBagLayout());
        statistichePannello.setBackground(Color.WHITE);
        statistichePannello.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;

        Chef chef = controller.getChefAttribute();
        int totaleCorsi = chef.getCorso().size();
        int sessioniOnline = 0;
        int sessioniInPresenza = 0;
        int totaleRicette = 0;
        int maxRicette = 0;
        int minRicette = Integer.MAX_VALUE;
        int sessioniConRicette = 0;

        LinkedList<String> categorie = new LinkedList<>();
        int[] corsiPerCategoria;
        
        // Utente appena creato
        if(totaleCorsi == 0) {
			JLabel noCorsiLabel = new JLabel("Nessun corso disponibile per il report mensile.");
			noCorsiLabel.setFont(new Font("Arial", Font.ITALIC, 16));
			noCorsiLabel.setForeground(Color.GRAY);
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.gridwidth = 2;
			statistichePannello.add(noCorsiLabel, gbc);
			mainPanel.add(statistichePannello, BorderLayout.NORTH);
			return;
		}
        
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
            statistichePannello.add(titleLabel1, gbc);

            JLabel valueLabel = new JLabel(reportData[i][1]);
            valueLabel.setFont(new Font("Arial", Font.PLAIN, 15));
            gbc.gridx = 1;
            statistichePannello.add(valueLabel, gbc);
        }

        mainPanel.add(statistichePannello, BorderLayout.NORTH);

        // Pannello dei grafici
        JPanel graficiPanel = new JPanel();
        graficiPanel.setLayout(new BoxLayout(graficiPanel, BoxLayout.Y_AXIS));
        graficiPanel.setBackground(new Color(245, 245, 245));
        graficiPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Grafico delle sessioni a torta
        DefaultPieDataset tortaGrafico = new DefaultPieDataset();
        tortaGrafico.setValue("Online", sessioniOnline);
        tortaGrafico.setValue("In Presenza", sessioniInPresenza);
        JFreeChart tortaChart = ChartFactory.createPieChart("Distribuzione Sessioni", tortaGrafico, true, true, false);
        ChartPanel tortaPanel = new ChartPanel(tortaChart);
        tortaPanel.setPopupMenu(null);
        graficiPanel.add(aggiungiNelPanello(tortaPanel, "Distribuzione Sessioni"));

        // Grafico dei corsi per categoria
        DefaultCategoryDataset corsiGrafico = new DefaultCategoryDataset();
        for (int i = 0; i < categorie.size(); i++) {
            corsiGrafico.addValue(corsiPerCategoria[i], "Corsi", categorie.get(i));
        }
        JFreeChart corsiChart = ChartFactory.createBarChart("Corsi per Categoria", "Categoria", "Numero Corsi", corsiGrafico, PlotOrientation.VERTICAL, false, true, false);
        ChartPanel corsiPanel = new ChartPanel(corsiChart);
        corsiPanel.setPopupMenu(null);
        graficiPanel.add(aggiungiNelPanello(corsiPanel, "Corsi per Categoria"));
        CategoryPlot corsiPlot = corsiChart.getCategoryPlot();
        BarRenderer cilindriCorsi = (BarRenderer) corsiPlot.getRenderer();
        cilindriCorsi.setMaximumBarWidth(0.05);

        // Grafico delle ricette
        DefaultCategoryDataset ricetteGrafico = new DefaultCategoryDataset();
        ricetteGrafico.addValue(minRicette, "Ricette", "Minimo");
        ricetteGrafico.addValue(mediaRicette, "Ricette", "Media");
        ricetteGrafico.addValue(maxRicette, "Ricette", "Massimo");
        JFreeChart ricetteChart = ChartFactory.createBarChart("Statistiche Ricette", "Metrica", "Numero Ricette", ricetteGrafico, PlotOrientation.VERTICAL, false, true, false);
        ChartPanel ricettePanel = new ChartPanel(ricetteChart);
        graficiPanel.add(aggiungiNelPanello(ricettePanel, "Statistiche Ricette"));
        ricettePanel.setPopupMenu(null);
        CategoryPlot ricettePlot = ricetteChart.getCategoryPlot();
        BarRenderer cilindriRicette = (BarRenderer) ricettePlot.getRenderer();
        cilindriRicette.setMaximumBarWidth(0.05);

        // ScrollPane
        JScrollPane scrollPane = new JScrollPane(graficiPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

    }

    // Metodo per impostare i grafici nel pannello
    private JPanel aggiungiNelPanello(ChartPanel chartPanel, String title) {
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

