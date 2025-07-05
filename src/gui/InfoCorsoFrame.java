package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import dto.*;
import controller.*;

public class InfoCorsoFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private Controller controller;
    private JTable courseTable;
    private boolean checkDoubleClick = false;
    
    private JFrame previous;

    public InfoCorsoFrame(Controller controller, JFrame previous) {

        super("Informazioni corso - UninaFoodLab");
        this.previous = previous;
        this.controller = controller;
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(800, 600));
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Logo.png")));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(0, 102, 204, 180));
        headerPanel.setPreferredSize(new Dimension(1000, 100));

        ImageIcon logo = new ImageIcon(getClass().getResource("/Logo.png"));
        Image originalImage = logo.getImage();
        double aspectRatio = (double) originalImage.getWidth(null) / originalImage.getHeight(null);
        int newWidth = 100;
        int newHeight = (int) (newWidth / aspectRatio);
        Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(scaledImage));
        headerPanel.add(logoLabel, BorderLayout.WEST);

        Corso corso = controller.getCorsoAttribute();
        JLabel corsoLabel = new JLabel("Informazioni " + corso.getNome() + "       ", SwingConstants.CENTER);
        corsoLabel.setFont(new Font("Arial", Font.BOLD, 30));
        corsoLabel.setForeground(Color.WHITE);
        headerPanel.add(corsoLabel, BorderLayout.CENTER);
        getContentPane().add(headerPanel, BorderLayout.NORTH);

        ImageIcon icon = new ImageIcon(getClass().getResource("/Back.png"));
        Image img = icon.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);
        JLabel Back = new JLabel(new ImageIcon(img));
        headerPanel.add(Back, BorderLayout.EAST);
        Back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                	dispose();
                	previous.setVisible(true);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                Back.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });

        // Main Panel con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        // Info Corso (NORTH)
        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        infoPanel.setPreferredSize(new Dimension(600, 250));
        GridBagConstraints gbcInfo = new GridBagConstraints();
        gbcInfo.insets = new Insets(5, 10, 5, 10);
        gbcInfo.anchor = GridBagConstraints.WEST;

        String[][] infoData = {
                {"Nome", corso.getNome()},
                {"Categoria", corso.getCategoria()},
                {"Data Inizio", corso.getData_inizio_formato()},
                {"Frequenza", corso.getFrequenza()},
                {"Costo", (corso.getCosto() == null ? 0 : corso.getCosto()) + " €"},
                {"Numero Sessioni", String.valueOf(corso.getNumero_sessioni())},
        };
        for (int i = 0; i < infoData.length; i++) {
            JLabel titleLabel = new JLabel(infoData[i][0] + ":");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 15));
            gbcInfo.gridx = 0;
            gbcInfo.gridy = i;
            infoPanel.add(titleLabel, gbcInfo);

            JLabel valueLabel = new JLabel(infoData[i][1]);
            valueLabel.setFont(new Font("Arial", Font.PLAIN, 15));
            gbcInfo.gridx = 1;
            gbcInfo.gridy = i;
            infoPanel.add(valueLabel, gbcInfo);
        }
        mainPanel.add(infoPanel, BorderLayout.NORTH);

        // Tabella (CENTER)
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(new Color(245, 245, 245));
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel sessioniLabel = new JLabel("Sessioni");
        sessioniLabel.setFont(new Font("Arial", Font.BOLD, 18));
        sessioniLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        tablePanel.add(sessioniLabel, BorderLayout.NORTH);

        String[] columnNames = {"TIPO", "LUOGO O LINK", "INIZIO", "FINE"};
        String[][] data = new String[corso.getSessioni().size()][4];
        for (int i = 0; i < corso.getSessioni().size(); i++) {
            if (corso.getSessioni().get(i) instanceof Sessione_online) {
                data[i] = new String[]{
                        "ONLINE",
                        corso.getSessioni().get(i).getLink(),
                        corso.getSessioni().get(i).getOrario_inizio(),
                        corso.getSessioni().get(i).getOrario_fine()
                };
            } else {
                data[i] = new String[]{
                        "IN PRESENZA",
                        corso.getSessioni().get(i).getLuogo(),
                        corso.getSessioni().get(i).getOrario_inizio(),
                        corso.getSessioni().get(i).getOrario_fine()
                };
            }
        }

        courseTable = new JTable(data, columnNames) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        courseTable.setFont(new Font("Arial", Font.PLAIN, 14));
        courseTable.setRowHeight(30);
        courseTable.getTableHeader().setReorderingAllowed(false);
        courseTable.getTableHeader().setResizingAllowed(false);
        courseTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        courseTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(courseTable);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(tablePanel, BorderLayout.CENTER);

        courseTable.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int row = courseTable.rowAtPoint(e.getPoint());
                if (row >= 0 &&
                        "IN PRESENZA".equals(courseTable.getModel().getValueAt(row, 0))) {
                    courseTable.setCursor(new Cursor(Cursor.HAND_CURSOR));
                } else {
                    courseTable.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
        });

        apriSessione(null);

        // Pannello bottoni
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        buttonPanel.setBackground(new Color(245, 245, 245));

        JButton addOnlineButton = new JButton("Aggiungi Sessione Online");
        styleButton(addOnlineButton);
        setHandCursor(addOnlineButton);
        addOnlineButton.addActionListener(_ -> new AggiungiSessioneOnlineDialog(controller, this).setVisible(true));
        buttonPanel.add(addOnlineButton);

        JButton addInPresenzaButton = new JButton("Aggiungi Sessione In Presenza");
        styleButton(addInPresenzaButton);
        setHandCursor(addInPresenzaButton);
        addInPresenzaButton.addActionListener(_ -> new AggiungiSessioneInPresenzaDialog(controller, this).setVisible(true));
        buttonPanel.add(addInPresenzaButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    public void apriSessione(ActionListener listener) {
        courseTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e) && !checkDoubleClick) {
                    checkDoubleClick = true;
                    int row = courseTable.rowAtPoint(e.getPoint());
                    if (row >= 0) {
                        String luogo = (String) courseTable.getModel().getValueAt(row, 1);
                        String orarioInizio = (String) courseTable.getModel().getValueAt(row, 2);
                        for (Sessione s : controller.getCorsoAttribute().getSessioni()) {
                            if (s instanceof Sessione_in_presenza &&
                                s.getLuogo().equals(luogo) &&
                                s.getOrario_inizio().equals(orarioInizio)) {
                                controller.setSessioneAttribute((Sessione_in_presenza) s);
                                dispose();
                                new RicetteFrame(controller, InfoCorsoFrame.this).setVisible(true);
                                break;
                            }
                        }
                    }
                    checkDoubleClick = false;
                }
            }
        });
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(0, 102, 204));
        button.setForeground(Color.WHITE);
        button.setMargin(new Insets(15, 20, 15, 20));
        button.setFocusPainted(false);
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
    
    public JFrame getChiamante() {
        return previous;
    }
}
