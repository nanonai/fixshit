package FinanceMgr;

import Classes.*;
import Admin.Main;
import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

public class ReportPage {
    private static JFrame parent;
    private static Font merriweather;
    private static JPanel content;
    private static JTextArea poDetail,salesDetail;
    private static CustomComponents.EmptyTextField startDateTxtField,endDateTxtField,
            totalPOAmtTxtField,totalSalesAmtTxtField,totalSalesCountTxtField, totalPOCountTxtField;

    public static void Loader(JFrame parent, Font merriweather, JPanel content){
        ReportPage.parent = parent;
        ReportPage.merriweather = merriweather;
        ReportPage.content = content;
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
        JLabel lbl_begin_date = new JLabel("Start Date : ");
        lbl_begin_date.setFont(merriweather.deriveFont(Font.BOLD, 16));
        lbl_begin_date.setOpaque(false);
        lbl_begin_date.setForeground(new Color(0, 0, 0, 252));
        inner.add(lbl_begin_date, igbc);

        igbc.gridx = 1;
        igbc.insets = new Insets(10, 5, 10, 5);
        startDateTxtField = new CustomComponents.EmptyTextField(19, "", new Color(122, 122, 122));
        startDateTxtField.setFont(merriweather.deriveFont(Font.BOLD, 16));
        startDateTxtField.setOpaque(true);
        startDateTxtField.setBackground(Color.LIGHT_GRAY);
        startDateTxtField.setForeground(new Color(0, 0, 0, 252));
        inner.add(startDateTxtField,igbc);

        igbc.gridx = 2;
        igbc.insets = new Insets(10, 10, 10, 10);
        JLabel lbl_to = new JLabel("End Date : ");
        lbl_to.setFont(merriweather.deriveFont(Font.BOLD, 16));
        lbl_to.setOpaque(false);
        lbl_to.setForeground(new Color(0, 0, 0, 252));
        inner.add(lbl_to, igbc);

        igbc.gridx = 3;
        igbc.insets = new Insets(10, 5, 10, 5);
        endDateTxtField = new CustomComponents.EmptyTextField(19, "", new Color(122, 122, 122));
        endDateTxtField.setFont(merriweather.deriveFont(Font.BOLD, 16));
        endDateTxtField.setOpaque(true);
        endDateTxtField.setBackground(Color.LIGHT_GRAY);
        endDateTxtField.setForeground(new Color(0, 0, 0, 252));
        inner.add(endDateTxtField, igbc);

        igbc.gridx = 0;
        igbc.gridy = 2;
        igbc.insets = new Insets(10, 10, 10, 10);
        igbc.anchor = GridBagConstraints.WEST;
        JLabel lbl_Status = new JLabel("Status : ");
        lbl_Status.setFont(merriweather.deriveFont(Font.BOLD, 16));
        lbl_Status.setOpaque(false);
        lbl_Status.setForeground(new Color(0, 0, 0, 252));
        inner.add(lbl_Status, igbc);

        igbc.gridx = 1;
        igbc.gridy = 2;
        igbc.insets = new Insets(10, 5, 10, 5);
        igbc.anchor = GridBagConstraints.WEST;
        String[] statusOptions = { "Pending", "Approved", "Arrived", "Denied", "Paid" };
        JComboBox<String> statusCombo = new JComboBox<>(statusOptions);
        statusCombo.setBackground(Color.WHITE);
        statusCombo.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 252), 1));
        statusCombo.setFont(new Font("Merriweather", Font.PLAIN, 18));
        statusCombo.setPreferredSize(new Dimension(120, 25));
        inner.add(statusCombo, igbc);

        JPanel button_panel1 = new JPanel(new GridBagLayout());
        button_panel1.setOpaque(false);
        inner.add(button_panel1, igbc);
        igbc.gridx = 1;
        igbc.insets = new Insets(10, 5, 10, 5);
        CustomComponents.CustomButton generateBtn = new CustomComponents.CustomButton("Generate Report", merriweather, new Color(30, 31, 34, 255),
                new Color(255, 255, 255), new Color(225, 108, 150), new Color(237, 136, 172),
                Main.transparent, 0, 16, Main.transparent, false, 5, false,
                null, 0, 0, 0);

        igbc.gridx = 0;
        igbc.gridy = 3;
        igbc.insets = new Insets(10, 10, 10, 10);
        JLabel lbl_po_status = new JLabel("Purchase Order Detail : ");
        lbl_po_status.setFont(merriweather.deriveFont(Font.BOLD, 16));
        lbl_po_status.setOpaque(false);
        lbl_po_status.setForeground(new Color(0, 0, 0, 252));
        inner.add(lbl_po_status, igbc);

        igbc.gridx = 1;
        igbc.gridy = 3;
        igbc.gridwidth = 3;
        igbc.weightx = 3;
        igbc.weighty = 3;
        igbc.insets = new Insets(5, 10, 5, 10);
        poDetail = new JTextArea(20, 80);
        poDetail.setLineWrap(true);
        poDetail.setWrapStyleWord(true);
        poDetail.setEditable(false);
        poDetail.setFont(merriweather.deriveFont(Font.PLAIN, 16));
        poDetail.setForeground(new Color(0, 0, 0, 252));
        poDetail.setBackground(Color.lightGray);
        JScrollPane poScrollPane = new JScrollPane(poDetail);
        poScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        poScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        poScrollPane.setPreferredSize(new Dimension(1000, 200));
        inner.add(poScrollPane, igbc);

        igbc.gridx = 0;
        igbc.gridy = 4;
        igbc.insets = new Insets(10, 10, 10, 10);
        JLabel lbl_Total_Status_Amount = new JLabel("Total Amount : ");
        lbl_Total_Status_Amount.setFont(merriweather.deriveFont(Font.BOLD, 16));
        lbl_Total_Status_Amount.setOpaque(false);
        lbl_Total_Status_Amount.setForeground(new Color(0, 0, 0, 252));
        inner.add(lbl_Total_Status_Amount, igbc);

        igbc.gridx = 1;
        igbc.gridy = 4;
        igbc.gridwidth = 1;
        igbc.weightx = 1;
        igbc.insets = new Insets(10, 5, 10, 5);
        totalPOAmtTxtField = new CustomComponents.EmptyTextField(19, "", new Color(0, 0, 0, 252));
        totalPOAmtTxtField.setFont(merriweather.deriveFont(Font.BOLD, 16));
        totalPOAmtTxtField.setOpaque(true);
        totalPOAmtTxtField.setBackground(Color.LIGHT_GRAY);
        totalPOAmtTxtField.setForeground(new Color(0, 0, 0, 252));
        inner.add(totalPOAmtTxtField, igbc);

        igbc.gridx = 0;
        igbc.gridy = 5;
        igbc.insets = new Insets(10, 10, 10, 10);
        JLabel lbl_Total_po_count = new JLabel("Number of Purchase Order :  ");
        lbl_Total_po_count.setFont(merriweather.deriveFont(Font.BOLD, 16));
        lbl_Total_po_count.setOpaque(false);
        lbl_Total_po_count.setForeground(new Color(0, 0, 0, 252));
        inner.add(lbl_Total_po_count, igbc);

        igbc.gridx = 1;
        igbc.gridy = 5;
        igbc.gridwidth = 1;
        igbc.weightx = 1;
        igbc.insets = new Insets(10, 5, 10, 5);
        totalPOCountTxtField = new CustomComponents.EmptyTextField(19, "", new Color(0, 0, 0, 252));
        totalPOCountTxtField.setFont(merriweather.deriveFont(Font.BOLD, 16));
        totalPOCountTxtField.setOpaque(true);
        totalPOCountTxtField.setBackground(Color.LIGHT_GRAY);
        totalPOCountTxtField.setForeground(new Color(0, 0, 0, 252));
        inner.add(totalPOCountTxtField, igbc);

        igbc.gridx = 0;
        igbc.gridy = 6;
        igbc.insets = new Insets(10, 10, 10, 10);
        JLabel lbl_sales_made = new JLabel("Sales Detail : ");
        lbl_sales_made.setFont(merriweather.deriveFont(Font.BOLD, 16));
        lbl_sales_made.setOpaque(false);
        lbl_sales_made.setForeground(new Color(0, 0, 0, 252));
        inner.add(lbl_sales_made, igbc);

        igbc.gridx = 1;
        igbc.gridy = 6;
        igbc.gridwidth = 3;
        igbc.weightx = 3;
        igbc.weighty = 3;
        igbc.insets = new Insets(10, 10, 10, 10);
        salesDetail = new JTextArea(20, 80);
        salesDetail.setLineWrap(true);
        salesDetail.setWrapStyleWord(true);
        salesDetail.setEditable(false);
        salesDetail.setFont(merriweather.deriveFont(Font.PLAIN, 16));
        salesDetail.setForeground(new Color(0, 0, 0, 252));
        salesDetail.setBackground(Color.lightGray);
        JScrollPane payScrollPane = new JScrollPane(salesDetail);
        payScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        payScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        payScrollPane.setPreferredSize(new Dimension(1000, 200));
        inner.add(payScrollPane, igbc);

        igbc.gridx = 0;
        igbc.gridy = 7;
        igbc.insets = new Insets(10, 10, 10, 10);
        JLabel lbl_Tota_sales_Amount = new JLabel("Total Sales Amount :  ");
        lbl_Tota_sales_Amount.setFont(merriweather.deriveFont(Font.BOLD, 16));
        lbl_Tota_sales_Amount.setOpaque(false);
        lbl_Tota_sales_Amount.setForeground(new Color(0, 0, 0, 252));
        inner.add(lbl_Tota_sales_Amount, igbc);

        igbc.gridx = 1;
        igbc.gridy = 7;
        igbc.gridwidth = 1;
        igbc.weightx = 1;
        igbc.insets = new Insets(10, 5, 10, 5);
        totalSalesAmtTxtField = new CustomComponents.EmptyTextField(19, "", new Color(0, 0, 0, 252));
        totalSalesAmtTxtField.setFont(merriweather.deriveFont(Font.BOLD, 16));
        totalSalesAmtTxtField.setOpaque(true);
        totalSalesAmtTxtField.setBackground(Color.LIGHT_GRAY);
        totalSalesAmtTxtField.setForeground(new Color(0, 0, 0, 252));
        inner.add(totalSalesAmtTxtField, igbc);

        igbc.gridx = 0;
        igbc.gridy = 8;
        igbc.insets = new Insets(10, 10, 10, 10);
        JLabel lbl_Tota_sales_Count = new JLabel("Number of Sales :  ");
        lbl_Tota_sales_Count.setFont(merriweather.deriveFont(Font.BOLD, 16));
        lbl_Tota_sales_Count.setOpaque(false);
        lbl_Tota_sales_Count.setForeground(new Color(0, 0, 0, 252));
        inner.add(lbl_Tota_sales_Count, igbc);

        igbc.gridx = 1;
        igbc.gridy = 8;
        igbc.insets = new Insets(10, 5, 10, 5);
        totalSalesCountTxtField = new CustomComponents.EmptyTextField(19, "", new Color(0, 0, 0, 252));
        totalSalesCountTxtField.setFont(merriweather.deriveFont(Font.BOLD, 16));
        totalSalesCountTxtField.setOpaque(true);
        totalSalesCountTxtField.setBackground(Color.LIGHT_GRAY);
        totalSalesCountTxtField.setForeground(new Color(0, 0, 0, 252));
        inner.add(totalSalesCountTxtField, igbc);

        generateBtn.addActionListener(_ ->
        {
            LocalDate startDate, endDate;
            String selectedStatus = Objects.requireNonNull(statusCombo.getSelectedItem()).toString();
            double totalStatusPoAmt = 0, totalSale = 0;
            if (isInvalidDate(startDateTxtField.getText().trim()) || isInvalidDate(endDateTxtField.getText().trim())) {
                CustomComponents.CustomOptionPane.showErrorDialog(
                        parent,
                        "Please enter a valid Date Format! (yyyy-MM-dd)",
                        "Error",
                        new Color(209, 88, 128),
                        new Color(255, 255, 255),
                        new Color(237, 136, 172),
                        new Color(255, 255, 255)
                );
                startDateTxtField.setText("");
                endDateTxtField.setText("");
                return;
            } else {
                startDate = LocalDate.parse(startDateTxtField.getText().trim());
                endDate = LocalDate.parse(endDateTxtField.getText().trim());
            }
            List<PurchaseOrder> po_list = new PurchaseOrder().ListAll();
            po_list.removeIf(po -> !Objects.equals(po.getStatus(), selectedStatus) ||
                    !po.getOrderDate().isAfter(startDate) ||
                    !po.getOrderDate().isBefore(endDate));
            
            StringBuilder poDetailsBuilder = new StringBuilder();
            int count = 1;
            for (PurchaseOrder po : po_list) {
                totalStatusPoAmt += po.getTotalAmt();
                poDetailsBuilder.append(count).append(".)    Purchase Order ID: ").append(po.getPurchaseOrderID()).append("\n");
                poDetailsBuilder.append("Purchase Manager Responsible: ").append(new User().GetObjectByID(po.getPurchaseMgrID()).getFullName()).append("\n");
                poDetailsBuilder.append("Item Name: ").append(new Item().GetObjectByID(po.getItemID()).getItemName()).append("\n");
                poDetailsBuilder.append("Supplier Name: ").append(new Supplier().GetObjectByID(po.getSupplierID()).getSupplierName()).append("\n");
                poDetailsBuilder.append("Purchase Quantity: ").append(po.getPurchaseQuantity()).append("\n");
                poDetailsBuilder.append("Total Amt: ").append(po.getTotalAmt()).append("\n");
                poDetailsBuilder.append("Order Date: ").append(po.getOrderDate().format(PurchaseOrder.df)).append("\n\n");
                count += 1;
            }

            StringBuilder salesDetailsBuilder = new StringBuilder();
            List<Sales> sls_list = new Sales().ListAll();
            sls_list.removeIf(sls -> !sls.getSalesDate().isAfter(startDate) ||
                    !sls.getSalesDate().isBefore(endDate));
            List<Item_Sales> it_sls = new ArrayList<>();
            count = 1;
            for (Sales sls : sls_list) {
                it_sls.addAll(new Item_Sales().ListAllWithType(sls.getSalesID()));
                salesDetailsBuilder.append(count).append(".)    Sales ID: ").append(sls.getSalesID()).append("\n");
                salesDetailsBuilder.append("Sales Manager Responsible: ").append(new User().GetObjectByID(sls.getSalesMgrID()).getFullName()).append("\n");
                StringBuilder items = new StringBuilder();
                List<Item_Sales> soft_list = new Item_Sales().ListAllWithType(sls.getSalesID());
                int n = 1;
                double p = 0;
                for (Item_Sales s : soft_list) {
                    items.append(new Item().GetObjectByID(s.getItemID()).getItemName());
                    p += s.getAmount();
                    n += 1;
                    if (n <= soft_list.size()) { items.append(", "); }
                }
                salesDetailsBuilder.append("Items Sold: ").append(items).append("\n");
                salesDetailsBuilder.append("Total Amount: ").append(p).append("\n");
                salesDetailsBuilder.append("Sales Date: ").append(sls.getSalesDate().format(Sales.df)).append("\n\n");
            }
            for (Item_Sales i : it_sls) { totalSale += i.getAmount(); }

            totalPOAmtTxtField.setText(String.format("%.2f", totalStatusPoAmt));
            totalSalesAmtTxtField.setText(String.format("%.2f", totalSale));
            poDetail.setText(poDetailsBuilder.toString());
            salesDetail.setText(salesDetailsBuilder.toString());
            totalPOCountTxtField.setText(Integer.toString(po_list.size()));
            totalSalesCountTxtField.setText(Integer.toString(sls_list.size()));
        });
        button_panel1.add(generateBtn, igbc);
        inner.add(button_panel1);

    }
    public static boolean isInvalidDate(String inputDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try {
            sdf.parse(inputDate);
            return false;
        } catch (ParseException e) {
            return true;
        }
    }
}
