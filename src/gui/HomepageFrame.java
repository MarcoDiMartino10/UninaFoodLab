package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

import controller.*;
import db_connection.*;
import dto.*;

public class HomepageFrame extends JFrame {
	
	// Attributi
	private static final long serialVersionUID = 1L;
	private Controller controller;
    private JButton nuovoCorsoButton;
    private JButton reportButton;
    private JComboBox<String> categoryFilter;
    private JTable courseTable;
    private Chef chef;
    private InfoCorsoFrame viewInfoCorso;
    private boolean checkDoubleClick = false;
    
    /*-----------------------------------------------------------------------------------------*/

    // Costruttore
    public HomepageFrame(Controller controller, Chef chef) {
        super("Homepage - UninaFoodLab");
        this.controller = controller;
        this.chef = chef;
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
        ImageIcon logo = new ImageIcon("resources/Logo.png");
        Image originalImage = logo.getImage();
        int originalWidth = originalImage.getWidth(null);
        int originalHeight = originalImage.getHeight(null);
        double aspectRatio = (double) originalWidth / originalHeight;
        int newWidth = 100;
        int newHeight = (int) (newWidth / aspectRatio);
        Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(scaledImage));
        headerPanel.add(logoLabel, BorderLayout.WEST);
        
        // Titolo al centro
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
            		controller.logout();
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
            if (!categoriaUnica.contains(categoria)) {
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
                filtraCorsiPerCategoria(chef, (String) categoryFilter.getSelectedItem());
            }
        });
        setHandCursor(categoryFilter);

        // Tabella dei corsi con colonna per aprire la pagina del corso
        String[] columnNames = {"ID(nascosto)", "CORSO", "CATEGORIA", "DATA DI INIZIO"};
        String[][] data = new String[chef.getCorso().size()][4];
        int i = 0;
	    while (i < chef.getCorso().size()) {
	         data[i] = new String[]{String.valueOf(chef.getCorso().get(i).getID()), chef.getCorso().get(i).getNome(), chef.getCorso().get(i).getCategoria(), chef.getCorso().get(i).getData_inizio().toString()};
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
        
        // Bottone Visualizza Report
        reportButton = new JButton("Visualizza Report");
        styleButton(reportButton);
        buttonPanel.add(reportButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
    
    /*-----------------------------------------------------------------------------------------*/
    
    // Stili bottoni in basso
    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(0, 102, 204));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        button.setPreferredSize(new Dimension(250, 50));
    }

    // ActionListener Aggiungi Corso e Visualizza Report
    public void addNuovoCorsoListener(ActionListener listener) {
        nuovoCorsoButton.addActionListener(listener);
    }
    public void addReportListener(ActionListener listener) {
        reportButton.addActionListener(listener);
    }
    
    // Filtro dei corsi in base alla categoria selezionata
    private void filtraCorsiPerCategoria(Chef chef, String categoria) {
        LinkedList<Corso> corsiFiltrati = new LinkedList<>();

        for (int i = 0; i < chef.getCorso().size(); i++) {
            Corso corso = chef.getCorso().get(i);
            if (categoria.equals("Tutti") || corso.getCategoria().equals(categoria)) {
                corsiFiltrati.add(corso);
            }
        }

        String[][] data = new String[corsiFiltrati.size()][4];
        for (int i = 0; i < corsiFiltrati.size(); i++) {
            Corso c = corsiFiltrati.get(i);
            data[i][0] = String.valueOf(c.getID());
            data[i][1] = c.getNome();
            data[i][2] = c.getCategoria();
            data[i][3] = c.getData_inizio().toString();
        }

        String[] columnNames = {"ID(nascosto)", "CORSO", "CATEGORIA", "DATA DI INIZIO"};
        courseTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
        courseTable.removeColumn(courseTable.getColumnModel().getColumn(0));
    }
    
    // Apri il corso selezionato al click
    public void apriCorso(ActionListener listener) {
        courseTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e) && !checkDoubleClick) {
                	checkDoubleClick = true;
                    int row = courseTable.rowAtPoint(e.getPoint()); 
                    if (row >= 0) {
                        int idCorso = Integer.parseInt((String) courseTable.getModel().getValueAt(row, 0));
                        for (int i = 0; i < chef.getCorso().size(); i++) {
                            if (chef.getCorso().get(i).getID() == idCorso) {
                            	controller.apriInfoCorso(chef.getCorso().get(i));
                                return;
                            }
                        }
                    }
                    checkDoubleClick = false;
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