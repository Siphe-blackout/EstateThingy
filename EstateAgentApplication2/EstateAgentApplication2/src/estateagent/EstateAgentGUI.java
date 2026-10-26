package estateagent;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DecimalFormat;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class EstateAgentGUI extends JFrame {
    private final JComboBox<String> cmbLocation = new JComboBox<>(
            new String[]{"Cape Town", "Durban", "Pretoria"});
    private final JTextField txtAgentName = new JTextField(20);
    private final JTextField txtPropertyPrice = new JTextField(20);
    private final JTextField txtCommissionPercentage = new JTextField(20);
    private final JTextArea txtReport = new JTextArea(10, 35);

    private final EstateAgent estateAgent = new EstateAgent();
    private final DecimalFormat moneyFormat = new DecimalFormat("#,##0.00");

    public EstateAgentGUI() {
        setTitle("Estate Agent Report");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        createMenu();
        createForm();
    }

    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu toolsMenu = new JMenu("Tools");

        JMenuItem exitItem = new JMenuItem("Exit");
        JMenuItem processItem = new JMenuItem("Process Report");
        JMenuItem clearItem = new JMenuItem("Clear");
        JMenuItem saveItem = new JMenuItem("Save Report");

        exitItem.addActionListener(e -> System.exit(0));
        processItem.addActionListener(e -> processReport());
        clearItem.addActionListener(e -> clearForm());
        saveItem.addActionListener(e -> saveReport());

        fileMenu.add(exitItem);
        toolsMenu.add(processItem);
        toolsMenu.add(clearItem);
        toolsMenu.add(saveItem);
        menuBar.add(fileMenu);
        menuBar.add(toolsMenu);
        setJMenuBar(menuBar);
    }

    private void createForm() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 5, 7, 5);
        gbc.anchor = GridBagConstraints.WEST;

        addComponent(panel, gbc, 0, 0, new JLabel("AGENT LOCATION:"));
        addComponent(panel, gbc, 1, 0, cmbLocation);
        addComponent(panel, gbc, 0, 1, new JLabel("ESTATE AGENT NAME:"));
        addComponent(panel, gbc, 1, 1, txtAgentName);
        addComponent(panel, gbc, 0, 2, new JLabel("PROPERTY PRICE (R):"));
        addComponent(panel, gbc, 1, 2, txtPropertyPrice);
        addComponent(panel, gbc, 0, 3, new JLabel("COMMISSION PERCENTAGE:"));
        addComponent(panel, gbc, 1, 3, txtCommissionPercentage);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(new JLabel("ESTATE AGENT REPORT:"), gbc);

        txtReport.setEditable(false);
        txtReport.setLineWrap(true);
        txtReport.setWrapStyleWord(true);

        gbc.gridy = 5;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(new JScrollPane(txtReport), gbc);

        add(panel, BorderLayout.CENTER);
    }

    private void addComponent(JPanel panel, GridBagConstraints gbc,
                              int x, int y, Component component) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        gbc.weightx = (x == 1) ? 1 : 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(component, gbc);
    }

    private void processReport() {
        String location = (String) cmbLocation.getSelectedItem();
        String name = txtAgentName.getText().trim();
        String priceText = txtPropertyPrice.getText().trim();
        String percentageText = txtCommissionPercentage.getText().trim();

        double price = Double.parseDouble(priceText);
        double percentage = Double.parseDouble(percentageText);
        double commission = estateAgent.CalculateCommission(priceText, percentageText);

        estateAgent.setAgentLocation(location);
        estateAgent.setAgentName(name);
        estateAgent.setPropertyPrice(price);
        estateAgent.setCommissionPercentage(percentage);

        String report = "AGENT LOCATION: " + location + "\n"
                + "AGENT NAME: " + name + "\n"
                + "PROPERTY PRICE: R " + moneyFormat.format(price) + "\n"
                + "COMMISSION PERCENTAGE: " + moneyFormat.format(percentage) + "%\n"
                + "CALCULATED COMMISSION: R " + moneyFormat.format(commission);

        txtReport.setText(report);
    }

    private void saveReport() {
        String report = txtReport.getText();
        if (report.isEmpty()) {
            return;
        }

        String fileContent = "ESTATE AGENT REPORT\\n"
                + "************************************\\n"
                + report + "\\n"
                + "************************************\\n";
        txtReport.setText(fileContent);
    }

    private void clearForm() {
        cmbLocation.setSelectedIndex(0);
        txtAgentName.setText("");
        txtPropertyPrice.setText("");
        txtCommissionPercentage.setText("");
        txtReport.setText("");
        txtAgentName.requestFocusInWindow();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EstateAgentGUI().setVisible(true));
    }
}
