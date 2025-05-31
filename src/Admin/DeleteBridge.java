package Admin;

import Classes.CustomComponents;
import Classes.User;
import Classes.Payment;
import Classes.PurchaseRequisition;
import Classes.PurchaseOrder;
import Classes.Sales;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class DeleteBridge {
    private static JFrame parent;
    private static Font merriweather;
    private static JComboBox<Object> user_combo, type_combo;
    private static JLabel username;
    private static CustomComponents.CustomList<String> record_list;
    private static List<User> deleting;

    public static void Loader(JFrame parent, Font merriweather) {
        DeleteBridge.parent = parent;
        DeleteBridge.merriweather = merriweather;
    }

    public static void UpdateList(List<User> deleting) {
        DeleteBridge.deleting = deleting;
    }

    public static int[] ShowPage() {
        int[] result = new int[]{0, 0};
        JDialog dialog = new JDialog(parent, "User Correlation", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize((parent.getWidth() / 2), (int) (parent.getHeight() / 1.3));
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(parent);

        int size_factor;
        if (parent.getWidth() >= parent.getHeight()) {
            size_factor = parent.getHeight() / 40;
        } else {
            size_factor = parent.getWidth() / 30;
        }

        int base_size = size_factor;

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 0);
        JLabel title = new JLabel("<html>User records exist and must be resolved<br>before deleting the account.</html>");
        title.setOpaque(false);
        title.setFont(merriweather.deriveFont(Font.BOLD, (float) (base_size * 1.1)));
        panel.add(title, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 10, 10, 0);
        JLabel userid_label = new JLabel("User ID:");
        userid_label.setOpaque(false);
        userid_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(userid_label, gbc);

        gbc.gridy = 2;
        JLabel username_label = new JLabel("Username:");
        username_label.setOpaque(false);
        username_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(username_label, gbc);

        gbc.gridy = 3;
        JLabel type_label = new JLabel("Record Type:");
        type_label.setOpaque(false);
        type_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(type_label, gbc);

        gbc.gridy = 4;
        JLabel rec_label = new JLabel("Records:");
        rec_label.setOpaque(false);
        rec_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(rec_label, gbc);

        gbc.gridy = 5;
        gbc.weighty = 4;
        JLabel placeholder1 = new JLabel("");
        placeholder1.setOpaque(false);
        panel.add(placeholder1, gbc);

        gbc.gridy = 6;
        gbc.weighty = 1;
        gbc.insets = new Insets(0, 10, 10, 0);
        CustomComponents.CustomButton cancel = new CustomComponents.CustomButton("Cancel",
                merriweather.deriveFont(Font.PLAIN), Color.WHITE, Color.WHITE,
                new Color(56, 53, 70), new Color(73, 69, 87),
                Main.transparent, 0, base_size, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        panel.add(cancel, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 10, 10);
        JPanel button_panel = new JPanel(new GridBagLayout());
        button_panel.setOpaque(false);
        panel.add(button_panel, gbc);

        gbc.gridx = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        JLabel placeholder2 = new JLabel("");
        placeholder2.setOpaque(false);
        button_panel.add(placeholder2, gbc);

        gbc.gridx = 1;
        CustomComponents.CustomButton trans = new CustomComponents.CustomButton("Transfer user Data",
                merriweather.deriveFont(Font.PLAIN), new Color(255, 255, 255), new Color(255, 255, 255),
                new Color(209, 88, 128), new Color(237, 136, 172),
                Main.transparent, 0, base_size, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        button_panel.add(trans, gbc);

        gbc.gridy = 1;
        gbc.weightx = 2;
        gbc.insets = new Insets(0, 0, 10, 10);
        List<String> combo_data1 = new ArrayList<>();
        for (User user: deleting) {
            combo_data1.add(String.format("User ID: %s", user.getUserID()));
        }
        user_combo = new JComboBox<>(combo_data1.toArray());
        user_combo.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        user_combo.setFocusable(false);
        user_combo.addActionListener(_ -> {
            username.setText("  " + deleting.get(user_combo.getSelectedIndex()).getUsername());
            type_combo.removeAllItems();
            if (deleting.get(user_combo.getSelectedIndex()).getUserID().startsWith("SM")) {
                if (!User.checkSalesRecordByID(deleting.get(user_combo.getSelectedIndex()).getUserID()).isEmpty()) {
                    type_combo.addItem("Sales Records");
                }
                if (!User.checkPRRecordByID(deleting.get(user_combo.getSelectedIndex()).getUserID()).isEmpty()) {
                    type_combo.addItem("Purchase Requisition Records");
                }
            } else if (deleting.get(user_combo.getSelectedIndex()).getUserID().startsWith("PM")) {
                type_combo.addItem("Purchase Order Records");
            } else {
                type_combo.addItem("Payment Records");
            }
        });
        panel.add(user_combo, gbc);

        gbc.gridy = 2;
        username = new JLabel("  " + deleting.get(user_combo.getSelectedIndex()).getUsername());
        username.setOpaque(true);
        username.setBackground(Color.WHITE);
        username.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        username.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(username, gbc);

        gbc.gridy = 3;
        List<String> combo_data2 = new ArrayList<>();
        if (deleting.get(user_combo.getSelectedIndex()).getUserID().startsWith("SM")) {
            if (!User.checkSalesRecordByID(deleting.get(user_combo.getSelectedIndex()).getUserID()).isEmpty()) {
                combo_data2.add("Sales Records");
            }
            if (!User.checkPRRecordByID(deleting.get(user_combo.getSelectedIndex()).getUserID()).isEmpty()) {
                combo_data2.add("Purchase Requisition Records");
            }
        } else if (deleting.get(user_combo.getSelectedIndex()).getUserID().startsWith("PM")) {
            combo_data2.add("Purchase Order Records");
        } else {
            combo_data2.add("Payment Records");
        }
        type_combo = new JComboBox<>(combo_data2.toArray());
        type_combo.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        type_combo.setFocusable(false);
        type_combo.addActionListener(_ -> {
            List<String> temp = new ArrayList<>();
            User current = deleting.get(user_combo.getSelectedIndex());
            int counter = 1;
            if (type_combo.getSelectedItem() == "Sales Records") {
                List<Sales> allSales = User.checkSalesRecordByID(current.getUserID());
                for (Sales sales: allSales) {
                    temp.add(counter + ".)");
                    temp.add(String.format("Sales ID:           %s", sales.getSalesID()));
                    temp.add(String.format("Sales Date:       %s", sales.getSalesDate().format(User.df)));
                    temp.add(" ");
                    counter += 1;
                }
            } else if (type_combo.getSelectedItem() == "Purchase Requisition Records") {
                List<PurchaseRequisition> allPR = User.checkPRRecordByID(current.getUserID());
                for (PurchaseRequisition pr: allPR) {
                    temp.add(counter + ".)");
                    temp.add(String.format("Purchase Requisition ID:       %s", pr.getPurchaseReqID()));
                    temp.add(String.format("Purchase Requisition Date:   %s", pr.getReqDate().format(User.df)));
                    temp.add(" ");
                    counter += 1;
                }
            } else if (type_combo.getSelectedItem() == "Purchase Order Records") {
                List<PurchaseOrder> allPO = User.checkPORecordByID(current.getUserID());
                for (PurchaseOrder po: allPO) {
                    temp.add(counter + ".)");
                    temp.add(String.format("Purchase Order ID:           %s", po.getPurchaseOrderID()));
                    temp.add(String.format("Purchase Order Date:       %s", po.getOrderDate().format(User.df)));
                    temp.add(String.format("Purchase Order Status:    %s", po.getStatus()));
                    temp.add(" ");
                    counter += 1;
                }
            } else {
                List<Payment> allPY = User.checkPYRecordByID(current.getUserID());
                for (Payment py: allPY) {
                    temp.add(counter + ".)");
                    temp.add(String.format("Payment ID:                %s", py.getPaymentID()));
                    temp.add(String.format("Payment Amount:     RM %s", py.getAmount()));
                    temp.add(String.format("Payment Date:            %s", py.getPaymentDate().format(User.df)));
                    temp.add(" ");
                    counter += 1;
                }
            }
            record_list.UpdateListContent(temp);
        });
        panel.add(type_combo, gbc);

        gbc.gridy = 4;
        gbc.gridheight = 2;
        List<String> temp = new ArrayList<>();
        User current = deleting.get(user_combo.getSelectedIndex());
        int counter = 1;
        if (type_combo.getSelectedItem() == "Sales Records") {
            List<Sales> allSales = User.checkSalesRecordByID(current.getUserID());
            for (Sales sales: allSales) {
                temp.add(counter + ".)");
                temp.add(String.format("Sales ID:           %s", sales.getSalesID()));
                temp.add(String.format("Sales Date:       %s", sales.getSalesDate().format(User.df)));
                temp.add(" ");
                counter += 1;
            }
        } else if (type_combo.getSelectedItem() == "Purchase Requisition Records") {
            List<PurchaseRequisition> allPR = User.checkPRRecordByID(current.getUserID());
            for (PurchaseRequisition pr: allPR) {
                temp.add(counter + ".)");
                temp.add(String.format("Purchase Requisition ID:       %s", pr.getPurchaseReqID()));
                temp.add(String.format("Purchase Requisition Date:   %s", pr.getReqDate().format(User.df)));
                temp.add(" ");
                counter += 1;
            }
        } else if (type_combo.getSelectedItem() == "Purchase Order Records") {
            List<PurchaseOrder> allPO = User.checkPORecordByID(current.getUserID());
            for (PurchaseOrder po: allPO) {
                temp.add(counter + ".)");
                temp.add(String.format("Purchase Order ID:           %s", po.getPurchaseOrderID()));
                temp.add(String.format("Purchase Order Date:       %s", po.getOrderDate().format(User.df)));
                temp.add(String.format("Purchase Order Status:    %s", po.getStatus()));
                temp.add(" ");
                counter += 1;
            }
        } else {
            List<Payment> allPY = User.checkPYRecordByID(current.getUserID());
            for (Payment py: allPY) {
                temp.add(counter + ".)");
                temp.add(String.format("Payment ID:                %s", py.getPaymentID()));
                temp.add(String.format("Payment Amount:     RM %s", py.getAmount()));
                temp.add(String.format("Payment Date:            %s", py.getPaymentDate().format(User.df)));
                temp.add(" ");
                counter += 1;
            }
        }
        record_list = new CustomComponents.CustomList<>(
                temp,
                0,
                base_size,
                merriweather,
                Color.BLACK, Color.BLACK,
                Color.WHITE, new Color(212, 212, 212)
        );
        CustomComponents.CustomScrollPane scrollPane = new CustomComponents.CustomScrollPane(
                false, 1, record_list,
                6, new Color(202, 202, 202), Main.transparent,
                Main.transparent, Main.transparent, Main.transparent,
                new Color(170, 170, 170), Color.WHITE,
                new Color(140, 140, 140), new Color(110, 110, 110),
                Color.WHITE, Color.WHITE, 6
        );
        panel.add(scrollPane, gbc);

        cancel.addActionListener(_ -> dialog.dispose());

        trans.addActionListener(_ -> {
            result[0] = 1;
            result[1] = user_combo.getSelectedIndex();
            dialog.dispose();
        });

        dialog.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Component clickedComponent = e.getComponent();
                SwingUtilities.invokeLater(clickedComponent::requestFocusInWindow);
            }
        });
        dialog.setContentPane(panel);
        dialog.setVisible(true);
        SwingUtilities.invokeLater(title::requestFocusInWindow);

        return result;
    }
}