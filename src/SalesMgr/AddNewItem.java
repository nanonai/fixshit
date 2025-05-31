package SalesMgr;

import Admin.*;
import Classes.*;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.*;
import java.awt.Font;
import java.awt.Color;
import java.awt.font.TextAttribute;
import java.util.List;

public class AddNewItem {
    private static JFrame parent;
    private static Font merriweather, boldonse;
    private static JPanel content;
    private static User current_user;
    private static Item past, future;
    private static JComboBox<String> types;
    private static CustomComponents.EmptyTextField itemname, unitprice, unitcost, stockcount, threshold, suppliername;

    public static void Loader(JFrame parent, Font merriweather, Font boldonse, JPanel content, User current_user) {
        AddNewItem.parent = parent;
        AddNewItem.merriweather = merriweather;
        AddNewItem.boldonse = boldonse;
        AddNewItem.content = content;
        AddNewItem.current_user = current_user;
    }

    public static void ShowPage() {
        final int[][] result = {{0, 0}};
        JDialog dialog = new JDialog(parent, "Add New Item", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(parent.getWidth() / 2, parent.getHeight() / 2);
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

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 0);
        gbc.fill = GridBagConstraints.BOTH;
        JLabel title = new JLabel("Add New Item");
        title.setOpaque(false);
        title.setFont(merriweather.deriveFont(Font.BOLD, (float) (base_size * 1.3)));
        panel.add(title, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 10, 10, 0);
        JLabel type_label = new JLabel("Item Category:");
        type_label.setOpaque(false);
        type_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(type_label, gbc);

        gbc.gridy = 2;
        JLabel name_label = new JLabel("Item Name:");
        name_label.setOpaque(false);
        name_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(name_label, gbc);

        gbc.gridy = 3;
        JLabel price_label = new JLabel("Unit Price:");
        price_label.setOpaque(false);
        price_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(price_label, gbc);

        gbc.gridy = 4;
        JLabel unitcost_label = new JLabel("Unit Cost:");
        unitcost_label.setOpaque(false);
        unitcost_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(unitcost_label, gbc);

        gbc.gridy = 5;
        JLabel qty_label = new JLabel("Stock Count:");
        qty_label.setOpaque(false);
        qty_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(qty_label, gbc);

        gbc.gridy = 6;
        JLabel min_label = new JLabel("Minimum Threshold:");
        min_label.setOpaque(false);
        min_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(min_label, gbc);

        gbc.gridy = 7;
        JLabel supplier_label = new JLabel("Supplier Name:");
        supplier_label.setOpaque(false);
        supplier_label.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size)));
        panel.add(supplier_label, gbc);

        gbc.gridy = 8;
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
        JLabel placeholder = new JLabel("");
        placeholder.setOpaque(false);
        button_panel.add(placeholder, gbc);

        gbc.gridx = 1;
        CustomComponents.CustomButton confirm = new CustomComponents.CustomButton("Confirm",
                merriweather.deriveFont(Font.PLAIN), new Color(255, 255, 255), new Color(255, 255, 255),
                new Color(209, 88, 128), new Color(237, 136, 172),
                Main.transparent, 0, base_size, Main.transparent, false, 5, false,
                null, 0, 0, 0);
        button_panel.add(confirm, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 10, 10);

        List<Item> allItems = new Item().ListAll();
        List<String> allCategory = new ArrayList<>();
        for (Item i : allItems) {
            if (!allCategory.contains(i.getCategory())) {
                allCategory.add(i.getCategory());
            }
        }
        types = new JComboBox<>(allCategory.toArray(new String[0]));
        types.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
        types.setEditable(true);
        types.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        types.addActionListener(_ -> {SwingUtilities.invokeLater(itemname::requestFocusInWindow);});
        setAlphaSpaceOnlyFilter(types);
        panel.add(types, gbc);

        gbc.gridy = 2;
        JPanel inner1 = new JPanel(new GridBagLayout());
        inner1.setOpaque(true);
        inner1.setBackground(Color.WHITE);
        inner1.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        panel.add(inner1, gbc);

        gbc.gridy = 3;
        JPanel inner2 = new JPanel(new GridBagLayout());
        inner2.setOpaque(true);
        inner2.setBackground(Color.WHITE);
        inner2.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        panel.add(inner2, gbc);

        gbc.gridy = 4;
        JPanel inner3 = new JPanel(new GridBagLayout());
        inner3.setOpaque(true);
        inner3.setBackground(Color.WHITE);
        inner3.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        panel.add(inner3, gbc);

        gbc.gridy = 5;
        JPanel inner4 = new JPanel(new GridBagLayout());
        inner4.setOpaque(true);
        inner4.setBackground(Color.WHITE);
        inner4.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        panel.add(inner4, gbc);

        gbc.gridy = 6;
        JPanel inner5 = new JPanel(new GridBagLayout());
        inner5.setOpaque(true);
        inner5.setBackground(Color.WHITE);
        inner5.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        panel.add(inner5, gbc);

        gbc.gridy = 7;
        JPanel inner6 = new JPanel(new GridBagLayout());
        inner6.setOpaque(true);
        inner6.setBackground(Color.WHITE);
        inner6.setBorder(BorderFactory.createLineBorder(new Color(209, 209, 209), 1));
        panel.add(inner6, gbc);

        GridBagConstraints igbc = new GridBagConstraints();
        igbc.fill = GridBagConstraints.BOTH;
        igbc.gridx = 0;
        igbc.gridy = 0;
        igbc.weightx = 5;
        igbc.weighty = 1;
        itemname = new CustomComponents.EmptyTextField(20, "",
                new Color(122, 122, 122));
        itemname.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
        itemname.addActionListener(_ -> {SwingUtilities.invokeLater(unitprice::requestFocusInWindow);});
        itemname.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                itemname.setForeground(Color.BLACK);
                itemname.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
                itemname.setToolTipText("Enter the item name (8 to 48 characters).");
            }

            @Override
            public void focusLost(FocusEvent e) {
                itemname.setToolTipText("");
                String input = itemname.getText();
                if ((input.length() < 8 || input.length() > 48) && !input.isEmpty()) {
                    itemname.setForeground(new Color(159, 4, 4));
                    Font font = itemname.getFont();
                    Map<TextAttribute, Object> attributes = new java.util.HashMap<>(font.getAttributes());
                    attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                    itemname.setFont(font.deriveFont(attributes));
                    itemname.setToolTipText("Item name length must be between 8 and 48 characters.");
                }
            }
        });
        ((AbstractDocument) itemname.getDocument()).setDocumentFilter(new AlphaNumericFilter());
        inner1.add(itemname, igbc);

        unitprice = new CustomComponents.EmptyTextField(20, "",
                new Color(122, 122, 122));
        unitprice.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
        unitprice.addActionListener(_ -> {SwingUtilities.invokeLater(unitcost::requestFocusInWindow);});
        unitprice.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                unitprice.setForeground(Color.BLACK);
                unitprice.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
                unitprice.setToolTipText("Enter unit price (positive number).");
            }

            @Override
            public void focusLost(FocusEvent e) {
                unitprice.setToolTipText("");
                String input = unitprice.getText().trim();
                try {
                    double value = Double.parseDouble(input);
                    if (value <= 0) {
                        unitprice.setForeground(new Color(159, 4, 4));
                        Font font = unitprice.getFont();
                        Map<TextAttribute, Object> attributes = new java.util.HashMap<>(font.getAttributes());
                        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                        unitprice.setFont(font.deriveFont(attributes));
                        unitprice.setToolTipText("Unit price must be a positive value.");
                    }
                } catch (NumberFormatException ex) {
                    unitprice.setForeground(new Color(159, 4, 4));
                    Font font = unitprice.getFont();
                    Map<TextAttribute, Object> attributes = new java.util.HashMap<>(font.getAttributes());
                    attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                    unitprice.setFont(font.deriveFont(attributes));
                    unitprice.setToolTipText("Unit price must be a valid number.");
                }
            }
        });
        ((AbstractDocument) unitprice.getDocument()).setDocumentFilter(new DigitLimitDotFilter(10));
        inner2.add(unitprice, igbc);

        unitcost = new CustomComponents.EmptyTextField(20, "",
                new Color(122, 122, 122));
        unitcost.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
        unitcost.addActionListener(_ -> {SwingUtilities.invokeLater(stockcount::requestFocusInWindow);});
        unitcost.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                unitcost.setForeground(Color.BLACK);
                unitcost.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
                unitcost.setToolTipText("Enter unit cost (positive number).");
            }

            @Override
            public void focusLost(FocusEvent e) {
                unitcost.setToolTipText("");
                String input = unitcost.getText();
                try {
                    double value = Double.parseDouble(input);
                    if (value <= 0) {
                        unitcost.setForeground(new Color(159, 4, 4));
                        Font font = unitcost.getFont();
                        Map<TextAttribute, Object> attributes = new java.util.HashMap<>(font.getAttributes());
                        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                        unitcost.setFont(font.deriveFont(attributes));
                        unitcost.setToolTipText("Unit cost must be a positive value.");
                    }
                } catch (NumberFormatException ex) {
                    unitcost.setForeground(new Color(159, 4, 4));
                    Font font = unitcost.getFont();
                    Map<TextAttribute, Object> attributes = new java.util.HashMap<>(font.getAttributes());
                    attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                    unitcost.setFont(font.deriveFont(attributes));
                    unitcost.setToolTipText("Unit cost must be a valid number.");
                }
            }
        });
        ((AbstractDocument) unitcost.getDocument()).setDocumentFilter(new DigitLimitDotFilter(10));
        inner3.add(unitcost, igbc);

        stockcount = new CustomComponents.EmptyTextField(20, "",
                new Color(122, 122, 122));
        stockcount.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
        stockcount.addActionListener(_ -> {SwingUtilities.invokeLater(threshold::requestFocusInWindow);});
        stockcount.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                stockcount.setForeground(Color.BLACK);
                stockcount.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
                stockcount.setToolTipText("Enter stock count (non-negative integer).");
            }

            @Override
            public void focusLost(FocusEvent e) {
                stockcount.setToolTipText("");
                String input = stockcount.getText();
                try {
                    int value = Integer.parseInt(input);
                    if (value < 0) {
                        stockcount.setForeground(new Color(159, 4, 4));
                        Font font = stockcount.getFont();
                        Map<TextAttribute, Object> attributes = new java.util.HashMap<>(font.getAttributes());
                        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                        stockcount.setFont(font.deriveFont(attributes));
                        stockcount.setToolTipText("Stock count must be a non-negative integer.");
                    }
                } catch (NumberFormatException ex) {
                    stockcount.setForeground(new Color(159, 4, 4));
                    Font font = stockcount.getFont();
                    Map<TextAttribute, Object> attributes = new java.util.HashMap<>(font.getAttributes());
                    attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                    stockcount.setFont(font.deriveFont(attributes));
                    stockcount.setToolTipText("Stock count must be an integer.");
                }
            }
        });
        ((AbstractDocument) stockcount.getDocument()).setDocumentFilter(new DigitLimitFilter(20));
        inner4.add(stockcount, igbc);

        threshold = new CustomComponents.EmptyTextField(20, "", new Color(122, 122, 122));
        threshold.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
        threshold.addActionListener(_ -> {SwingUtilities.invokeLater(suppliername::requestFocusInWindow);});
        threshold.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                threshold.setForeground(Color.BLACK);
                threshold.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
                threshold.setToolTipText("Enter threshold (non-negative integer).");
            }

            @Override
            public void focusLost(FocusEvent e) {
                threshold.setToolTipText("");
                String input = threshold.getText();
                try {
                    int value = Integer.parseInt(input);
                    if (value < 0) {
                        threshold.setForeground(new Color(159, 4, 4));
                        Font font = threshold.getFont();
                        Map<TextAttribute, Object> attributes = new java.util.HashMap<>(font.getAttributes());
                        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                        threshold.setFont(font.deriveFont(attributes));
                        threshold.setToolTipText("Threshold must be a non-negative integer.");
                    }
                } catch (NumberFormatException ex) {
                    threshold.setForeground(new Color(159, 4, 4));
                    Font font = threshold.getFont();
                    Map<TextAttribute, Object> attributes = new java.util.HashMap<>(font.getAttributes());
                    attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                    threshold.setFont(font.deriveFont(attributes));
                    threshold.setToolTipText("Threshold must be an integer.");
                }
            }
        });
        ((AbstractDocument) threshold.getDocument()).setDocumentFilter(new DigitLimitFilter(20));
        inner5.add(threshold, igbc);


        suppliername = new CustomComponents.EmptyTextField(20, "",
                new Color(122, 122, 122));
        suppliername.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
        suppliername.addActionListener(_ -> {SwingUtilities.invokeLater(confirm::doClick);});
        suppliername.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                suppliername.setForeground(Color.BLACK);
                suppliername.setFont(merriweather.deriveFont(Font.PLAIN, (float) (base_size * 0.8)));
                suppliername.setToolTipText("Enter supplier name.");
            }

            @Override
            public void focusLost(FocusEvent e) {
                suppliername.setToolTipText("");
                String input = suppliername.getText().trim();

                if (input.isEmpty()) {
                    suppliername.setForeground(new Color(159, 4, 4));
                    Font font = suppliername.getFont();
                    Map<TextAttribute, Object> attributes = new HashMap<>(font.getAttributes());
                    attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                    suppliername.setFont(font.deriveFont(attributes));
                    suppliername.setToolTipText("Supplier name cannot be empty.");
                } else {
                    Supplier supplierID= new Supplier().GetObjectByName(input);
                    if (supplierID == null) {
                        suppliername.setForeground(new Color(159, 4, 4));
                        Font font = suppliername.getFont();
                        Map<TextAttribute, Object> attributes = new HashMap<>(font.getAttributes());
                        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                        suppliername.setFont(font.deriveFont(attributes));
                        suppliername.setToolTipText("Supplier name not found or does not exist!");
                    } else {
                        suppliername.setForeground(Color.BLACK);
                        suppliername.setFont(suppliername.getFont().deriveFont(Font.PLAIN));
                        suppliername.setToolTipText("");
                    }
                }
            }
        });
        ((AbstractDocument) suppliername.getDocument()).setDocumentFilter(new AlphaNumericFilter());
        inner6.add(suppliername, igbc);

        CustomComponents.CustomXIcon icon_clear1 = new CustomComponents.CustomXIcon((int) (base_size * 0.8), 3,
                new Color(209, 209, 209), true);
        CustomComponents.CustomXIcon icon_clear2 = new CustomComponents.CustomXIcon((int) (base_size * 0.8), 3,
                Color.BLACK, true);

        igbc.gridx = 3;
        igbc.weightx = 1;
        JButton clear1 = new JButton(icon_clear1);
        clear1.setRolloverIcon(icon_clear2);
        clear1.setOpaque(false);
        clear1.setFocusable(false);
        clear1.setBorder(BorderFactory.createEmptyBorder());
        clear1.addActionListener(_ -> {itemname.Reset();});
        inner1.add(clear1, igbc);

        JButton clear2 = new JButton(icon_clear1);
        clear2.setRolloverIcon(icon_clear2);
        clear2.setOpaque(false);
        clear2.setFocusable(false);
        clear2.setBorder(BorderFactory.createEmptyBorder());
        clear2.addActionListener(_ -> {unitprice.Reset();});
        inner2.add(clear2, igbc);

        JButton clear3 = new JButton(icon_clear1);
        clear3.setRolloverIcon(icon_clear2);
        clear3.setOpaque(false);
        clear3.setFocusable(false);
        clear3.setBorder(BorderFactory.createEmptyBorder());
        clear3.addActionListener(_ -> {unitcost.Reset();});
        inner3.add(clear3, igbc);

        JButton clear4 = new JButton(icon_clear1);
        clear4.setRolloverIcon(icon_clear2);
        clear4.setOpaque(false);
        clear4.setFocusable(false);
        clear4.setBorder(BorderFactory.createEmptyBorder());
        clear4.addActionListener(_ -> {stockcount.Reset();});
        inner4.add(clear4, igbc);

        JButton clear5 = new JButton(icon_clear1);
        clear5.setRolloverIcon(icon_clear2);
        clear5.setOpaque(false);
        clear5.setFocusable(false);
        clear5.setBorder(BorderFactory.createEmptyBorder());
        clear5.addActionListener(_ -> {threshold.Reset();});
        inner5.add(clear5, igbc);

        JButton clear6 = new JButton(icon_clear1);
        clear6.setRolloverIcon(icon_clear2);
        clear6.setOpaque(false);
        clear6.setFocusable(false);
        clear6.setBorder(BorderFactory.createEmptyBorder());
        clear6.addActionListener(_ -> {suppliername.Reset();});
        inner6.add(clear6, igbc);

        cancel.addActionListener(_ -> {
            dialog.dispose();
        });

        confirm.addActionListener(_ -> {
            if (itemname.getText().isEmpty() || unitprice.getText().isEmpty() || unitcost.getText().isEmpty() ||
                    stockcount.getText().isEmpty() || threshold.getText().isEmpty() || suppliername.getText().isEmpty()) {

                CustomComponents.CustomOptionPane.showErrorDialog(
                        parent,
                        "Fields cannot be left empty or just contain spaces!",
                        "Error",
                        new Color(209, 88, 128),
                        new Color(255, 255, 255),
                        new Color(237, 136, 172),
                        new Color(255, 255, 255)
                );
            } else {
                Item checking = new Item("", itemname.getText(), Double.parseDouble(unitprice.getText().trim()),
                        Double.parseDouble(unitcost.getText().trim()), Integer.parseInt(stockcount.getText()),
                        Integer.parseInt(threshold.getText()), Objects.requireNonNull(types.getSelectedItem()).toString(), null);
                String validity = new Item().ValidityChecker(checking);
                if (validity.charAt(0) == '0') {
                    CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "Item name must be between 8 and 48 characters!",
                            "Error",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255)
                    );
                } else if (validity.charAt(1) == 'X') {
                    CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "Item name already exists!",
                            "Error",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255)
                    );
                } else if (validity.charAt(2) == '0') {
                    CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "Unit price must be a valid positive number!",
                            "Error",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255)
                    );
                } else if (validity.charAt(3) == '0') {
                    CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "Unit cost must be a valid positive number!",
                            "Error",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255)
                    );
                } else if (validity.charAt(4) == 'X') {
                    CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "Unit cost must be lesser than unit price!",
                            "Error",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255)
                    );
                } else if (validity.charAt(5) == '0') {
                    CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "Stock count must be a valid non-negative integer!",
                            "Error",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255)
                    );
                } else if (validity.charAt(6) == '0') {
                    CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "Threshold must be a valid non-negative integer!",
                            "Error",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255)
                    );
                } else if (validity.charAt(7) == '0') {
                    CustomComponents.CustomOptionPane.showErrorDialog(
                            parent,
                            "Category name must be between 8 and 48 characters!",
                            "Error",
                            new Color(209, 88, 128),
                            new Color(255, 255, 255),
                            new Color(237, 136, 172),
                            new Color(255, 255, 255)
                    );
                } else {
                    Supplier spl = new Supplier().GetObjectByName(suppliername.getText().strip());
                    if (spl == null) {
                        CustomComponents.CustomOptionPane.showErrorDialog(
                                parent,
                                "Supplier not found! PLease add supplier!",
                                "Error",
                                new Color(209, 88, 128),
                                new Color(255, 255, 255),
                                new Color(237, 136, 172),
                                new Color(255, 255, 255)
                        );
                    } else {
                        String supplierID = spl.getSupplierID();
                        double unitPriceValue = Double.parseDouble(unitprice.getText().trim());
                        double unitCostValue = Double.parseDouble(unitcost.getText().trim());
                        int stockCountValue = Integer.parseInt(stockcount.getText().trim());
                        int thresholdValue = Integer.parseInt(threshold.getText().trim());

                        String item_type = (String) Objects.requireNonNull(types.getSelectedItem());
                        String new_id = new Item().IdMaker();
                        LocalDate currentDate = LocalDate.now();
                        future = new Item(new_id, itemname.getText(), unitPriceValue, unitCostValue,
                                stockCountValue, thresholdValue, item_type, currentDate);
                        new Item().AddNew(future);

                        Item_Supplier itemSupplier = new Item_Supplier(future.getItemID(), spl.getSupplierID());
                        new Item_Supplier().AddNew(itemSupplier);

                        boolean keep_adding = CustomComponents.CustomOptionPane.showConfirmDialog(
                                parent,
                                "Keep adding new items?",
                                "Confirmation",
                                new Color(209, 88, 128),
                                new Color(255, 255, 255),
                                new Color(237, 136, 172),
                                new Color(255, 255, 255),
                                new Color(56, 53, 70),
                                new Color(255, 255, 255),
                                new Color(73, 69, 87),
                                new Color(255, 255, 255),
                                true
                        );

                        if (!keep_adding) {
                            dialog.dispose();
                        } else {
                            itemname.Reset();
                            unitprice.Reset();
                            unitcost.Reset();
                            types.setSelectedIndex(-1);
                            stockcount.Reset();
                            threshold.Reset();
                            suppliername.Reset();
                        }
                    }
                }
            }
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
    }

    static class DigitLimitFilter extends DocumentFilter {
        private final int maxLength;

        public DigitLimitFilter(int maxLength) {
            this.maxLength = maxLength;
        }

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            if (string != null && string.matches("\\d+")) {
                int newLength = fb.getDocument().getLength() + string.length();
                if (newLength <= maxLength) {
                    super.insertString(fb, offset, string, attr);
                } else {
                    int allowed = maxLength - fb.getDocument().getLength();
                    if (allowed > 0) {
                        super.insertString(fb, offset, string.substring(0, allowed), attr);
                    }
                }
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            if (text != null && text.matches("\\d+")) {
                int currentLength = fb.getDocument().getLength();
                int newLength = currentLength - length + text.length();
                if (newLength <= maxLength) {
                    super.replace(fb, offset, length, text, attrs);
                } else {
                    int allowed = maxLength - (currentLength - length);
                    if (allowed > 0) {
                        super.replace(fb, offset, length, text.substring(0, allowed), attrs);
                    }
                }
            } else if (Objects.requireNonNull(text).isEmpty()) {
                super.replace(fb, offset, length, text, attrs);
            }
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length)
                throws BadLocationException {
            super.remove(fb, offset, length);
        }
    }

    static class DigitLimitDotFilter extends DocumentFilter {
        private final int maxLength;

        public DigitLimitDotFilter(int maxLength) {
            this.maxLength = maxLength;
        }

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            if (string != null && isValidInput(fb.getDocument().getText(0, fb.getDocument().getLength()), string)) {
                int newLength = fb.getDocument().getLength() + string.length();
                if (newLength <= maxLength) {
                    super.insertString(fb, offset, string, attr);
                } else {
                    int allowed = maxLength - fb.getDocument().getLength();
                    if (allowed > 0) {
                        String truncated = truncateToAllowed(fb.getDocument().getText(0, fb.getDocument().getLength()), string, allowed);
                        super.insertString(fb, offset, truncated, attr);
                    }
                }
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            if (text == null || text.isEmpty()) {
                super.replace(fb, offset, length, text, attrs); // allow deletions
                return;
            }

            String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
            StringBuilder sb = new StringBuilder(currentText);
            sb.replace(offset, offset + length, text);

            if (isValidInput(currentText, text)) {
                int newLength = sb.length();
                if (newLength <= maxLength) {
                    super.replace(fb, offset, length, text, attrs);
                } else {
                    int allowed = maxLength - (currentText.length() - length);
                    if (allowed > 0) {
                        String truncated = truncateToAllowed(currentText, text, allowed);
                        super.replace(fb, offset, length, truncated, attrs);
                    }
                }
            }
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length)
                throws BadLocationException {
            super.remove(fb, offset, length);
        }

        private boolean isValidInput(String currentText, String newText) {
            for (char ch : newText.toCharArray()) {
                if (!Character.isDigit(ch) && ch != '.') {
                    return false;
                }
            }
            long dotsInCurrent = currentText.chars().filter(c -> c == '.').count();
            long dotsInNew = newText.chars().filter(c -> c == '.').count();
            return (dotsInCurrent + dotsInNew) <= 1;
        }

        private String truncateToAllowed(String currentText, String newText, int allowed) {
            StringBuilder result = new StringBuilder();
            long dotCount = currentText.chars().filter(c -> c == '.').count();

            for (char ch : newText.toCharArray()) {
                if (result.length() >= allowed) break;
                if (Character.isDigit(ch)) {
                    result.append(ch);
                } else if (ch == '.' && dotCount == 0) {
                    result.append(ch);
                    dotCount++;
                }
            }
            return result.toString();
        }
    }

    static class AlphaNumericFilter extends DocumentFilter {
        private static final int MAX_LENGTH = 255;
        private static final String ALPHA_NUMERIC_REGEX = "[a-zA-Z0-9 ]+";

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (string != null && string.matches(ALPHA_NUMERIC_REGEX)) {
                int currentLength = fb.getDocument().getLength();
                if ((currentLength + string.length()) <= MAX_LENGTH) {
                    super.insertString(fb, offset, string, attr);
                }
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (text == null || text.isEmpty() || text.matches(ALPHA_NUMERIC_REGEX)) {
                int currentLength = fb.getDocument().getLength();
                int newLength = currentLength - length + (text != null ? text.length() : 0);
                if (newLength <= MAX_LENGTH) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        }
    }

    private static void setAlphaSpaceOnlyFilter(JComboBox<String> comboBox) {
        Component editorComponent = comboBox.getEditor().getEditorComponent();
        if (editorComponent instanceof JTextComponent textComponent) {
            AbstractDocument doc = (AbstractDocument) textComponent.getDocument();
            doc.setDocumentFilter(new AlphaSpaceFilter());
        }
    }

    static class AlphaSpaceFilter extends DocumentFilter {
        private static final String REGEX = "[a-zA-Z ]+";

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            if (string != null && string.matches(REGEX)) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            if (text != null && text.matches(REGEX)) {
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }
}