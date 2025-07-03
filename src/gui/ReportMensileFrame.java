//package gui;
//
//import dto.*;
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.plot.PlotOrientation;
//import org.jfree.data.category.DefaultCategoryDataset;
//import org.jfree.data.general.DefaultPieDataset;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.time.LocalDate;
//import java.time.Month;
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.Map;
//
//public class ReportMensileFrame extends JFrame {
//    
//    private Chef chef;
//
//    public ReportMensileFrame(Chef chef) {
//        super("Report Mensile - UninaFoodLab");
//        this.chef = chef;
//        setSize(1000, 700);
//        setLocationRelativeTo(null);
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        setMinimumSize(new Dimension(800, 600));
//        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Logo.png")));
//
//        initUI();
//    }
//
//    private void initUI() {
//        // Calcola dati report
//        int totaleCorsi = chef.getCorso().size();
//        int sessioniOnline = 0;
//        int sessioniPresenza = 0;
//
//        int totaleRicette = 0;
//        int maxRicette = Integer.MIN_VALUE;
//        int minRicette = Integer.MAX_VALUE;
//        int sessioniPresenzaCount = 0;
//
//        // Consideriamo solo corsi attivi nel mese corrente (esempio: mese corrente)
//        LocalDate oggi = LocalDate.now();
//        Month meseCorrente = oggi.getMonth();
//        int annoCorrente = oggi.getYear();
//
//        // Map for courses by category
//        Map<String, Integer> coursesByCategory = new HashMap<>();
//        
//        for (Corso corso : chef.getCorso()) {
//            // Controlla se il corso inizia nel mese e anno corrente
//            if (corso.getData_inizio().getMonth() == meseCorrente && corso.getData_inizio().getYear() == annoCorrente) {
//                // Add to category map
//                String category = corso.getCategoria();
//                coursesByCategory.put(category, coursesByCategory.getOrDefault(category, 0) + 1);
//                
//                // sessioni per questo corso
//                LinkedList<Sessione> sessioni = corso.getSessioni();
//                for (Sessione s : sessioni) {
//                    // Controlla se la sessione Ã¨ nel mese corrente
//                    LocalDate dataSessione = s.getOrario_inizio_timestamp().toLocalDateTime().toLocalDate();
//                    if (dataSessione.getMonth() == meseCorrente && dataSessione.getYear() == annoCorrente) {
//                        if (s instanceof Sessione_online) {
//                            sessioniOnline++;
//                        } else if (s instanceof Sessione_in_presenza) {
//                            sessioniPresenza++;
//                            sessioniPresenzaCount++;
//                            int numRicette = ((Sessione_in_presenza) s).getRicette().size();
//                            totaleRicette += numRicette;
//                            if (numRicette > maxRicette) maxRicette = numRicette;
//                            if (numRicette < minRicette) minRicette = numRicette;
//                        }
//                    }
//                }
//            }
//        }
//
//        double mediaRicette = (sessioniPresenzaCount > 0) ? (double) totaleRicette / sessioniPresenzaCount : 0;
//        if (sessioniPresenzaCount == 0) {
//            minRicette = 0;  // se non ci sono sessioni in presenza
//            maxRicette = 0;
//        }
//
//        // Header Panel
//        JPanel headerPanel = new JPanel(new BorderLayout());
//        headerPanel.setBackground(new Color(0, 102, 204, 180));
//        headerPanel.setPreferredSize(new Dimension(1000, 100));
//
//        // Logo
//        ImageIcon logo = new ImageIcon(getClass().getResource("/Logo.png"));
//        Image originalImage = logo.getImage();
//        double aspectRatio = (double) originalImage.getWidth(null) / originalImage.getHeight(null);
//        int newWidth = 100;
//        int newHeight = (int) (newWidth / aspectRatio);
//        Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
//        JLabel logoLabel = new JLabel(new ImageIcon(scaledImage));
//        headerPanel.add(logoLabel, BorderLayout.WEST);
//
//        // Title
//        String titleText = "Report Mensile per Chef " + chef.getNome() + " " + chef.getCognome();
//        JLabel titleLabel = new JLabel(titleText, SwingConstants.CENTER);
//        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
//        titleLabel.setForeground(Color.WHITE);
//        headerPanel.add(titleLabel, BorderLayout.CENTER);
//
//        // Close Icon
//        ImageIcon closeIcon = new ImageIcon(getClass().getResource("/Back.png"));
//        Image closeImg = closeIcon.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);
//        JLabel closeLabel = new JLabel(new ImageIcon(closeImg));
//        headerPanel.add(closeLabel, BorderLayout.EAST);
//        closeLabel.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                if (SwingUtilities.isLeftMouseButton(e)) {
//                    dispose();
//                }
//            }
//
//            @Override
//            public void mouseEntered(MouseEvent e) {
//                closeLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
//            }
//
//            @Override
//            public void mouseExited(MouseEvent e) {
//                closeLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
//            }
//        });
//
//        getContentPane().add(headerPanel, BorderLayout.NORTH);
//
//        // Main Panel
//        JPanel mainPanel = new JPanel(new BorderLayout());
//        mainPanel.setBackground(Color.WHITE);
//        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
//        getContentPane().add(mainPanel, BorderLayout.CENTER);
//
//        // Text Report
//        JPanel textPanel = new JPanel(new BorderLayout());
//        textPanel.setBackground(Color.WHITE);
//        textPanel.setBorder(BorderFactory.createCompoundBorder(
//                BorderFactory.createLineBorder(new Color(200, 200, 200)),
//                BorderFactory.createEmptyBorder(15, 15, 15, 15)
//        ));
//
//        JTextArea reportText = new JTextArea();
//        reportText.setEditable(false);
//        reportText.setFont(new Font("Arial", Font.PLAIN, 16));
//        reportText.setText(
//            "Report Mensile per Chef: " + chef.getNome() + " " + chef.getCognome() + "\n\n" +
//            "Mese: " + meseCorrente + " " + annoCorrente + "\n\n" +
//            "Numero totale corsi tenuti: " + totaleCorsi + "\n" +
//            "Numero sessioni online: " + sessioniOnline + "\n" +
//            "Numero sessioni in presenza: " + sessioniPresenza + "\n\n" +
//            "Sessioni in presenza - Ricette realizzate:\n" +
//            "Media: " + String.format("%.2f", mediaRicette) + "\n" +
//            "Massimo: " + maxRicette + "\n" +
//            "Minimo: " + minRicette + "\n"
//        );
//        textPanel.add(new JScrollPane(reportText), BorderLayout.CENTER);
//        mainPanel.add(textPanel, BorderLayout.NORTH);
//
//        // Charts in Tabs
//        JTabbedPane tabbedPane = new JTabbedPane();
//        tabbedPane.setFont(new Font("Arial", Font.PLAIN, 14));
//
//        // Bar Chart: Courses by Category
//        DefaultCategoryDataset categoryDataset = new DefaultCategoryDataset();
//        for (Map.Entry<String, Integer> entry : coursesByCategory.entrySet()) {
//            categoryDataset.addValue(entry.getValue(), "Corsi", entry.getKey());
//        }
//        JFreeChart coursesChart = ChartFactory.createBarChart(
//                "Corsi per Categoria",
//                "Categoria",
//                "Numero di Corsi",
//                categoryDataset,
//                PlotOrientation.VERTICAL,
//                true,
//                true,
//                false
//        );
//        ChartPanel coursesChartPanel = new ChartPanel(coursesChart);
//        coursesChartPanel.setPreferredSize(new Dimension(700, 350));
//        tabbedPane.addTab("Corsi per Categoria", coursesChartPanel);
//
//        // Pie Chart: Sessions Distribution
//        DefaultPieDataset sessionDataset = new DefaultPieDataset();
//        sessionDataset.setValue("Sessioni Online", sessioniOnline);
//        sessionDataset.setValue("Sessioni In Presenza", sessioniPresenza);
//        JFreeChart sessionsChart = ChartFactory.createPieChart(
//                "Distribuzione Sessioni",
//                sessionDataset,
//                true,
//                true,
//                false
//        );
//        ChartPanel sessionsChartPanel = new ChartPanel(sessionsChart);
//        sessionsChartPanel.setPreferredSize(new Dimension(700, 350));
//        tabbedPane.addTab("Distribuzione Sessioni", sessionsChartPanel);
//
//        // Bar Chart: Recipes Stats
//        DefaultCategoryDataset recipesDataset = new DefaultCategoryDataset();
//        recipesDataset.addValue(minRicette, "Ricette", "Minimo");
//        recipesDataset.addValue(maxRicette, "Ricette", "Massimo");
//        recipesDataset.addValue(mediaRicette, "Ricette", "Media");
//        JFreeChart recipesChart = ChartFactory.createBarChart(
//                "Ricette nelle Sessioni In Presenza",
//                "Metrica",
//                "Numero di Ricette",
//                recipesDataset,
//                PlotOrientation.VERTICAL,
//                true,
//                true,
//                false
//        );
//        ChartPanel recipesChartPanel = new ChartPanel(recipesChart);
//        recipesChartPanel.setPreferredSize(new Dimension(700, 350));
//        tabbedPane.addTab("Statistiche Ricette", recipesChartPanel);
//
//        mainPanel.add(tabbedPane, BorderLayout.CENTER);
//    }
//}
//
//package gui;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//import java.util.LinkedList;
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.plot.PlotOrientation;
//import org.jfree.data.category.DefaultCategoryDataset;
//import dto.*;
//import controller.*;
//
//public class ReportMensileFrame extends JFrame {
//
//    private static final long serialVersionUID = 1L;
//    private Controller controller;
//
//    public ReportMensileFrame(Controller controller) {
//        super("Report Mensile - UninaFoodLab");
//        this.controller = controller;
//        setSize(1500, 1000);
//        setLocationRelativeTo(null);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setMinimumSize(new Dimension(800, 600));
//        setResizable(false);
//        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Logo.png")));
//
//        // Header
//        JPanel headerPanel = new JPanel(new BorderLayout());
//        headerPanel.setBackground(new Color(0, 102, 204, 180));
//        headerPanel.setPreferredSize(new Dimension(1000, 100));
//
//        ImageIcon logo = new ImageIcon(getClass().getResource("/Logo.png"));
//        Image originalImage = logo.getImage();
//        double aspectRatio = (double) originalImage.getWidth(null) / originalImage.getHeight(null);
//        int newWidth = 100;
//        int newHeight = (int) (newWidth / aspectRatio);
//        Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
//        JLabel logoLabel = new JLabel(new ImageIcon(scaledImage));
//        headerPanel.add(logoLabel, BorderLayout.WEST);
//
//        JLabel titleLabel = new JLabel("Report Mensile", SwingConstants.CENTER);
//        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
//        titleLabel.setForeground(Color.WHITE);
//        headerPanel.add(titleLabel, BorderLayout.CENTER);
//        getContentPane().add(headerPanel, BorderLayout.NORTH);
//
//        ImageIcon icon = new ImageIcon(getClass().getResource("/Back.png"));
//        Image img = icon.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);
//        JLabel backLabel = new JLabel(new ImageIcon(img));
//        headerPanel.add(backLabel, BorderLayout.EAST);
//        backLabel.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                if (SwingUtilities.isLeftMouseButton(e)) {
//                    controller.chiudiReportMensileFrame();
//                }
//            }
//
//            @Override
//            public void mouseEntered(MouseEvent e) {
//                backLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
//            }
//
//            @Override
//            public void mouseExited(MouseEvent e) {
//                backLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
//            }
//        });
//
//        // Main Panel
//        JPanel mainPanel = new JPanel(new BorderLayout());
//        mainPanel.setBackground(new Color(245, 245, 245));
//        getContentPane().add(mainPanel, BorderLayout.CENTER);
//
//        // Pannello dati
//        JPanel dataPanel = new JPanel(new GridBagLayout());
//        dataPanel.setBackground(Color.WHITE);
//        dataPanel.setBorder(BorderFactory.createCompoundBorder(
//                BorderFactory.createLineBorder(new Color(200, 200, 200)),
//                BorderFactory.createEmptyBorder(15, 15, 15, 15)
//        ));
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.insets = new Insets(5, 10, 5, 10);
//        gbc.anchor = GridBagConstraints.WEST;
//
//        // Calcolo statistiche
//        Chef chef = controller.getChef();
//        int totaleCorsi = chef.getCorso().size();
//        int sessioniOnline = 0;
//        int sessioniInPresenza = 0;
//        int totaleRicette = 0;
//        int maxRicette = 0;
//        int minRicette = Integer.MAX_VALUE;
//        int sessioniConRicette = 0;
//
//        for (Corso corso : chef.getCorso()) {
//            for (Sessione sessione : corso.getSessioni()) {
//                if (sessione instanceof Sessione_online) {
//                    sessioniOnline++;
//                } else if (sessione instanceof Sessione_in_presenza) {
//                    sessioniInPresenza++;
//                    int numRicette = ((Sessione_in_presenza) sessione).getRicette().size();
//                    totaleRicette += numRicette;
//                    maxRicette = Math.max(maxRicette, numRicette);
//                    if (numRicette > 0) {
//                        minRicette = Math.min(minRicette, numRicette);
//                        sessioniConRicette++;
//                    }
//                }
//            }
//        }
//        double mediaRicette = sessioniConRicette > 0 ? (double) totaleRicette / sessioniConRicette : 0;
//        minRicette = minRicette == Integer.MAX_VALUE ? 0 : minRicette;
//
//        String[][] reportData = {
//                {"Numero totale corsi", String.valueOf(totaleCorsi)},
//                {"Sessioni online", String.valueOf(sessioniOnline)},
//                {"Sessioni in presenza", String.valueOf(sessioniInPresenza)},
//                {"Media ricette per sessione in presenza", String.format("%.2f", mediaRicette)},
//                {"Massimo ricette per sessione in presenza", String.valueOf(maxRicette)},
//                {"Minimo ricette per sessione in presenza", String.valueOf(minRicette)}
//        };
//
//        for (int i = 0; i < reportData.length; i++) {
//            JLabel titleLabel1 = new JLabel(reportData[i][0] + ":");
//            titleLabel1.setFont(new Font("Arial", Font.BOLD, 15));
//            gbc.gridx = 0;
//            gbc.gridy = i;
//            dataPanel.add(titleLabel1, gbc);
//
//            JLabel valueLabel = new JLabel(reportData[i][1]);
//            valueLabel.setFont(new Font("Arial", Font.PLAIN, 15));
//            gbc.gridx = 1;
//            gbc.gridy = i;
//            dataPanel.add(valueLabel, gbc);
//        }
//        mainPanel.add(dataPanel, BorderLayout.NORTH);
//
//        // Grafico
//        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//        dataset.addValue(totaleCorsi, "Dati", "Corsi Totali");
//        dataset.addValue(sessioniOnline, "Dati", "Sessioni Online");
//        dataset.addValue(sessioniInPresenza, "Dati", "Sessioni In Presenza");
//        dataset.addValue(mediaRicette, "Dati", "Media Ricette");
//        dataset.addValue(maxRicette, "Dati", "Max Ricette");
//        dataset.addValue(minRicette, "Dati", "Min Ricette");
//
//        JFreeChart barChart = ChartFactory.createBarChart(
//                "Report Mensile",
//                "Metriche",
//                "Valori",
//                dataset,
//                PlotOrientation.VERTICAL,
//                false, true, false
//        );
//        ChartPanel chartPanel = new ChartPanel(barChart);
//        chartPanel.setPreferredSize(new Dimension(800, 300));
//        mainPanel.add(chartPanel, BorderLayout.CENTER);
//
//        // Pannello bottoni
////        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
////        buttonPanel.setBackground(new Color(245, 245, 245));
////
////        JButton backButton = new JButton("Torna alla Homepage");
////        styleButton(backButton);
////        backButton.addActionListener(_ -> controller.chiudiReportMensileFrame());
////        buttonPanel.add(backButton);
////
////        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
//    }
//
//    private void styleButton(JButton button) {
//        button.setFont(new Font("Arial", Font.BOLD, 16));
//        button.setBackground(new Color(0, 102, 204));
//        button.setForeground(Color.WHITE);
//        button.setMargin(new Insets(15, 20, 15, 20));
//        button.setFocusPainted(false);
//    }
//}
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
    private Controller controller;

    public ReportMensileFrame(Controller controller) {
        super("Report Mensile - UninaFoodLab");
        this.controller = controller;
        setSize(1300, 900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(800, 600));
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

        ImageIcon backIcon = new ImageIcon(getClass().getResource("/Back.png"));
        Image backImg = backIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        JLabel backLabel = new JLabel(new ImageIcon(backImg));
        backLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        headerPanel.add(backLabel, BorderLayout.EAST);

        backLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    controller.chiudiReportMensileFrame();
                }
            }

            public void mouseEntered(MouseEvent e) {
                backLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(MouseEvent e) {
                backLabel.setCursor(Cursor.getDefaultCursor());
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
            String categoria = corso.getCategoria();
            if (!categorie.contains(categoria)) categorie.add(categoria);
        }

        corsiPerCategoria = new int[categorie.size()];

        for (Corso corso : chef.getCorso()) {
            for (int i = 0; i < categorie.size(); i++) {
                if (corso.getCategoria().equals(categorie.get(i))) {
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
        chartsPanel.add(wrapChartInPanel(pieChartPanel, "Distribuzione Sessioni"));

        // GRAFICO 2
        DefaultCategoryDataset categoryDataset = new DefaultCategoryDataset();
        for (int i = 0; i < categorie.size(); i++) {
            categoryDataset.addValue(corsiPerCategoria[i], "Corsi", categorie.get(i));
        }
        JFreeChart categoryChart = ChartFactory.createBarChart("Corsi per Categoria", "Categoria", "Numero Corsi",
                categoryDataset, PlotOrientation.VERTICAL, false, true, false);
        ChartPanel categoryChartPanel = new ChartPanel(categoryChart);
        chartsPanel.add(wrapChartInPanel(categoryChartPanel, "Corsi per Categoria"));
        CategoryPlot catPlot = categoryChart.getCategoryPlot();
        BarRenderer catRenderer = (BarRenderer) catPlot.getRenderer();
        catRenderer.setMaximumBarWidth(0.1); // da 0.0 a 1.0 (più piccolo = più stretto)


        // GRAFICO 3
        DefaultCategoryDataset recipeDataset = new DefaultCategoryDataset();
        recipeDataset.addValue(minRicette, "Ricette", "Minimo");
        recipeDataset.addValue(mediaRicette, "Ricette", "Media");
        recipeDataset.addValue(maxRicette, "Ricette", "Massimo");
        JFreeChart recipeChart = ChartFactory.createBarChart("Statistiche Ricette", "Metrica", "Numero Ricette",
                recipeDataset, PlotOrientation.VERTICAL, false, true, false);
        ChartPanel recipeChartPanel = new ChartPanel(recipeChart);
        chartsPanel.add(wrapChartInPanel(recipeChartPanel, "Statistiche Ricette"));
        CategoryPlot recipePlot = recipeChart.getCategoryPlot();
        BarRenderer recipeRenderer = (BarRenderer) recipePlot.getRenderer();
        recipeRenderer.setMaximumBarWidth(0.1); // oppure 0.05 per barre molto sottili


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

