package Classes;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Item_Supplier implements SystemEntities<Item_Supplier> {
    private String ItemID, SupplierID;

    public Item_Supplier() {

    }

    public Item_Supplier(String ItemID, String SupplierID) {
        this.ItemID = ItemID;
        this.SupplierID = SupplierID;
    }

    public String getItemID() {
        return ItemID;
    }

    public void setItemID(String itemID) {
        ItemID = itemID;
    }

    public String getSupplierID() {
        return SupplierID;
    }

    public void setSupplierID(String supplierID) {
        SupplierID = supplierID;
    }

    @Override
    public List<Item_Supplier> ListAll() {
        List<Item_Supplier> allItemSupplier = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(item_supplier_file))) {
            String ItemID = "", SupplierID = "";

            String line;
            int counter = 1;
            while ((line = reader.readLine()) != null) {
                switch (counter) {
                    case 1:
                        ItemID = line.substring(16);
                        break;
                    case 2:
                        SupplierID = line.substring(16);
                        break;
                    default:
                        counter = 0;
                        allItemSupplier.add(new Item_Supplier(ItemID, SupplierID));
                        break;
                }
                counter += 1;
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        return allItemSupplier;
    }

    // Item Supplier treat type as Item ID
    @Override
    public List<Item_Supplier> ListAllWithFilter(String filter) {
        List<Item_Supplier> allItemSupplier = ListAll();
        List<Item_Supplier> filter_List = new ArrayList<>();
        for (Item_Supplier itemSupplier: allItemSupplier) {
            if (Objects.equals(itemSupplier.ItemID, filter)) {
                filter_List.add(itemSupplier);
            }
        }
        return filter_List;
    }

    // Item Supplier treat type as Supplier ID
    @Override
    public List<Item_Supplier> ListAllWithType(String type) {
        List<Item_Supplier> allItemSupplier = ListAll();
        List<Item_Supplier> filter_List = new ArrayList<>();
        for (Item_Supplier itemSupplier: allItemSupplier) {
            if (Objects.equals(itemSupplier.SupplierID, type)) {
                filter_List.add(itemSupplier);
            }
        }
        return filter_List;
    }

    public Item_Supplier GetWithBoth(String ItemID, String SupplierID) {
        List<Item_Supplier> byItem = ListAllWithFilter(ItemID);
        List<Item_Supplier> bySupplier = ListAllWithType(SupplierID);
        List<Item_Supplier> common = new ArrayList<>(byItem);
        common.retainAll(bySupplier);
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
    public Item_Supplier GetObjectByID(String id) {
        return null;
    }

    @Override
    public List<Item_Supplier> GetObjectsByIDS(List<String> ids) {
        return null;
    }

    @Override
    public void AddNew(Item_Supplier object) {
        try (FileWriter writer = new FileWriter(item_supplier_file, true)) {
            writer.write("ItemID:         " + object.ItemID + "\n");
            writer.write("SupplierID:     " + object.SupplierID + "\n");
            writer.write("~~~~~\n");
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    @Override
    public void Overwrite(List<Item_Supplier> objects) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(item_supplier_file))) {
            for (Item_Supplier itemSupplier : objects) {
                writer.write("ItemID:         " + itemSupplier.ItemID + "\n");
                writer.write("SupplierID:     " + itemSupplier.SupplierID + "\n");
                writer.write("~~~~~\n");
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    @Override
    public void Modify(String id, Item_Supplier object) {

    }

    public void ModifyByBoth(String itemID, String supplierID, Item_Supplier object) {
        List<Item_Supplier> allItemSupplier = ListAll();
        for (Item_Supplier itemSupplier : allItemSupplier) {
            if (Objects.equals(itemSupplier.ItemID, itemID) && Objects.equals(itemSupplier.SupplierID, supplierID)) {
                itemSupplier.ItemID = object.ItemID;
                itemSupplier.SupplierID = object.SupplierID;
            }
        }
        Overwrite(allItemSupplier);
    }

    @Override
    public void Remove(Item_Supplier object) {
        List<Item_Supplier> allItemSupplier = ListAll();
        allItemSupplier.removeIf(itemSales -> Objects.equals(itemSales.ItemID, object.ItemID) &&
                Objects.equals(itemSales.SupplierID, object.SupplierID));
        Overwrite(allItemSupplier);
    }

    @Override
    public String ValidityChecker(Item_Supplier object) {
        String indicator = "";
        if (new Item().GetObjectByID(object.ItemID) != null) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        if (new Supplier().GetObjectByID(object.SupplierID) != null) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        return indicator;
    }

    @Override
    public String ValidityCheckerWithHistory(Item_Supplier object, Item_Supplier history) {
        return "";
    }
}
