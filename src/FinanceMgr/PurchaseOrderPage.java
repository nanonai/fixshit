package FinanceMgr;
import Admin.*;
import Classes.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

public class PurchaseOrderPage {
    private static JFrame parent;
    private static Font merriweather;
    private static JPanel content;
    public static User current_user;
    private static JLabel lbl_indicate;
    private static JComboBox<String> entries;
    private static CustomComponents.CustomTable table_purOrder;
    private static int list_length = 10, page_counter = 0;
    private static JComboBox<String>  pages;
    private static List<PurchaseOrder> purchaseOrder_List;
    private static JButton p_first;
    private static JButton p_last;

    public static void Loader(JFrame parent,Font merriweather,
                              JPanel content,User current_user){
        PurchaseOrderPage.parent = parent;
        PurchaseOrderPage.merriweather = merriweather;
        PurchaseOrderPage.content = content;
        PurchaseOrderPage.current_user = current_user;

    }
    public  static void ShowPage(){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.gridx = 6;
        gbc.weightx = 14;
        gbc.insets = new Insets(0, 0, 0, 20);
        JLabel placeholder1 = new JLabel("");
        content.add(placeholder1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 10;
        gbc.weightx = 1;
        gbc.weighty = 18;
        gbc.insets = new Insets(0, 20, 20, 20);
        JPanel inner = new JPanel(new GridBagLayout());
        inner.setOpaque(true);
        inner.setBackground(Color.WHITE);
        content.add(inner, gbc);

        GridBagConstraints igbc = new GridBagConstraints();
        igbc.gridx = 0;
        igbc.gridy = 0;
        igbc.weightx = 1;
        igbc.weighty = 1;
        igbc.fill = GridBagConstraints.BOTH;
        igbc.insets = new Insets(10, 10, 10, 10);

        JLabel lbl_show = new JLabel("Show");
        lbl_show.setFont(merriweather.deriveFont(Font.BOLD, 16));
        lbl_show.setOpaque(false);
        lbl_show.setForeground(new Color(122, 122, 122));
        inner.add(lbl_show, igbc);

        igbc.gridx = 1;
        igbc.insets = new Insets(10, 5, 10, 5);
        String[] entry_item = {"5", "10", "15", "20"};
        entries = new JComboBox<>(entry_item);
        entries.setFont(merriweather.deriveFont(Font.BOLD, 14));
        entries.setForeground(new Color(122, 122, 122));
        entries.setFocusable(false);
        entries.setSelectedItem("10");
        entries.addActionListener(_ -> {
            list_length = Integer.parseInt((String) Objects.requireNonNull(entries.getSelectedItem()));
            UpdatePOPages(list_length);
            page_counter = 0;
            UpdatePOTable(list_length, page_counter);
        });
        inner.add(entries, igbc);

        igbc.gridx = 2;
        JLabel lbl_entries = new JLabel("entries");
        lbl_entries.setFont(merriweather.deriveFont(Font.BOLD, 16));
        lbl_entries.setOpaque(false);
        lbl_entries.setForeground(new Color(122, 122, 122));
        inner.add(lbl_entries, igbc);

        igbc.gridx = 3;
        igbc.weightx = 25;
        JLabel placeholder2 = new JLabel("");
        inner.add(placeholder2, igbc);

        igbc.gridx = 4;
        igbc.weightx = 10;
        JLabel placeholder3 = new JLabel("");
        inner.add(placeholder3, igbc);

        igbc.gridwidth = 5;
        igbc.gridx = 0;
        igbc.gridy = 1;
        igbc.weightx = 1;
        igbc.weighty = 10;
        igbc.insets = new Insets(0, 0, 10, 0);
        String[] titles = new String[]{"PurchaseOrderID", "ItemID","SupplierID","PurchaseQuantity",
                "TotalAmt","OrderDate","PurchaseMgrID","Status"};
        purchaseOrder_List = new PurchaseOrder().ListAll();
        Object[][] data = new Object[purchaseOrder_List.size()][titles.length];
        int counter = 0;
        for (PurchaseOrder purchaseOrder : purchaseOrder_List) {
            data[counter] = new Object[]{purchaseOrder.getPurchaseOrderID(), purchaseOrder.getItemID(), purchaseOrder.getSupplierID(),
                    purchaseOrder.getPurchaseQuantity(), purchaseOrder.getTotalAmt(), purchaseOrder.getOrderDate(),
                    purchaseOrder.getPurchaseMgrID(), purchaseOrder.getStatus()};
            counter += 1;
        }

        table_purOrder = new CustomComponents.CustomTable(titles, data, merriweather.deriveFont(Font.BOLD, 18),
                merriweather.deriveFont(Font.PLAIN, 16), Color.BLACK, Color.BLACK,
                Color.WHITE, new Color(212, 212, 212), 1, 30);
        lbl_indicate = new JLabel("");
        pages = new JComboBox<>();
        UpdatePOTable(list_length, page_counter);
        UpdatePOPages(list_length);
        table_purOrder.setShowHorizontalLines(true);
        table_purOrder.setShowVerticalLines(true);
        table_purOrder.setGridColor(new Color(230, 230, 230));

        CustomComponents.CustomScrollPane scrollPane1 = new CustomComponents.CustomScrollPane(false, 1, table_purOrder,
                6, new Color(202, 202, 202), Main.transparent,
                Main.transparent, Main.transparent, Main.transparent,
                new Color(170, 170, 170), Color.WHITE,
                new Color(140, 140, 140), new Color(110, 110, 110),
                Color.WHITE, Color.WHITE, 6);
        inner.add(scrollPane1, igbc);

        igbc.gridwidth = 4;
        igbc.gridx = 0;
        igbc.gridy = 2;
        igbc.weighty = 1;
        igbc.insets = new Insets(0, 10, 2, 0);
        lbl_indicate.setFont(merriweather.deriveFont(Font.BOLD, 16));
        lbl_indicate.setOpaque(false);
        lbl_indicate.setForeground(new Color(122, 122, 122));
        inner.add(lbl_indicate, igbc);

        igbc.gridwidth = 1;
        igbc.gridx = 4;
        igbc.insets = new Insets(0, 0, 2, 5);
        JPanel page_panel = new JPanel(new GridBagLayout());
        page_panel.setOpaque(false);
        inner.add(page_panel, igbc);

        GridBagConstraints ii_gbc = new GridBagConstraints();
        ii_gbc.gridx = 0;
        ii_gbc.gridy = 0;
        ii_gbc.weightx = 1;
        ii_gbc.weighty = 1;
        ii_gbc.insets = new Insets(0, 0, 0, 0);
        ii_gbc.fill = GridBagConstraints.BOTH;
        p_first = new JButton("First");
        p_first.setFont(merriweather.deriveFont(Font.BOLD, 16));
        p_first.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        p_first.setForeground(new Color(122, 122, 122));
        p_first.addActionListener(_ -> {
            page_counter = 0;
            pages.setSelectedIndex(0);
            UpdatePOTable(list_length, page_counter);
        });
        p_first.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                p_first.setForeground(Color.BLACK);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                p_first.setForeground(new Color(122, 122, 122));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                p_first.setForeground(new Color(122, 122, 122));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                Point point = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), p_first);
                if (p_first.contains(point)) {
                    p_first.setForeground(Color.BLACK);
                } else {
                    p_first.setForeground(new Color(122, 122, 122));
                }
            }
        });
        page_panel.add(p_first, ii_gbc);

        ii_gbc.gridx = 1;
        CustomComponents.CustomArrowIcon left_icon1 = new CustomComponents.CustomArrowIcon(16, 3,
                new Color(122, 122, 122), true);
        CustomComponents.CustomArrowIcon left_icon2 = new CustomComponents.CustomArrowIcon(16, 3,
                Color.BLACK, true);
        JButton p_left = new JButton(left_icon1);
        p_left.setRolloverIcon(left_icon2);
        p_left.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        p_left.addActionListener(_ -> {
            if (page_counter > 0) {
                page_counter -= 1;
                pages.setSelectedIndex(page_counter);
                UpdatePOTable(list_length, page_counter);
            }
        });
        page_panel.add(p_left, ii_gbc);

        ii_gbc.gridx = 2;
        pages.setFont(merriweather.deriveFont(Font.BOLD, 16));
        pages.setForeground(new Color(122, 122, 122));
        pages.setFocusable(false);
        pages.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        pages.addActionListener(_ -> {
            if (pages.getItemCount() > 0) {
                page_counter = pages.getSelectedIndex();
                UpdatePOTable(list_length, page_counter);
            }
        });
        page_panel.add(pages, ii_gbc);

        ii_gbc.gridx = 3;
        CustomComponents.CustomArrowIcon right_icon1 = new CustomComponents.CustomArrowIcon(16, 3,
                new Color(122, 122, 122), false);
        CustomComponents.CustomArrowIcon right_icon2 = new CustomComponents.CustomArrowIcon(16, 3,
                Color.BLACK, false);
        JButton p_right = new JButton(right_icon1);
        p_right.setRolloverIcon(right_icon2);
        p_right.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        p_right.addActionListener(_ -> {
            if (page_counter < pages.getItemCount() - 1) {
                page_counter += 1;
                pages.setSelectedIndex(page_counter);
                UpdatePOTable(list_length, page_counter);
            }
        });
        page_panel.add(p_right, ii_gbc);

        ii_gbc.gridx = 4;
        p_last = new JButton("Last");
        p_last.setFont(merriweather.deriveFont(Font.BOLD, 16));
        p_last.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        p_last.setForeground(new Color(122, 122, 122));
        p_last.addActionListener(_ -> {
            page_counter = pages.getItemCount() - 1;
            pages.setSelectedIndex(page_counter);
            UpdatePOTable(list_length, page_counter);
        });
        p_last.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                p_last.setForeground(Color.BLACK);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                p_last.setForeground(new Color(122, 122, 122));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                p_last.setForeground(new Color(122, 122, 122));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                Point point = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), p_last);
                if (p_last.contains(point)) {
                    p_last.setForeground(Color.BLACK);
                } else {
                    p_last.setForeground(new Color(122, 122, 122));
                }
            }
        });
        page_panel.add(p_last, ii_gbc);

        igbc.gridwidth = 4;
        igbc.gridx = 0;
        igbc.gridy = 3;
        igbc.insets = new Insets(0, 5, 10, 0);
        JPanel button_panel1 = new JPanel(new GridBagLayout());
        button_panel1.setOpaque(false);
        inner.add(button_panel1, igbc);
        ii_gbc.gridx = 0;

        igbc.gridwidth = 1;
        igbc.gridx = 4;
        igbc.insets = new Insets(0, 0, 10, 5);
        JPanel button_panel2 = new JPanel(new GridBagLayout());
        button_panel2.setOpaque(false);
        inner.add(button_panel2, igbc);

        ii_gbc.gridx = 1;
        CustomComponents.CustomButton viewPO = new CustomComponents.CustomButton("View Purchase Order", merriweather,
                new Color(255, 255, 255),
                new Color(255, 255, 255),
                new Color(225, 108, 150),
                new Color(237, 136, 172),
                Main.transparent, 0, 16, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        viewPO.addActionListener(_ -> {
            if (table_purOrder.getSelectedRowCount() == 0) {
                CustomComponents.CustomOptionPane.showErrorDialog(
                        parent,
                        "Please select a PO to View!",
                        "Error",
                        new Color(209, 88, 128),
                        new Color(255, 255, 255),
                        new Color(237, 136, 172),
                        new Color(255, 255, 255)
                );
            } else {
                String selected_id = table_purOrder.getValueAt(table_purOrder.getSelectedRow(),
                        table_purOrder.getColumnModel().getColumnIndex("PurchaseOrderID")).toString();
                ViewPurchaseOrder.UpdatePurchaseOrder(new PurchaseOrder().GetObjectByID(selected_id));
                ViewPurchaseOrder.ShowPage();
            }
        });
        button_panel1.add(viewPO, ii_gbc);

        ii_gbc.gridx = 2;
        CustomComponents.CustomButton updateStatus = new CustomComponents.CustomButton(
                "Modify PO", merriweather, new Color(255, 255, 255),
                new Color(236, 227, 227), new Color(158, 47, 84), new Color(237, 136, 172),
                Main.transparent, 0, 16, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        updateStatus.addActionListener(_ -> {
            if (table_purOrder.getSelectedRowCount() == 0) {
                CustomComponents.CustomOptionPane.showErrorDialog(
                        parent,
                        "Please select a PO to update status!",
                        "Error",
                        new Color(209, 88, 128),
                        new Color(255, 255, 255),
                        new Color(237, 136, 172),
                        new Color(255, 255, 255)
                );
            }else {
                int selectedRow = table_purOrder.getSelectedRow();
                String status = table_purOrder.getValueAt(
                        selectedRow,
                        table_purOrder.getColumnModel().getColumnIndex("Status")
                ).toString();
                switch (status) {
                    case "Paid" -> CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "The PO has been Paid can not modify!",
                            "Error",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255)
                    );
                    case "Arrived" -> CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "The PO has Arrived can not modify!",
                            "Error",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255)
                    );
                    case "Completed" -> CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "The PO has completed can not modify!",
                            "Error",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255)
                    );
                    default -> {
                        String selected_id = table_purOrder.getValueAt(table_purOrder.getSelectedRow(),
                                table_purOrder.getColumnModel().getColumnIndex("PurchaseOrderID")).toString();
                        ModifyPO.UpdatePO(new PurchaseOrder().GetObjectByID(selected_id));
                        ModifyPO.ShowPage();
                    }
                }
            }
        });
        button_panel1.add(updateStatus, ii_gbc);

        ii_gbc.gridx = 3;
        CustomComponents.CustomButton makePayment = new CustomComponents.CustomButton("Make Payment", merriweather, new Color(255, 255, 255),
                new Color(255, 255, 255), new Color(225, 108, 150), new Color(237, 136, 172),
                Main.transparent, 0, 16, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        makePayment.addActionListener(_ -> {
            if (table_purOrder.getSelectedRowCount() == 0) {
                CustomComponents.CustomOptionPane.showErrorDialog(
                        parent,
                        "Please select a PO for Payment!",
                        "Error",
                        new Color(209, 88, 128),
                        new Color(255, 255, 255),
                        new Color(237, 136, 172),
                        new Color(255, 255, 255)
                );
                return;
            }
            int selectedRow = table_purOrder.getSelectedRow();
            String status = table_purOrder.getValueAt(
                    selectedRow,
                    table_purOrder.getColumnModel().getColumnIndex("Status")
            ).toString();

            if (status.equals("Arrived")) {
                String selected_id = table_purOrder.getValueAt(
                        selectedRow,
                        table_purOrder.getColumnModel().getColumnIndex("PurchaseOrderID")
                ).toString();

                MakePayment.UpdatePO(new PurchaseOrder().GetObjectByID(selected_id));
                boolean see = MakePayment.ShowPage();
                if (see) {
                    System.out.println("Payment interface displayed.");
                }
            } else if(status.equals("Paid")) {
                CustomComponents.CustomOptionPane.showErrorDialog(
                        parent,
                        "The Purchase Order has been Paid !!",
                        "Error",
                        new Color(209, 88, 128),
                        new Color(255, 255, 255),
                        new Color(237, 136, 172),
                        new Color(255, 255, 255)
                );
            }else{
                CustomComponents.CustomOptionPane.showErrorDialog(
                        parent,
                        "The Purchase Order hasn't arrived, hence cannot make Payment!",
                        "Error",
                        new Color(209, 88, 128),
                        new Color(255, 255, 255),
                        new Color(237, 136, 172),
                        new Color(255, 255, 255)
                );}
        });
        button_panel1.add(makePayment, ii_gbc);

        ViewPurchaseOrder.Loader(parent, merriweather);
        ModifyPO.Loader(parent, merriweather);
        MakePayment.Loader(parent, merriweather, current_user);
    }
    public static void UpdatePOTable(int length, int page) {
        String[] titles = new String[]{"PurchaseOrderID", "ItemID", "SupplierID", "PurchaseQuantity", "TotalAmt", "OrderDate", "PurchaseMgrID","Status"};
        Object[][] data;
        int counter = 0;
        int anti_counter = page * length;
        if (length >= purchaseOrder_List.size() - page * length) {
            data = new Object[purchaseOrder_List.size() - page * length][titles.length];
        } else {
            data = new Object[length][titles.length];
        }
        for (PurchaseOrder purchaseOrder : purchaseOrder_List) {
            if (anti_counter != 0) {
                anti_counter -= 1;
            } else {
                data[counter] = new Object[]{purchaseOrder.getPurchaseOrderID(), purchaseOrder.getItemID(),
                        purchaseOrder.getSupplierID(), purchaseOrder.getPurchaseQuantity(), purchaseOrder.getTotalAmt(),
                        purchaseOrder.getOrderDate(), purchaseOrder.getPurchaseMgrID(), purchaseOrder.getStatus()};
                counter += 1;
                if (counter == length || counter == purchaseOrder_List.size()) { break; }
            }
        }
        table_purOrder.UpdateTableContent(titles, data);
        String temp2 = "<html>Displaying <b>%s</b> to <b>%s</b> of <b>%s</b> records</html>";
        if (length >= purchaseOrder_List.size()) {
            lbl_indicate.setText(String.format(temp2, (!purchaseOrder_List.isEmpty()) ? 1 : 0, purchaseOrder_List.size(),
                    purchaseOrder_List.size()));
        } else {
            lbl_indicate.setText(String.format(temp2, page * length + 1,
                    Math.min((page + 1) * length, purchaseOrder_List.size()),
                    purchaseOrder_List.size()));
        }
    }

    public static void UpdatePOPages(int length) {
        int pageCount = (int) Math.ceil(purchaseOrder_List.size() / (double) length);
        if (purchaseOrder_List.size() <= length) {
            pageCount = 1;
        }
        pages.removeAllItems();
        for (int i = 1; i <= pageCount; i++) {
            pages.addItem(String.format("Page %s of %s", i, pageCount));
        }
        pages.repaint();
        pages.revalidate();
    }
}