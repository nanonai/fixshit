package Classes;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PurchaseRequisition implements SystemEntities<PurchaseRequisition> {
    private String PurchaseReqID, ItemID, SupplierID, SalesMgrID;
    private int Quantity, Status;
    private LocalDate ReqDate;

    public PurchaseRequisition() {

    }

    public PurchaseRequisition(String PurchaseReqID, String ItemID, String SupplierID, int Quantity,
                               LocalDate ReqDate, String SalesMgrID, int Status){
        this.PurchaseReqID = PurchaseReqID;
        this.ItemID = ItemID;
        this.SupplierID = SupplierID;
        this.Quantity = Quantity;
        this.ReqDate = ReqDate;
        this.SalesMgrID = SalesMgrID;
        this.Status = Status;
    }

    public String getPurchaseReqID() {
        return PurchaseReqID;
    }

    public void setPurchaseReqID(String purchaseReqID) {
        PurchaseReqID = purchaseReqID;
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

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public LocalDate getReqDate() {
        return ReqDate;
    }

    public void setReqDate(LocalDate reqDate) {
        ReqDate = reqDate;
    }

    public String getSalesMgrID() {
        return SalesMgrID;
    }

    public void setSalesMgrID(String salesMgrID) {
        SalesMgrID = salesMgrID;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    @Override
    public List<PurchaseRequisition> ListAll() {
        List<PurchaseRequisition> allPR = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(pr_file))) {
            String PurchaseReqID = "", ItemID = "", SupplierID = "",SalesMgrID = "";
            int Quantity = 0, Status = 0;
            LocalDate ReqDate = null;
            int counter = 1;
            String line;

            while ((line = reader.readLine()) != null) {
                switch (counter) {
                    case 1:
                        PurchaseReqID = line.substring(21);
                        break;
                    case 2:
                        ItemID = line.substring(21);
                        break;
                    case 3:
                        SupplierID = line.substring(21);
                        break;
                    case 4:
                        Quantity = Integer.parseInt(line.substring(21));
                        break;
                    case 5:
                        ReqDate = LocalDate.parse(line.substring(21), df);
                        break;
                    case 6:
                        SalesMgrID = line.substring(21);
                        break;
                    case 7:
                        Status = Integer.parseInt(line.substring(21));
                        break;
                    default:
                        counter = 0;
                        allPR.add(new PurchaseRequisition(
                                PurchaseReqID, ItemID, SupplierID, Quantity, ReqDate, SalesMgrID, Status
                        ));
                }
                counter += 1;
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        return allPR;
    }

    @Override
    public List<PurchaseRequisition> ListAllWithFilter(String filter) {
        List<PurchaseRequisition> allPR = ListAll();
        List<PurchaseRequisition> filter_list = new ArrayList<>();
        String temp = filter.toLowerCase().replace(" ", "");
        if (filter.isEmpty()) {
            return allPR;
        }
        for (PurchaseRequisition pr: allPR) {
            String stat = (pr.Status == 0) ? (pr.ReqDate.isBefore(LocalDate.now())) ? "Overdue" : "Pending" : "Processed";
            if ((pr.PurchaseReqID.toLowerCase().contains(temp) ||
                    pr.ItemID.toLowerCase().contains(temp) ||
                    pr.SupplierID.toLowerCase().contains(temp) ||
                    Integer.toString(pr.Quantity).contains(temp) ||
                    pr.ReqDate.toString().contains(temp) ||
                    pr.SalesMgrID.toLowerCase().contains(temp) ||
                    stat.toLowerCase().contains(temp))) {
                filter_list.add(pr);
            }
        }
        return filter_list;
    }

    @Override
    public List<PurchaseRequisition> ListAllWithType(String type) {
        return null;
    }

    @Override
    public String IdMaker() {
        List<PurchaseRequisition> allPR = ListAll();
        boolean repeated = false;
        boolean success = false;
        String newId = "";
        while (!success) {
            long newNum = (long) (Math.random() * 1E4);
            StringBuilder number = new StringBuilder(String.valueOf(newNum));
            while (number.length() < 4) {
                number.insert(0, "0");
            }
            newId = String.format("%s%s", "PR", number);
            for (PurchaseRequisition pr : allPR) {
                if (Objects.equals(pr.PurchaseReqID, newId)) {
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
    public PurchaseRequisition GetObjectByID(String id) {
        List<PurchaseRequisition> allPR = ListAll();
        for (PurchaseRequisition pr: allPR) {
            if (Objects.equals(pr.PurchaseReqID, id)) {
                return pr;
            }
        }
        return null;
    }

    @Override
    public List<PurchaseRequisition> GetObjectsByIDS(List<String> ids) {
        List<PurchaseRequisition> prs = new ArrayList<>();
        for (String id: ids) {
            prs.add(GetObjectByID(id));
        }
        return prs;
    }

    @Override
    public void AddNew(PurchaseRequisition object) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pr_file, true))) {
            writer.write("PurchaseReqID :      " + object.PurchaseReqID + "\n");
            writer.write("ItemID:              " + object.ItemID + "\n");
            writer.write("SupplierID:          " + object.SupplierID + "\n");
            writer.write("Quantity:            " + object.Quantity + "\n");
            writer.write("ReqDate:             " + object.ReqDate.format(df) + "\n");
            writer.write("SalesMgrID:          " + object.SalesMgrID + "\n");
            writer.write("Status:              " + object.Status + "\n");
            writer.write("~~~~~\n");
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    @Override
    public void Overwrite(List<PurchaseRequisition> objects) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pr_file))) {
            for (PurchaseRequisition pr : objects) {
                writer.write("PurchaseReqID :      " + pr.PurchaseReqID + "\n");
                writer.write("ItemID:              " + pr.ItemID + "\n");
                writer.write("SupplierID:          " + pr.SupplierID + "\n");
                writer.write("Quantity:            " + pr.Quantity + "\n");
                writer.write("ReqDate:             " + pr.ReqDate.format(df) + "\n");
                writer.write("SalesMgrID:          " + pr.SalesMgrID + "\n");
                writer.write("Status:              " + pr.Status + "\n");
                writer.write("~~~~~\n");
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    @Override
    public void Modify(String id, PurchaseRequisition object) {
        List<PurchaseRequisition> allPR = ListAll();
        for (PurchaseRequisition pr : allPR) {
            if (Objects.equals(pr.PurchaseReqID, id)) {
                pr.PurchaseReqID = object.PurchaseReqID;
                pr.ItemID = object.ItemID;
                pr.SupplierID = object.SupplierID;
                pr.Quantity = object.Quantity;
                pr.ReqDate = object.ReqDate;
                pr.SalesMgrID = object.SalesMgrID;
                pr.Status = object.Status;
            }
        }
        Overwrite(allPR);
    }

    @Override
    public void Remove(PurchaseRequisition object) {
        List<PurchaseRequisition> allPR = ListAll();
        allPR.removeIf(pr -> Objects.equals(pr.PurchaseReqID, object.PurchaseReqID));
        Overwrite(allPR);
    }

    @Override
    public String ValidityChecker(PurchaseRequisition object) {
        return "";
    }

    @Override
    public String ValidityCheckerWithHistory(PurchaseRequisition object, PurchaseRequisition history) {
        return "";
    }
}