package InventoryMgr;

import Admin.*;
import Classes.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;

public class StockReport {

    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content;
    private static Classes.User current_user;
    private static int indicator, base_size;
    private static java.util.List<PurchaseOrder> AllPo;
    private static JList POList;
    private static JButton s_btn,clearbtn,p_first,p_left,p_right,p_last;
    private static JDialog dialogAdd, dialogDelete, dialogEdit;
    private static CustomComponents.CustomButton btnRec,btnDelete,btnEdit;
    private static CustomComponents.CustomScrollPane scrollPane1;
    private static CustomComponents.CustomSearchIcon search_icon1, search_icon2;
    private static CustomComponents.CustomXIcon icon_clear1, icon_clear2;
    private static CustomComponents.EmptyTextField search;
    private static CustomComponents.CustomTable table_item;
    private static CustomComponents.CustomArrowIcon right_icon1,right_icon2, left_icon1, left_icon2;
    private static JLabel lbl_show, lbl_entries,lbl_indicate;
    private static JComboBox entries,pages;
    private static int list_length = 10, page_counter = 0;
    private static Item current_item;
    private static int lifetime_sales = 0;

    public static void Loader(JFrame parent, Font merriweather, Font boldonse, JPanel content, Classes.User current_user) {
        StockReport.parent = parent;
        StockReport.merriweather = merriweather;
        StockReport.boldonse = boldonse;
        StockReport.content = content;
        StockReport.current_user = current_user;
    }

    public static void ShowPage(Item item) {
        current_item = item;

        JDialog dialog = new JDialog(parent, "Stock Summary: " + item.getItemName(), true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize((int) (parent.getWidth() / 1.5), (int) (parent.getHeight() / 1.5));
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(parent);

        int size_factor = 0;
        if (parent.getWidth() >= parent.getHeight()) {
            size_factor = parent.getHeight() / 40;
        } else {
            size_factor = parent.getWidth() / 30;
        }

        int base_size = size_factor;

        AllPo = new PurchaseOrder().ListAll();
        String[] titles = new String[]{"PurchaseOrderID", "ItemID", "PurchaseQuantity", "SupplierID", "OrderDate", "PurchaseMgrID", "Status"};
        Object[][] data = new Object[AllPo.size()][titles.length];
        int counter = 0;
        for (PurchaseOrder purchaseOrder : AllPo) {
            if (purchaseOrder.getItemID().equals(item.getItemID())) {
                data[counter] = new Object[]{purchaseOrder.getPurchaseOrderID(), purchaseOrder.getItemID(), purchaseOrder.getPurchaseQuantity(),
                        purchaseOrder.getSupplierID(), purchaseOrder.getOrderDate(), purchaseOrder.getPurchaseMgrID(), purchaseOrder.getStatus()};
                counter += 1;
            }
        }

        List<Item_Sales> allSales = new Item_Sales().ListAll();
        for (Item_Sales is : allSales) {
            if (is.getItemID().equals(item.getItemID())) {
                lifetime_sales += is.getQuantity();
            }
        }

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 8, 5, 5);
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel title = new JLabel("Stock Summary: " + item.getItemName());
        title.setOpaque(false);
        title.setFont(merriweather.deriveFont(Font.BOLD, (float) (base_size * 1.2)));
        panel.add(title, gbc);

//      Lifetime Sales
        gbc.gridy++;
        gbc.weighty = 1;
        gbc.insets = new Insets(5, 8, 5, 5);
        JLabel sg = new JLabel("Lifetime Sales: " + Integer.toString(lifetime_sales) + " units");
        sg.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 1.1)));
        panel.add(sg, gbc);

