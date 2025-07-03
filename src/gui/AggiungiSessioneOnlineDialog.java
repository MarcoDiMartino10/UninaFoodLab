package gui;

import java.util.Calendar;

import java.awt.*;
import java.sql.Timestamp;
import java.util.*;
import javax.swing.*;

import controller.*;

public class AggiungiSessioneOnlineDialog extends JDialog {

    // Attributi
    private static final long serialVersionUID = 1L;
    Controller controller;

    public AggiungiSessioneOnlineDialog(Controller controller) {
    	super((InfoCorsoFrame) null, "Aggiungi Sessione Online", true);
        this.controller = controller;
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
        annullaButton.addActionListener(_ -> controller.chiudiAggiungiSessioneOnlineDialog());

        // Listener invia
        inviaButton.addActionListener(_ -> {
            String link = linkField.getText().trim();

            // Controllo link
            if (link.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Il campo link non può essere vuoto.", "Errore", JOptionPane.ERROR_MESSAGE);
                linkField.requestFocus();
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

            
            controller.aggiungiSessioneOnline(link, inizio, fine);
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