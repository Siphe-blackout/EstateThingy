package estateagent;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
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

    private final DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(Locale.US);
    private final DecimalFormat moneyFormat = new DecimalFormat("#,##0.00", symbols);
    private final DecimalFormat percentFormat = new DecimalFormat("0.##", symbols);

    public EstateAgentGUI() {
        setTitle("Estate Agent Report");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 550);
        setLocationRelativeTo(null);
        createMenu();
        createForm();
    }

    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu toolsMenu = new JMenu("Tools");

        JMenuItem saveItem = new JMenuItem("Save Report");
        JMenuItem exitItem = new JMenuItem("Exit");
        JMenuItem processItem = new JMenuItem("Process Report");
        JMenuItem clearItem = new JMenuItem("Clear");

        saveItem.addActionListener(e -> saveReport());
        exitItem.addActionListener(e -> System.exit(0));
        processItem.addActionListener(e -> processReport());
        clearItem.addActionListener(e -> clearForm());

        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        toolsMenu.add(processItem);
        toolsMenu.add(clearItem);
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

        JButton btnProcess = new JButton("Process");
        JButton btnClear = new JButton("Clear");
        btnProcess.addActionListener(e -> processReport());
        btnClear.addActionListener(e -> clearForm());
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        buttons.add(btnProcess);
        buttons.add(Box.createHorizontalStrut(10));
        buttons.add(btnClear);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(buttons, gbc);

        gbc.gridy = 5;
        panel.add(new JLabel("ESTATE AGENT REPORT:"), gbc);

        txtReport.setEditable(false);
        txtReport.setLineWrap(true);
        txtReport.setWrapStyleWord(true);

        gbc.gridy = 6;
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

        if (name.isEmpty()) {
            showError("Please enter the estate agent's name.");
            txtAgentName.requestFocusInWindow();
            return;
        }

        // A fresh object per report, so no stale state is carried over.
        EstateAgent agent = new EstateAgent();
        double commission;
        try {
            // Parses, validates and stores price and percentage on the agent.
            commission = agent.calculateCommission(
                    txtPropertyPrice.getText(), txtCommissionPercentage.getText());
        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
            return;
        }
        agent.setAgentLocation(location);
        agent.setAgentName(name);

        String report = "AGENT LOCATION: " + agent.getAgentLocation() + "\n"
                + "AGENT NAME: " + agent.getAgentName() + "\n"
                + "PROPERTY PRICE: R " + moneyFormat.format(agent.getPropertyPrice()) + "\n"
                + "COMMISSION PERCENTAGE: " + percentFormat.format(agent.getCommissionPercentage()) + "%\n"
                + "CALCULATED COMMISSION: R " + moneyFormat.format(commission);

        txtReport.setText(report);
    }

    private void saveReport() {
        String report = txtReport.getText();
        if (report.isEmpty()) {
            showError("Process a report before saving.");
            return;
        }

        // Built only for the file; the on-screen report is left unchanged.
        String fileContent = "ESTATE AGENT REPORT\n"
                + "************************************\n"
                + report + "\n"
                + "************************************\n";

        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new java.io.File("EstateAgentReport.txt"));
        if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        try {
            Files.write(chooser.getSelectedFile().toPath(),
                    fileContent.getBytes(StandardCharsets.UTF_8));
            JOptionPane.showMessageDialog(this, "Report saved.");
        } catch (IOException ex) {
            showError("Could not save the file: " + ex.getMessage());
        }
    }

    private void clearForm() {
        cmbLocation.setSelectedIndex(0);
        txtAgentName.setText("");
        txtPropertyPrice.setText("");
        txtCommissionPercentage.setText("");
        txtReport.setText("");
        txtAgentName.requestFocusInWindow();
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Input error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EstateAgentGUI().setVisible(true));
    }
}