//      Table for in & outs;
        gbc.weighty = 10;
        gbc.gridy++;
        table_item = new CustomComponents.CustomTable(titles, data, merriweather.deriveFont(Font.BOLD, 18),
                merriweather.deriveFont(Font.PLAIN, 16), Color.BLACK, Color.BLACK,
                Color.WHITE, new Color(212, 212, 212), 1, 30);

        lbl_indicate = new JLabel("");
        lbl_indicate.setFont(merriweather.deriveFont(Font.BOLD, 16));
        lbl_indicate.setOpaque(false);
        lbl_indicate.setForeground(new Color(122, 122, 122));

        pages = new JComboBox<>();
        table_item.setShowHorizontalLines(true);
        table_item.setShowVerticalLines(true);
        table_item.setGridColor(new Color(230, 230, 230));

        scrollPane1 = new CustomComponents.CustomScrollPane(false, 1, table_item,
                6, new Color(202, 202, 202), Main.transparent,
                Main.transparent, Main.transparent, Main.transparent,
                new Color(170, 170, 170), Color.WHITE,
                new Color(140, 140, 140), new Color(110, 110, 110),
                Color.WHITE, Color.WHITE, 6);

        panel.add(scrollPane1, gbc);

        gbc.gridy++;
        gbc.weighty = 1;
        JPanel subpanel = new JPanel(new GridBagLayout());
        subpanel.setOpaque(false);
        panel.add(subpanel, gbc);

        GridBagConstraints i_gbc = new GridBagConstraints();
        i_gbc.gridx = 0;
        i_gbc.gridy = 0;
        i_gbc.weightx = 1;
        i_gbc.weighty = 1;
        i_gbc.fill = GridBagConstraints.NONE;

        i_gbc.gridx++;
        JPanel subpanel2 = new JPanel(new GridBagLayout());
        subpanel2.setOpaque(false);
        subpanel.add(subpanel2, i_gbc);

        GridBagConstraints ii_gbc2 = new GridBagConstraints();
        ii_gbc2.gridx = 0;
        ii_gbc2.gridy = 0;
        ii_gbc2.weightx = 1;
        ii_gbc2.weighty = 1;
        ii_gbc2.fill = GridBagConstraints.NONE;

        JLabel jl1 = new JLabel("Show ");
        jl1.setFont(merriweather.deriveFont(Font.BOLD, 16));
        jl1.setForeground(new Color(122, 122, 122));
        subpanel2.add(jl1, ii_gbc2);

        ii_gbc2.gridx++;
        String[] entry_item = {"5", "10", "15", "20"};
        entries = new JComboBox<>(entry_item);
        entries.setFont(merriweather.deriveFont(Font.BOLD, 14));
        entries.setForeground(new Color(122, 122, 122));
        entries.setFocusable(false);
        entries.setSelectedItem("10");
        entries.addActionListener(e -> {
            list_length = Integer.parseInt((String) Objects.requireNonNull(entries.getSelectedItem()));
            page_counter = 0;
            UpdatePages(AllPo.size());
            UpdateTable(AllPo, list_length, page_counter);
        });
        subpanel2.add(entries, ii_gbc2);

        ii_gbc2.gridx++;
        JLabel jl2 = new JLabel(" entries");
        jl2.setForeground(new Color(122, 122, 122));
        jl2.setFont(merriweather.deriveFont(Font.BOLD, 16));
        subpanel2.add(jl2, ii_gbc2);

        i_gbc.gridx++;
        JPanel page_panel = new JPanel(new GridBagLayout());
        page_panel.setOpaque(false);
        subpanel.add(page_panel, i_gbc);

        GridBagConstraints ii_gbc = new GridBagConstraints();
        ii_gbc.gridx = 0;
        ii_gbc.gridy = 0;
        ii_gbc.weightx = 1;
        ii_gbc.weighty = 1;
        ii_gbc.fill = GridBagConstraints.NONE;
        p_first = new JButton("First");
        p_first.setFont(merriweather.deriveFont(Font.BOLD, 16));
        p_first.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        p_first.setPreferredSize(new Dimension(50, 27));
        p_first.setForeground(new Color(122, 122, 122));
        p_first.addActionListener(_ -> {
            page_counter = 0;
            pages.setSelectedIndex(page_counter);
            UpdateTable(AllPo, list_length, page_counter);
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
                UpdateTable(AllPo, list_length, page_counter);
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
                UpdateTable(AllPo, list_length, page_counter);
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
                UpdateTable(AllPo, list_length, page_counter);
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
            UpdateTable(AllPo, list_length, page_counter);
        });
        page_panel.add(p_last, ii_gbc);

        UpdateTable(AllPo, list_length, page_counter);
        UpdatePages(AllPo.size());

        dialog.setContentPane(panel);
        dialog.setVisible(true);
        SwingUtilities.invokeLater(title::requestFocusInWindow);
    }

    public static void GenerateStockSummary() {

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
            } else {

                if (po.getItemID().equals(current_item.getItemID())) {
                    Supplier sup = new Supplier().GetObjectByID(po.getSupplierID());

                    data[counter] = new Object[]{
                            po.getPurchaseOrderID(),
                            po.getItemID(),
                            po.getPurchaseQuantity(),
                            sup.getSupplierName(),
                            po.getOrderDate(),
                            po.getPurchaseMgrID(),
                            po.getStatus()
                    };
                    counter += 1;
                }

                if (counter == length || counter == filteredItems.size()) { break; }
            }
        }

        table_item.UpdateTableContent(titles, data);

        String temp2 = "<html>Displaying <b>%s</b> to <b>%s</b> of <b>%s</b> records</html>";
        int start = page * length + 1;
        int end = Math.min((page + 1) * length, filteredItems.size());
        lbl_indicate.setText(String.format(temp2, start, end, filteredItems.size()));
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
