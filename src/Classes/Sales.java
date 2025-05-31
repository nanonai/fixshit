package Classes;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Sales implements SystemEntities<Sales> {
    private String SalesID, SalesMgrID;
    private LocalDate SalesDate;

    public Sales() {
        
    }
    
    public Sales(String SalesID, LocalDate SalesDate, String SalesMgrID) {
        this.SalesID = SalesID;
        this.SalesDate = SalesDate;
        this.SalesMgrID = SalesMgrID;
    }

    public String getSalesID() {
        return SalesID;
    }

    public void setSalesID(String salesID) {
        SalesID = salesID;
    }

    public LocalDate getSalesDate() {
        return SalesDate;
    }

    public void setSalesDate(LocalDate salesDate) {
        SalesDate = salesDate;
    }

    public String getSalesMgrID() {
        return SalesMgrID;
    }

    public void setSalesMgrID(String salesMgrID) {
        SalesMgrID = salesMgrID;
    }

    @Override
    public List<Sales> ListAll() {
        List<Sales> allSales = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(sales_file))) {
            String SalesID = "", SalesMgrID = "";
            LocalDate SalesDate = null;

            String line;
            int counter = 1;
            while ((line = reader.readLine()) != null) {
                switch (counter) {
                    case 1:
                        SalesID = line.substring(16);
                        break;
                    case 2:
                        SalesDate = LocalDate.parse(line.substring(16), df);
                        break;
                    case 3:
                        SalesMgrID = line.substring(16);
                        break;
                    default:
                        counter = 0;
                        allSales.add(new Sales(SalesID, SalesDate, SalesMgrID));
                        break;
                }
                counter += 1;
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        return allSales;
    }

    @Override
    public List<Sales> ListAllWithFilter(String filter) {
        List<Sales> sales_list = ListAll();
        List<Sales> filter_list = new ArrayList<>();
        String temp = filter.toLowerCase().replace(" ", "");
        if (filter.isEmpty()) {
            return sales_list;
        }
        for (Sales sales: sales_list) {
            if ((sales.SalesID.toLowerCase().contains(temp) ||
                    sales.SalesMgrID.toLowerCase().contains(temp) ||
                    sales.SalesDate.toString().contains(temp))) {
                filter_list.add(sales);
            }
        }
        return filter_list;
    }

    @Override
    public List<Sales> ListAllWithType(String type) {
        return null;
    }

    @Override
    public String IdMaker() {
        List<Sales> allSales = ListAll();
        boolean repeated = false;
        boolean success = false;
        String newId = "";
        while (!success) {
            long newNum = (long) (Math.random() * 1E4);
            StringBuilder number = new StringBuilder(String.valueOf(newNum));
            while (number.length() < 4) {
                number.insert(0, "0");
            }
            newId = String.format("%s%s", "SL", number);
            for (Sales sales : allSales) {
                if (Objects.equals(sales.SalesID, newId)) {
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
    public Sales GetObjectByID(String id) {
        List<Sales> allSales = ListAll();
        for (Sales sales : allSales) {
            if (Objects.equals(sales.SalesID, id)) {
                return sales;
            }
        }
        return null;
    }

    @Override
    public List<Sales> GetObjectsByIDS(List<String> ids) {
        List<Sales> sales = new ArrayList<>();
        for (String id: ids) {
            sales.add(GetObjectByID(id));
        }
        return sales;
    }

    @Override
    public void AddNew(Sales object) {
        try (FileWriter writer = new FileWriter(sales_file, true)) {
            writer.write("SalesID:        " + object.SalesID + "\n");
            writer.write("SalesDate:      " + object.SalesDate + "\n");
            writer.write("SalesMgrID:     " + object.SalesMgrID + "\n");
            writer.write("~~~~~\n");
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    @Override
    public void Overwrite(List<Sales> objects) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(sales_file))) {
            for (Sales sl : objects) {
                writer.write("SalesID:        " + sl.SalesID + "\n");
                writer.write("SalesDate:      " + sl.SalesDate + "\n");
                writer.write("SalesMgrID:     " + sl.SalesMgrID + "\n");
                writer.write("~~~~~\n");
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    @Override
    public void Modify(String id, Sales object) {
        List<Sales> allSales = ListAll();
        for (Sales sl : allSales) {
            if (Objects.equals(sl.SalesID, id)) {
                sl.SalesID = object.SalesID;
                sl.SalesDate = object.SalesDate;
                sl.SalesMgrID = object.SalesMgrID;
            }
        }
        Overwrite(allSales);
    }

    @Override
    public void Remove(Sales object) {
        List<Sales> allSales = ListAll();
        allSales.removeIf(sales -> Objects.equals(sales.SalesID, object.SalesID));
        Overwrite(allSales);
    }

    @Override
    public String ValidityChecker(Sales object) {
        return (new User().GetObjectByID(object.SalesMgrID) != null) ? "1" : "0";
    }

    @Override
    public String ValidityCheckerWithHistory(Sales object, Sales history) {
        return "";
    }
}
