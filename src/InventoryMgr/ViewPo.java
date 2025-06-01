package InventoryMgr;

import Classes.*;
import Admin.*;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class ViewPo {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content;
    private static User current_user;
    private static int indicator, base_size;
    private static java.util.List<PurchaseOrder> AllPO;
    private static JList POList;
    private static JButton s_btn,clearbtn,p_first,p_left,p_right,p_last;
    private static JDialog dialogAdd, dialogDelete, dialogEdit;
    private static CustomComponents.CustomButton btnRec,btnDelete,btnEdit;
    private static CustomComponents.CustomScrollPane scrollPane1;
    private static CustomComponents.CustomSearchIcon search_icon1, search_icon2;
    private static CustomComponents.CustomXIcon icon_clear1, icon_clear2;
    private static CustomComponents.EmptyTextField search;
    private static CustomComponents.CustomTable POTable;
    private static CustomComponents.CustomArrowIcon right_icon1,right_icon2, left_icon1, left_icon2;
    private static JLabel lbl_show, lbl_entries,lbl_indicate;
    private static JComboBox entries,pages;
    private static int list_length = 10, page_counter = 0, filter = 0, mode = 1;

    public static void Loader(JFrame parent, Font merriweather, Font boldonse, JPanel content, User current_user) {
        ViewPo.parent = parent;
        ViewPo.merriweather = merriweather;
        ViewPo.boldonse = boldonse;
        ViewPo.content = content;
        ViewPo.current_user = current_user;
    }

    public static void ShowPage() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 0, 10);
        lbl_show = new JLabel("Show");
        lbl_show.setFont(merriweather.deriveFont(Font.BOLD, 16));
        lbl_show.setOpaque(false);
        lbl_show.setForeground(new Color(122, 122, 122));
        content.add(lbl_show, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(10, 5, 0, 5);
        String[] entry_item = {"5", "10", "15", "20"};
        entries = new JComboBox<>(entry_item);
        entries.setFont(merriweather.deriveFont(Font.BOLD, 14));
        entries.setForeground(new Color(122, 122, 122));
        entries.setFocusable(false);
        entries.setSelectedItem("10");
        entries.addActionListener(e -> {
            list_length = Integer.parseInt((String) Objects.requireNonNull(entries.getSelectedItem()));
            UpdatePages(AllPO.size());
            page_counter = 0;
            UpdateTable(AllPO, list_length, page_counter);

        });
        content.add(entries, gbc);

        gbc.gridx = 2;
        lbl_entries = new JLabel("entries");
        lbl_entries.setFont(merriweather.deriveFont(Font.BOLD, 16));
        lbl_entries.setOpaque(false);
        lbl_entries.setForeground(new Color(122, 122, 122));
        content.add(lbl_entries, gbc);

        gbc.gridx = 3;
        gbc.weightx = 25;
        JLabel placeholder1 = new JLabel("");
        content.add(placeholder1, gbc);

        gbc.gridx = 4;
        gbc.weightx = 1;
        JLabel placeholder2 = new JLabel("");
        content.add(placeholder2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 6;
        gbc.gridwidth = 6;
        gbc.fill = GridBagConstraints.BOTH;

        AllPO = new PurchaseOrder().ListAll();
        String[] titles = new String[]{"PurchaseOrderID", "ItemID", "PurchaseQuantity", "SupplierID", "OrderDate", "PurchaseMgrID", "Status"};
        Object[][] data = new Object[AllPO.size()][titles.length];
        int counter = 0;
        for (PurchaseOrder purchaseOrder : AllPO) {
            data[counter] = new Object[]{purchaseOrder.getPurchaseOrderID(), purchaseOrder.getItemID(), purchaseOrder.getPurchaseQuantity(),
                    purchaseOrder.getSupplierID(), purchaseOrder.getOrderDate(), purchaseOrder.getPurchaseMgrID(), purchaseOrder.getStatus()};
            counter += 1;
        }

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        POTable = new CustomComponents.CustomTable(titles, data, merriweather.deriveFont(Font.BOLD, 18), merriweather.deriveFont(Font.PLAIN, 16), Color.BLACK, Color.BLACK,
                Color.WHITE, new Color(212, 212, 212), 1, 30);
        POTable.setShowHorizontalLines(true);
        POTable.setShowVerticalLines(true);
        POTable.setGridColor(new Color(230, 230, 230));

        lbl_indicate = new JLabel("");
        lbl_indicate.setFont(merriweather.deriveFont(Font.BOLD, 16));
        lbl_indicate.setOpaque(false);
        lbl_indicate.setForeground(new Color(122, 122, 122));

        pages = new JComboBox<>();
        UpdateTable(AllPO, list_length, page_counter);
        UpdatePages(AllPO.size());
        POTable.setShowHorizontalLines(true);
        POTable.setShowVerticalLines(true);
        POTable.setGridColor(new Color(230, 230, 230));

        scrollPane1 = new CustomComponents.CustomScrollPane(false, 1, POTable,
                6, new Color(202, 202, 202), Main.transparent,
                Main.transparent, Main.transparent, Main.transparent,
                new Color(170, 170, 170), Color.WHITE,
                new Color(140, 140, 140), new Color(110, 110, 110),
                Color.WHITE, Color.WHITE, 6);
        content.add(scrollPane1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weighty = 0.5;
        gbc.insets = new Insets(0, 2, 2, 0);
        content.add(lbl_indicate, gbc);

        gbc.gridx = 4;
        gbc.gridy = 2;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        JPanel page_panel = new JPanel(new GridBagLayout());
        page_panel.setOpaque(false);
        content.add(page_panel, gbc);

        GridBagConstraints ii_gbc = new GridBagConstraints();

        ii_gbc.gridx = 0;
        ii_gbc.gridy = 0;
        ii_gbc.fill = GridBagConstraints.BOTH;
        ii_gbc.weightx = 1;
        ii_gbc.weighty = 1;
        p_first = new JButton("First");
        p_first.setFont(merriweather.deriveFont(Font.BOLD, 16));
        p_first.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        p_first.setPreferredSize(new Dimension(50, 27));
        p_first.setForeground(new Color(122, 122, 122));
        p_first.addActionListener(_ -> {
            page_counter = 0;
            pages.setSelectedIndex(page_counter);
            UpdateTable(AllPO, list_length, page_counter);
        });
        page_panel.add(p_first, ii_gbc);

        ii_gbc.gridx = 1;
        left_icon1 = new CustomComponents.CustomArrowIcon(24, 3, new Color(122, 122, 122), true);
        left_icon2 = new CustomComponents.CustomArrowIcon(24, 3, Color.BLACK, true);
        p_left = new JButton(left_icon1);
        p_left.setRolloverIcon(left_icon2);
        p_left.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        p_left.setPreferredSize(new Dimension(30, 27));
        p_left.addActionListener(_ -> {
            if (page_counter > 0) {
                page_counter--;
                pages.setSelectedIndex(page_counter);
                UpdateTable(AllPO, list_length, page_counter);
            }
        });
        page_panel.add(p_left, ii_gbc);

        ii_gbc.gridx = 2;
        pages.setFont(merriweather.deriveFont(Font.BOLD, 16));
        pages.setForeground(new Color(122, 122, 122));
        pages.setFocusable(false);
        pages.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        pages.setPreferredSize(new Dimension(150, 27));
        pages.addActionListener(e -> {
            if (pages.getItemCount() > 0) {
                page_counter = pages.getSelectedIndex();
                UpdateTable(AllPO, list_length, page_counter);
            }
        });
        page_panel.add(pages, ii_gbc);

        ii_gbc.gridx = 3;
        right_icon1 = new CustomComponents.CustomArrowIcon(24, 3, new Color(122, 122, 122), false);
        right_icon2 = new CustomComponents.CustomArrowIcon(24, 3, Color.BLACK, false);
        p_right = new JButton(right_icon1);
        p_right.setRolloverIcon(right_icon2);
        p_right.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        p_right.setPreferredSize(new Dimension(30, 27));
        p_right.addActionListener(_ -> {
            if (page_counter < pages.getItemCount() - 1) {
                page_counter++;
                pages.setSelectedIndex(page_counter);
                UpdateTable(AllPO, list_length, page_counter);
            }
        });
        page_panel.add(p_right, ii_gbc);

        ii_gbc.gridx = 4;
        p_last = new JButton("Last");
        p_last.setFont(merriweather.deriveFont(Font.BOLD, 16));
        p_last.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        p_last.setPreferredSize(new Dimension(50, 27));
        p_last.setForeground(new Color(122, 122, 122));
        p_last.addActionListener(_ -> {
            page_counter = pages.getItemCount() - 1;
            pages.setSelectedIndex(page_counter);
            UpdateTable(AllPO, list_length, page_counter);
        });
        page_panel.add(p_last, ii_gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout()); // Using GridBagLayout for better alignment of buttons
        buttonPanel.setOpaque(false); // Set the panel background to transparent
        content.add(buttonPanel, gbc);
    }

    public static void UpdateTable(java.util.List<PurchaseOrder> filteredItems, int length, int page) {
        String[] titles = new String[]{"PurchaseOrderID", "ItemID", "PurchaseQuantity", "SupplierID","OrderDate","PurchaseMgrID","Status"};
        Object[][] data;
        int counter = 0;
        int anti_counter = page * length;

        if (length >= filteredItems.size() - page * length) {
            data = new Object[filteredItems.size() - page * length][titles.length];
        } else {
            data = new Object[length][titles.length];
        }

        for (PurchaseOrder po : filteredItems) {
            if (anti_counter != 0) {
                anti_counter -= 1;
                continue;
            } else {
//                String supplierName = getSupplierName(po.getSupplierID(), "datafile/supplier.txt");

                data[counter] = new Object[]{
                        po.getPurchaseOrderID(),
                        po.getItemID(),
                        po.getPurchaseQuantity(),
                        "Test",
                        po.getOrderDate(),
                        po.getPurchaseMgrID(),
                        po.getStatus()
                };
                counter += 1;
                if (counter == length || counter == filteredItems.size()) { break; }
            }
        }

        POTable.UpdateTableContent(titles, data);

        String temp2 = "<html>Displaying <b>%s</b> to <b>%s</b> of <b>%s</b> records</html>";
        if (length >= AllPO.size()) {
            lbl_indicate.setText(String.format(temp2, (!AllPO.isEmpty()) ? 1 : 0, AllPO.size(),
                    AllPO.size()));
        } else {
            lbl_indicate.setText(String.format(temp2, page * length + 1,
                    Math.min((page + 1) * length, AllPO.size()),
                    AllPO.size()));
        }
    }

    public static void UpdatePages(int totalItems) {
        int pageCount = (int) Math.ceil(totalItems / (double) list_length);
        if (totalItems <= list_length) {
            pageCount = 1; // If the filtered results are less than the page length, show 1 page
        }
        pages.removeAllItems(); // Remove existing pages
        for (int i = 1; i <= pageCount; i++) {
            pages.addItem(String.format("Page %s of %s", i, pageCount));
        }
        pages.repaint();
        pages.revalidate();
    }
}
