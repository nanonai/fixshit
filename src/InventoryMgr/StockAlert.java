package InventoryMgr;

import Classes.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class StockAlert {
    public static void Popup(JFrame parent){
        List<Item> lowStock = Checker();
        if (!lowStock.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append("<html><b>Items low on stock:</b><br>");
            for (Item item : lowStock) {
                sb.append("ID: ").append(item.getItemID())
                        .append(" - Name: ").append(item.getItemName()).append("<br>");
            }
            sb.append("</html>");
            CustomComponents.CustomOptionPane.showErrorDialog(parent, sb.toString(), "Low stock alert",
                    new Color(209, 88, 128),
                    new Color(255, 255, 255),
                    new Color(237, 136, 172),
                    new Color(255, 255, 255));
        }
    }

    public static List<Item> Checker() {
        List<Item> allItems = new Item().ListAll();
        List<Item> lowStock = new ArrayList<>();
        for (Item item : allItems) {
            if (item.getStockCount() <= item.getThreshold()) {
                lowStock.add(item);
            }
        }
        return lowStock;
    }
}
