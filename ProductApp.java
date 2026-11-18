import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ProductApp extends JFrame {

    private final JTextField txtCustomer = new JTextField(20);
    private final JComboBox<String> cmbProduct = new JComboBox<>(new String[]{
            "Cozy Nights Monarch", "Restonic Barbados", "Sealy Phuket"});
    private final JTextField txtPrice = new JTextField(20);
    private final JButton btnProcess = new JButton("PROCESS");

    public ProductApp() {
        setTitle("Product App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2, 10, 15));
        ((JComponent) getContentPane()).setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(new JLabel("Customer Name:"));
        add(txtCustomer);
        add(new JLabel("Select Product:"));
        add(cmbProduct);
        add(new JLabel("Enter Price:"));
        add(txtPrice);
        add(new JLabel(""));
        add(btnProcess);

        // Menu (Q.2.4)
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(exitItem);

        JMenu toolsMenu = new JMenu("Tools");
        JMenuItem processItem = new JMenuItem("Process");
        JMenuItem clearItem = new JMenuItem("Clear");
        toolsMenu.add(processItem);
        toolsMenu.add(clearItem);

        menuBar.add(fileMenu);
        menuBar.add(toolsMenu);
        setJMenuBar(menuBar);

        // Events: button and menu item share the same logic
        btnProcess.addActionListener(e -> processSale());
        processItem.addActionListener(e -> processSale());
        clearItem.addActionListener(e -> clearForm());
        exitItem.addActionListener(e -> System.exit(0));

        pack();
        setLocationRelativeTo(null);
    }

    private void processSale() {
        String customer = txtCustomer.getText().trim();
        String product = (String) cmbProduct.getSelectedItem();

        if (customer.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the customer name.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double price;
        try {
            price = Double.parseDouble(txtPrice.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid price.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ProductCalculations calc = new ProductCalculations(price);
        double vat = calc.GetVat();
        double commission = calc.GetCommission();
        double total = calc.GetTotal();

        String message = "Customer Name: " + customer + "\n"
                + "Product " + product + "\n"
                + "VAT: R " + vat + "\n"
                + "COMMISSION: R " + commission + "\n"
                + "TOTAL: R " + total;

        JOptionPane.showMessageDialog(this, message, "Message",
                JOptionPane.INFORMATION_MESSAGE);

        saveToFile(customer, product, vat, commission, total);
    }

    private void saveToFile(String customer, String product,
                            double vat, double commission, double total) {
        try (PrintWriter out = new PrintWriter(new FileWriter("data.txt", true))) {
            out.println("DATA LOG");
            out.println("*".repeat(30));
            out.println("Customer Name: " + customer);
            out.println("Product: " + product);
            out.println("VAT: R " + vat);
            out.println("COMMISSION: R " + commission);
            out.println("TOTAL: R " + total);
            out.println("*".repeat(30));
            out.println();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Could not save to data.txt.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        txtCustomer.setText("");
        txtPrice.setText("");
        cmbProduct.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ProductApp().setVisible(true));
    }
}