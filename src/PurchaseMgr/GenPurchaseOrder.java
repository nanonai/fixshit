package PurchaseMgr;

import Admin.*;
import Classes.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class GenPurchaseOrder {
    private static JFrame parent;
    private static Font merriweather;
    private static JPanel content;
    private static User current_user;
    private static JButton s_btn, p_left, p_right, p_first, p_last, x_btn;
    private static CustomComponents.CustomButton add2, add, add1, delete1;
    private static JLabel lbl_show, lbl_entries, lbl_indicate;
    private static JComboBox<String> entries, pages;
    private static CustomComponents.EmptyTextField search;
    private static CustomComponents.CustomSearchIcon search_icon1, search_icon2;
    private static CustomComponents.CustomArrowIcon left_icon1, left_icon2, right_icon1, right_icon2;
    private static CustomComponents.CustomXIcon icon_clear1, icon_clear2;
    private static CustomComponents.CustomTable table_po;
    public static int list_length = 10, page_counter = 0, mode = 1;
    private static List<PurchaseOrder> po_list;
    private static final Set<Integer> previousSelection = new HashSet<>();

    public static void Loader(JFrame parent, Font merriweather, JPanel content, User current_user) {
        GenPurchaseOrder.parent = parent;
        GenPurchaseOrder.merriweather = merriweather;
        GenPurchaseOrder.content = content;
        GenPurchaseOrder.current_user = current_user;
    }

    public static void ShowPage() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 6;
        gbc.weightx = 1;
        gbc.weighty = 18;
        gbc.insets = new Insets(10, 10, 10, 10);
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
        igbc.insets = new Insets(10, 10, 0, 10);
        lbl_show = new JLabel("Show");
        lbl_show.setFont(merriweather.deriveFont(Font.BOLD, 16));
        lbl_show.setOpaque(false);
        lbl_show.setForeground(new Color(122, 122, 122));
        inner.add(lbl_show, igbc);

        igbc.gridx = 1;
        igbc.insets = new Insets(10, 5, 0, 5);
        String[] entry_item = {"5", "10", "15", "20"};
        entries = new JComboBox<>(entry_item);
        entries.setFont(merriweather.deriveFont(Font.BOLD, 14));
        entries.setForeground(new Color(122, 122, 122));
        entries.setFocusable(false);
        entries.setSelectedItem("10");
        entries.addActionListener(e -> {
            list_length = Integer.parseInt((String) Objects.requireNonNull(entries.getSelectedItem()));
            UpdatePages(list_length);
            page_counter = 0;
            UpdateTable(list_length, page_counter);
        });
        inner.add(entries, igbc);

        igbc.gridx = 2;
        lbl_entries = new JLabel("entries");
        lbl_entries.setFont(merriweather.deriveFont(Font.BOLD, 16));
        lbl_entries.setOpaque(false);
        lbl_entries.setForeground(new Color(122, 122, 122));
        inner.add(lbl_entries, igbc);

        igbc.gridx = 3;
        igbc.weightx = 25;
        JLabel placeholder2 = new JLabel("");
        inner.add(placeholder2, igbc);

        igbc.gridx = 4;
        igbc.weightx = 1;
        CustomComponents.RoundedPanel search_panel = new CustomComponents.RoundedPanel(8, 0, 1, Color.WHITE,
                new Color(209, 209, 209));
        search_panel.setLayout(new GridBagLayout());
        inner.add(search_panel, igbc);

        GridBagConstraints ii_gbc = new GridBagConstraints();
        ii_gbc.gridx = 0;
        ii_gbc.gridy = 0;
        ii_gbc.weightx = 1;
        ii_gbc.weighty = 1;
        ii_gbc.fill = GridBagConstraints.BOTH;
        ii_gbc.insets = new Insets(6, 6, 10, 5);
        search_icon1 = new CustomComponents.CustomSearchIcon(16, 3,
                new Color(122, 122, 122), Color.WHITE);
        search_icon2 = new CustomComponents.CustomSearchIcon(16, 3,
                Color.BLACK, Color.WHITE);
        s_btn = new JButton(search_icon1);
        s_btn.setRolloverIcon(search_icon2);
        s_btn.setBorderPainted(false);
        s_btn.setContentAreaFilled(false);
        s_btn.setFocusPainted(false);
        s_btn.addActionListener(_ -> SearchStuff());
        search_panel.add(s_btn, ii_gbc);

        icon_clear1 = new CustomComponents.CustomXIcon(16, 3,
                new Color(209, 209, 209), true);
        icon_clear2 = new CustomComponents.CustomXIcon(16, 3,
                Color.BLACK, true);

        ii_gbc.gridx = 1;
        ii_gbc.insets = new Insets(6, 0, 8, 0);
        search = new CustomComponents.EmptyTextField(19, "Search...\r\r", new Color(122, 122, 122));
        search.setFont(merriweather.deriveFont(Font.BOLD, 14));
        search.addActionListener(_ -> SearchStuff());
        search.getDocument().addDocumentListener(new DocumentListener() {
            private void update() {
                String text = search.getText();
                boolean isPlaceholder = text.equals(search.GetPlaceHolder());
                x_btn.setVisible(!text.isEmpty() && !isPlaceholder);
                search.UpdateColumns((!text.isEmpty() && !isPlaceholder) ? 17 : 19);
            }

            @Override
            public void insertUpdate(DocumentEvent e) { update(); }

            @Override
            public void removeUpdate(DocumentEvent e) { update(); }

            @Override
            public void changedUpdate(DocumentEvent e) { update(); }
        });
        search.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}

            @Override
            public void focusLost(FocusEvent e) {
                String text = search.getText();
                boolean isPlaceholder = text.equals(search.GetPlaceHolder());
                x_btn.setVisible(!text.isEmpty() && !isPlaceholder);
                search.UpdateColumns((!text.isEmpty() && !isPlaceholder) ? 17 : 19);
            }
        });
        search_panel.add(search, ii_gbc);

        ii_gbc.gridx = 2;
        ii_gbc.insets = new Insets(0, 8, 0, 0);
        x_btn = new JButton(icon_clear1);
        x_btn.setRolloverIcon(icon_clear2);
        x_btn.setFocusable(false);
        x_btn.setBorderPainted(false);
        x_btn.setVisible(false);
        x_btn.addActionListener(_ -> {
            search.Reset();
            x_btn.setVisible(false);
            search.UpdateColumns(19);
            SwingUtilities.invokeLater(lbl_show::requestFocusInWindow);
            SearchStuff();
        });
        search_panel.add(x_btn, ii_gbc);

        igbc.gridwidth = 5;
        igbc.gridx = 0;
        igbc.gridy = 1;
        igbc.weightx = 1;
        igbc.weighty = 4;
        igbc.insets = new Insets(0, 3, 0, 3);
        String[] titles = new String[]{"Id", "ItemID", "Quantity", "SupplierID", "Order Date", "Manager", "Status"};
        po_list = new PurchaseOrder().ListAll();
        Object[][] data = new Object[po_list.size()][titles.length];
        int counter = 0;
        for (PurchaseOrder purchaseOrder : po_list) {
            data[counter] = new Object[]{purchaseOrder.getPurchaseOrderID(), purchaseOrder.getItemID(), purchaseOrder.getPurchaseQuantity(),
                    purchaseOrder.getSupplierID(), purchaseOrder.getOrderDate(), purchaseOrder.getPurchaseMgrID(), purchaseOrder.getStatus()};
            counter += 1;
        }

        table_po = new CustomComponents.CustomTable(titles, data, merriweather.deriveFont(Font.BOLD, 18),
                merriweather.deriveFont(Font.PLAIN, 16), Color.BLACK, Color.BLACK,
                Color.WHITE, new Color(212, 212, 212), 1, 30);

        lbl_indicate = new JLabel("");
        pages = new JComboBox<>();
        UpdateTable(list_length, page_counter);
        UpdatePages(list_length);
        table_po.setShowHorizontalLines(true);
        table_po.setShowVerticalLines(true);
        table_po.setGridColor(new Color(230, 230, 230));

        CustomComponents.CustomScrollPane scrollPane1 = new CustomComponents.CustomScrollPane(false,
                1, table_po,
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

        ii_gbc.gridx = 0;
        ii_gbc.gridy = 0;
        ii_gbc.weightx = 1;
        ii_gbc.weighty = 1;
        ii_gbc.insets = new Insets(0, 0, 0, 0);
        p_first = new JButton("First");
        p_first.setFont(merriweather.deriveFont(Font.BOLD, 16));
        p_first.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        p_first.setForeground(new Color(122, 122, 122));
        p_first.addActionListener(_ -> {
            page_counter = 0;
            pages.setSelectedIndex(0);
            UpdateTable(list_length, page_counter);
            previousSelection.clear();
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
        left_icon1 = new CustomComponents.CustomArrowIcon(16, 3,
                new Color(122, 122, 122), true);
        left_icon2 = new CustomComponents.CustomArrowIcon(16, 3,
                Color.BLACK, true);
        p_left = new JButton(left_icon1);
        p_left.setRolloverIcon(left_icon2);
        p_left.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        p_left.addActionListener(_ -> {
            if (page_counter > 0) {
                page_counter -= 1;
                pages.setSelectedIndex(page_counter);
                UpdateTable(list_length, page_counter);
                previousSelection.clear();
            }
        });
        page_panel.add(p_left, ii_gbc);

        ii_gbc.gridx = 2;
        pages.setFont(merriweather.deriveFont(Font.BOLD, 16));
        pages.setForeground(new Color(122, 122, 122));
        pages.setFocusable(false);
        pages.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        pages.addActionListener(e -> {
            if (pages.getItemCount() > 0) {
                page_counter = pages.getSelectedIndex();
                UpdateTable(list_length, page_counter);
                previousSelection.clear();
            }
        });
        page_panel.add(pages, ii_gbc);

        ii_gbc.gridx = 3;
        right_icon1 = new CustomComponents.CustomArrowIcon(16, 3,
                new Color(122, 122, 122), false);
        right_icon2 = new CustomComponents.CustomArrowIcon(16, 3,
                Color.BLACK, false);
        p_right = new JButton(right_icon1);
        p_right.setRolloverIcon(right_icon2);
        p_right.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        p_right.addActionListener(_ -> {
            if (page_counter < pages.getItemCount() - 1) {
                page_counter += 1;
                pages.setSelectedIndex(page_counter);
                UpdateTable(list_length, page_counter);
                previousSelection.clear();
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
            UpdateTable(list_length, page_counter);
            previousSelection.clear();
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

        igbc.gridwidth = 5;
        igbc.gridx = 0;
        igbc.gridy = 3;
        igbc.insets = new Insets(0, 5, 10, 0);
        JPanel button_panel1 = new JPanel(new GridBagLayout());
        button_panel1.setOpaque(false);
        inner.add(button_panel1, igbc);

        ii_gbc.gridx = 0;
        add = new CustomComponents.CustomButton("Add Purchase Order", merriweather, new Color(255, 255, 255),
                new Color(255, 255, 255), new Color(209, 88, 128), new Color(237, 136, 172),
                Main.transparent, 0, 16, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        add.addActionListener(_ -> {
            AddPurchaseOrder.Loader(parent, merriweather, current_user);
            AddPurchaseOrder.ShowPage();
        });
        button_panel1.add(add, ii_gbc);

        ii_gbc.gridx = 1;
        ii_gbc.insets = new Insets(0,20, 0, 0);
        add1 = new CustomComponents.CustomButton("View Details", merriweather, new Color(255, 255, 255),
                new Color(255, 255, 255), new Color(209, 88, 128), new Color(237, 136, 172),
                Main.transparent, 0, 16, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        add1.addActionListener(_ -> {
            if (table_po.getSelectedRowCount() == 0) {
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
                String selected_id = table_po.getValueAt(table_po.getSelectedRow(),
                        table_po.getColumnModel().getColumnIndex("Id")).toString();
                PurchaseOrderDetails.UpdatePurchaseOrder(new PurchaseOrder().GetObjectByID(selected_id));
                PurchaseOrderDetails.ShowPage();
            }
        });
        button_panel1.add(add1, ii_gbc);

        ii_gbc.gridx = 3;
        ii_gbc.insets = new Insets(0,20, 0, 0);
        add2 = new CustomComponents.CustomButton("Edit Purchase Order", merriweather, new Color(255, 255, 255),
                new Color(255, 255, 255), new Color(209, 88, 128), new Color(237, 136, 172),
                Main.transparent, 0, 16, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        add2.addActionListener(_ -> {
            if (table_po.getSelectedRowCount() == 0) {
                CustomComponents.CustomOptionPane.showErrorDialog(
                        parent,
                        "Please select a PO to Edit!",
                        "Error",
                        new Color(209, 88, 128),
                        new Color(255, 255, 255),
                        new Color(237, 136, 172),
                        new Color(255, 255, 255)
                );
            } else {
                int selectedRow = table_po.getSelectedRow();
                String selected_id = table_po.getValueAt(selectedRow,
                        table_po.getColumnModel().getColumnIndex("Id")).toString();
                String selectedPOID = table_po.getValueAt(selectedRow, 0).toString();
                String status = new PurchaseOrder().GetObjectByID(selectedPOID).getStatus();

                if ("Pending".equalsIgnoreCase(status)) {
                    EditPurchaseOrder.UpdatePurchaseOrder(
                            new PurchaseOrder().GetObjectByID(selected_id));
                    EditPurchaseOrder.ShowPage();
                    po_list = new PurchaseOrder().ListAll();
                    UpdatePages(po_list.size());
                    UpdateTable(list_length, page_counter);

                } else {
                    CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "This Purchase Order has been Approved / Paid / Denied. Cannot edit.",
                            "Cannot Edit",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255)
                    );
                }
            }

        });
        button_panel1.add(add2, ii_gbc);

        ii_gbc.gridx = 4;
        ii_gbc.insets = new Insets(0, 20, 0, 4);
        delete1 = new CustomComponents.CustomButton("Delete Purchase Order", merriweather, Color.WHITE, Color.WHITE,
                new Color(56, 53, 70), new Color(73, 69, 87), null, 0, 16,
                Main.transparent, false, 5, false, null, 0,
                0, 0);
        delete1.addActionListener(_ -> {
            if (table_po.getSelectedRowCount() > 0){
                int selectedRow = table_po.getSelectedRow();
                String selectedPOID = table_po.getValueAt(selectedRow, 0).toString();
                String status = new PurchaseOrder().GetObjectByID(selectedPOID).getStatus();

                if ("Approved".equalsIgnoreCase(status) || "Arrived".equalsIgnoreCase(status) || "Paid".equalsIgnoreCase(status)) {
                    CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "This Purchase Order cannot be deleted.",
                            "Cannot Delete",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255)
                    );
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(parent, "Are you sure you want to delete PurchaseOrderID: " + selectedPOID + "?",
                        "Confirm Delete", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    new PurchaseOrder().Remove(new PurchaseOrder().GetObjectByID(selectedPOID));
                    po_list = new PurchaseOrder().ListAll();
                    UpdatePages(po_list.size());
                    UpdateTable(list_length, page_counter);
                }
            }
            else {
                CustomComponents.CustomOptionPane.showErrorDialog(parent, "Please select an ID to delete", "Error!",
                        new Color(209, 88, 128),
                        new Color(255, 255, 255),
                        new Color(237, 136, 172),
                        new Color(255, 255, 255));
            }
        });
        button_panel1.add(delete1, ii_gbc);
    }

    public static void UpdateTable(int length, int page) {
        String[] titles = new String[]{"Id", "ItemID", "Quantity", "SupplierID", "Order Date", "Manager", "Status"};
        Object[][] data;
        int counter = 0;
        int anti_counter = page * length;
        if (length >= po_list.size() - page * length) {
            data = new Object[po_list.size() - page * length][titles.length];
        } else {
            data = new Object[length][titles.length];
        }
        for (PurchaseOrder purchaseOrder : po_list) {
            if (anti_counter != 0) {
                anti_counter -= 1;
            } else {
                data[counter] = new Object[]{purchaseOrder.getPurchaseOrderID(), purchaseOrder.getItemID(), purchaseOrder.getPurchaseQuantity(),
                        purchaseOrder.getSupplierID(), purchaseOrder.getOrderDate(), purchaseOrder.getPurchaseMgrID(), purchaseOrder.getStatus()};
                counter += 1;
                if (counter == length || counter == po_list.size()) { break; }
            }
        }
        table_po.UpdateTableContent(titles, data);
        String temp2 = "<html>Displaying <b>%s</b> to <b>%s</b> of <b>%s</b> records</html>";
        if (length >= po_list.size()) {
            lbl_indicate.setText(String.format(temp2, (!po_list.isEmpty()) ? 1 : 0, po_list.size(),
                    po_list.size()));
        } else {
            lbl_indicate.setText(String.format(temp2, page * length + 1,
                    Math.min((page + 1) * length, po_list.size()),
                    po_list.size()));
        }
    }

    public static void UpdatePages(int length) {
        int pageCount = (int) Math.ceil(po_list.size() / (double) length);
        if (po_list.size() <= length) {
            pageCount = 1;
        }
        pages.removeAllItems();
        for (int i = 1; i <= pageCount; i++) {
            pages.addItem(String.format("Page %s of %s", i, pageCount));
        }
        pages.repaint();
        pages.revalidate();
    }

    public static void SearchStuff() {
        String searcher = (!search.getText().isEmpty() && !Objects.equals(search.getText(), "Search...\r\r")) ?
                search.getText() : "";
        List<PurchaseOrder> temp_user_list = new PurchaseOrder().ListAllWithFilter(searcher);
        if (temp_user_list.isEmpty()) {
            CustomComponents.CustomOptionPane.showInfoDialog(
                    parent,
                    "No results found.",
                    "Notification",
                    new Color(88, 149, 209),
                    new Color(255, 255, 255),
                    new Color(125, 178, 228),
                    new Color(255, 255, 255)
            );
        } else {
            po_list = temp_user_list;
            page_counter = 0;
            UpdatePages(list_length);
            UpdateTable(list_length, page_counter);
            SwingUtilities.invokeLater(table_po::requestFocusInWindow);
        }
    }

    public static void UpdateComponentSize(int base_size) {
        search_icon1.UpdateSize((int) (base_size * 0.8));
        search_icon2.UpdateSize((int) (base_size * 0.8));
        s_btn.setSize(search_icon1.getIconWidth(), search_icon1.getIconHeight());
        s_btn.repaint();
        icon_clear1.UpdateSize(base_size);
        icon_clear2.UpdateSize(base_size);
        x_btn.setSize(icon_clear1.getIconWidth(), icon_clear1.getIconHeight());
        x_btn.repaint();
        left_icon1.UpdateSize(base_size);
        left_icon2.UpdateSize(base_size);
        p_left.setSize(left_icon1.getIconWidth(), left_icon1.getIconHeight());
        p_left.repaint();
        right_icon1.UpdateSize(base_size);
        right_icon2.UpdateSize(base_size);
        p_right.setSize(right_icon1.getIconWidth(), right_icon1.getIconHeight());
        p_right.repaint();
        p_first.setFont(merriweather.deriveFont(Font.BOLD, (int) (base_size * 0.8)));
        p_last.setFont(merriweather.deriveFont(Font.BOLD, (int) (base_size * 0.8)));
        pages.setFont(merriweather.deriveFont(Font.BOLD, (int) (base_size * 0.8)));
        lbl_show.setFont(merriweather.deriveFont(Font.BOLD, (int) (base_size * 0.9)));
        lbl_entries.setFont(merriweather.deriveFont(Font.BOLD, (int) (base_size * 0.9)));
        lbl_indicate.setFont(merriweather.deriveFont(Font.BOLD, (int) (base_size * 0.9)));
        entries.setFont(merriweather.deriveFont(Font.BOLD, (int) (base_size * 0.85)));
        pages.setFont(merriweather.deriveFont(Font.BOLD, (int) (base_size * 0.85)));
        search.setFont(merriweather.deriveFont(Font.BOLD, (int) (base_size * 0.85)));
        table_po.SetChanges(merriweather.deriveFont(Font.BOLD, (int) (base_size * 0.95)),
                merriweather.deriveFont(Font.PLAIN, (int) (base_size * 0.9)), mode);
        add2.UpdateCustomButton(0, (int) (base_size * 0.9), null, 0);
        add.UpdateCustomButton(0, (int) (base_size * 0.9), null, 0);
        add1.UpdateCustomButton(0, (int) (base_size * 0.9), null, 0);
        delete1.UpdateCustomButton(0, (int) (base_size * 0.9), null, 0);
    }
}