package SalesMgr;

import Admin.*;
import Classes.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;

public class ItemMng {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content, page_panel, buttonPanel;
    private static User current_user;
    private static List<Item> AllItems;
    private static JButton s_btn, clear_btn, p_first, p_left, p_right, p_last;
    private static CustomComponents.CustomButton btnAdd,btnEdit, btnView, cancel_delete, btnDelete1, btnDelete2;
    private static CustomComponents.CustomScrollPane scrollPane1;
    private static CustomComponents.CustomSearchIcon search_icon1, search_icon2;
    private static CustomComponents.CustomXIcon icon_clear1, icon_clear2;
    private static CustomComponents.EmptyTextField search;
    private static CustomComponents.CustomTable table_item;
    private static CustomComponents.CustomArrowIcon right_icon1,right_icon2, left_icon1, left_icon2;
    private static JLabel lbl_show, lbl_entries,lbl_indicate;
    private static JComboBox entries,pages;
    private static int list_length = 10, page_counter = 0, filter = 0, mode = 1;
    private static boolean deleting;
    private static Set<String> deleting_id = new LinkedHashSet<>();
    private static final Set<Integer> previousSelection = new HashSet<>();


    public static void Loader(JFrame parent, Font merriweather, Font boldonse,
                              JPanel content, User current_user) {
        SalesMgr.ItemMng.parent = parent;
        SalesMgr.ItemMng.merriweather = merriweather;
        SalesMgr.ItemMng.boldonse = boldonse;
        SalesMgr.ItemMng.content = content;
        SalesMgr.ItemMng.current_user = current_user;
    }

    public static void ShowPage() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        JPanel inner = new JPanel(new GridBagLayout());
        inner.setOpaque(true);
        inner.setBackground(Color.WHITE);
        content.add(inner, gbc);

