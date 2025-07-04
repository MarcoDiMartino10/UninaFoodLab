package gui;

import java.util.Calendar;

import java.awt.*;
import java.sql.Timestamp;
import java.util.*;
import javax.swing.*;

import controller.*;
import dto.*;

public class AggiungiSessioneInPresenzaDialog extends JDialog {

    // Attributi
    private static final long serialVersionUID = 1L;
    Controller controller;

    public AggiungiSessioneInPresenzaDialog(Controller controller) {
    	super((InfoCorsoFrame) null, "Aggiungi Sessione in Presenza", true);
        this.controller = controller;
        setSize(550, 350);
        setLocationRelativeTo(this);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        getRootPane().setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204, 180), 2));
        getRootPane().setBackground(Color.WHITE);
        setBackground(Color.WHITE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Header del dialog
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(0, 102, 204, 180));
        headerPanel.setPreferredSize(new Dimension(450, 50));
        JLabel titleLabel = new JLabel("Aggiungi Nuova Sessione In Presenza", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Creo il pannello centrale
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);

        // Pannello input
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Luogo
        JLabel luogoLabel = new JLabel("Luogo:");
        luogoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JTextField luogoField = new JTextField(20);
        luogoField.setFont(new Font("Arial", Font.PLAIN, 14));
        luogoField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        inputPanel.add(luogoLabel, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(luogoField, gbc);

     // Label e spinner per Data e Orario Inizio
        JLabel dataOrarioInizioLabel = new JLabel("Data e orario inizio sessione:");
        dataOrarioInizioLabel.setFont(new Font("Arial", Font.BOLD, 14));

        SpinnerDateModel inizioModel = new SpinnerDateModel(new Date(), null, null, Calendar.MINUTE);
        JSpinner orarioInizioSpinner = new JSpinner(inizioModel);
        JSpinner.DateEditor inizioEditor = new JSpinner.DateEditor(orarioInizioSpinner, "dd/MM/yyyy HH:mm");
        orarioInizioSpinner.setEditor(inizioEditor);
        orarioInizioSpinner.getEditor().getComponent(0).setFont(new Font("Segoe UI", Font.PLAIN, 14));
        orarioInizioSpinner.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(100, 150, 250), 2),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        orarioInizioSpinner.setBackground(new Color(240, 248, 255));

        // Posizionamento inizio
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        inputPanel.add(dataOrarioInizioLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(orarioInizioSpinner, gbc);

        // Label e spinner per Data e Orario Fine
        JLabel dataOrarioFineLabel = new JLabel("Data e orario fine sessione:");
        dataOrarioFineLabel.setFont(new Font("Arial", Font.BOLD, 14));

        SpinnerDateModel fineModel = new SpinnerDateModel(new Date(), null, null, Calendar.MINUTE);
        JSpinner orarioFineSpinner = new JSpinner(fineModel);
        JSpinner.DateEditor fineEditor = new JSpinner.DateEditor(orarioFineSpinner, "dd/MM/yyyy HH:mm");
        orarioFineSpinner.setEditor(fineEditor);
        orarioFineSpinner.getEditor().getComponent(0).setFont(new Font("Segoe UI", Font.PLAIN, 14));
        orarioFineSpinner.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(100, 150, 250), 2),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        orarioFineSpinner.setBackground(new Color(240, 248, 255));

        // Posizionamento fine
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        inputPanel.add(dataOrarioFineLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(orarioFineSpinner, gbc);
        
     // Max_posti
        JLabel maxPostiLabel = new JLabel("Numero massimo di posti:");
        maxPostiLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JTextField maxPostiField = new JTextField(20);
        maxPostiField.setFont(new Font("Arial", Font.PLAIN, 14));
        maxPostiField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        inputPanel.add(maxPostiLabel, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(maxPostiField, gbc);

        // Pannello bottoni
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 4));
        buttonPanel.setBackground(Color.WHITE);

        // Pulsante annulla
        JButton annullaButton = new JButton("Annulla");
        styleButton(annullaButton);
        annullaButton.setBackground(Color.LIGHT_GRAY);
        annullaButton.setForeground(Color.BLACK);
        setHandCursor(annullaButton);
        buttonPanel.add(annullaButton);

        // Pulsante invia
        JButton inviaButton = new JButton("Invia");
        styleButton(inviaButton);
        setHandCursor(inviaButton);
        buttonPanel.add(inviaButton);

        centerPanel.add(inputPanel, BorderLayout.NORTH);
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Listener annulla
        annullaButton.addActionListener(_ -> controller.chiudiAggiungiSessioneInPresenzaDialog());

        // Listener invia
        inviaButton.addActionListener(_ -> {
            String luogo = luogoField.getText().trim();

            // Controllo link
            if (luogo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Il campo luogo non può essere vuoto.", "Errore", JOptionPane.ERROR_MESSAGE);
                luogoField.requestFocus();
                return;
            }

            // check date e orari
            Date inizioDate = (Date) orarioInizioSpinner.getValue();
            Date fineDate = (Date) orarioFineSpinner.getValue();

            if (inizioDate == null) {
                JOptionPane.showMessageDialog(this, "Seleziona una data e orario di inizio validi.", "Errore", JOptionPane.ERROR_MESSAGE);
                orarioInizioSpinner.requestFocus();
                return;
            }
            if (fineDate == null) {
                JOptionPane.showMessageDialog(this, "Seleziona una data e orario di fine validi.", "Errore", JOptionPane.ERROR_MESSAGE);
                orarioFineSpinner.requestFocus();
                return;
            }

         // Cast diretto dai JSpinner
            Timestamp inizio = new Timestamp(((Date) orarioInizioSpinner.getValue()).getTime());
            Timestamp fine = new Timestamp(((Date) orarioFineSpinner.getValue()).getTime());
            Timestamp now = new Timestamp(System.currentTimeMillis());

            // Controllo: inizio non prima di ora
            if (inizio.before(now)) {
                JOptionPane.showMessageDialog(this, "La data e orario di inizio non possono essere anteriori all'attuale.", "Errore", JOptionPane.ERROR_MESSAGE);
                orarioInizioSpinner.requestFocus();
                return;
            }

            // Controllo: fine non prima di ora
            if (fine.before(now)) {
                JOptionPane.showMessageDialog(this, "La data e orario di fine non possono essere anteriori all'attuale.", "Errore", JOptionPane.ERROR_MESSAGE);
                orarioFineSpinner.requestFocus();
                return;
            }

            // Controllo: inizio < fine
            if (!fine.after(inizio)) {
                JOptionPane.showMessageDialog(this, "La data e orario di fine devono essere successivi a quelli di inizio.", "Errore", JOptionPane.ERROR_MESSAGE);
                orarioFineSpinner.requestFocus();
                return;
            }
            
            if (fine.getTime() - inizio.getTime() < 15 * 60 * 1000) {
				JOptionPane.showMessageDialog(this, "La sessione online deve durare almeno 15 minuti.", "Errore", JOptionPane.ERROR_MESSAGE);
				orarioFineSpinner.requestFocus();
				return;
			}
            if (fine.getTime() - inizio.getTime() > 5 * 60 * 60 * 1000) {
            	JOptionPane.showMessageDialog(this, "La sessione online non può durare più di 5 ore.", "Errore", JOptionPane.ERROR_MESSAGE);
            	orarioFineSpinner.requestFocus();
            	return;
            }
            
            int maxPosti;
            try {
            	int max_posti = Integer.parseInt(maxPostiField.getText().trim());
            	if (max_posti <= 1) {
                    JOptionPane.showMessageDialog(this, "Il numero massimo di posti deve essere maggiore di 1.", "Errore", JOptionPane.ERROR_MESSAGE);
                    maxPostiField.requestFocus();
                    return;
                }
            	maxPosti = max_posti;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Inserisci un numero massimo di posti valido.", "Errore", JOptionPane.ERROR_MESSAGE);
                maxPostiField.requestFocus();
                return;
            }

            for (Sessione sessione : controller.getCorso().getSessioni()) {
				if (sessione instanceof Sessione_in_presenza) {
					if (inizio.before(sessione.getOrario_fine_timestamp()) && fine.after(sessione.getOrario_inizio_timestamp())) {
						JOptionPane.showMessageDialog(this, "In questo orario in questo luogo si sta svolgendo un'altra sessione.", "Errore", JOptionPane.ERROR_MESSAGE);
						orarioInizioSpinner.requestFocus();
						return;
					}
				}
			}
            controller.saveSessioneInPresenzaToDatabase(luogo, inizio, fine, maxPosti);
        });

        setContentPane(mainPanel);
    }
    
    // Metodo per lo stile dei bottoni
    private void styleButton(JButton button) {
	    button.setFont(new Font("Arial", Font.BOLD, 16));
	    button.setBackground(new Color(0, 102, 204));
	    button.setForeground(Color.WHITE);
	    button.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
	    button.setPreferredSize(new Dimension(230, 50));
	}
    
    // Metodo per impostare il cursore a mano
    private void setHandCursor(JButton button) {
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

}