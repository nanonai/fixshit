package Classes;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Payment implements SystemEntities<Payment> {
    private String PaymentID, PurchaseOrderID, FinanceMgrID;
    private double Amount;
    private LocalDate PaymentDate;

    public Payment() {

    }

    public Payment(String PaymentID,String PurchaseOrderID,
                   double Amount,LocalDate PaymentDate,String FinanceMgrID){
        this.PaymentID = PaymentID;
        this.PurchaseOrderID = PurchaseOrderID;
        this.Amount = Amount;
        this.PaymentDate = PaymentDate;
        this.FinanceMgrID = FinanceMgrID;
    }

    public String getPaymentID() {
        return PaymentID;
    }

    public void setPaymentID(String paymentID) {
        PaymentID = paymentID;
    }

    public String getPurchaseOrderID() {
        return PurchaseOrderID;
    }

    public void setPurchaseOrderID(String purchaseOrderID) {
        PurchaseOrderID = purchaseOrderID;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }

    public LocalDate getPaymentDate() {
        return PaymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        PaymentDate = paymentDate;
    }

    public String getFinanceMgrID() {
        return FinanceMgrID;
    }

    public void setFinanceMgrID(String financeMgrID) {
        FinanceMgrID = financeMgrID;
    }

    @Override
    public List<Payment> ListAll() {
        List<Payment> allPY = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(payment_file))) {
            String PaymentID = "", PurchaseOrderID = "", FinanceMgrID = "";
            double Amount = 0;
            LocalDate PaymentDate = null;
            int counter = 1;
            String line;
            while ((line = reader.readLine()) != null) {
                switch (counter) {
                    case 1:
                        PaymentID = line.substring(21);
                        break;
                    case 2:
                        PurchaseOrderID = line.substring(21);
                        break;
                    case 3:
                        Amount = Double.parseDouble(line.substring(21).trim());
                        break;
                    case 4:
                        PaymentDate = LocalDate.parse(line.substring(21).trim(), df);
                        break;
                    case 5:
                        FinanceMgrID = line.substring(21);
                        break;
                    default:
                        counter = 0;
                        allPY.add(new Payment(PaymentID, PurchaseOrderID, Amount, PaymentDate,
                                FinanceMgrID));
                        break;
                }
                counter += 1;
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        return allPY;
    }

    @Override
    public List<Payment> ListAllWithFilter(String filter) {
        List<Payment> allPY = ListAll();
        List<Payment> filter_list = new ArrayList<>();
        String temp = filter.toLowerCase().replace(" ", "");
        if (filter.isEmpty()) {
            return allPY;
        }
        for (Payment py : allPY) {
            if ((py.PaymentID.toLowerCase().contains(temp) ||
                    py.PurchaseOrderID.toLowerCase().contains(temp) ||
                    Double.toString(py.Amount).contains(temp) ||
                    py.PaymentDate.toString().contains(temp) ||
                    py.FinanceMgrID.toLowerCase().contains(temp))) {
                filter_list.add(py);
            }
        }
        return filter_list;
    }

    @Override
    public List<Payment> ListAllWithType(String type) {
        return null;
    }

    @Override
    public String IdMaker() {
        List<Payment> allPY = ListAll();
        boolean repeated = false;
        boolean success = false;
        String newId = "";
        while (!success) {
            long newNum = (long) (Math.random() * 1E4);
            StringBuilder number = new StringBuilder(String.valueOf(newNum));
            while (number.length() < 4) {
                number.insert(0, "0");
            }
            newId = String.format("%s%s", "PY", number);
            for (Payment py : allPY) {
                if (Objects.equals(py.PaymentID, newId)) {
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
    public Payment GetObjectByID(String id) {
        List<Payment> allPY = ListAll();
        for (Payment py : allPY) {
            if (Objects.equals(py.PaymentID, id)) {
                return py;
            }
        }
        return null;
    }

    @Override
    public List<Payment> GetObjectsByIDS(List<String> ids) {
        List<Payment> pys = new ArrayList<>();
        for (String id: ids) {
            pys.add(GetObjectByID(id));
        }
        return pys;
    }

    @Override
    public void AddNew(Payment object) {
        try (FileWriter writer = new FileWriter(payment_file, true)) {
            writer.write("PaymentID:           " + object.PaymentID + "\n");
            writer.write("PurchaseOrderID:     " + object.PurchaseOrderID + "\n");
            writer.write("Amount:              " + object.Amount + "\n");
            writer.write("PaymentDate:         " + object.PaymentDate.format(df) + "\n");
            writer.write("FinanceMgrID:        " + object.FinanceMgrID + "\n");
            writer.write("~~~~~\n");
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    @Override
    public void Overwrite(List<Payment> objects) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(payment_file))) {
            for (Payment py: objects) {
                writer.write("PaymentID:           " + py.PaymentID + "\n");
                writer.write("PurchaseOrderID:     " + py.PurchaseOrderID + "\n");
                writer.write("Amount:              " + py.Amount + "\n");
                writer.write("PaymentDate:         " + py.PaymentDate.format(df) + "\n");
                writer.write("FinanceMgrID:        " + py.FinanceMgrID + "\n");
                writer.write("~~~~~\n");
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    @Override
    public void Modify(String id, Payment object) {
        List<Payment> allPY = ListAll();
        for (Payment py: allPY) {
            if (Objects.equals(py.PaymentID, id)) {
                py.PaymentID = object.PaymentID;
                py.PurchaseOrderID = object.PurchaseOrderID;
                py.Amount = object.Amount;
                py.PaymentDate = object.PaymentDate;
                py.FinanceMgrID = object.FinanceMgrID;
            }
        }
        Overwrite(allPY);
    }

    @Override
    public void Remove(Payment object) {
        List<Payment> allPY = ListAll();
        allPY.removeIf(payment -> Objects.equals(payment.PaymentID, object.PaymentID));
        Overwrite(allPY);
    }

    @Override
    public String ValidityChecker(Payment object) {
        return "";
    }

    @Override
    public String ValidityCheckerWithHistory(Payment object, Payment history) {
        return "";
    }
}