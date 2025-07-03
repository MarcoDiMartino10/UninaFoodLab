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
	private JTable courseTable;
	private boolean checkDoubleClick = false;
	
    /*-----------------------------------------------------------------------------------------*/

	// Costruttore
    public InfoCorsoFrame(Controller controller) {
    	
    	// Finestra
        super("Informazioni corso - UninaFoodLab");
        this.controller = controller;
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(800, 600));
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Logo.png")));

        // Pannello header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(0, 102, 204, 180));
        headerPanel.setPreferredSize(new Dimension(1000, 100));
        
        // Logo
        ImageIcon logo = new ImageIcon(getClass().getResource("/Logo.png"));
        Image originalImage = logo.getImage();
        int originalWidth = originalImage.getWidth(null);
        int originalHeight = originalImage.getHeight(null);
        double aspectRatio = (double) originalWidth / originalHeight;
        int newWidth = 100;
        int newHeight = (int) (newWidth / aspectRatio);
        Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(scaledImage));
        headerPanel.add(logoLabel, BorderLayout.WEST);

        // Titolo header
        Corso corso = controller.getCorso();
        String nomeCorso = "Informazioni " + corso.getNome() + "       ";
        JLabel corsoLabel = new JLabel(nomeCorso, SwingConstants.CENTER);
        corsoLabel.setFont(new Font("Arial", Font.BOLD, 30));
        corsoLabel.setForeground(Color.WHITE);
        headerPanel.add(corsoLabel, BorderLayout.CENTER);
        getContentPane().add(headerPanel, BorderLayout.NORTH);
        
        // Tasto back
        ImageIcon icon = new ImageIcon(getClass().getResource("/Back.png"));
        Image img = icon.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(img);
        JLabel Back = new JLabel(scaledIcon);
        headerPanel.add(Back, BorderLayout.EAST);
        Back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	if (SwingUtilities.isLeftMouseButton(e)) {
            		controller.chiudiInfoCorso();
            	}
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                Back.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });

        // Pannello principale
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        // Pannello informazioni corso
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
            {"Data Inizio", corso.getData_inizio().toString()},
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

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        mainPanel.add(infoPanel, gbc);

        // Pannello tabella sessioni
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(new Color(245, 245, 245));
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        // Scritta Sessioni
        JLabel sessioniLabel = new JLabel("Sessioni");
        sessioniLabel.setFont(new Font("Arial", Font.BOLD, 18));
        sessioniLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        tablePanel.add(sessioniLabel, BorderLayout.NORTH);

        // Tabella sessioni
        String[] columnNames = {"TIPO", "LUOGO O LINK", "INIZIO", "FINE"};
        String[][] data = new String[corso.getSessioni().size()][4];
        for (int i = 0; i < corso.getSessioni().size(); i++) {
            if (corso.getSessioni().get(i) instanceof Sessione_online) {
                data[i] = new String[]{"ONLINE" , corso.getSessioni().get(i).getLink(), corso.getSessioni().get(i).getOrario_inizio(), corso.getSessioni().get(i).getOrario_fine()};
            } else {
                data[i] = new String[]{"IN PRESENZA", corso.getSessioni().get(i).getLuogo(), corso.getSessioni().get(i).getOrario_inizio(), corso.getSessioni().get(i).getOrario_fine()};
            }
        }
        courseTable = new JTable(data, columnNames) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        courseTable.setFont(new Font("Arial", Font.PLAIN, 14));
        courseTable.setRowHeight(30);
        courseTable.getTableHeader().setReorderingAllowed(false);
        courseTable.getTableHeader().setResizingAllowed(false);
        courseTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        
        JScrollPane scrollPane = new JScrollPane(courseTable);
        scrollPane.setPreferredSize(new Dimension(600, 300));
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(tablePanel, gbc);

        courseTable.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int row = courseTable.rowAtPoint(e.getPoint());
                int column = 0;
                if (row >= 0 &&
                    "IN PRESENZA".equals(courseTable.getModel().getValueAt(row, column))) {
                    courseTable.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
                } else {
                    courseTable.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); 
                }
            }
        });

        
        apriSessione(null);
        
     // Pannello bottoni aggiunta
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        buttonPanel.setBackground(new Color(245, 245, 245));

        // Bottone Aggiungi Sessione Online
        JButton addOnlineButton = new JButton("Aggiungi Sessione Online");
        styleButton(addOnlineButton);
        addOnlineButton.addActionListener(_ -> controller.apriAggiungiSessioneOnlineDialog());
        buttonPanel.add(addOnlineButton);

        // Bottone Aggiungi Sessione In Presenza
        JButton addInPresenzaButton = new JButton("Aggiungi Sessione In Presenza");
        styleButton(addInPresenzaButton);
        addInPresenzaButton.addActionListener(_ -> controller.apriAggiungiSessioneInPresenzaDialog());
        buttonPanel.add(addInPresenzaButton);

        // Aggiungi pannello bottoni al mainPanel
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(buttonPanel, gbc);

    }
    
    /*-----------------------------------------------------------------------------------------*/
    
    // Metodo per aprire la sessione in presenza selezionata
    public void apriSessione(ActionListener listener) {
        courseTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e) && !checkDoubleClick) {
                    checkDoubleClick = true;
                    int row = courseTable.rowAtPoint(e.getPoint());
                    if (row >= 0) {
                        String luogo = (String) courseTable.getModel().getValueAt(row, 1);
                        String orarioInizio = (String) courseTable.getModel().getValueAt(row, 2);
                        controller.sessioneSelezionata(luogo, orarioInizio);
                    }
                    checkDoubleClick = false;
                }
            }
        });
    }
    
 // Stili bottoni in basso
    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(0, 102, 204));
        button.setForeground(Color.WHITE);
        button.setMargin(new Insets(15, 20, 15, 20));  // padding interno più naturale
        button.setPreferredSize(null);  // togli dimensioni fisse, lascia che si adattino
        button.setFocusPainted(false);
    }





    
}