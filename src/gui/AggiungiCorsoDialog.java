package gui;

import org.jdatepicker.impl.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import javax.swing.JFormattedTextField.AbstractFormatter;

import java.awt.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
import javax.swing.*;

import controller.*;

public class AggiungiCorsoDialog extends JDialog {

    // Attributi
    private static final long serialVersionUID = 1L;
    Controller controller;
    String nomeCorso;
    String categoria;
    String dataInizio;
    String frequenza;
    String costo;
    int numsessioni;

    private JTextField nomeField;
    private JTextField categoriaField;
    private JTextField frequenzaField;
    private JTextField costoField;
    private JTextField numsessioniField;

    public AggiungiCorsoDialog(Controller controller) {
    	super((InfoCorsoFrame) null, "Aggiungi Corso", true);
        this.controller = controller;
        setSize(550, 400);
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
        JLabel titleLabel = new JLabel("Aggiungi Nuovo Corso", SwingConstants.CENTER);
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

        // Nome corso
        JLabel nomeLabel = new JLabel("Nome Corso:");
        nomeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nomeField = new JTextField(20);
        nomeField.setFont(new Font("Arial", Font.PLAIN, 14));
        nomeField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        inputPanel.add(nomeLabel, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(nomeField, gbc);

        // Categoria
        JLabel categoriaLabel = new JLabel("Categoria:");
        categoriaLabel.setFont(new Font("Arial", Font.BOLD, 14));
        categoriaField = new JTextField(20);
        categoriaField.setFont(new Font("Arial", Font.PLAIN, 14));
        categoriaField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        inputPanel.add(categoriaLabel, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(categoriaField, gbc);

        // Data inizio
        JLabel dataInizioLabel = new JLabel("Data di inizio:");
        dataInizioLabel.setFont(new Font("Arial", Font.BOLD, 14));
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Oggi");
        p.put("text.month", "Mese");
        p.put("text.year", "Anno");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        datePicker.getJFormattedTextField().setFont(new Font("Segoe UI", Font.PLAIN, 14));
        datePicker.getJFormattedTextField().setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(100, 150, 250), 2),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        datePicker.getJFormattedTextField().setBackground(new Color(240, 248, 255));
        datePanel.setBackground(new Color(230, 240, 255));
        datePanel.getComponent(0).setBackground(new Color(230, 240, 255));

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        inputPanel.add(dataInizioLabel, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(datePicker, gbc);

        // Frequenza
        JLabel frequenzaLabel = new JLabel("Frequenza:");
        frequenzaLabel.setFont(new Font("Arial", Font.BOLD, 14));
        frequenzaField = new JTextField(20);
        frequenzaField.setFont(new Font("Arial", Font.PLAIN, 14));
        frequenzaField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        inputPanel.add(frequenzaLabel, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(frequenzaField, gbc);

        // Costo
        JLabel costoLabel = new JLabel("Costo:");
        costoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        costoField = new JTextField(20);
        costoField.setFont(new Font("Arial", Font.PLAIN, 14));
        costoField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        inputPanel.add(costoLabel, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(costoField, gbc);

        // Numero sessioni
        JLabel numsessioniLabel = new JLabel("Numero sessioni:");
        numsessioniLabel.setFont(new Font("Arial", Font.BOLD, 14));
        numsessioniField = new JTextField(5);
        numsessioniField.setFont(new Font("Arial", Font.PLAIN, 14));
        numsessioniField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        inputPanel.add(numsessioniLabel, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(numsessioniField, gbc);

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
        annullaButton.addActionListener(_ -> controller.chiudiAggiungiCorsoDialog());

        // Listener invia
        inviaButton.addActionListener(_ -> {
            int numSessioni;
            String nomeCorso = nomeField.getText().trim();
            String categoria = categoriaField.getText().trim();
            String frequenza = frequenzaField.getText().trim();
            String costoStr = costoField.getText().trim();
            String numSessioniStr = numsessioniField.getText().trim();

            // Controllo nome corso
            if (nomeCorso.isEmpty()) {
                //JOptionPane.showMessageDialog(this, "Il campo nome corso non può essere vuoto.", "Errore", JOptionPane.ERROR_MESSAGE);
                //nomeField.requestFocus();
            	dialogErrore("Il campo nome corso non può essere vuoto.");
                return;
            }

            // Controllo categoria
            if (categoria.isEmpty()) {
                //JOptionPane.showMessageDialog(this, "Il campo categoria non può essere vuoto.", "Errore", JOptionPane.ERROR_MESSAGE);
                //categoriaField.requestFocus();
            	dialogErrore("Il campo categoria non può essere vuoto.");
                return;
            }

            // Controllo data inizio
            Date selectedDate = (Date) datePicker.getModel().getValue();
            if (selectedDate == null) {
                //JOptionPane.showMessageDialog(this, "Seleziona una data di inizio valida.", "Errore", JOptionPane.ERROR_MESSAGE);
            	dialogErrore("Seleziona una data di inizio valida.");
                return;
            }
            LocalDate dataInizio = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if (dataInizio.isBefore(LocalDate.now())) {
                //JOptionPane.showMessageDialog(this, "La data di inizio non può essere precedente a oggi.", "Errore", JOptionPane.ERROR_MESSAGE);
            	dialogErrore("La data di inizio non può essere precedente a oggi.");
                return;
            }

            // Controllo frequenza
            if (frequenza.isEmpty()) {
                //JOptionPane.showMessageDialog(this, "Il campo frequenza non può essere vuoto.", "Errore", JOptionPane.ERROR_MESSAGE);
                //frequenzaField.requestFocus();
            	dialogErrore("Il campo frequenza non può essere vuoto.");
                return;
            }

            // Controllo costo
            BigDecimal costo;
            try {
                costo = new BigDecimal(costoStr);
                if (costo.compareTo(BigDecimal.ZERO) < 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                //JOptionPane.showMessageDialog(this, "Inserisci un valore numerico valido per il costo.", "Errore", JOptionPane.ERROR_MESSAGE);
                //costoField.requestFocus();
                dialogErrore("Inserisci un valore numerico valido per il costo.");
                return;
            }

            // Controllo numero sessioni
            try {
                numSessioni = Integer.parseInt(numSessioniStr);
                if (numSessioni <= 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                //JOptionPane.showMessageDialog(this, "Inserisci un numero intero positivo per il numero di sessioni.", "Errore", JOptionPane.ERROR_MESSAGE);
                //numsessioniField.requestFocus();
            	dialogErrore("Inserisci un numero intero positivo per il numero di sessioni.");
                return;
            }
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

    // Formatter per la data
    public class DateLabelFormatter extends AbstractFormatter {
		private static final long serialVersionUID = 1L;
		private String datePattern = "dd-MM-yyyy";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            if (text == null || text.trim().isEmpty()) return null;
            return dateFormatter.parse(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }
            return "";
        }
    }
    
    // Metodo per mostrare un messaggio di errore
    public void dialogErrore(String messaggio_errore) {
        nomeField.requestFocusInWindow();

        JDialog dialog = new JDialog(this, "Errore", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setSize(400, 130);
        dialog.setLocationRelativeTo(this);
        dialog.setUndecorated(true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.getRootPane().setBorder(BorderFactory.createLineBorder(new Color(200, 0, 0), 2));

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel messageLabel = new JLabel("<html><center>" + messaggio_errore + "</center></html>", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 14));
        messageLabel.setForeground(new Color(200, 0, 0)); // rosso scuro

        JButton okButton = new JButton("RIPROVA");
        okButton.setFont(new Font("Arial", Font.BOLD, 13));
        okButton.setBackground(new Color(0, 102, 204));
        okButton.setForeground(Color.WHITE);
        okButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        okButton.setFocusPainted(false);
        okButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        okButton.addActionListener(_ -> dialog.dispose());
        dialog.getRootPane().setDefaultButton(okButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(okButton);

        panel.add(messageLabel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setContentPane(panel);
        dialog.setVisible(true);
    }

}