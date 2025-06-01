package FinanceMgr;

import Admin.*;
import Classes.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicBoolean;
import java.time.LocalDate;

public class MakePayment {
    private static JFrame parent;
    private static Font merriweather;
    private static PurchaseOrder current_PO;
    private static Payment paymentDetail;
    private static User current_user;

    public static void Loader(JFrame parent, Font merriweather, User current_user) {
        MakePayment.parent = parent;
        MakePayment.merriweather = merriweather;
        MakePayment.current_user = current_user;
    }

    public static void UpdatePO(PurchaseOrder purchaseOrder) {
        MakePayment.current_PO = purchaseOrder;
    }

    public static boolean ShowPage() {
        AtomicBoolean view_or_not = new AtomicBoolean(false);
        JDialog dialog = new JDialog(parent, "Make Payment", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(parent.getWidth() / 2, (int) (parent.getHeight() / 1.5));
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(parent);

        int size_factor = 0;
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
        JLabel title = new JLabel("Make Payment");
        title.setOpaque(false);
        title.setFont(merriweather.deriveFont(Font.BOLD, (float) (base_size * 1.3)));
        panel.add(title, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 10, 10, 0);
        JLabel purchaseOrderID_label = new JLabel("Purchase OrderID :");
        purchaseOrderID_label.setOpaque(false);
        purchaseOrderID_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(purchaseOrderID_label, gbc);

        gbc.gridy = 2;
        JLabel itemID_label = new JLabel("ItemID :");
        itemID_label.setOpaque(false);
        itemID_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(itemID_label, gbc);

        gbc.gridy = 3;
        JLabel supplierID_label = new JLabel("SupplierID :");
        supplierID_label.setOpaque(false);
        supplierID_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(supplierID_label, gbc);

        gbc.gridy = 4;
        JLabel quantity_label = new JLabel("PurchaseQuantity :");
        quantity_label.setOpaque(false);
        quantity_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(quantity_label, gbc);

        gbc.gridy = 5;
        JLabel TotalAmt_label = new JLabel("TotalAmt :");
        TotalAmt_label.setOpaque(false);
        TotalAmt_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(TotalAmt_label, gbc);

        gbc.gridy = 6;
        JLabel orderDate_label = new JLabel("OrderDate :");
        orderDate_label.setOpaque(false);
        orderDate_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(orderDate_label, gbc);

        gbc.gridy = 7;
        JLabel purchaseManID_label = new JLabel("PurchaseMgrID :");
        purchaseManID_label.setOpaque(false);
        purchaseManID_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(purchaseManID_label, gbc);

        gbc.gridy = 8;
        JLabel status_label = new JLabel("Status :");
        status_label.setOpaque(false);
        status_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(status_label, gbc);

        gbc.gridy = 9;
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
        CustomComponents.CustomButton makePayment = new CustomComponents.CustomButton("Make Payment",
                merriweather.deriveFont(Font.PLAIN), new Color(255, 255, 255), new Color(255, 255, 255),
                new Color(209, 88, 128), new Color(237, 136, 172),
                Main.transparent, 0, base_size, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        button_panel.add(makePayment, gbc);

        gbc.gridy = 1;
        gbc.weightx = 2;
        gbc.insets = new Insets(0, 0, 10, 10);
        JLabel purchaseOrderID = new JLabel("    " + current_PO.getPurchaseOrderID());
        purchaseOrderID.setOpaque(true);
        purchaseOrderID.setBackground(Color.WHITE);
        purchaseOrderID.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        purchaseOrderID.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(purchaseOrderID, gbc);

        gbc.gridy = 2;
        JLabel itemID = new JLabel("    " + current_PO.getItemID());
        itemID.setOpaque(true);
        itemID.setBackground(Color.WHITE);
        itemID.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        itemID.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(itemID, gbc);

        gbc.gridy = 3;
        JLabel supplierID = new JLabel("    " + current_PO.getSupplierID());
        supplierID.setOpaque(true);
        supplierID.setBackground(Color.WHITE);
        supplierID.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        supplierID.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(supplierID, gbc);

        gbc.gridy = 4;
        JLabel purchaseQuantity = new JLabel("    " + current_PO.getPurchaseQuantity());
        purchaseQuantity.setOpaque(true);
        purchaseQuantity.setBackground(Color.WHITE);
        purchaseQuantity.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        purchaseQuantity.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(purchaseQuantity, gbc);

        gbc.gridy = 5;
        JLabel totalAmt = new JLabel("    " + current_PO.getTotalAmt());
        totalAmt.setOpaque(true);
        totalAmt.setBackground(Color.WHITE);
        totalAmt.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        totalAmt.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(totalAmt, gbc);

        gbc.gridy = 6;
        JLabel orderDate = new JLabel("    " + current_PO.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        orderDate.setOpaque(true);
        orderDate.setBackground(Color.WHITE);
        orderDate.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        orderDate.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(orderDate, gbc);

        gbc.gridy = 7;
        JLabel purchaseMgrID = new JLabel("    " + current_PO.getPurchaseMgrID());
        purchaseMgrID.setOpaque(true);
        purchaseMgrID.setBackground(Color.WHITE);
        purchaseMgrID.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        purchaseMgrID.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(purchaseMgrID, gbc);

        gbc.gridy = 8;
        JLabel status = new JLabel("    " + current_PO.getStatus());
        status.setOpaque(true);
        status.setBackground(Color.WHITE);
        status.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        status.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(status, gbc);

        gbc.gridy = 7;
        JPanel inner = new JPanel(new GridBagLayout());
        inner.setOpaque(false);
        panel.add(inner, gbc);

        makePayment.addActionListener(_ -> {
            String payment_id = new Payment().IdMaker();
            String po_id = current_PO.getPurchaseOrderID();
            double amountPaid = current_PO.getTotalAmt();
            LocalDate date = LocalDate.now();
            String financeMgrID = current_user.getUserID();
            paymentDetail = new Payment(payment_id, po_id, amountPaid, date, financeMgrID);
            new Payment().AddNew(paymentDetail);
            String updatedStatus = "Paid";
            current_PO.setStatus(updatedStatus);

            PurchaseOrder po = new PurchaseOrder(
                    current_PO.getPurchaseOrderID(),
                    current_PO.getItemID(),
                    current_PO.getSupplierID(),
                    current_PO.getPurchaseQuantity(),
                    current_PO.getTotalAmt(),
                    current_PO.getOrderDate(),
                    current_PO.getPurchaseMgrID(),
                    updatedStatus
            );
            new PurchaseOrder().Modify(current_PO.getPurchaseOrderID(), po);
            CustomComponents.CustomOptionPane.showErrorDialog(
                    parent,
                    "Payment successfully!",
                    "Payment",
                    new Color(209, 88, 128),
                    new Color(255, 255, 255),
                    new Color(237, 136, 172),
                    new Color(255, 255, 255)
            );

            view_or_not.set(true);
            dialog.dispose();
        });

        close.addActionListener(_ -> {
            dialog.dispose();
        });

        makePayment.addActionListener(_ -> {
            view_or_not.set(true);
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

        return view_or_not.get();
    }

}

