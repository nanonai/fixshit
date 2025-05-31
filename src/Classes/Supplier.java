package Classes;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class Supplier implements SystemEntities<Supplier> {
    private String SupplierID;
    private String SupplierName;
    private String ContactPerson;
    private String Phone;
    private String Email;
    private String Address;

    public Supplier() {

    }

    public Supplier(String SupplierID, String SupplierName, String ContactPerson, String Phone, String Email, String Address) {
        this.SupplierID = SupplierID;
        this.SupplierName = SupplierName;
        this.ContactPerson = ContactPerson;
        this.Phone = Phone;
        this.Email = Email;
        this.Address = Address;
    }

    public String getSupplierID() {
        return SupplierID;
    }

    public void setSupplierID(String supplierID) {
        SupplierID = supplierID;
    }

    public String getSupplierName() {
        return SupplierName;
    }

    public void setSupplierName(String supplierName) {
        SupplierName = supplierName;
    }

    public String getContactPerson() {
        return ContactPerson;
    }

    public void setContactPerson(String contactPerson) {
        ContactPerson = contactPerson;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    @Override
    public List<Supplier> ListAll() {
        List<Supplier> allSupplier = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(supplier_file))) {
            String SupplierID = "", SupplierName = "", ContactPerson = "", Phone = "", Email = "", Address = "";

            String line;
            int counter = 1;
            while ((line = reader.readLine()) != null) {
                switch (counter) {
                    case 1:
                        SupplierID = line.substring(16);
                        break;
                    case 2:
                        SupplierName = line.substring(16);
                        break;
                    case 3:
                        ContactPerson = line.substring(16);
                        break;
                    case 4:
                        Phone = line.substring(16);
                        break;
                    case 5:
                        Email = line.substring(16);
                        break;
                    case 6:
                        Address = line.substring(16);
                        break;
                    default:
                        counter = 0;
                        allSupplier.add(new Supplier(SupplierID, SupplierName, ContactPerson, Phone, Email, Address));
                        break;
                }
                counter += 1;
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        return allSupplier;
    }

    @Override
    public List<Supplier> ListAllWithFilter(String filter) {
        List<Supplier> allSupplier = ListAll();
        List<Supplier> filter_list = new ArrayList<>();
        String temp = filter.toLowerCase().replace(" ", "");
        if (filter.isEmpty()) {
            return allSupplier;
        }
        for (Supplier supplier : allSupplier) {
            if ((supplier.SupplierID.toLowerCase().contains(temp) ||
                    supplier.SupplierName.toLowerCase().replace(" ", "").contains(temp) ||
                    supplier.ContactPerson.toLowerCase().replace(" ", "").contains(temp) ||
                    supplier.Phone.contains(temp) ||
                    supplier.Email.toLowerCase().replace(" ", "").contains(temp) ||
                    supplier.Address.toLowerCase().replace(" ", "").contains(temp))) {
                filter_list.add(supplier);
            }
        }
        return filter_list;
    }

    // Supplier class treat type as search in only names
    @Override
    public List<Supplier> ListAllWithType(String type) {
        List<Supplier> allSupplier = ListAll();
        List<Supplier> filter_list = new ArrayList<>();
        String temp = type.toLowerCase().replace(" ", "");
        if (type.isEmpty()) {
            return null;
        }
        for (Supplier supplier : allSupplier) {
            if (supplier.SupplierName.toLowerCase().replace(" ", "").contains(temp)) {
                filter_list.add(supplier);
            }
        }
        return filter_list;
    }

    @Override
    public String IdMaker() {
        List<Supplier> allSupplier = ListAll();
        boolean repeated = false;
        boolean success = false;
        String newId = "";
        while (!success) {
            long newNum = (long) (Math.random() * 1E4);
            StringBuilder number = new StringBuilder(String.valueOf(newNum));
            while (number.length() < 4) {
                number.insert(0, "0");
            }
            newId = String.format("%s%s", "S", number);
            for (Supplier supplier : allSupplier) {
                if (Objects.equals(supplier.SupplierID, newId)) {
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
    public Supplier GetObjectByID(String id) {
        List<Supplier> allSupplier = ListAll();
        for (Supplier supplier : allSupplier){
            if (Objects.equals(supplier.SupplierID, id)){
                return supplier;
            }
        }
        return null;
    }

    @Override
    public List<Supplier> GetObjectsByIDS(List<String> ids) {
        List<Supplier> suppliers = new ArrayList<>();
        for (String id: ids) {
            suppliers.add(GetObjectByID(id));
        }
        return suppliers;
    }

    public Supplier GetObjectByName(String name) {
        List<Supplier> allSupplier = ListAll();
        for (Supplier supplier : allSupplier) {
            if (Objects.equals(supplier.SupplierName, name)) {
                return supplier;
            }
        }
        return null;
    }

    @Override
    public void AddNew(Supplier object) {
        try (FileWriter writer = new FileWriter(supplier_file, true)) {
            writer.write("SupplierID:     " + object.SupplierID + "\n");
            writer.write("SupplierName:   " + object.SupplierName + "\n");
            writer.write("ContactPerson:  " + object.ContactPerson + "\n");
            writer.write("Phone:          " + object.Phone + "\n");
            writer.write("Email:          " + object.Email + "\n");
            writer.write("Address:        " + object.Address + "\n");
            writer.write("~~~~~\n");
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    @Override
    public void Overwrite(List<Supplier> objects) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(supplier_file))) {
            for (Supplier supplier : objects) {
                writer.write("SupplierID:     " + supplier.SupplierID + "\n");
                writer.write("SupplierName:   " + supplier.SupplierName + "\n");
                writer.write("ContactPerson:  " + supplier.ContactPerson + "\n");
                writer.write("Phone:          " + supplier.Phone + "\n");
                writer.write("Email:          " + supplier.Email + "\n");
                writer.write("Address:        " + supplier.Address + "\n");
                writer.write("~~~~~\n");
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    @Override
    public void Modify(String id, Supplier object) {
        List<Supplier> allSupplier = ListAll();
        for (Supplier supplier : allSupplier) {
            if (Objects.equals(supplier.SupplierID, id)) {
                supplier.SupplierID = object.SupplierID;
                supplier.SupplierName = object.SupplierName;
                supplier.ContactPerson = object.ContactPerson;
                supplier.Phone = object.Phone;
                supplier.Email = object.Email;
                supplier.Address = object.Address;
            }
        }
        Overwrite(allSupplier);
    }

    @Override
    public void Remove(Supplier object) {
        List<Supplier> allSupplier = ListAll();
        allSupplier.removeIf(supplier -> Objects.equals(supplier.SupplierID, object.SupplierID));
        Overwrite(allSupplier);
    }

    public Boolean NameChecker(String name) {
        List<Supplier> allSupplier = ListAll();
        boolean repeated = false;
        for (Supplier supplier : allSupplier) {
            if (Objects.equals(supplier.SupplierName, name)) {
                repeated = true;
                break;
            }
        }
        return !repeated;
    }

    public boolean EmailChecker(String email) {
        List<Supplier> allSupplier = ListAll();
        boolean repeated = false;
        for (Supplier supplier : allSupplier) {
            if (Objects.equals(supplier.Email, email)) {
                repeated = true;
                break;
            }
        }
        return !repeated;
    }

    public boolean PhoneChecker(String phone) {
        List<Supplier> allSupplier = ListAll();
        boolean repeated = false;
        for (Supplier supplier : allSupplier) {
            if (Objects.equals(supplier.Phone, phone)) {
                repeated = true;
                break;
            }
        }
        return !repeated;
    }

    public boolean AddressChecker(String address) {
        List<Supplier> allSupplier = ListAll();
        boolean repeated = false;
        for (Supplier supplier : allSupplier) {
            if (Objects.equals(supplier.Address, address)) {
                repeated = true;
                break;
            }
        }
        return !repeated;
    }

    @Override
    public String ValidityChecker(Supplier object) {
        String indicator = "";
        if (object.SupplierName.length() >= 8 && object.SupplierName.length() <= 48) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        if (NameChecker(object.SupplierName)) {
            indicator += "O";
        } else {
            indicator += "X";
        }
        if (object.ContactPerson.length() >= 8 && object.ContactPerson.length() <= 30) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        if (Pattern.compile(phone_regex).matcher(object.Phone).matches()) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        if (PhoneChecker(object.Phone)) {
            indicator += "O";
        } else {
            indicator += "X";
        }
        if (Pattern.compile(email_regex).matcher(object.Email).matches()) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        if (EmailChecker(object.Email)) {
            indicator += "O";
        } else {
            indicator += "X";
        }
        if (object.Address.length() >= 8 && object.Address.length() <= 255) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        if (AddressChecker(object.Address)) {
            indicator += "O";
        } else {
            indicator += "X";
        }
        return indicator;
    }

    @Override
    public String ValidityCheckerWithHistory(Supplier object, Supplier history) {
        String indicator = "";
        if (object.SupplierName.length() >= 8 && object.SupplierName.length() <= 48) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        if (NameChecker(object.SupplierName)) {
            indicator += "O";
        } else {
            if (object.SupplierName.equals(history.SupplierName)) {
                indicator += "O";
            } else {
                indicator += "X";
            }
        }
        if (object.ContactPerson.length() >= 8 && object.ContactPerson.length() <= 30) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        if (Pattern.compile(phone_regex).matcher(object.Phone).matches()) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        if (PhoneChecker(object.Phone)) {
            indicator += "O";
        } else {
            if (object.Phone.equals(history.Phone)) {
                indicator += "O";
            } else {
                indicator += "X";
            }
        }
        if (Pattern.compile(email_regex).matcher(object.Email).matches()) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        if (EmailChecker(object.Email)) {
            indicator += "O";
        } else {
            if (object.Email.equals(history.Email)) {
                indicator += "O";
            } else {
                indicator += "X";
            }
        }
        if (object.Address.length() >= 8 && object.Address.length() <= 255) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        if (AddressChecker(object.Address)) {
            indicator += "O";
        } else {
            if (object.Address.equals(history.Address)) {
                indicator += "O";
            } else {
                indicator += "X";
            }
        }
        return indicator;
    }
}
