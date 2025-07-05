package gui;

import javax.swing.*;

import controller.*;

import java.awt.*;
import java.awt.event.*;

public class RegistrazioneFrame extends JFrame {
	
	// Attributi
	private static final long serialVersionUID = 1L;
	private JTextField campoNome;
	private JTextField campoCognome;
    private JTextField campoEmail;
    private JTextField campoTelefono;
    private JTextArea campoBiografia;
    private JPasswordField campoPassword;
    private JButton bottoneLogin;
    
/*-----------------------------------------------------------------------------------------*/    
    
    // Costruttore
    public RegistrazioneFrame(Controller controller, LoginFrame previous) {
        
    	// Finestra
    	super("Registrazione a UninaFoodLab");
        setSize(850, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Logo.png")));
        setMinimumSize(new Dimension(700, 750));
        
        // Pannello di sfondo
        JPanel pannelloSfondo = new JPanel() {
        	private static final long serialVersionUID = 1L;
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image sfondo = new ImageIcon(getClass().getResource("/SfondoLogin.jpg")).getImage();
                g.drawImage(sfondo, 0, 0, getWidth(), getHeight(), this);
            }
        };
        pannelloSfondo.setLayout(new GridBagLayout());
        getContentPane().add(pannelloSfondo);

        // Pannello principale
        JPanel pannelloLogin = new JPanel(new GridBagLayout());
        pannelloLogin.setPreferredSize(new Dimension(600, 650));
        pannelloLogin.setBackground(new Color(255, 255, 255, 200));
        pannelloLogin.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        pannelloSfondo.add(pannelloLogin);

        // Titolo
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel scrittaTitolo = new JLabel("UninaFoodLab", SwingConstants.CENTER);
        scrittaTitolo.setFont(new Font("Arial", Font.BOLD, 30));
        scrittaTitolo.setForeground(new Color(0, 102, 204));
        pannelloLogin.add(scrittaTitolo, gbc);

        // Logo
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        ImageIcon logo = new ImageIcon("resources/Logo.png");
        Image originalLogo = logo.getImage();
        int larghezza = originalLogo.getWidth(null);
        int altezza = originalLogo.getHeight(null);
        double rapporto = (double) larghezza / altezza;
        int newLarghezza = 100;
        int newAltezza = (int) (newLarghezza / rapporto);
        Image newLogo = originalLogo.getScaledInstance(newLarghezza, newAltezza, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(newLogo));
        pannelloLogin.add(logoLabel, gbc);
        
        // Scritta Nome
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;

        JLabel scrittaNome = new JLabel("Nome:");
        scrittaNome.setFont(new Font("Arial", Font.PLAIN, 14));
        pannelloLogin.add(scrittaNome, gbc);

        // Campo Nome
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        
        campoNome = new JTextField(20);
        campoNome.setFont(new Font("Arial", Font.PLAIN, 14));
        campoNome.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        campoNome.addActionListener(_ -> campoCognome.requestFocusInWindow());
        pannelloLogin.add(campoNome, gbc);
        
        // Scritta Cognome
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;

        JLabel scrittaCognome = new JLabel("Cognome:");
        scrittaCognome.setFont(new Font("Arial", Font.PLAIN, 14));
        pannelloLogin.add(scrittaCognome, gbc);

        // Campo Cognome
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        
        campoCognome = new JTextField(20);
        campoCognome.setFont(new Font("Arial", Font.PLAIN, 14));
        campoCognome.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        campoCognome.addActionListener(_ -> campoTelefono.requestFocusInWindow());
        pannelloLogin.add(campoCognome, gbc);
        
        // Scritta Numero_telefono
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;

        JLabel scrittaTelefono = new JLabel("Numero di telefono:");
        scrittaTelefono.setFont(new Font("Arial", Font.PLAIN, 14));
        pannelloLogin.add(scrittaTelefono, gbc);

        // Campo numero_telefono
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        
        campoTelefono = new JTextField(20);
        campoTelefono.setFont(new Font("Arial", Font.PLAIN, 14));
        campoTelefono.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        campoTelefono.addActionListener(_ -> campoBiografia.requestFocusInWindow());
        pannelloLogin.add(campoTelefono, gbc);
        
        // Scritta Biografia
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;

        JLabel scrittaBiografia= new JLabel("Biografia:");
        scrittaBiografia.setFont(new Font("Arial", Font.PLAIN, 14));
        pannelloLogin.add(scrittaBiografia, gbc);

        // Biografia - TextArea
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        campoBiografia = new JTextArea(2, 20);
        campoBiografia.setLineWrap(true);
        campoBiografia.setWrapStyleWord(true);
        campoBiografia.setFont(new Font("Arial", Font.PLAIN, 14));
        campoBiografia.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        JScrollPane scrollPaneBiografia = new JScrollPane(campoBiografia);
        pannelloLogin.add(scrollPaneBiografia, gbc);
        
        // Scritta Email
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.EAST;

        JLabel scrittaEmail = new JLabel("Email:");
        scrittaEmail.setFont(new Font("Arial", Font.PLAIN, 14));
        pannelloLogin.add(scrittaEmail, gbc);

        // Campo Email
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        campoEmail = new JTextField(20);
        campoEmail.setFont(new Font("Arial", Font.PLAIN, 14));
        campoEmail.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        campoEmail.addActionListener(_ -> campoPassword.requestFocusInWindow());
        pannelloLogin.add(campoEmail, gbc);

        // Scritta Password
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.EAST;

        JLabel scrittaPassword = new JLabel("Password:");
        scrittaPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        pannelloLogin.add(scrittaPassword, gbc);

        // Campo Password
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        campoPassword = new JPasswordField(20);
        campoPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        campoPassword.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        campoPassword.addActionListener(_ -> bottoneLogin.doClick());
        pannelloLogin.add(campoPassword, gbc);

        // Bottone Login
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        bottoneLogin = new JButton("Registrati");
        bottoneLogin.setFont(new Font("Arial", Font.BOLD, 14));
        bottoneLogin.setBackground(new Color(0, 102, 204));
        bottoneLogin.setForeground(Color.WHITE);
        bottoneLogin.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        bottoneLogin.setFocusPainted(false);
        pannelloLogin.add(bottoneLogin, gbc);
        
        // Cursore mano quando si passa sopra il bottone
        bottoneLogin.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e) {
                bottoneLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                bottoneLogin.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        
        // Listener per il bottone Login
        bottoneLogin.addActionListener(_ -> {
        	if(campoNome.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Il campo nome non può essere vuoto.", "Errore", JOptionPane.ERROR_MESSAGE);
				campoNome.requestFocusInWindow();
				return;
			}
        	
        	if (!campoNome.getText().matches("[a-zA-ZàèéìòùÀÈÉÌÒÙ' ]+") || campoNome.getText().trim().isEmpty()) {
        	    JOptionPane.showMessageDialog(this, "Il campo nome deve contenere solo lettere.", "Errore", JOptionPane.ERROR_MESSAGE);
        	    campoNome.requestFocusInWindow();
        	    return;
        	}
        	
        	if(campoNome.getText().length() > 25) {
        		JOptionPane.showMessageDialog(this, "Il campo nome non può superare i 25 caratteri.", "Errore", JOptionPane.ERROR_MESSAGE);
				campoNome.requestFocusInWindow();
				return;
        	}
        	
        	if(campoCognome.getText().isEmpty()) {
        		JOptionPane.showMessageDialog(this, "Il campo cognome non può essere vuoto.", "Errore", JOptionPane.ERROR_MESSAGE);
				campoCognome.requestFocusInWindow();
				return;
        	}
        	
        	if (!campoCognome.getText().matches("[a-zA-ZàèéìòùÀÈÉÌÒÙ' ]+") || campoCognome.getText().trim().isEmpty()) {
        	    JOptionPane.showMessageDialog(this, "Il campo cognome deve contenere solo lettere.", "Errore", JOptionPane.ERROR_MESSAGE);
        	    campoCognome.requestFocusInWindow();
        	    return;
        	}
        	
        	if(campoCognome.getText().length() > 25) {
        		JOptionPane.showMessageDialog(this, "Il campo cognome non può superare i 25 caratteri.", "Errore", JOptionPane.ERROR_MESSAGE);
				campoCognome.requestFocusInWindow();
				return;
        	}
        	
        	if(campoTelefono.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Il campo numero di telefono non può essere vuoto.", "Errore", JOptionPane.ERROR_MESSAGE);
				campoTelefono.requestFocusInWindow();
				return;
			}
        	
        	if(!campoTelefono.getText().matches("\\d+")) {
        		JOptionPane.showMessageDialog(this, "Il campo numero di telefono deve contenere solo numeri.", "Errore", JOptionPane.ERROR_MESSAGE);
				campoTelefono.requestFocusInWindow();
				return;
        	}
        	
        	if(campoTelefono.getText().length() < 8 || campoTelefono.getText().length() > 15) {
				JOptionPane.showMessageDialog(this, "Il numero di telefono deve essere compreso tra 8 e 15 cifre.", "Errore", JOptionPane.ERROR_MESSAGE);
				campoTelefono.requestFocusInWindow();
				return;
			}
        	
			if(campoBiografia.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Il campo biografia non può essere vuoto.", "Errore", JOptionPane.ERROR_MESSAGE);
				campoBiografia.requestFocusInWindow();
				return;
			}
			
			if(campoBiografia.getText().length() > 500) {
				JOptionPane.showMessageDialog(this, "La biografia non può superare i 500 caratteri.", "Errore", JOptionPane.ERROR_MESSAGE);
				campoBiografia.requestFocusInWindow();
				return;
			}
						
			if(campoEmail.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Il campo email non può essere vuoto.", "Errore", JOptionPane.ERROR_MESSAGE);
				campoEmail.requestFocusInWindow();
				return;
			}
			
			if(!campoEmail.getText().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
				JOptionPane.showMessageDialog(this, "L'email inserita non è valida.", "Errore", JOptionPane.ERROR_MESSAGE);
				campoEmail.requestFocusInWindow();
				return;
			}
			
			if(campoEmail.getText().length() > 100) {
				JOptionPane.showMessageDialog(this, "L'email non può superare i 100 caratteri.", "Errore", JOptionPane.ERROR_MESSAGE);
				campoEmail.requestFocusInWindow();
				return;
			}
			
			if(campoPassword.getPassword().length == 0) {
				JOptionPane.showMessageDialog(this, "Il campo password non può essere vuoto.", "Errore", JOptionPane.ERROR_MESSAGE);
				campoPassword.requestFocusInWindow();
				return;
			}
			
			if(campoPassword.getPassword().length < 5 || campoPassword.getPassword().length > 20 || !new String(campoPassword.getPassword()).matches(".*[a-zA-Z].*") || !new String(campoPassword.getPassword()).matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
				JOptionPane.showMessageDialog(this, "La password deve avere almeno 5 caratteri alfanumerici.\nNon più di 20 caratteri.\nAlmeno una lettera maisucola.\nAlmeno un carattere speciale( es: @,!,/)", "Errore", JOptionPane.ERROR_MESSAGE);
				campoPassword.requestFocusInWindow();
				return;
			}
		
        if(controller.saveChef(campoNome.getText(), campoCognome.getText(), campoEmail.getText().toLowerCase(), new String(campoPassword.getPassword()), campoTelefono.getText(), campoBiografia.getText())) {
			JOptionPane.showMessageDialog(this, "Registrazione avvenuta con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
			dispose();
			new HomepageFrame(controller, this).setVisible(true);
		} else {
			JOptionPane.showMessageDialog(this, "Utente già registrato.", "Errore", JOptionPane.ERROR_MESSAGE);
			return;
		}
        
        });
        
        // Scritta cliccabile per l'accesso
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.SOUTH;

        JLabel scrittaLogin = new JLabel("Sei già registrato? Clicca qui");
        scrittaLogin.setFont(new Font("Arial", Font.PLAIN, 13));
        scrittaLogin.setForeground(Color.BLUE);
        scrittaLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));

        scrittaLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	dispose();
            	previous.setVisible(true);
            }
        });

        pannelloLogin.add(scrittaLogin, gbc);

    }

}
