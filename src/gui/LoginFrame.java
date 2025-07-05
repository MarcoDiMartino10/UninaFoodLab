package gui;

import javax.swing.*;

import controller.*;

import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {
	
	// Attributi
	private static final long serialVersionUID = 1L;
    private JTextField campoEmail;
    private JPasswordField campoPassword;
    private JButton bottoneLogin;
    private Controller controller;
        
/*-----------------------------------------------------------------------------------------*/    
    
    // Costruttore
    public LoginFrame(Controller controller) {
        
    	// Finestra
    	super("Accesso a UninaFoodLab");
    	this.controller = controller;
        setSize(800, 600);
        setMinimumSize(new Dimension(550, 500));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Logo.png")));
        
        // Pannello di sfondo
        JPanel pannelloSfondo = new JPanel() {
        	private static final long serialVersionUID = 1L;
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image sfondo = new ImageIcon(getClass().getResource("/Sfondo.jpg")).getImage();
                g.drawImage(sfondo, 0, 0, getWidth(), getHeight(), this);
            }
        };
        pannelloSfondo.setLayout(new GridBagLayout());
        getContentPane().add(pannelloSfondo);

        // Pannello principale
        JPanel pannelloLogin = new JPanel(new GridBagLayout());
        pannelloLogin.setPreferredSize(new Dimension(450, 400));
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

        // Scritta Email
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;

        JLabel scrittaEmail = new JLabel("Email:");
        scrittaEmail.setFont(new Font("Arial", Font.PLAIN, 14));
        pannelloLogin.add(scrittaEmail, gbc);

        // Campo Email
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 1;
        gbc.gridy = 2;
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
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;

        JLabel scrittaPassword = new JLabel("Password:");
        scrittaPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        pannelloLogin.add(scrittaPassword, gbc);

        // Campo Password
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 1;
        gbc.gridy = 3;
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
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        bottoneLogin = new JButton("Accedi");
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
        	if (controller.getAllInfoChef(campoEmail.getText(), new String(campoPassword.getPassword())) == null) {
				credenzialiErrate();
				return;
			}
        	dispose();
        	new HomepageFrame(controller, this).setVisible(true);
        });
        
     // Scritta cliccabile per la registrazione
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.SOUTH;

        JLabel scrittaRegistrati = new JLabel("Non sei registrato? Clicca qui");
        scrittaRegistrati.setFont(new Font("Arial", Font.PLAIN, 13));
        scrittaRegistrati.setForeground(Color.BLUE);
        scrittaRegistrati.setCursor(new Cursor(Cursor.HAND_CURSOR));

        scrittaRegistrati.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { 
            	dispose();
            	new RegistrazioneFrame(controller, LoginFrame.this).setVisible(true);
            }
        });

        pannelloLogin.add(scrittaRegistrati, gbc);

    }
    
    /*-----------------------------------------------------------------------------------------*/
    
    // Metodo per mostrare un messaggio di errore in caso di credenziali errate
    public void credenzialiErrate() {
    	JOptionPane.showMessageDialog(this, "Email o password non corretti.\n" +(controller.count--)+ " tentativi rimasti.", "Errore", JOptionPane.ERROR_MESSAGE);
    	controller.checkcount();
        campoEmail.requestFocusInWindow();
    }


}
