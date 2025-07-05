package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import dto.*;
import controller.*;

public class RicetteFrame extends JFrame {
	
	// Attributi
	private static final long serialVersionUID = 1L;
    private JButton aggiungiRicettaButton;
    private JTable ricetteTable;
    
    private JFrame previous;
    
    /*-----------------------------------------------------------------------------------------*/

    // Costruttore
    public RicetteFrame(Controller controller, JFrame previous) {
        super("Homepage - UninaFoodLab");
        this.previous = previous;
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
        
        // Titolo header
        String testoTitolo = "Ricette di " + controller.getCorsoAttribute().getNome() + "           ";
        JLabel titleLabel = new JLabel(testoTitolo, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        getContentPane().add(headerPanel, BorderLayout.NORTH);
        
        // Icona per tornare indietro
        ImageIcon icon = new ImageIcon(getClass().getResource("/Back.png"));
        Image img = icon.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(img);
        JLabel Back = new JLabel(scaledIcon);
        headerPanel.add(Back, BorderLayout.EAST);
        Back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	if (SwingUtilities.isLeftMouseButton(e)) {
            		dispose();
            		previous.setVisible(true);
            	}
            }
        });
        setHandCursor(Back);

        // Pannello centrale
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        // Tabella delle ricette della sessione
        Sessione_in_presenza sessione = controller.getSessioneAttribute();
        String[] columnNames = {"ID(nascosto)", "NOME RICETTA", "INGREDIENTI"};
        String[][] data = new String[sessione.getRicette().size()][3];
        int i = 0;
	    while (i < sessione.getRicette().size()) {
	         data[i] = new String[3];
	         data[i][0] = String.valueOf(sessione.getRicette().get(i).getID());
	         data[i][1] = sessione.getRicette().get(i).getNome();
	         String nomeIngredienti = "<html>";
	         for (Ingrediente ingrediente : sessione.getRicette().get(i).getIngredienti()) {
	        	 nomeIngredienti += ingrediente.getNome() + " - (" + (ingrediente.getQuantità() == null ? "" : ingrediente.getQuantità() + " ") + ingrediente.getUnità_di_misura() + ")" + "<br>";
	         }   
	         data[i][2] = nomeIngredienti;	         
	         i++;
	    }
	    
        ricetteTable = new JTable(data, columnNames) {
        	private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        ricetteTable.removeColumn(ricetteTable.getColumnModel().getColumn(0));

        ricetteTable.setFont(new Font("Arial", Font.PLAIN, 14));
        ricetteTable.setRowHeight(35);
        ricetteTable.getTableHeader().setReorderingAllowed(false);
        ricetteTable.getTableHeader().setResizingAllowed(false);
        
        for (i = 0; i < ricetteTable.getRowCount(); i++) {
        	int ingredientiCount = sessione.getRicette().get(i).getIngredienti().size();
        	if (ingredientiCount == 1) {
        		ricetteTable.setRowHeight(i, 35);
			} else {
				ricetteTable.setRowHeight(i, ingredientiCount*20);
			}
		}

        JScrollPane scrollPane = new JScrollPane(ricetteTable);
        scrollPane.setPreferredSize(new Dimension(800, 350));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Pannello inferiore per il pulsante
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(Color.WHITE);

        aggiungiRicettaButton = new JButton("Aggiungi Ricetta");
        styleButton(aggiungiRicettaButton);
        buttonPanel.add(aggiungiRicettaButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        setHandCursor(aggiungiRicettaButton);
        aggiungiRicettaButton.addActionListener(_ -> new AggiungiRicettaDialog(controller, false, this).setVisible(true));
    }
    
    /*-----------------------------------------------------------------------------------------*/
    
    // Cursore a mano quando si passa sopra un bottone
    private void setHandCursor(JComponent component) {
        component.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                component.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            @Override public void mouseExited(MouseEvent e) {
                component.setCursor(Cursor.getDefaultCursor());
            }
        });
    }
    
    // Stile bottone in basso
    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(0, 102, 204));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        button.setPreferredSize(new Dimension(250, 50));
    }
    
    public JFrame getChiamante() {
        return previous;
    }
    
    

}