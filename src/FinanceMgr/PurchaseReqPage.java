package FinanceMgr;

import Classes.*;
import Admin.Main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

public class PurchaseReqPage {
    private static JFrame parent;
    private static Font merriweather;
    private static JPanel content;
    private static JComboBox<String> entries;
    private static CustomComponents.CustomTable table_purReq;
    private static List<PurchaseRequisition> purchaseReq_list;
    private static JLabel lbl_indicate;
    private static JComboBox<String>  pages;
    private static int list_length = 10, page_counter = 0;
    private static JButton p_first;
    private static JButton p_last;

    public static void Loader(JFrame parent, Font merriweather, JPanel content){
        PurchaseReqPage.parent = parent;
        PurchaseReqPage.merriweather = merriweather;
        PurchaseReqPage.content = content;

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
            UpdatePRPages(list_length);
            page_counter = 0;
            UpdatePRTable(list_length, page_counter);
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
        String[] titles = new String[]{"PurchaseReqID", "ItemID", "SupplierID", "Quantity", "ReqDate", "SalesMgrID", "Status"};
        purchaseReq_list = new PurchaseRequisition().ListAll();
        Object[][] data = new Object[purchaseReq_list.size()][titles.length];
        int counter = 0;
        for (PurchaseRequisition purchaseRequisition : purchaseReq_list) {
            data[counter] = new Object[]{purchaseRequisition.getPurchaseReqID(), purchaseRequisition.getItemID(), purchaseRequisition.getSupplierID(),
                    purchaseRequisition.getQuantity(), purchaseRequisition.getReqDate(), purchaseRequisition.getSalesMgrID(),
                    (purchaseRequisition.getStatus() == 0) ? "Pending" : "Processed"};
            counter += 1;
        }

        table_purReq = new CustomComponents.CustomTable(titles, data, merriweather.deriveFont(Font.BOLD, 18),
                merriweather.deriveFont(Font.PLAIN, 16), Color.BLACK, Color.BLACK,
                Color.WHITE, new Color(212, 212, 212), 1, 30);

        lbl_indicate = new JLabel("");
        pages = new JComboBox<>();
        UpdatePRTable(list_length, page_counter);
        UpdatePRPages(list_length);
        table_purReq.setShowHorizontalLines(true);
        table_purReq.setShowVerticalLines(true);
        table_purReq.setGridColor(new Color(230, 230, 230));

        CustomComponents.CustomScrollPane scrollPane1 = new CustomComponents.CustomScrollPane(false, 1, table_purReq,
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
        ii_gbc.fill = GridBagConstraints.BOTH;
        p_first = new JButton("First");
        p_first.setFont(merriweather.deriveFont(Font.BOLD, 16));
        p_first.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        p_first.setForeground(new Color(122, 122, 122));
        p_first.addActionListener(_ -> {
            page_counter = 0;
            pages.setSelectedIndex(0);
            UpdatePRTable(list_length, page_counter);
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
                UpdatePRTable(list_length, page_counter);
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
                UpdatePRTable(list_length, page_counter);
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
                UpdatePRTable(list_length, page_counter);
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
            UpdatePRTable(list_length, page_counter);
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

        igbc.gridwidth = 3;
        igbc.gridx = 0;
        igbc.gridy = 3;
        igbc.insets = new Insets(0, 0, 0, 0);
        CustomComponents.CustomButton viewPurchaseReq = new CustomComponents.CustomButton("View Details", merriweather,
                new Color(255, 255, 255),
                new Color(255, 255, 255),
                new Color(225, 108, 150),
                new Color(237, 136, 172),
                Main.transparent, 0, 16, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        viewPurchaseReq.addActionListener(_ -> {
            if (table_purReq.getSelectedRowCount() == 0) {
                CustomComponents.CustomOptionPane.showErrorDialog(
                        parent,
                        "Please select a Purchase Requisition to view!",
                        "Error",
                        new Color(209, 88, 128),
                        new Color(255, 255, 255),
                        new Color(237, 136, 172),
                        new Color(255, 255, 255)
                );
            } else {
                String selected_id = table_purReq.getValueAt(table_purReq.getSelectedRow(),
                        table_purReq.getColumnModel().getColumnIndex("PurchaseReqID")).toString();
                ViewPurchaseReq.UpdatePurchaseReq(new PurchaseRequisition().GetObjectByID(selected_id));
                ViewPurchaseReq.ShowPage();
            }
        });
        inner.add(viewPurchaseReq, igbc);
        ViewPurchaseReq.Loader(parent, merriweather);
    }
    public static void UpdatePRTable(int length, int page) {
        String[] titles = new String[]{"PurchaseReqID", "ItemID", "SupplierID", "Quantity", "ReqDate", "SalesMrgID", "Status"};
        Object[][] data;
        int counter = 0;
        int anti_counter = page * length;
        if (length >= purchaseReq_list.size() - page * length) {
            data = new Object[purchaseReq_list.size() - page * length][titles.length];
        } else {
            data = new Object[length][titles.length];
        }
        for (PurchaseRequisition purchaseRequisition : purchaseReq_list) {
            if (anti_counter != 0) {
                anti_counter -= 1;
            } else {
                data[counter] = new Object[]{purchaseRequisition.getPurchaseReqID(), purchaseRequisition.getItemID(), purchaseRequisition.getSupplierID(),
                        purchaseRequisition.getQuantity(), purchaseRequisition.getReqDate(), purchaseRequisition.getSalesMgrID(),
                        (purchaseRequisition.getStatus() == 0) ? "Pending" : "Processed"};
                counter += 1;
                if (counter == length || counter == purchaseReq_list.size()) { break; }
            }
        }
        table_purReq.UpdateTableContent(titles, data);
        String temp2 = "<html>Displaying <b>%s</b> to <b>%s</b> of <b>%s</b> records</html>";
        if (length >= purchaseReq_list.size()) {
            lbl_indicate.setText(String.format(temp2, (!purchaseReq_list.isEmpty()) ? 1 : 0, purchaseReq_list.size(),
                    purchaseReq_list.size()));
        } else {
            lbl_indicate.setText(String.format(temp2, page * length + 1,
                    Math.min((page + 1) * length, purchaseReq_list.size()),
                    purchaseReq_list.size()));
        }
    }

    public static void UpdatePRPages(int length) {
        int pageCount = (int) Math.ceil(purchaseReq_list.size() / (double) length);
        if (purchaseReq_list.size() <= length) {
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
