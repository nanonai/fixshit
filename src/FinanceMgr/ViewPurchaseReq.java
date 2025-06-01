package FinanceMgr;

import Classes.*;
import Admin.Main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;

public class ViewPurchaseReq {
    private static JFrame parent;
    private static Font merriweather;
    private static PurchaseRequisition current_pr;

    public static void Loader(JFrame parent, Font merriweather) {
        ViewPurchaseReq.parent = parent;
        ViewPurchaseReq.merriweather = merriweather;
    }

    public static void UpdatePurchaseReq(PurchaseRequisition purchaseRequisition) {
        ViewPurchaseReq.current_pr = purchaseRequisition;
    }

    public static void ShowPage() {
        JDialog dialog = new JDialog(parent, "View Purchase Requisition Details", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(parent.getWidth() / 2, (int) (parent.getHeight() / 1.5));
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
        JLabel title = new JLabel("View PR Details");
        title.setOpaque(false);
        title.setFont(merriweather.deriveFont(Font.BOLD, (float) (base_size * 1.3)));
        panel.add(title, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 10, 10, 0);
        JLabel itemID_label = new JLabel("Purchase Req ID:");
        itemID_label.setOpaque(false);
        itemID_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(itemID_label, gbc);

        gbc.gridy = 2;
        JLabel itemName_label = new JLabel("Item ID :");
        itemName_label.setOpaque(false);
        itemName_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(itemName_label, gbc);

        gbc.gridy = 3;
        JLabel unitPrice_label = new JLabel("Supplier ID :");
        unitPrice_label.setOpaque(false);
        unitPrice_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(unitPrice_label, gbc);

        gbc.gridy = 4;
        JLabel quantity_label = new JLabel("Quantity :");
        quantity_label.setOpaque(false);
        quantity_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(quantity_label, gbc);

        gbc.gridy = 5;
        JLabel threshold_label = new JLabel("ReqDate :");
        threshold_label.setOpaque(false);
        threshold_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(threshold_label, gbc);

        gbc.gridy = 6;
        JLabel category_label = new JLabel("SalesMrgID :");
        category_label.setOpaque(false);
        category_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(category_label, gbc);

        gbc.gridy = 7;
        JLabel status_label = new JLabel("Status :");
        status_label.setOpaque(false);
        status_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(status_label, gbc);

        gbc.gridy = 8;
        gbc.insets = new Insets(0, 10, 10, 0);
        CustomComponents.CustomButton close = new CustomComponents.CustomButton("Close",
                merriweather.deriveFont(Font.PLAIN), Color.WHITE, Color.WHITE,
                new Color(56, 53, 70), new Color(73, 69, 87),
                Main.transparent, 0, base_size, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        panel.add(close, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 10, 10);
        JPanel button_panel = new JPanel(new GridBagLayout());
        button_panel.setOpaque(false);
        panel.add(button_panel, gbc);

        gbc.gridx = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        JLabel placeholder = new JLabel("");
        placeholder.setOpaque(false);
        button_panel.add(placeholder, gbc);

        gbc.gridx = 1;
        CustomComponents.CustomButton view = new CustomComponents.CustomButton("",
                merriweather.deriveFont(Font.PLAIN), new Color(255, 255, 255), new Color(209, 209, 209),
                new Color(240, 240, 240, 255), new Color(240, 240, 240),
                Main.transparent, 0, base_size, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        button_panel.add(view, gbc);

        gbc.gridy = 1;
        gbc.weightx = 2;
        gbc.insets = new Insets(0, 0, 10, 10); // retained from above
        JLabel itemID = new JLabel("    " + current_pr.getPurchaseReqID());
        itemID.setOpaque(true);
        itemID.setBackground(Color.WHITE);
        itemID.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        itemID.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(itemID, gbc);

        gbc.gridy = 2;
        JLabel itemName = new JLabel("    " + current_pr.getItemID());
        itemName.setOpaque(true);
        itemName.setBackground(Color.WHITE);
        itemName.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        itemName.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(itemName, gbc);

        gbc.gridy = 3;
        JLabel unitPrice = new JLabel("    " + current_pr.getSupplierID());
        unitPrice.setOpaque(true);
        unitPrice.setBackground(Color.WHITE);
        unitPrice.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        unitPrice.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(unitPrice, gbc);

        gbc.gridy = 4;
        JLabel quantity = new JLabel("    " + current_pr.getQuantity());
        quantity.setOpaque(true);
        quantity.setBackground(Color.WHITE);
        quantity.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        quantity.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(quantity, gbc);

        gbc.gridy = 5;
        JLabel threshold = new JLabel("    " + current_pr.getReqDate());
        threshold.setOpaque(true);
        threshold.setBackground(Color.WHITE);
        threshold.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        threshold.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(threshold, gbc);

        gbc.gridy = 6;
        JLabel category = new JLabel("    " + current_pr.getSalesMgrID());
        category.setOpaque(true);
        category.setBackground(Color.WHITE);
        category.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        category.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(category, gbc);

        gbc.gridy = 7;
        String status_text = (current_pr.getStatus() == 0) ?
                (current_pr.getReqDate().isAfter(LocalDate.now())) ? "Overdue" : "Pending" : "Processed";
        JLabel status = new JLabel("    " + status_text);
        status.setOpaque(true);
        status.setBackground(Color.WHITE);
        status.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        status.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(status, gbc);

        close.addActionListener(_ -> dialog.dispose());

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
    }
}


