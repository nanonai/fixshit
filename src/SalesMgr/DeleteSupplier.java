package SalesMgr;

import Admin.*;
import Classes.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class DeleteSupplier {
    private static JFrame parent;
    private static Font merriweather;
    private static List<Supplier> suppliers;

    public static void Loader(JFrame parent, Font merriweather) {
        DeleteSupplier.parent = parent;
        DeleteSupplier.merriweather = merriweather;
    }

    public static void UpdateSupplier(List<Supplier> suppliers) {
        DeleteSupplier.suppliers = suppliers;
    }

    public static boolean ShowPage() {
        AtomicBoolean delete_or_not = new AtomicBoolean(false);
        JDialog dialog = new JDialog(parent, "Delete Items", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(parent.getWidth() / 2, (int) (parent.getHeight() / 1.5));
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(parent);

        int base_size = (parent.getWidth() >= parent.getHeight()) ? parent.getHeight() / 40 : parent.getWidth() / 30;

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(10, 10, 0, 10);
        JLabel title = new JLabel("Please confirm to delete all suppliers listed below:");
        title.setOpaque(false);
        title.setFont(merriweather.deriveFont(Font.BOLD, (float) (base_size * 1.3)));
        panel.add(title, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 10, 10);
        List<String> data = new ArrayList<>();
        int counter = 1;
        for (Supplier supplier : suppliers) {
            data.add(String.format("%d. SupplierID: %s", counter, supplier.getSupplierID()));
            data.add(String.format("    Name: %s", supplier.getSupplierName()));
            data.add(String.format("    Contact Person: %s", supplier.getContactPerson()));
            data.add(String.format("    Phone: %s", supplier.getPhone()));
            data.add(String.format("    Email: %s", supplier.getEmail()));
            data.add(String.format("    Address: %s", supplier.getAddress()));
            data.add(" ");
            counter++;
        }

        CustomComponents.CustomList<String> list = new CustomComponents.CustomList<>(
                data, 1, base_size, merriweather.deriveFont(Font.PLAIN),
                Color.BLACK, Color.BLACK, Color.WHITE, new Color(212, 212, 212));

        gbc.weighty = 6;
        CustomComponents.CustomScrollPane scrollPane = new CustomComponents.CustomScrollPane(
                false, 1, list, 6, new Color(202, 202, 202), Main.transparent,
                Main.transparent, Main.transparent, Main.transparent,
                new Color(170, 170, 170), Color.WHITE,
                new Color(140, 140, 140), new Color(110, 110, 110),
                Color.WHITE, Color.WHITE, 6);
        panel.add(scrollPane, gbc);

        gbc.gridy = 2;
        gbc.weighty = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 10, 10, 0);
        CustomComponents.CustomButton cancel = new CustomComponents.CustomButton("Cancel",
                merriweather.deriveFont(Font.PLAIN), Color.WHITE, Color.WHITE,
                new Color(56, 53, 70), new Color(73, 69, 87),
                Main.transparent, 0, base_size, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        panel.add(cancel, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        JLabel placeholder = new JLabel("");
        placeholder.setOpaque(false);
        panel.add(placeholder, gbc);

        gbc.gridx = 2;
        gbc.insets = new Insets(0, 0, 10, 10);
        CustomComponents.CustomButton delete = new CustomComponents.CustomButton(
                "Delete Suppliers (" + suppliers.size() + ")",
                merriweather.deriveFont(Font.PLAIN), Color.WHITE, Color.WHITE,
                new Color(159, 4, 4), new Color(161, 40, 40),
                Main.transparent, 0, base_size, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        panel.add(delete, gbc);

        cancel.addActionListener(_ -> dialog.dispose());

        delete.addActionListener(_ -> {
            boolean proceed = CustomComponents.CustomOptionPane.showConfirmDialog(
                    parent, "Confirm to delete?", "Confirmation",
                    new Color(159, 4, 4),
                    Color.WHITE, new Color(161, 40, 40), Color.WHITE,
                    new Color(56, 53, 70), Color.WHITE,
                    new Color(73, 69, 87), Color.WHITE, false
            );
            if (proceed) {
                for (Supplier supplier : suppliers) {
                    new Supplier().Remove(supplier);
                }
                delete_or_not.set(true);
            }
            dialog.dispose();
        });

        dialog.setContentPane(panel);
        dialog.setVisible(true);
        SwingUtilities.invokeLater(title::requestFocusInWindow);
        return delete_or_not.get();
    }
}
