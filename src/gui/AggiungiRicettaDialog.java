package gui;

import java.awt.*;
import java.math.BigDecimal;
import java.util.*;
import javax.swing.*;

import controller.*;
import dto.*;

public class AggiungiRicettaDialog extends JDialog {
	
	// Attributi
	private static final long serialVersionUID = 1L;
	Controller controller;
	String nomeRicetta;
	private JTextField nomeField;
	
	/*-----------------------------------------------------------------------------------------*/
	
	// Costruttore
	public AggiungiRicettaDialog(Controller controller, boolean flag, JFrame previous) {
		
		super((RicetteFrame) null, "Aggiungi Corso", true);
        this.controller = controller;
        setSize(520, 280);
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
        JLabel titleLabel = new JLabel("Aggiungi Nuova Ricetta", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Pannello centrale per input e ingredienti
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

        // Nome ricetta
        JLabel nomeLabel = new JLabel("Nome Ricetta:");
        nomeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nomeField = new JTextField(20);
        nomeField.setFont(new Font("Arial", Font.PLAIN, 14));
        nomeField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(nomeLabel, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        inputPanel.add(nomeField, gbc);

        // Numero ingredienti
        JLabel numIngLabel = new JLabel("Numero Ingredienti:");
        numIngLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JTextField numIngField = new JTextField(5);
        numIngField.setFont(new Font("Arial", Font.PLAIN, 14));
        numIngField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        inputPanel.add(numIngLabel, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        inputPanel.add(numIngField, gbc);

        // Pulsante genera
        JButton generaButton = new JButton("Inserisci Ingredienti");
        styleButton(generaButton);
        generaButton.setPreferredSize(new Dimension(200, 40));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        inputPanel.add(generaButton, gbc);

        centerPanel.add(inputPanel, BorderLayout.NORTH);

        // Pannello ingredienti
        JPanel ingredientiPanel = new JPanel();
        ingredientiPanel.setLayout(new BoxLayout(ingredientiPanel, BoxLayout.Y_AXIS));
        ingredientiPanel.setBackground(Color.WHITE);
        JScrollPane ingredientiScroll = new JScrollPane(ingredientiPanel);
        ingredientiScroll.setBorder(BorderFactory.createEmptyBorder());
        ingredientiScroll.setVisible(false);

        centerPanel.add(ingredientiScroll, BorderLayout.CENTER);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Pannello bottoni
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonsPanel.setBackground(Color.WHITE);
        buttonsPanel.setVisible(false);

        // Bottone Invia
        JButton inviaButton = new JButton("Invia");
        styleButton(inviaButton);
        inviaButton.setPreferredSize(new Dimension(120, 40));

        // Bottone Annulla
        JButton annullaButton = new JButton("Annulla");
        styleButton(annullaButton);
        annullaButton.setBackground(Color.LIGHT_GRAY);
        annullaButton.setForeground(Color.BLACK);
        annullaButton.setPreferredSize(new Dimension(120, 40));

        buttonsPanel.add(annullaButton);
        buttonsPanel.add(inviaButton);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
        
        LinkedList<JTextField> nomeFields = new LinkedList<>();
        LinkedList<JTextField> quantitaFields = new LinkedList<>();
        LinkedList<JTextField> unitàFields = new LinkedList<>();
        
        // Genera ingredienti
        generaButton.addActionListener(_ -> {
            
        	// Controlli nome e numero ingredienti
        	ingredientiPanel.removeAll();
            int numIngredienti;
            String nomeRicetta = nomeField.getText().trim();
            if (nomeRicetta.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Inserisci il nome della ricetta.");
                return;
            }
            if (nomeRicetta.length() > 30) {
                JOptionPane.showMessageDialog(this, "Il nome della ricetta deve contenere al massimo 30 caratteri.");
                return;
            }
            this.nomeRicetta = nomeRicetta;
            try {
                numIngredienti = Integer.parseInt(numIngField.getText().trim());
                if (numIngredienti <= 0) {
                    JOptionPane.showMessageDialog(this, "Inserisci un numero valido.");
                    return;
                } if (numIngredienti > 50) {
					JOptionPane.showMessageDialog(this, "Inserisci un numero di ingredienti inferiore o uguale a 50.");
					return;
				}
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Inserisci un numero valido.");
                return;
            }

            // Titolo riga ingredienti
            JPanel headerIng = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
            headerIng.setBackground(new Color(240, 240, 240));
            headerIng.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
            JLabel ingLabel = new JLabel("                                   Ingrediente", SwingConstants.LEFT);
            ingLabel.setFont(new Font("Arial", Font.BOLD, 14));
            ingLabel.setPreferredSize(new Dimension(230, 25));
            JLabel qtyLabel = new JLabel("     Quantità", SwingConstants.LEFT);
            qtyLabel.setFont(new Font("Arial", Font.BOLD, 14));
            qtyLabel.setPreferredSize(new Dimension(90, 25));
            JLabel unitLabel = new JLabel("  Unità", SwingConstants.LEFT);
            unitLabel.setFont(new Font("Arial", Font.BOLD, 14));
            unitLabel.setPreferredSize(new Dimension(80, 25));

            headerIng.add(ingLabel);
            headerIng.add(qtyLabel);
            headerIng.add(unitLabel);
            ingredientiPanel.add(headerIng);
            
            nomeFields.clear();
            quantitaFields.clear();
            unitàFields.clear();
            
            // Righe ingredienti
            for (int i = 0; i < numIngredienti; i++) {
                JPanel singoloIngrediente = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
                singoloIngrediente.setBackground(Color.WHITE);
                singoloIngrediente.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
                JLabel ingNumLabel = new JLabel("Ingrediente " + (i + 1) + ":");
                ingNumLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                ingNumLabel.setPreferredSize(new Dimension(100, 25));
                
                JTextField nomeIng = new JTextField(12);
                nomeIng.setFont(new Font("Arial", Font.PLAIN, 14));
                nomeIng.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)
                ));
                nomeFields.add(nomeIng);
                
                JTextField quantitaIng = new JTextField(5);
                quantitaIng.setFont(new Font("Arial", Font.PLAIN, 14));
                quantitaIng.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)
                ));
                quantitaFields.add(quantitaIng);
                
                JTextField unitaMisuraIng = new JTextField(5);
                unitaMisuraIng.setFont(new Font("Arial", Font.PLAIN, 14));
                unitaMisuraIng.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)
                ));
                unitàFields.add(unitaMisuraIng);

                singoloIngrediente.add(ingNumLabel);
                singoloIngrediente.add(nomeIng);
                singoloIngrediente.add(quantitaIng);
                singoloIngrediente.add(unitaMisuraIng);
                ingredientiPanel.add(singoloIngrediente);
            }

            ingredientiScroll.setVisible(true);
            buttonsPanel.setVisible(true);
            ingredientiPanel.revalidate();
            ingredientiPanel.repaint();
            centerPanel.revalidate();
            centerPanel.repaint();

            setSize(520, 500);
            setLocationRelativeTo(this);
        });
        
        // Bottone Annulla
        annullaButton.addActionListener(_ -> dispose());
        
        // Bottone Invia
        inviaButton.addActionListener(_ -> {
        	LinkedList<Ingrediente>ingredienti = new LinkedList<>();
            int IdRicetta = controller.nuovoIdRicetta();
            int IdIngrediente = controller.nuovoIdIngrediente();
            LinkedList<String> nomiUsati = new LinkedList<>();
            
            // Controlli su ingredienti
            for (int i = 0; i < nomeFields.size(); i++) {
                String nomeIngrediente = nomeFields.get(i).getText().trim();
                String testo = quantitaFields.get(i).getText().trim();
                String unità = unitàFields.get(i).getText().trim();

                if (nomeIngrediente.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Inserire il nome dell'ingrediente numero " + (i + 1) + ".");
                    return;
                }
                if (nomeIngrediente.length() > 30) {
                    JOptionPane.showMessageDialog(this, "Il nome dell'ingrediente numero " + (i + 1) + " supera i 30 caratteri.");
                    return;
                }
                
                if (nomiUsati.contains(nomeIngrediente)) {
                    JOptionPane.showMessageDialog(this, "Ingrediente \"" + nomeIngrediente + "\" inserito più volte.");
                    return;
                }
                nomiUsati.add(nomeIngrediente);

                BigDecimal quantità = null;
                if (!testo.isEmpty()) {
                    try {
                        quantità = new BigDecimal(testo);
                        if (quantità.compareTo(new BigDecimal("10000.00")) > 0) {
                            JOptionPane.showMessageDialog(this, "La quantità dell'ingrediente numero " + (i + 1) + " è superiore a 10000.00.");
                            return;
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Quantità non valida per l'ingrediente numero " + (i + 1));
                        return;
                    }
                } else {
                	quantità = null;
                    unità = "q.b.";
                }

                if (unità.length() > 10) {
                    JOptionPane.showMessageDialog(this, "L'unità di misura dell'ingrediente numero " + (i + 1) + " contiene più di 10 caratteri.");
                    return;
                }

                Ingrediente ingrediente = new Ingrediente(IdIngrediente++, nomeIngrediente, quantità, unità, IdRicetta);
                ingredienti.add(ingrediente);
            }
            
            // Comportamento bottone Invia
            dispose();
            if (flag == false) {
            	controller.saveRicettaAndIngrediente(nomeRicetta, ingredienti);
            	previous.dispose();
                new RicetteFrame(controller, ((RicetteFrame) previous).getChiamante()).setVisible(true);
            }
            else {
            	controller.saveSessioneAndRicettaAndIngrediente(nomeRicetta, ingredienti);
            }
        });


        getContentPane().add(mainPanel);
    }
	
	/*-----------------------------------------------------------------------------------------*/
	
	// Metodo per lo stile dei bottoni
	private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(0, 102, 204));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        button.setPreferredSize(new Dimension(250, 50));
    }
	
}
