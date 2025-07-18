package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

import controller.*;
import dto.*;

public class HomepageFrame extends JFrame {
	
	// Attributi
	private static final long serialVersionUID = 1L;
	private Controller controller;
    private JButton nuovoCorsoButton;
    private JButton reportButton;
    private JComboBox<String> categoryFilter;
    private JTable courseTable;
    private boolean checkDoubleClick = false;
    
    /*-----------------------------------------------------------------------------------------*/

    // Costruttore
    public HomepageFrame(Controller controller) {
        super("Homepage - UninaFoodLab");
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
        
        // Logo a sinistra
        ImageIcon logo = new ImageIcon(getClass().getResource("/Logo.png"));
        Image originalImage = logo.getImage();
        double aspectRatio = (double) originalImage.getWidth(null) / originalImage.getHeight(null);
        int newWidth = 100;
        int newHeight = (int) (newWidth / aspectRatio);
        Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(scaledImage));
        headerPanel.add(logoLabel, BorderLayout.WEST);
        
        // Titolo al centro
        Chef chef = controller.getChefAttribute();
        String testoTitolo = "Benvenuto Chef " + chef.getNome() + " " + chef.getCognome() + "           ";
        JLabel titleLabel = new JLabel(testoTitolo, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        getContentPane().add(headerPanel, BorderLayout.NORTH);
        
        // Icona di logout a destra
        ImageIcon icon = new ImageIcon(getClass().getResource("/Logout.png"));
        Image img = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(img);
        JLabel Logout = new JLabel(scaledIcon);
        headerPanel.add(Logout, BorderLayout.EAST);
        Logout.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	if (SwingUtilities.isLeftMouseButton(e)) {
            		dispose();
            		controller.apriLoginFrame();
            	}
            }
        });
        setHandCursor(Logout);

        // Pannello centrale
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        // Pannello superiore per il filtro e scritta "I tuoi corsi"
        JPanel filterPanel = new JPanel(new BorderLayout());
        filterPanel.setBackground(Color.WHITE);
        JLabel corsiLabel = new JLabel("I tuoi corsi");
        corsiLabel.setFont(new Font("Arial", Font.BOLD, 20));
        filterPanel.add(corsiLabel, BorderLayout.WEST);
        JPanel categoryPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        categoryPanel.setBackground(Color.WHITE);
        
        // Categorie senza ripetizioni per il filtro
        LinkedList<String> categoriaUnica = new LinkedList<>();
        for (int i = 0; i < chef.getCorso().size(); i++) {
            String categoria = chef.getCorso().get(i).getCategoria();
            if (!categoriaUnica.contains(categoria.toLowerCase())) {
				categoria = categoria.toLowerCase();
            	categoriaUnica.add(categoria);
            }
        }
        String[] categorie = new String[categoriaUnica.size() + 1];
        categorie[0] = "Tutti";
        for (int i = 0; i < categoriaUnica.size(); i++) {
            categorie[i + 1] = categoriaUnica.get(i);
        }
        
        // ComboBox per il filtro delle categorie
        categoryFilter = new JComboBox<>(categorie);
        categoryFilter.setFont(new Font("Arial", Font.PLAIN, 14));
        categoryFilter.setPreferredSize(new Dimension(200, 30));
        categoryPanel.add(new JLabel("Filtra per categoria: "));
        categoryPanel.add(categoryFilter);
        filterPanel.add(categoryPanel, BorderLayout.EAST);
        mainPanel.add(filterPanel, BorderLayout.NORTH);
        
        categoryFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filtraCorsiPerCategoria((String) categoryFilter.getSelectedItem());
            }
        });
        setHandCursor(categoryFilter);

        // Tabella dei corsi con colonna per aprire la pagina del corso
        String[] columnNames = {"ID(nascosto)", "CORSO", "CATEGORIA", "DATA DI INIZIO"};
        String[][] data = new String[chef.getCorso().size()][4];
        int i = 0;
	    while (i < chef.getCorso().size()) {
	         data[i] = new String[]{String.valueOf(chef.getCorso().get(i).getID()), chef.getCorso().get(i).getNome(), chef.getCorso().get(i).getCategoria(), chef.getCorso().get(i).getData_inizio_formato()};
	         i++;
	    }
	    courseTable = new JTable(data, columnNames) {
        	private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        courseTable.removeColumn(courseTable.getColumnModel().getColumn(0));
        courseTable.setFont(new Font("Arial", Font.PLAIN, 14));
        courseTable.setRowHeight(35);
        courseTable.getTableHeader().setReorderingAllowed(false);
        courseTable.getTableHeader().setResizingAllowed(false);
        
        JScrollPane scrollPane = new JScrollPane(courseTable);
        scrollPane.setPreferredSize(new Dimension(800, 350));
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        setHandCursor(courseTable);
        
        apriCorso(null);

        // Pannello inferiore per i pulsanti
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(Color.WHITE);
        
        // Bottone Aggiungi Corso
        nuovoCorsoButton = new JButton("Aggiungi Corso");
        styleButton(nuovoCorsoButton);
        buttonPanel.add(nuovoCorsoButton);
        setHandCursor(nuovoCorsoButton);
        nuovoCorsoButton.addActionListener(_ -> {
        	controller.apriAggiungiCorsoDialog();
        });
        
        // Bottone Visualizza Report
        reportButton = new JButton("Visualizza Report");
        styleButton(reportButton);
        buttonPanel.add(reportButton);
        setHandCursor(reportButton);
        reportButton.addActionListener(_ -> {
        	dispose();
        	controller.apriReportMensileFrame();
        });

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }
    
    /*-----------------------------------------------------------------------------------------*/
    
    // Stili bottoni in basso
    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(0, 102, 204));
        button.setForeground(Color.WHITE);
        button.setMargin(new Insets(15, 20, 15, 20));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(300, 50));
    }
    
    // Filtra i corsi in base alla categoria selezionata
    private void filtraCorsiPerCategoria(String categoria) {
        LinkedList<Corso> corsiFiltrati = controller.getCorsiFiltratiPerCategoria(categoria);

        String[][] data = new String[corsiFiltrati.size()][4];
        for (int i = 0; i < corsiFiltrati.size(); i++) {
            Corso c = corsiFiltrati.get(i);
            data[i][0] = String.valueOf(c.getID());
            data[i][1] = c.getNome();
            data[i][2] = c.getCategoria();
            data[i][3] = c.getData_inizio_formato();
        }

        String[] columnNames = {"ID(nascosto)", "CORSO", "CATEGORIA", "DATA DI INIZIO"};
        courseTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
        courseTable.removeColumn(courseTable.getColumnModel().getColumn(0));
    }
    
    // Apri il corso selezionato al click
    private void apriCorso(ActionListener listener) {
        courseTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e) && !checkDoubleClick) {
                	checkDoubleClick = true;
                    int row = courseTable.rowAtPoint(e.getPoint()); 
                    if (row >= 0) {
                        int idCorso = Integer.parseInt((String) courseTable.getModel().getValueAt(row, 0));
                        LinkedList<Corso> corsi = controller.getChefAttribute().getCorso();
                        for (int i = 0; i < corsi.size(); i++) {
                            if (corsi.get(i).getID() == idCorso) {
                            	controller.setCorsoAttribute(corsi.get(i));
                            	dispose();
                            	controller.apriInfoCorsoFrame();
                            	checkDoubleClick = false;
                                return;
                            }
                        }
                    }
                }
            }
        });
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