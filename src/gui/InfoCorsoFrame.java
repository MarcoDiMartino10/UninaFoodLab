package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import dto.*;
import controller.*;

public class InfoCorsoFrame extends JFrame {
	
	// Attributi
    private static final long serialVersionUID = 1L;
    private Controller controller;
    private JTable sessioniTable;
    private boolean checkDoubleClick = false;

    public InfoCorsoFrame(Controller controller) {

        super("Informazioni corso - UninaFoodLab");
        this.controller = controller;
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(800, 600));
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Logo.png")));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(0, 102, 204, 180));
        headerPanel.setPreferredSize(new Dimension(1000, 100));

        ImageIcon logo = new ImageIcon(getClass().getResource("/Logo.png"));
        Image originalImage = logo.getImage();
        double aspectRatio = (double) originalImage.getWidth(null) / originalImage.getHeight(null);
        int newWidth = 100;
        int newHeight = (int) (newWidth / aspectRatio);
        Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(scaledImage));
        headerPanel.add(logoLabel, BorderLayout.WEST);

        Corso corso = controller.getCorsoAttribute();
        JLabel corsoLabel = new JLabel("Informazioni " + corso.getNome() + "       ", SwingConstants.CENTER);
        corsoLabel.setFont(new Font("Arial", Font.BOLD, 30));
        corsoLabel.setForeground(Color.WHITE);
        headerPanel.add(corsoLabel, BorderLayout.CENTER);
        getContentPane().add(headerPanel, BorderLayout.NORTH);

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

        // PAnwnello dell'info corso
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        infoPanel.setPreferredSize(new Dimension(600, 250));
        GridBagConstraints gbcInfo = new GridBagConstraints();
        gbcInfo.insets = new Insets(5, 10, 5, 10);
        gbcInfo.anchor = GridBagConstraints.WEST;

        String[][] infoData = {
                {"Nome", corso.getNome()},
                {"Categoria", corso.getCategoria()},
                {"Data Inizio", corso.getData_inizio_formato()},
                {"Frequenza", corso.getFrequenza()},
                {"Costo", (corso.getCosto() == null ? 0 : corso.getCosto()) + " €"},
                {"Numero Sessioni", String.valueOf(corso.getNumero_sessioni())},
        };
        for (int i = 0; i < infoData.length; i++) {
            JLabel titleLabel = new JLabel(infoData[i][0] + ":");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 15));
            gbcInfo.gridx = 0;
            gbcInfo.gridy = i;
            infoPanel.add(titleLabel, gbcInfo);

            JLabel valueLabel = new JLabel(infoData[i][1]);
            valueLabel.setFont(new Font("Arial", Font.PLAIN, 15));
            gbcInfo.gridx = 1;
            gbcInfo.gridy = i;
            infoPanel.add(valueLabel, gbcInfo);
        }
        mainPanel.add(infoPanel, BorderLayout.NORTH);

        // Tabella Sessioni
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(new Color(245, 245, 245));
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel sessioniLabel = new JLabel("Sessioni");
        sessioniLabel.setFont(new Font("Arial", Font.BOLD, 18));
        sessioniLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        tablePanel.add(sessioniLabel, BorderLayout.NORTH);

        String[] columnNames = {"TIPO", "LUOGO O LINK", "INIZIO", "FINE"};
        String[][] data = new String[corso.getSessioni().size()][4];
        for (int i = 0; i < corso.getSessioni().size(); i++) {
            if (corso.getSessioni().get(i) instanceof Sessione_online) {
            	Sessione_online sessioneOnline = (Sessione_online) corso.getSessioni().get(i);
                data[i] = new String[]{
                        "ONLINE",
                        sessioneOnline.getLink(),
                        corso.getSessioni().get(i).getOrario_inizio(),
                        corso.getSessioni().get(i).getOrario_fine()
                };
            } else {
            	Sessione_in_presenza sessioneInPresenza = (Sessione_in_presenza) corso.getSessioni().get(i);
                data[i] = new String[]{
                        "IN PRESENZA",
                        sessioneInPresenza.getLuogo(),
                        corso.getSessioni().get(i).getOrario_inizio(),
                        corso.getSessioni().get(i).getOrario_fine()
                };
            }
        }

        sessioniTable = new JTable(data, columnNames) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        sessioniTable.setFont(new Font("Arial", Font.PLAIN, 14));
        sessioniTable.setRowHeight(30);
        sessioniTable.getTableHeader().setReorderingAllowed(false);
        sessioniTable.getTableHeader().setResizingAllowed(false);
        sessioniTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        sessioniTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(sessioniTable);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(tablePanel, BorderLayout.CENTER);

        sessioniTable.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int row = sessioniTable.rowAtPoint(e.getPoint());
                if (row >= 0 &&
                        "IN PRESENZA".equals(sessioniTable.getModel().getValueAt(row, 0))) {
                	sessioniTable.setCursor(new Cursor(Cursor.HAND_CURSOR));
                } else {
                	sessioniTable.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
        });

        apriSessione(null);

        // Pannello bottoni
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        buttonPanel.setBackground(new Color(245, 245, 245));

        JButton addOnlineButton = new JButton("Aggiungi Sessione Online");
        styleButton(addOnlineButton);
        setHandCursor(addOnlineButton);
        addOnlineButton.addActionListener(_ -> controller.apriAggiungiSessioneOnlineDialog());
        buttonPanel.add(addOnlineButton);

        JButton addInPresenzaButton = new JButton("Aggiungi Sessione In Presenza");
        styleButton(addInPresenzaButton);
        setHandCursor(addInPresenzaButton);
        addInPresenzaButton.addActionListener(_ -> controller.apriAggiungiSessioneInPresenzaDialog());
        buttonPanel.add(addInPresenzaButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }
    
    // Apri Sessione selezionata
    private void apriSessione(ActionListener listener) {
    	sessioniTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e) && !checkDoubleClick) {
                    checkDoubleClick = true;
                    int row = sessioniTable.rowAtPoint(e.getPoint());
                    if (row >= 0) {
                        String luogo = (String) sessioniTable.getModel().getValueAt(row, 1);
                        String orarioInizio = (String) sessioniTable.getModel().getValueAt(row, 2);
                        for (Sessione s : controller.getCorsoAttribute().getSessioni()) {
                            if (s instanceof Sessione_in_presenza && ((Sessione_in_presenza)s).getLuogo().equals(luogo) && s.getOrario_inizio().equals(orarioInizio)) {
                                controller.setSessioneAttribute((Sessione_in_presenza) s);
                                dispose();
                                controller.apriRicetteFrame();
                                break;
                            }
                        }
                    }
                    checkDoubleClick = false;
                }
            }
        });
    }
    
    // Stile del bottone
    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(0, 102, 204));
        button.setForeground(Color.WHITE);
        button.setMargin(new Insets(15, 20, 15, 20));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(300, 50));
    }
    
    // Mette il cursore con il dito sul bottone passato come parametro
    private void setHandCursor(JComponent component) {
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                component.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                component.setCursor(Cursor.getDefaultCursor());
            }
        });
    }
}
