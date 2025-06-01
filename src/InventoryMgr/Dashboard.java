package InventoryMgr;

import Admin.*;
import Classes.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Dashboard {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content;
    private static User current_user;
    private static BufferedImage bg, img;
    private static int indicator, base_size;
    private static CustomComponents.RoundedPanel item_panel, sup_panel, ds_panel, po_panel,pr_panel, low_stock_summary, items_summary;
    private static BufferedImage icon_item, icon_check,icon_dailysales, icon_po, icon_pr;
    private static CustomComponents.ImageCell item_img, supplier_img, dailysales_img, po_img, pr_img;
    private static List<Item> allItems;
    private static List<Sales> allSales;
    private static List<Item_Sales> allItemSales;
    private static CustomComponents.CustomTable sa_table, item_table;
    private static CustomComponents.CustomRoundChart best_sold_quantity;
    private static JPanel data_panel;
    private static CustomComponents.CustomVaryingTextIcon bs_icon;
    private static JLabel bsTitle;

    public static void Loader(JFrame parent, Font merriweather, Font boldonse, JPanel content, User current_user) {
        try {
            bg = ImageIO.read(new File("images/login_bg.jpg"));
            img = ImageIO.read(new File("images/info.png"));
            icon_item = ImageIO.read(new File("images/image_icon.png"));
            icon_check = ImageIO.read(new File("images/supplier_icon.png"));
            icon_po = ImageIO.read(new File("images/PO_icon.png"));
        } catch (IOException e) {
            e.getStackTrace();
        }
        Dashboard.parent = parent;
        Dashboard.merriweather = merriweather;
        Dashboard.boldonse = boldonse;
        Dashboard.content = content;
        Dashboard.current_user = current_user;
    }

    public static void ShowPage() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        item_panel = new CustomComponents.RoundedPanel(0, 5, 0, Color.WHITE, null);
        item_panel.setLayout(new GridBagLayout());
        content.add(item_panel, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(5, 0, 5, 0);
        ds_panel = new CustomComponents.RoundedPanel(0, 5, 0, Color.WHITE, null);
        ds_panel.setLayout(new GridBagLayout());
        content.add(ds_panel, gbc);

        gbc.gridx = 2;
        gbc.insets = new Insets(5, 5, 5, 5);
        po_panel = new CustomComponents.RoundedPanel(0, 5, 0, Color.WHITE, null);
        po_panel.setLayout(new GridBagLayout());
        content.add(po_panel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 3.7;
        gbc.weighty = 0.8;
        gbc.gridwidth = 5;
        gbc.insets = new Insets(0, 0, 0, 0);
        JPanel bottom_panel = new JPanel(new GridBagLayout());
        bottom_panel.setOpaque(false);
        content.add(bottom_panel, gbc);

//      Left Panel
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1.3;
        gbc.weighty = 1;
        gbc.insets = new Insets(0, 5, 5, 0);
        items_summary = new CustomComponents.RoundedPanel(0, 5, 0, Color.WHITE, null);
        items_summary.setLayout(new GridBagLayout());
        bottom_panel.add(items_summary, gbc);

        GridBagConstraints itemsummary_gbc = new GridBagConstraints();
        itemsummary_gbc.gridx = 0;
        itemsummary_gbc.gridy = 0;
        itemsummary_gbc.weightx = 1;
        itemsummary_gbc.weighty = 1;
        itemsummary_gbc.gridwidth = 1;
        itemsummary_gbc.fill = GridBagConstraints.BOTH;
        itemsummary_gbc.insets = new Insets(-18, 0, 0, 0);

        itemsummary_gbc.gridy = 1;
        itemsummary_gbc.weighty = 6;
        itemsummary_gbc.insets = new Insets(0, 0, 20, 0);

        List<Item> i = new Item().ListAll();
        String[] item_titles = new String[]{"ID", "Name", "Price", "Cost", "Stock", "Category"};
        Object[][] item_data = new Object[i.size()][item_titles.length];
        int item_counter = 0;
        for (Item item : i) {
            item_data[item_counter] = new Object[]{
                    item.getItemID(),
                    item.getItemName(),
                    item.getUnitPrice(),
                    item.getUnitCost(),
                    item.getStockCount(),
                    item.getCategory()
            };
            item_counter += 1;
        }

        item_table = new CustomComponents.CustomTable(item_titles, item_data, merriweather.deriveFont(Font.BOLD, 18),
                merriweather.deriveFont(Font.PLAIN, 16), Color.BLACK, Color.BLACK,
                Color.WHITE, new Color(212, 212, 212), 1, 30);
        item_table.setShowHorizontalLines(true);
        item_table.setShowVerticalLines(true);

        JScrollPane item_scroll = new CustomComponents.CustomScrollPane(
                false, 1, item_table,
                6, new Color(202, 202, 202), Main.transparent,
                Main.transparent, Main.transparent, Main.transparent,
                new Color(170, 170, 170), Color.WHITE,
                new Color(140, 140, 140), new Color(110, 110, 110),
                Color.WHITE, Color.WHITE, 6
        );

        itemsummary_gbc.gridy = 2;
        itemsummary_gbc.insets = new Insets(0, 20, -10, 0);
        JLabel lbl_item = new JLabel("All Items");
        lbl_item.setFont(merriweather.deriveFont(Font.BOLD, 20));
        lbl_item.setOpaque(false);
        lbl_item.setForeground(new Color(0, 0, 0));
        items_summary.add(lbl_item, itemsummary_gbc);

        itemsummary_gbc.gridy = 3;
        itemsummary_gbc.weighty = 1;
        itemsummary_gbc.insets = new Insets(0, 0, 0, 0);
        data_panel = new JPanel(new GridBagLayout());
        data_panel.setOpaque(false);
        items_summary.add(item_scroll, itemsummary_gbc);

//      Right Panel
        gbc.gridx = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 1.3;
        gbc.weighty = 1;
        gbc.insets = new Insets(0, 0, 5, 5);
        low_stock_summary = new CustomComponents.RoundedPanel(0, 5, 0, Color.WHITE, null);
        low_stock_summary.setLayout(new GridBagLayout());
        bottom_panel.add(low_stock_summary, gbc);

        GridBagConstraints igbc = new GridBagConstraints();
        igbc.gridx = 0;
        igbc.gridy = 0;
        igbc.weightx = 1;
        igbc.weighty = 1;
        igbc.gridwidth = 1;
        igbc.fill = GridBagConstraints.BOTH;
        igbc.insets = new Insets(-18, 0, 0, 0);

        igbc.gridy = 1;
        igbc.weighty = 6;
        igbc.insets = new Insets(0, 0, 20, 0);

        List<Item> sa = StockAlert.Checker();
        String[] sa_titles = new String[]{"ItemID", "ItemName", "StockCount", "Threshold", "Category", "Last Update"};
        Object[][] sa_data = new Object[sa.size()][sa_titles.length];
        int sa_counter = 0;
        for (Item item : sa) {
            sa_data[sa_counter] = new Object[]{
                    item.getItemID(),
                    item.getItemName(),
                    item.getStockCount(),
                    item.getThreshold(),
                    item.getCategory(),
                    item.getLastUpdate(),
            };
            sa_counter += 1;
        }

        sa_table = new CustomComponents.CustomTable(sa_titles, sa_data, merriweather.deriveFont(Font.BOLD, 18),
                merriweather.deriveFont(Font.PLAIN, 16), Color.BLACK, Color.BLACK,
                Color.WHITE, new Color(212, 212, 212), 1, 30);
        sa_table.setShowHorizontalLines(true);
        sa_table.setShowVerticalLines(true);

        JScrollPane sa_scroll = new CustomComponents.CustomScrollPane(
                false, 1, sa_table,
                6, new Color(202, 202, 202), Main.transparent,
                Main.transparent, Main.transparent, Main.transparent,
                new Color(170, 170, 170), Color.WHITE,
                new Color(140, 140, 140), new Color(110, 110, 110),
                Color.WHITE, Color.WHITE, 6
        );

        igbc.gridy = 2;
        igbc.insets = new Insets(0, 20, -10, 0);
        JLabel lbl_sa = new JLabel("Low Stock Items");
        lbl_sa.setFont(merriweather.deriveFont(Font.BOLD, 20));
        lbl_sa.setOpaque(false);
        lbl_sa.setForeground(new Color(0, 0, 0));
        low_stock_summary.add(lbl_sa, igbc);

        igbc.gridy = 3;
        igbc.weighty = 1;
        igbc.insets = new Insets(0, 0, 0, 0);
        data_panel = new JPanel(new GridBagLayout());
        data_panel.setOpaque(false);
        low_stock_summary.add(sa_scroll, igbc);

//      Other Crap
        GridBagConstraints itgbc = new GridBagConstraints();
        itgbc.gridx = 0;
        itgbc.gridy = 0;
        itgbc.weightx = 1;
        itgbc.weighty = 1;
        itgbc.fill = GridBagConstraints.BOTH;
        item_img = new CustomComponents.ImageCell(icon_item, 1.4, 5);
        item_panel.add(item_img, itgbc);

        itgbc.gridy = 1;
        itgbc.weighty = 0;
        itgbc.insets = new Insets(10, 10, 10, 10);
        CustomComponents.CustomButton btnItem = new CustomComponents.CustomButton(
                "Items", merriweather, Color.WHITE, Color.WHITE,
                new Color(225, 108, 150), new Color(225, 153, 179), null, 0, 16,
                Main.transparent, false, 5, false, null, 0, 0, 0);
        btnItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnItem.addActionListener(_ -> {
            InventoryHome.indicator = 2;
            InventoryHome.PageChanger();
        });
        item_panel.add(btnItem, itgbc);

        GridBagConstraints dgbc = new GridBagConstraints();
        dgbc.gridx = 0;
        dgbc.gridy = 0;
        dgbc.weightx = 1;
        dgbc.weighty = 1;
        dgbc.fill = GridBagConstraints.BOTH;
        dailysales_img = new CustomComponents.ImageCell(icon_check, 1.5, 5);
        ds_panel.add(dailysales_img, dgbc);

        dgbc.gridy = 1;
        dgbc.weighty = 0;
        dgbc.insets = new Insets(10, 10, 10, 10);
        CustomComponents.CustomButton btnSales = new CustomComponents.CustomButton(
                "Check Stock Alert", merriweather, Color.WHITE, Color.WHITE,
                new Color(255, 114, 92), new Color(249, 155, 140), null, 0, 16,
                Main.transparent, false, 5, false, null, 0, 0, 0);
        btnSales.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSales.addActionListener(_ -> {
            StockAlert.Popup(parent);
        });
        ds_panel.add(btnSales, dgbc);

        GridBagConstraints pgbc = new GridBagConstraints();
        pgbc.gridx = 0;
        pgbc.gridy = 0;
        pgbc.weightx = 1;
        pgbc.weighty = 1;
        pgbc.fill = GridBagConstraints.BOTH;
        po_img = new CustomComponents.ImageCell(icon_po, 1.3, 5);
        po_panel.add(po_img, pgbc);

        pgbc.gridy = 1;
        pgbc.weighty = 0;
        pgbc.insets = new Insets(10, 10, 10, 10);
        CustomComponents.CustomButton btnPO = new CustomComponents.CustomButton(
                "Purchase Order", merriweather, Color.WHITE, Color.WHITE,
                new Color(255, 180, 55), new Color(254, 210, 137), null, 0, 16,
                Main.transparent, false, 5, false, null, 0, 0, 0);
        btnPO.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnPO.addActionListener(_ -> {
            InventoryHome.indicator = 3;
            InventoryHome.PageChanger();
        });
        po_panel.add(btnPO, pgbc);
    }
}
