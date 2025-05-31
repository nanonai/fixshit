package Classes;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Item implements SystemEntities<Item> {
    private String ItemID, ItemName, Category;
    private double UnitPrice, UnitCost;
    private int StockCount, Threshold;
    private LocalDate LastUpdate;

    public Item() {

    }

    public Item(String ItemID, String ItemName, double UnitPrice, double UnitCost,
                int StockCount, int Threshold, String Category, LocalDate LastUpdate) {
        this.ItemID = ItemID;
        this.ItemName = ItemName;
        this.UnitPrice = UnitPrice;
        this.UnitCost = UnitCost;
        this.StockCount = StockCount;
        this.Threshold = Threshold;
        this.Category = Category;
        this.LastUpdate = LastUpdate;
    }

    public String getItemID() {
        return ItemID;
    }

    public void setItemID(String itemID) {
        ItemID = itemID;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public double getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        UnitPrice = unitPrice;
    }

    public double getUnitCost() {
        return UnitCost;
    }

    public void setUnitCost(double unitCost) {
        UnitCost = unitCost;
    }

    public int getStockCount() {
        return StockCount;
    }

    public void setStockCount(int stockCount) {
        StockCount = stockCount;
    }

    public int getThreshold() {
        return Threshold;
    }

    public void setThreshold(int threshold) {
        Threshold = threshold;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public LocalDate getLastUpdate() {
        return LastUpdate;
    }

    public void setLastUpdate(LocalDate lastUpdate) {
        LastUpdate = lastUpdate;
    }

    @Override
    public List<Item> ListAll() {
        List<Item> allItem = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(item_file))) {
            String ItemID = "", ItemName = "", Category = "";
            double UnitPrice = 0, UnitCost = 0;
            int StockCount = 0, Threshold = 0;
            LocalDate LastUpdate = null;

            String line;
            int counter = 1;
            while ((line = reader.readLine()) != null) {
                switch (counter) {
                    case 1:
                        ItemID = line.substring(16);
                        break;
                    case 2:
                        ItemName = line.substring(16);
                        break;
                    case 3:
                        UnitPrice = Double.parseDouble(line.substring(16));
                        break;
                    case 4:
                        UnitCost = Double.parseDouble(line.substring(16));
                        break;
                    case 5:
                        StockCount = Integer.parseInt(line.substring(16));
                        break;
                    case 6:
                        Threshold = Integer.parseInt(line.substring(16));
                        break;
                    case 7:
                        Category = line.substring(16);
                        break;
                    case 8:
                        LastUpdate = LocalDate.parse(line.substring(16), df);
                        break;
                    default:
                        counter = 0;
                        allItem.add(new Item(ItemID, ItemName, UnitPrice, UnitCost,
                                StockCount, Threshold, Category, LastUpdate));
                        break;
                }
                counter += 1;
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        return allItem;
    }

    @Override
    public List<Item> ListAllWithFilter(String filter) {
        List<Item> item_list = ListAll();
        List<Item> filter_list = new ArrayList<>();
        String temp = filter.toLowerCase().replace(" ", "");
        if (filter.isEmpty()) {
            return item_list;
        }
        for (Item item: item_list) {
            if ((item.ItemID.toLowerCase().contains(temp) ||
                    item.ItemName.toLowerCase().replace(" ", "").contains(temp) ||
                    Double.toString(item.UnitPrice).contains(temp) ||
                    Double.toString(item.UnitCost).contains(temp) ||
                    Integer.toString(item.StockCount).contains(temp) ||
                    Integer.toString(item.Threshold).contains(temp) ||
                    item.Category.toLowerCase().replace(" ", "").contains(temp) ||
                    item.LastUpdate.toString().contains(temp))) {
                filter_list.add(item);
            }
        }
        return filter_list;
    }

    @Override
    public List<Item> ListAllWithType(String type) {
        return null;
    }

    @Override
    public String IdMaker() {
        List<Item> allItem = ListAll();
        boolean repeated = false;
        boolean success = false;
        String newId = "";
        while (!success) {
            long newNum = (long) (Math.random() * 1E4);
            StringBuilder number = new StringBuilder(String.valueOf(newNum));
            while (number.length() < 4) {
                number.insert(0, "0");
            }
            newId = String.format("%s%s", "I", number);
            for (Item item : allItem) {
                if (Objects.equals(item.ItemID, newId)) {
                    repeated = true;
                    break;
                }
            }
            success = !repeated;
            repeated = false;
        }
        return newId;
    }

    @Override
    public String IdMakerWithType(String type) {
        return "";
    }

    @Override
    public Item GetObjectByID(String id) {
        List<Item> allItem = ListAll();
        for (Item item : allItem) {
            if (Objects.equals(item.ItemID, id)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public List<Item> GetObjectsByIDS(List<String> ids) {
        List<Item> items = new ArrayList<>();
        for (String id: ids) {
            items.add(GetObjectByID(id));
        }
        return items;
    }

    public Item GetObjectByName(String name) {
        List<Item> allItem = ListAll();
        for (Item item : allItem) {
            if (Objects.equals(item.ItemName, name)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public void AddNew(Item object) {
        try (FileWriter writer = new FileWriter(item_file, true)) {
            writer.write("ItemID:         " + object.ItemID + "\n");
            writer.write("ItemName:       " + object.ItemName + "\n");
            writer.write("UnitPrice:      " + object.UnitPrice + "\n");
            writer.write("UnitCost:       " + object.UnitCost + "\n");
            writer.write("StockCount:     " + object.StockCount + "\n");
            writer.write("Threshold:      " + object.Threshold + "\n");
            writer.write("Category:       " + object.Category + "\n");
            writer.write("LastUpdate:     " + object.LastUpdate.format(df) + "\n");
            writer.write("~~~~~\n");
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    @Override
    public void Overwrite(List<Item> objects) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(item_file))) {
            for (Item item : objects) {
                writer.write("ItemID:         " + item.ItemID + "\n");
                writer.write("ItemName:       " + item.ItemName + "\n");
                writer.write("UnitPrice:      " + item.UnitPrice + "\n");
                writer.write("UnitCost:       " + item.UnitCost + "\n");
                writer.write("StockCount:     " + item.StockCount + "\n");
                writer.write("Threshold:      " + item.Threshold + "\n");
                writer.write("Category:       " + item.Category + "\n");
                writer.write("LastUpdate:     " + item.LastUpdate.format(df) + "\n");
                writer.write("~~~~~\n");
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    @Override
    public void Modify(String id, Item object) {
        List<Item> allItem = ListAll();
        for (Item item : allItem) {
            if (Objects.equals(item.ItemID, id)) {
                item.ItemID = object.ItemID;
                item.ItemName = object.ItemName;
                item.UnitPrice = object.UnitPrice;
                item.UnitCost = object.UnitCost;
                item.StockCount = object.StockCount;
                item.Threshold = object.Threshold;
                item.Category = object.Category;
                item.LastUpdate = object.LastUpdate;
            }
        }
        Overwrite(allItem);
    }

    @Override
    public void Remove(Item object) {
        List<Item> allItem = ListAll();
        allItem.removeIf(item -> Objects.equals(item.ItemID, object.ItemID));
        Overwrite(allItem);
    }

    public Boolean NameChecker(String name) {
        List<Item> allItem = ListAll();
        boolean repeated = false;
        for (Item item : allItem) {
            if (Objects.equals(item.ItemName, name)) {
                repeated = true;
                break;
            }
        }
        return !repeated;
    }

    @Override
    public String ValidityChecker(Item object) {
        String indicator = "";
        if (object.ItemName.length() >= 8 && object.ItemName.length() <= 48) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        if (NameChecker(object.ItemName)) {
            indicator += "O";
        } else {
            indicator += "X";
        }
        if (object.UnitPrice > 0) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        if (object.UnitCost > 0) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        if (object.UnitCost < object.UnitPrice) {
            indicator += "O";
        } else {
            indicator += "X";
        }
        if (object.StockCount >= 0) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        if (object.Threshold >= 0) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        if (object.Category.length() >= 8 && object.Category.length() <= 48) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        return indicator;
    }

    @Override
    public String ValidityCheckerWithHistory(Item object, Item history) {
        String indicator = "";
        if (object.ItemName.length() >= 8 && object.ItemName.length() <= 48) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        if (NameChecker(object.ItemName)) {
            indicator += "O";
        } else {
            if (object.ItemName.equals(history.ItemName)) {
                indicator += "O";
            } else {
                indicator += "X";
            }
        }
        if (object.UnitPrice > 0) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        if (object.UnitCost > 0) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        if (object.StockCount >= 0) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        if (object.Threshold >= 0) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        if (object.Category.length() >= 8 && object.Category.length() <= 48) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        return indicator;
    }
}