        gbc.insets = new Insets(10, 10, 0, 10);
        lbl_show = new JLabel("Show");
        lbl_show.setFont(merriweather.deriveFont(Font.BOLD, 16));
        lbl_show.setForeground(new Color(122, 122, 122));
        inner.add(lbl_show, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0;
        gbc.insets = new Insets(10, 5, 0, 5);
        String[] entry_item = {"5", "10", "15", "20"};
        entries = new JComboBox<>(entry_item);
        entries.setFont(merriweather.deriveFont(Font.BOLD, 14));
        entries.setForeground(new Color(122, 122, 122));
        entries.setFocusable(false);
        entries.setSelectedItem("10");
        entries.addActionListener(e -> {
            list_length = Integer.parseInt((String) Objects.requireNonNull(entries.getSelectedItem()));
            UpdatePages(AllItems.size());
            page_counter = 0;
            UpdateTable(list_length, page_counter);
        });
        inner.add(entries, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0;
        lbl_entries = new JLabel("entries");
        lbl_entries.setFont(merriweather.deriveFont(Font.BOLD, 16));
        lbl_entries.setForeground(new Color(122, 122, 122));
        inner.add(lbl_entries, gbc);

        gbc.gridx = 3;
        gbc.weightx = 25;
        JLabel placeholder1 = new JLabel("");
        inner.add(placeholder1, gbc);

        gbc.gridx = 4;
        gbc.weightx = 1;
        CustomComponents.RoundedPanel search_panel = new CustomComponents.RoundedPanel(8, 0, 1, Color.WHITE,
                new Color(209, 209, 209));
        search_panel.setLayout(new GridBagLayout());
        inner.add(search_panel, gbc);

        GridBagConstraints igbc = new GridBagConstraints();
        igbc.gridx = 0;
        igbc.gridy = 0;
        igbc.weightx = 1;
        igbc.weighty = 1;
        igbc.fill = GridBagConstraints.BOTH;
        igbc.insets = new Insets(6, 6, 10, 5);
        search_icon1 = new CustomComponents.CustomSearchIcon(16, 3, new Color(122, 122, 122), Color.WHITE);
        search_icon2 = new CustomComponents.CustomSearchIcon(16, 3, Color.BLACK, Color.WHITE);
        s_btn = new JButton(search_icon1);
        s_btn.setRolloverIcon(search_icon2);
        s_btn.setBorderPainted(false);
        s_btn.setContentAreaFilled(false);
        s_btn.setFocusPainted(false);
        s_btn.addActionListener(_ -> SearchStuff());
        search_panel.add(s_btn, igbc);

        igbc.gridx = 1;
        igbc.insets = new Insets(6, 0, 6, 0);
        search = new CustomComponents.EmptyTextField(20, "Search...\r\r", new Color(122, 122, 122));
        search.setFont(merriweather.deriveFont(Font.BOLD, 14));
        search.addActionListener(_ -> SearchStuff());
        search_panel.add(search, igbc);

        igbc.gridx = 2;
        igbc.weightx = 0.9;
        igbc.weighty = 0.9;
        igbc.fill = GridBagConstraints.BOTH;
        icon_clear1 = new CustomComponents.CustomXIcon(23, 3,
                new Color(209, 209, 209), true);
        icon_clear2 = new CustomComponents.CustomXIcon( 23, 3,
                Color.BLACK, true);
        clear_btn = new JButton(icon_clear1);
        clear_btn.setRolloverIcon(icon_clear2);
        clear_btn.setOpaque(false);
        clear_btn.setFocusable(false);
        clear_btn.setBorder(BorderFactory.createEmptyBorder());
        clear_btn.addActionListener(e -> {
            search.setText("");
            search.requestFocus();
            UpdatePages(AllItems.size());
            UpdateTable(list_length, page_counter);
        });
        search.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (search.getText().equals("Search...")) {
                    search.setText("");
                    search.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (search.getText().isEmpty()) {
                    search.setText("Search...");
                    search.setForeground(new Color(122, 122, 122));
                }
            }
        });
        search_panel.add(clear_btn, igbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 6;
        gbc.gridwidth = 6;
        gbc.fill = GridBagConstraints.BOTH;

        AllItems = new Item().ListAll();
        String[] titles = new String[]{"ItemID", "ItemName", "Unit Price", "Unit Cost", "StockCount", "Threshold",
                "Category", "Last Update","Supplier Name"};
        Object[][] data = new Object[AllItems.size()][titles.length];
        int counter = 0;
        for (Item item : AllItems) {
            List<Item_Supplier> item_suppliers = new Item_Supplier().ListAllWithFilter(item.getItemID());
            StringBuilder supplierName = new StringBuilder();
            int looper = 1;
            for (Item_Supplier it_spl : item_suppliers) {
                supplierName.append(new Supplier().GetObjectByID(it_spl.getSupplierID()).getSupplierName());
                if (looper < item_suppliers.size()) { supplierName.append(", "); }
                looper += 1;
            }

            data[counter] = new Object[]{
                    item.getItemID(),
                    item.getItemName(),
                    item.getUnitPrice(),
                    item.getUnitCost(),
                    item.getStockCount(),
                    item.getThreshold(),
                    item.getCategory(),
                    item.getLastUpdate(),
                    supplierName.toString()
            };
            counter += 1;
        }

        table_item = new CustomComponents.CustomTable(titles, data, merriweather.deriveFont(Font.BOLD, 18),
                merriweather.deriveFont(Font.PLAIN, 16), Color.BLACK, Color.BLACK,
                Color.WHITE, new Color(212, 212, 212), 1, 30);
        table_item.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && deleting) {
                    SwingUtilities.invokeLater(() -> {
                        int[] selectedRows = table_item.getSelectedRows();
                        Set<Integer> currentSelection = new HashSet<>();
                        for (int row : selectedRows) {
                            currentSelection.add(row);
                        }
                        Set<Integer> newlySelected = new HashSet<>(currentSelection);
                        newlySelected.removeAll(previousSelection);
                        Set<Integer> deselected = new HashSet<>(previousSelection);
                        deselected.removeAll(currentSelection);
                        for (int row : newlySelected) {
                            deleting_id.add(table_item.getValueAt(row,
                                    table_item.getColumnModel().getColumnIndex("ItemID")).toString());
                            System.out.println("Selected ItemID: " + deleting_id);
                        }
                        for (int row : deselected) {
                            deleting_id.remove(table_item.getValueAt(row,
                                    table_item.getColumnModel().getColumnIndex("ItemID")).toString());
                            System.out.println("Deselected ItemID: " + deleting_id);
                        }
                        btnDelete2.setText(String.format("Delete Item (%s)", deleting_id.size()));
                        previousSelection.clear();
                        previousSelection.addAll(currentSelection);
                    });
                }
            }
        });

        lbl_indicate = new JLabel("");
        lbl_indicate.setFont(merriweather.deriveFont(Font.BOLD, 16));
        lbl_indicate.setOpaque(false);
        lbl_indicate.setForeground(new Color(122, 122, 122));

        pages = new JComboBox<>();
        UpdateTable(list_length, page_counter);
        UpdatePages(AllItems.size());
        table_item.setShowHorizontalLines(true);
        table_item.setShowVerticalLines(true);
        table_item.setGridColor(new Color(230, 230, 230));


        scrollPane1 = new CustomComponents.CustomScrollPane(false, 1, table_item,
                6, new Color(202, 202, 202), Main.transparent,
                Main.transparent, Main.transparent, Main.transparent,
                new Color(170, 170, 170), Color.WHITE,
                new Color(140, 140, 140), new Color(110, 110, 110),
                Color.WHITE, Color.WHITE, 6);

        inner.add(scrollPane1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weighty = 0.5;
        gbc.insets = new Insets(0, 2, 2, 0);
        inner.add(lbl_indicate, gbc);

        gbc.gridx = 4;
        gbc.weightx = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0,0,0,11);
        gbc.fill = GridBagConstraints.BOTH;
        page_panel = new JPanel(new GridBagLayout());
        page_panel.setOpaque(false);
        inner.add(page_panel, gbc);

        GridBagConstraints ii_gbc = new GridBagConstraints();

        ii_gbc.gridx = 0;
        ii_gbc.gridy = 0;
        ii_gbc.fill = GridBagConstraints.BOTH;
        ii_gbc.weightx = 0.5;
        ii_gbc.weighty = 0.5;
        p_first = new JButton("First");
        p_first.setFont(merriweather.deriveFont(Font.BOLD, 16));
        p_first.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        p_first.setPreferredSize(new Dimension(50, 27));
        p_first.setForeground(new Color(122, 122, 122));
        p_first.addActionListener(_ -> {
            page_counter = 0;
            pages.setSelectedIndex(page_counter);
            UpdateTable(list_length, page_counter);
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
                UpdateTable(list_length, page_counter);
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
                UpdateTable(list_length, page_counter);
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
                UpdateTable(list_length, page_counter);
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
            UpdateTable(list_length, page_counter);
        });
        page_panel.add(p_last, ii_gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.weightx = 1;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0,0,0,70);
        buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);
        inner.add(buttonPanel, gbc);

        GridBagConstraints buttongbc = new GridBagConstraints();
        buttongbc.gridx = 0;
        buttongbc.weightx = 1;
        buttongbc.fill = GridBagConstraints.BOTH;
        buttongbc.insets = new Insets(3, 7, 5, 20);
        btnAdd = new CustomComponents.CustomButton("Add Item", merriweather, Color.WHITE, Color.WHITE,
                new Color(225, 108, 150), new Color(237, 136, 172),
                Main.transparent, 0, 20, Main.transparent, false,
                5, false, null, 0, 0, 0);
        btnAdd.setPreferredSize(new Dimension(185, 40));
        btnAdd.addActionListener(_ -> {
            AddNewItem.ShowPage();
            AllItems = new Item().ListAll();
            UpdatePages(AllItems.size());
            UpdateTable(list_length, page_counter);
        });
        buttonPanel.add(btnAdd, buttongbc);

        buttongbc.gridx = 1;
        btnEdit = new CustomComponents.CustomButton("Edit Item", merriweather, Color.WHITE, Color.WHITE,
                new Color(225, 108, 150), new Color(237, 136, 172),
                Main.transparent, 0, 20, Main.transparent, false,
                5, false, null, 0, 0, 0);
        btnEdit.setPreferredSize(new Dimension(185, 40));

        btnEdit.addActionListener(_ -> {
            if (table_item.getSelectedRowCount() == 0) {
                CustomComponents.CustomOptionPane.showErrorDialog(
                        parent,
                        "Please select an item to edit!",
                        "Error",
                        new Color(209, 88, 128),
                        new Color(255, 255, 255),
                        new Color(237, 136, 172),
                        new Color(255, 255, 255)
                );
            } else {
                String selected_id = table_item.getValueAt(
                        table_item.getSelectedRow(),
                        table_item.getColumnModel().getColumnIndex("ItemID")
                ).toString();

                System.out.println("Edit button clicked. Selected ItemID: " + selected_id);

                Item selected_item = null;
                for (Item item : AllItems) {
                    if (item.getItemID().equals(selected_id)) {
                        selected_item = item;
                        break;
                    }
                }

                if (selected_item != null) {
//                    EditNewItem.Loader(parent, merriweather, boldonse, content, current_user, selected_item);
//                    EditNewItem.ShowPage();

                    AllItems = new Item().ListAll();
                    UpdatePages(AllItems.size());
                    UpdateTable(list_length, page_counter);

                } else {
                    CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "Selected item not found in the list!",
                            "Error",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255)
                    );
                }
            }
        });
        buttonPanel.add(btnEdit, buttongbc);


        buttongbc.gridx = 2;
        btnView = new CustomComponents.CustomButton("View Item", merriweather, Color.WHITE, Color.WHITE,
                new Color(225, 108, 150), new Color(237, 136, 172),
                Main.transparent, 0, 20, Main.transparent, false,
                5, false, null, 0, 0, 0);
        btnView.setPreferredSize(new Dimension(185, 40));
        btnView.addActionListener(_ -> {
            if (table_item.getSelectedRowCount() == 0) {
                CustomComponents.CustomOptionPane.showErrorDialog(
                        parent,
                        "Please select an item to view!",
                        "Error",
                        new Color(209, 88, 128),
                        new Color(255, 255, 255),
                        new Color(237, 136, 172),
                        new Color(255, 255, 255)
                );
            } else {
                String selected_id = table_item.getValueAt(
                        table_item.getSelectedRow(),
                        table_item.getColumnModel().getColumnIndex("ItemID")
                ).toString();

                Item selected_item = null;
                for (Item item : AllItems) {
                    if (item.getItemID().equals(selected_id)) {
                        selected_item = item;
                        break;
                    }
                }

                if (selected_item != null) {
//                    ViewItem.Loader(parent, merriweather, boldonse, content, selected_item);
//                    boolean see = ViewItem.ShowPage();
//                    if (see) {
//                        System.out.println("Item viewed.");
//                    }
                } else {
                    CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "Selected item not found in the list!",
                            "Error",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255)
                    );
                }
            }
        });
        buttonPanel.add(btnView, buttongbc);

        cancel_delete = new CustomComponents.CustomButton("Cancel", merriweather, Color.WHITE, Color.WHITE,
                new Color(56, 53, 70), new Color(73, 69, 87), null, 0, 16,
                Main.transparent, false, 5, false, null, 0,
                0, 0);
        cancel_delete.setPreferredSize(new Dimension(185, 40));
        cancel_delete.setVisible(false);
        cancel_delete.addActionListener(_ -> {
            deleting = false;
            deleting_id.clear();
            btnView.setEnabled(true);
            btnAdd.setEnabled(true);
            btnEdit.setEnabled(true);
            btnView.setVisible(true);
            cancel_delete.setVisible(false);

            btnView.UpdateColor(new Color(255, 255, 255), new Color(255, 255, 255),
                    new Color(225, 108, 150), new Color(237, 136, 172),
                    Main.transparent);
            btnAdd.UpdateColor(new Color(255, 255, 255), new Color(255, 255, 255),
                    new Color(209, 88, 128), new Color(237, 136, 172),
                    Main.transparent);
            btnEdit.UpdateColor(new Color(255, 255, 255), new Color(255, 255, 255),
                    new Color(225, 108, 150), new Color(237, 136, 172),
                    Main.transparent);

            table_item.SetColors(Color.BLACK, Color.BLACK, Color.WHITE, new Color(212, 212, 212));
            mode = 1;
            table_item.SetChanges(merriweather.deriveFont(Font.BOLD, 18),
                    merriweather.deriveFont(Font.PLAIN, 16), mode);
            scrollPane1.UpdateBorder(1, new Color(202, 202, 202), Main.transparent,
                    Main.transparent, Main.transparent, Main.transparent);

            btnDelete2.setVisible(false);
            btnDelete1.setVisible(true);
        });
        cancel_delete.setVisible(false);
        buttonPanel.add(cancel_delete, buttongbc);


        buttongbc.gridx = 3;
        btnDelete1 = new CustomComponents.CustomButton("Delete Item", merriweather, Color.WHITE, Color.WHITE,
                new Color(50, 8, 32), new Color(174, 122, 140),
                Main.transparent, 0, 20, Main.transparent, false,
                5, false, null, 0, 0, 0);
        btnDelete1.setPreferredSize(new Dimension(185, 40));
        btnDelete1.addActionListener(_ -> {
            deleting = CustomComponents.CustomOptionPane.showConfirmDialog(
                    parent,
                    "Enter delete selection mode?",
                    "Confirmation",
                    new Color(159, 4, 4),
                    new Color(255, 255, 255),
                    new Color(161, 40, 40),
                    new Color(255, 255, 255),
                    new Color(56, 53, 70),
                    new Color(255, 255, 255),
                    new Color(73, 69, 87),
                    new Color(255, 255, 255),
                    false
            );

            if (deleting) {
                btnView.setEnabled(false);
                btnAdd.setEnabled(false);
                btnEdit.setEnabled(false);
                btnView.setVisible(false);
                cancel_delete.setVisible(true);
                btnView.UpdateColor(new Color(199, 200, 202), new Color(199, 200, 202),
                        new Color(242, 242, 242), new Color(241, 241, 242),
                        new Color(241, 241, 242));
                btnAdd.UpdateColor(new Color(199, 200, 202), new Color(199, 200, 202),
                        new Color(241, 241, 242), new Color(241, 241, 242),
                        new Color(241, 241, 242));
                btnEdit.UpdateColor(new Color(199, 200, 202), new Color(199, 200, 202),
                        new Color(241, 241, 242), new Color(241, 241, 242),
                        new Color(241, 241, 242));
                table_item.SetColors(Color.BLACK, Color.BLACK, Color.WHITE, new Color(255, 203, 205));
                mode = 0;
                table_item.SetChanges(merriweather.deriveFont(Font.BOLD, 18),
                        merriweather.deriveFont(Font.PLAIN, 16), mode);
                scrollPane1.UpdateBorder(3, new Color(159, 4, 4), Main.transparent,
                        Main.transparent, Main.transparent, Main.transparent);

                btnDelete1.setVisible(false);
                btnDelete2.setVisible(true);
            }
        });
        buttonPanel.add(btnDelete1, buttongbc);

        btnDelete2 = new CustomComponents.CustomButton("Delete Item (0)", merriweather, Color.WHITE, Color.WHITE,
                new Color(159, 4, 4), new Color(161, 40, 40), null, 0, 16,
                Main.transparent, false, 5, false, null, 0,
                0, 0);
        btnDelete2.setPreferredSize(new Dimension(160, 40));
        btnDelete2.addActionListener(_ -> {
            if (deleting_id.isEmpty()) {
                CustomComponents.CustomOptionPane.showErrorDialog(
                        parent,
                        "Please select at least one item to delete!",
                        "Error",
                        new Color(209, 88, 128),
                        new Color(255, 255, 255),
                        new Color(237, 136, 172),
                        new Color(255, 255, 255)
                );
            } else {

                List<String> ids = new ArrayList<>(deleting_id);

                List<Item> itemsToDelete = new ArrayList<>();
                for (String itemId : ids) {
                    Item item = new Item().GetObjectByID(itemId);
                    if (item != null) {
                        itemsToDelete.add(item);
                    }
                }

                List<Item_Supplier> d_itemSuppliers = new Item_Supplier().GetObjectsByIDS(ids);
//                DeleteItem.UpdateItemSupplier(d_itemSuppliers);
//                DeleteItem.Loader(parent, merriweather, boldonse, content, itemsToDelete);

//                boolean delete = DeleteItem.ShowPage();
//                if (delete) {
//                    deleting = false;
//
//                    for (String itemId : ids) {
//                        new Item().Remove(new Item().GetObjectByID(itemId));
//                    }
//
//                    for (Item_Supplier itemSupplier : d_itemSuppliers) {
//                        new Item_Supplier().Remove(itemSupplier);
//                    }
//
//                    AllItems = new Item().ListAll();
//                    deleting_id.clear();
//
//                    btnView.setEnabled(true);
//                    btnAdd.setEnabled(true);
//                    btnEdit.setEnabled(true);
//                    btnView.setVisible(true);
//                    cancel_delete.setVisible(false);
//
//                    btnView.UpdateColor(new Color(255, 255, 255), new Color(255, 255, 255),
//                            new Color(225, 108, 150), new Color(237, 136, 172),
//                            Main.transparent);
//                    btnAdd.UpdateColor(new Color(255, 255, 255), new Color(255, 255, 255),
//                            new Color(209, 88, 128), new Color(237, 136, 172),
//                            Main.transparent);
//                    btnEdit.UpdateColor(new Color(255, 255, 255), new Color(255, 255, 255),
//                            new Color(225, 108, 150), new Color(237, 136, 172),
//                            Main.transparent);
//
//                    table_item.SetColors(Color.BLACK, Color.BLACK, Color.WHITE, new Color(212, 212, 212));
//                    mode = 1;
//                    table_item.SetChanges(merriweather.deriveFont(Font.BOLD, 18),
//                            merriweather.deriveFont(Font.PLAIN, 16), mode);
//
//                    scrollPane1.UpdateBorder(1, new Color(202, 202, 202), Main.transparent,
//                            Main.transparent, Main.transparent, Main.transparent);
//                    filter = 0;
//                    page_counter = 0;
//                    UpdatePages(list_length);
//                    pages.setSelectedIndex(0);
//                    UpdatePages(AllItems.size());
//                    UpdateTable(list_length, page_counter);
//
//                    btnDelete2.setVisible(false);
//                    btnDelete1.setVisible(true);
//                }
            }
        });
        btnDelete2.setVisible(false);
        buttonPanel.add(btnDelete2, ii_gbc);
        AddNewItem.Loader(parent, merriweather, boldonse, content, current_user);
    }


    public static void UpdateTable(int length, int page) {
        String[] titles = new String[]{"ItemID", "ItemName", "Unit Price", "Unit Cost", "StockCount", "Threshold",
                "Category", "Last Update", "Supplier Name"};
        Object[][] data;
        int counter = 0;
        int anti_counter = page * length;

        if (length >= AllItems.size() - page * length) {
            data = new Object[AllItems.size() - page * length][titles.length];
        } else {
            data = new Object[length][titles.length];
        }

        for (Item item : AllItems) {
            if (anti_counter != 0) {
                anti_counter -= 1;
                continue;
            } else {
                List<Item_Supplier> item_suppliers = new Item_Supplier().ListAllWithFilter(item.getItemID());
                StringBuilder supplierName = new StringBuilder();
                int looper = 1;
                for (Item_Supplier it_spl : item_suppliers) {
                    supplierName.append(new Supplier().GetObjectByID(it_spl.getSupplierID()).getSupplierName());
                    if (looper < item_suppliers.size()) { supplierName.append(", "); }
                    looper += 1;
                }

                data[counter] = new Object[]{
                        item.getItemID(),
                        item.getItemName(),
                        item.getUnitPrice(),
                        item.getUnitCost(),
                        item.getStockCount(),
                        item.getThreshold(),
                        item.getCategory(),
                        item.getLastUpdate(),
                        supplierName.toString()
                };
                counter += 1;
                if (counter == length || counter == AllItems.size()) { break; }
            }
        }

        table_item.UpdateTableContent(titles, data);

        String temp2 = "<html>Displaying <b>%s</b> to <b>%s</b> of <b>%s</b> records</html>";
        if (length >= AllItems.size()) {
            lbl_indicate.setText(String.format(temp2, (!AllItems.isEmpty()) ? 1 : 0, AllItems.size(),
                    AllItems.size()));
        } else {
            lbl_indicate.setText(String.format(temp2, page * length + 1,
                    Math.min((page + 1) * length, AllItems.size()),
                    AllItems.size()));
        }
    }


    public static void UpdatePages(int totalItems) {
        int pageCount = (int) Math.ceil(totalItems / (double) list_length);
        if (totalItems <= list_length) {
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
        String searcher = (!search.getText().isEmpty() && !Objects.equals(search.getText(), "Search..."))
                ? search.getText().trim() : "";

        List<Item> temp_list = new Item().ListAllWithFilter(searcher);
        if (!searcher.isEmpty()) {
            List<String> list_of_added = new ArrayList<>();
            for (Item i : temp_list) {
                list_of_added.add(i.getItemID());
            }
            List<Supplier> list_by_name = new Supplier().ListAllWithType(searcher);
            if (!list_by_name.isEmpty()) {
                for (Supplier spl : list_by_name) {
                    List<Item_Supplier> soft_list = new Item_Supplier().ListAllWithType(spl.getSupplierID());
                    for (Item_Supplier it_spl : soft_list) {
                        if (!list_of_added.contains(it_spl.getItemID())) { list_of_added.add(it_spl.getItemID()); }
                    }
                }
                temp_list = new ArrayList<>();
                for (String id : list_of_added) {
                    temp_list.add(new Item().GetObjectByID(id));
                }
            }
        }
        if (temp_list.isEmpty()) {
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
            AllItems = temp_list;
            page_counter = 0;
            UpdatePages(list_length);
            UpdateTable(list_length, page_counter);
            SwingUtilities.invokeLater(table_item::requestFocusInWindow);
        }
    }

    public static void UpdateComponentSize(int base_size) {
        search_icon1.UpdateSize((int) (base_size * 0.8));
        search_icon2.UpdateSize((int) (base_size * 0.8));
        s_btn.setSize(search_icon1.getIconWidth(), search_icon1.getIconHeight());
        s_btn.repaint();
        icon_clear1.UpdateSize((int) (base_size * 0.8));
        icon_clear2.UpdateSize((int) (base_size * 0.8));
        clear_btn.setSize(icon_clear1.getIconWidth(), icon_clear1.getIconHeight());
        clear_btn.setPreferredSize(new Dimension(icon_clear1.getIconWidth(), icon_clear1.getIconHeight()));
        clear_btn.revalidate();
        clear_btn.repaint();
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
        table_item.SetChanges(merriweather.deriveFont(Font.BOLD, (int) (base_size * 0.95)),
                merriweather.deriveFont(Font.PLAIN, (int) (base_size * 0.9)), mode);
        btnAdd.UpdateCustomButton(0, (int) (base_size * 0.9), null, 0);
        btnEdit.UpdateCustomButton(0, (int) (base_size * 0.9), null, 0);
        btnDelete1.UpdateCustomButton(0, (int) (base_size * 0.9), null, 0);
        btnDelete2.UpdateCustomButton(0, (int) (base_size * 0.9), null, 0);
        btnView.UpdateCustomButton(0, (int) (base_size * 0.9), null, 0);
        cancel_delete.UpdateCustomButton(0, (int) (base_size * 0.9), null, 0);

        s_btn.revalidate();
        p_left.revalidate();
        p_right.revalidate();
        p_first.revalidate();
        p_last.revalidate();
        pages.revalidate();
        lbl_show.revalidate();
        lbl_entries.revalidate();
        lbl_indicate.revalidate();
        entries.revalidate();
        search.revalidate();
        table_item.revalidate();
        btnAdd.revalidate();
        btnEdit.revalidate();
        btnView.revalidate();
        btnDelete1.revalidate();
        btnDelete2.revalidate();
        cancel_delete.revalidate();

        s_btn.repaint();
        p_left.repaint();
        p_right.repaint();
        p_first.repaint();
        p_last.repaint();
        pages.repaint();
        lbl_show.repaint();
        lbl_entries.repaint();
        lbl_indicate.repaint();
        entries.repaint();
        search.repaint();
        table_item.repaint();
        btnAdd.repaint();
        btnEdit.repaint();
        btnView.repaint();
        btnDelete1.repaint();
        btnDelete2.repaint();
        cancel_delete.repaint();
    }
}
