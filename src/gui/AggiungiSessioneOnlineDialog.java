package gui;

import java.util.Calendar;

import java.awt.*;
import java.sql.Timestamp;
import java.util.*;
import javax.swing.*;

import controller.*;
import dto.*;

public class AggiungiSessioneOnlineDialog extends JDialog {

    // Attributi
    private static final long serialVersionUID = 1L;

    // Costruttore
    public AggiungiSessioneOnlineDialog(Controller controller) {
    	
    	super((InfoCorsoFrame) null, "Aggiungi Sessione Online", true);
        setSize(550, 300);
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
        JLabel titleLabel = new JLabel("Aggiungi Nuova Sessione Online", SwingConstants.CENTER);
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

        // Link
        JLabel linkLabel = new JLabel("Link:");
        linkLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JTextField linkField = new JTextField(20);
        linkField.setFont(new Font("Arial", Font.PLAIN, 14));
        linkField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        inputPanel.add(linkLabel, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(linkField, gbc);

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

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        inputPanel.add(dataOrarioFineLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(orarioFineSpinner, gbc);

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
        
        linkField.addActionListener(_ -> inviaButton.doClick());

        // Listener annulla
        annullaButton.addActionListener(_ -> dispose());

        // Listener invia
        inviaButton.addActionListener(_ -> {
            String link = linkField.getText().trim();

            // Controllo link
            if (link.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Il campo link non può essere vuoto.", "Messaggio di errore", JOptionPane.ERROR_MESSAGE);
                linkField.requestFocus();
                return;
            }
            if (link.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Il campo link non può contenere solo numeri.", "Messaggio di errore", JOptionPane.ERROR_MESSAGE);
                linkField.requestFocus();
                return;
            }

            // Controllo date e orari
            Date inizioDate = (Date) orarioInizioSpinner.getValue();
            Date fineDate = (Date) orarioFineSpinner.getValue();

            if (inizioDate == null) {
                JOptionPane.showMessageDialog(this, "Seleziona una data e orario di inizio validi.", "Messaggio di errore", JOptionPane.ERROR_MESSAGE);
                orarioInizioSpinner.requestFocus();
                return;
            }
            if (fineDate == null) {
                JOptionPane.showMessageDialog(this, "Seleziona una data e orario di fine validi.", "Messaggio di errore", JOptionPane.ERROR_MESSAGE);
                orarioFineSpinner.requestFocus();
                return;
            }

            Timestamp inizio = new Timestamp(((Date) orarioInizioSpinner.getValue()).getTime());
            Timestamp fine = new Timestamp(((Date) orarioFineSpinner.getValue()).getTime());
            Timestamp now = new Timestamp(System.currentTimeMillis());

            if (inizio.before(now)) {
                JOptionPane.showMessageDialog(this, "La data e orario di inizio non possono essere anteriori all'attuale.", "Messaggio di errore", JOptionPane.ERROR_MESSAGE);
                orarioInizioSpinner.requestFocus();
                return;
            }

            if (fine.before(now)) {
                JOptionPane.showMessageDialog(this, "La data e orario di fine non possono essere anteriori all'attuale.", "Messaggio di errore", JOptionPane.ERROR_MESSAGE);
                orarioFineSpinner.requestFocus();
                return;
            }

            if (!fine.after(inizio)) {
                JOptionPane.showMessageDialog(this, "La data e orario di fine devono essere successivi a quelli di inizio.", "Messaggio di errore", JOptionPane.ERROR_MESSAGE);
                orarioFineSpinner.requestFocus();
                return;
            }
            
            if (fine.getTime() - inizio.getTime() < 15 * 60 * 1000) {
				JOptionPane.showMessageDialog(this, "La sessione online deve durare almeno 15 minuti.", "Messaggio di errore", JOptionPane.ERROR_MESSAGE);
				orarioFineSpinner.requestFocus();
				return;
			}
            if (fine.getTime() - inizio.getTime() > 5 * 60 * 60 * 1000) {
            	JOptionPane.showMessageDialog(this, "La sessione online non può durare più di 5 ore.", "Messaggio di errore", JOptionPane.ERROR_MESSAGE);
            	orarioFineSpinner.requestFocus();
            	return;
            }
            
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(now);
            calendar.add(Calendar.YEAR, 1);
            Timestamp massimoConsentito = new Timestamp(calendar.getTimeInMillis());
            
            if (inizio.after(massimoConsentito)) {
				JOptionPane.showMessageDialog(this, "La data di inizio non può essere oltre un anno dalla data attuale.", "Messaggio di errore", JOptionPane.ERROR_MESSAGE);
				orarioInizioSpinner.requestFocus();
				return;
			}

            for (Sessione sessione : controller.getCorsoAttribute().getSessioni()) {
				if (sessione instanceof Sessione_online) {
					if (inizio.before(sessione.getOrario_fine_timestamp()) && fine.after(sessione.getOrario_inizio_timestamp())) {
						JOptionPane.showMessageDialog(this, "In questo orario in questo link si sta svolgendo un'altra sessione.", "Messaggio di errore", JOptionPane.ERROR_MESSAGE);
						orarioInizioSpinner.requestFocus();
						return;
					}
				}
			}
            
            // Salvo la sessione
            controller.saveSessioneOnline(link, inizio, fine);
            dispose();
			controller.aggiornaInfoCorsoFrame();
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