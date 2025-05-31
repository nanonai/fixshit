package Classes;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PurchaseOrder implements SystemEntities<PurchaseOrder> {
    private String PurchaseOrderID, ItemID, SupplierID, PurchaseMgrID, Status;
    private int PurchaseQuantity;
    private double TotalAmt;
    private LocalDate OrderDate;

    public PurchaseOrder() {

    }

    public PurchaseOrder(String PurchaseOrderID, String ItemID, String SupplierID, int PurchaseQuantity,
                         double TotalAmt, LocalDate OrderDate, String PurchaseMgrID, String Status){
        this.PurchaseOrderID = PurchaseOrderID;
        this.ItemID =   ItemID;
        this.SupplierID = SupplierID;
        this.PurchaseQuantity = PurchaseQuantity;
        this.TotalAmt = TotalAmt;
        this.OrderDate = OrderDate;
        this.PurchaseMgrID = PurchaseMgrID;
        this.Status = Status;
    }

    public String getPurchaseOrderID() {
        return PurchaseOrderID;
    }

    public void setPurchaseOrderID(String purchaseOrderID) {
        PurchaseOrderID = purchaseOrderID;
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

    public int getPurchaseQuantity() {
        return PurchaseQuantity;
    }

    public void setPurchaseQuantity(int purchaseQuantity) {
        PurchaseQuantity = purchaseQuantity;
    }

    public double getTotalAmt() {
        return TotalAmt;
    }

    public void setTotalAmt(double totalAmt) {
        TotalAmt = totalAmt;
    }

    public LocalDate getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        OrderDate = orderDate;
    }

    public String getPurchaseMgrID() {
        return PurchaseMgrID;
    }

    public void setPurchaseMgrID(String purchaseMgrID) {
        PurchaseMgrID = purchaseMgrID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    @Override
    public List<PurchaseOrder> ListAll() {
        List<PurchaseOrder> allPO = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(po_file))) {
            String PurchaseOrderID = "", ItemID= "", SupplierID = "",PurchaseMgrID = " ", Status = "";
            int PurchaseQuantity = 0;
            double TotalAmt = 0.0;
            LocalDate OrderDate = null;
            int counter = 1;
            String line;
            while ((line = reader.readLine()) != null) {
                switch (counter) {
                    case 1:
                        PurchaseOrderID = line.substring(21);
                        break;
                    case 2:
                        ItemID = line.substring(21);
                        break;
                    case 3:
                        SupplierID = line.substring(21);
                        break;
                    case 4:
                        PurchaseQuantity = Integer.parseInt(line.substring(21));
                        break;
                    case 5:
                        TotalAmt = Double.parseDouble(line.substring(21));
                        break;
                    case 6:
                        OrderDate = LocalDate.parse(line.substring(21), df);
                        break;
                    case 7:
                        PurchaseMgrID = line.substring(21);
                        break;
                    case 8:
                        Status = line.substring(21);
                        break;
                    default:
                        counter = 0;
                        allPO.add(new PurchaseOrder(PurchaseOrderID,ItemID,SupplierID,
                                PurchaseQuantity,TotalAmt,OrderDate,PurchaseMgrID,Status));
                        break;
                }
                counter += 1;
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        return allPO;
    }

    @Override
    public List<PurchaseOrder> ListAllWithFilter(String filter) {
        List<PurchaseOrder> allPO = ListAll();
        List<PurchaseOrder> filter_list = new ArrayList<>();
        String temp = filter.toLowerCase().replace(" ", "");
        if (filter.isEmpty()) {
            return allPO;
        }
        for (PurchaseOrder po: allPO) {
            if (po.PurchaseOrderID.toLowerCase().contains(temp) ||
                    po.ItemID.toLowerCase().contains(temp) ||
                    po.SupplierID.toLowerCase().contains(temp) ||
                    Integer.toString(po.PurchaseQuantity).contains(temp) ||
                    Double.toString(po.TotalAmt).contains(temp) ||
                    po.OrderDate.toString().contains(temp) ||
                    po.PurchaseMgrID.toLowerCase().contains(temp) ||
                    po.Status.toLowerCase().contains(temp)) {
                filter_list.add(po);
            }
        }
        return filter_list;
    }

    @Override
    public List<PurchaseOrder> ListAllWithType(String type) {
        return null;
    }

    @Override
    public String IdMaker() {
        List<PurchaseOrder> allPO = ListAll();
        boolean repeated = false;
        boolean success = false;
        String newId = "";
        while (!success) {
            long newNum = (long) (Math.random() * 1E4);
            StringBuilder number = new StringBuilder(String.valueOf(newNum));
            while (number.length() < 4) {
                number.insert(0, "0");
            }
            newId = String.format("%s%s", "PO", number);
            for (PurchaseOrder po : allPO) {
                if (Objects.equals(po.PurchaseOrderID, newId)) {
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
    public PurchaseOrder GetObjectByID(String id) {
        List<PurchaseOrder> allPO = ListAll();
        for (PurchaseOrder po : allPO){
            if (Objects.equals(po.SupplierID, id)){
                return po;
            }
        }
        return null;
    }

    @Override
    public List<PurchaseOrder> GetObjectsByIDS(List<String> ids) {
        List<PurchaseOrder> pos = new ArrayList<>();
        for (String id: ids) {
            pos.add(GetObjectByID(id));
        }
        return pos;
    }

    @Override
    public void AddNew(PurchaseOrder object) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(po_file, true))) {
            writer.write("PurchaseOrderID:     " + object.PurchaseOrderID + "\n");
            writer.write("ItemID:              " + object.ItemID + "\n");
            writer.write("SupplierID:          " + object.SupplierID + "\n");
            writer.write("PurchaseQuantity:    " + object.PurchaseQuantity + "\n");
            writer.write("TotalAmt:            " + object.TotalAmt + "\n");
            writer.write("OrderDate:           " + object.OrderDate.format(df) + "\n");
            writer.write("PurchaseMgrID:       " + object.PurchaseMgrID + "\n");
            writer.write("Status:              " + object.Status + "\n");
            writer.write("~~~~~\n");
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    @Override
    public void Overwrite(List<PurchaseOrder> objects) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(po_file))) {
            for (PurchaseOrder po : objects) {
                writer.write("PurchaseOrderID:     " + po.PurchaseOrderID + "\n");
                writer.write("ItemID:              " + po.ItemID + "\n");
                writer.write("SupplierID:          " + po.SupplierID + "\n");
                writer.write("PurchaseQuantity:    " + po.PurchaseQuantity + "\n");
                writer.write("TotalAmt:            " + po.TotalAmt + "\n");
                writer.write("OrderDate:           " + po.OrderDate.format(df) + "\n");
                writer.write("PurchaseMgrID:       " + po.PurchaseMgrID + "\n");
                writer.write("Status:              " + po.Status + "\n");
                writer.write("~~~~~\n");
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    @Override
    public void Modify(String id, PurchaseOrder object) {
        List<PurchaseOrder> allPO = ListAll();
        for (PurchaseOrder po : allPO) {
            if (Objects.equals(po.PurchaseOrderID, id)) {
                po.PurchaseOrderID = object.PurchaseOrderID;
                po.ItemID = object.ItemID;
                po.SupplierID = object.SupplierID;
                po.PurchaseQuantity = object.PurchaseQuantity;
                po.TotalAmt = object.TotalAmt;
                po.OrderDate = object.OrderDate;
                po.PurchaseMgrID = object.PurchaseMgrID;
                po.Status = object.Status;
            }
        }
        Overwrite(allPO);
    }

    @Override
    public void Remove(PurchaseOrder object) {
        List<PurchaseOrder> allPO = ListAll();
        allPO.removeIf(po -> Objects.equals(po.PurchaseOrderID, object.PurchaseOrderID));
        Overwrite(allPO);
    }

    @Override
    public String ValidityChecker(PurchaseOrder object) {
        return "";
    }

    @Override
    public String ValidityCheckerWithHistory(PurchaseOrder object, PurchaseOrder history) {
        return "";
    }
}