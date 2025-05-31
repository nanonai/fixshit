package Classes;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Item_Sales implements SystemEntities<Item_Sales> {
    private String ItemID, SalesID;
    private int Quantity;
    private double Amount;

    public Item_Sales() {

    }

    public Item_Sales(String ItemID, String SalesID, int Quantity, double Amount) {
        this.ItemID = ItemID;
        this.SalesID = SalesID;
        this.Quantity = Quantity;
        this.Amount = Amount;
    }

    public String getItemID() {
        return ItemID;
    }

    public void setItemID(String itemID) {
        ItemID = itemID;
    }

    public String getSalesID() {
        return SalesID;
    }

    public void setSalesID(String salesID) {
        SalesID = salesID;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }

    @Override
    public List<Item_Sales> ListAll() {
        List<Item_Sales> allItemSales = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(item_sales_file))) {
            String ItemID = "", SalesID = "";
            int Quantity = 0;
            double Amount = 0;

            String line;
            int counter = 1;
            while ((line = reader.readLine()) != null) {
                switch (counter) {
                    case 1:
                        ItemID = line.substring(16);
                        break;
                    case 2:
                        SalesID = line.substring(16);
                        break;
                    case 3:
                        Quantity = Integer.parseInt(line.substring(16));
                        break;
                    case 4:
                        Amount = Double.parseDouble(line.substring(16));
                        break;
                    default:
                        counter = 0;
                        allItemSales.add(new Item_Sales(ItemID, SalesID, Quantity, Amount));
                        break;
                }
                counter += 1;
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        return allItemSales;
    }

    // Item Sales treat filter as Item ID
    @Override
    public List<Item_Sales> ListAllWithFilter(String filter) {
        List<Item_Sales> allItemSales = ListAll();
        List<Item_Sales> filter_List = new ArrayList<>();
        for (Item_Sales itemSales: allItemSales) {
            if (Objects.equals(itemSales.ItemID, filter)) {
                filter_List.add(itemSales);
            }
        }
        return filter_List;
    }

    // Item Sales treat type as Supplier ID
    @Override
    public List<Item_Sales> ListAllWithType(String type) {
        List<Item_Sales> allItemSales = ListAll();
        List<Item_Sales> filter_List = new ArrayList<>();
        for (Item_Sales itemSales: allItemSales) {
            if (Objects.equals(itemSales.SalesID, type)) {
                filter_List.add(itemSales);
            }
        }
        return filter_List;
    }

    public Item_Sales GetWithBoth(String ItemID, String SalesID) {
        List<Item_Sales> byItem = ListAllWithFilter(ItemID);
        List<Item_Sales> bySales = ListAllWithType(SalesID);
        List<Item_Sales> common = new ArrayList<>(byItem);
        common.retainAll(bySales);
        return common.getFirst();
    }

    @Override
    public String IdMaker() {
        return "";
    }

    @Override
    public String IdMakerWithType(String type) {
        return "";
    }

    @Override
    public Item_Sales GetObjectByID(String id) {
        return null;
    }

    @Override
    public List<Item_Sales> GetObjectsByIDS(List<String> ids) {
        return null;
    }

    @Override
    public void AddNew(Item_Sales object) {
        try (FileWriter writer = new FileWriter(item_sales_file, true)) {
            writer.write("ItemID:         " + object.ItemID + "\n");
            writer.write("SalesID:        " + object.SalesID + "\n");
            writer.write("Quantity:       " + object.Quantity + "\n");
            writer.write("Amount:         " + object.Amount + "\n");
            writer.write("~~~~~\n");
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    @Override
    public void Overwrite(List<Item_Sales> objects) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(item_sales_file))) {
            for (Item_Sales itemSales : objects) {
                writer.write("ItemID:         " + itemSales.ItemID + "\n");
                writer.write("SalesID:        " + itemSales.SalesID + "\n");
                writer.write("Quantity:       " + itemSales.Quantity + "\n");
                writer.write("Amount:         " + itemSales.Amount + "\n");
                writer.write("~~~~~\n");
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    @Override
    public void Modify(String id, Item_Sales object) {

    }

    public void ModifyByBoth(String itemID, String salesID, Item_Sales object) {
        List<Item_Sales> allItemSales = ListAll();
        for (Item_Sales itemSales : allItemSales) {
            if (Objects.equals(itemSales.ItemID, itemID) && Objects.equals(itemSales.SalesID, salesID)) {
                itemSales.ItemID = object.ItemID;
                itemSales.SalesID = object.SalesID;
                itemSales.Quantity = object.Quantity;
                itemSales.Amount = object.Amount;
            }
        }
        Overwrite(allItemSales);
    }

    @Override
    public void Remove(Item_Sales object) {
        List<Item_Sales> allItemSales = ListAll();
        allItemSales.removeIf(itemSales -> Objects.equals(itemSales.ItemID, object.ItemID) &&
                Objects.equals(itemSales.SalesID, object.SalesID));
        Overwrite(allItemSales);
    }

    @Override
    public String ValidityChecker(Item_Sales object) {
        String indicator = "";
        if (new Item().GetObjectByID(object.ItemID) != null) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        if (new Sales().GetObjectByID(object.SalesID) != null) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        if (object.Quantity > 0) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        return indicator;
    }

    @Override
    public String ValidityCheckerWithHistory(Item_Sales object, Item_Sales history) {
        return "";
    }
}
