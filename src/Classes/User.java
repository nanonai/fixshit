package Classes;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class User implements SystemEntities<User> {
    private String UserID, Username, Password, FullName, Email, AccType, Phone;
    private int RememberMe;
    private LocalDate DateOfRegis;

    public User() {

    }

    public User(String UserID, String Username, String Password, String FullName,
                String Email, String Phone, String AccType, LocalDate DateOfRegis,
                int RememberMe) {
        this.UserID = UserID;
        this.Username = Username;
        this.Password = Password;
        this.FullName = FullName;
        this.Email = Email;
        this.Phone = Phone;
        this.AccType = AccType;
        this.DateOfRegis = DateOfRegis;
        this.RememberMe = RememberMe;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAccType() {
        return AccType;
    }

    public void setAccType(String accType) {
        AccType = accType;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public int getRememberMe() {
        return RememberMe;
    }

    public void setRememberMe(int rememberMe) {
        RememberMe = rememberMe;
    }

    public LocalDate getDateOfRegis() {
        return DateOfRegis;
    }

    public void setDateOfRegis(LocalDate dateOfRegis) {
        DateOfRegis = dateOfRegis;
    }

    @Override
    public List<User> ListAll() {
        List<User> allUser = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(user_file))) {
            String UserID = "", Username = "", Password = "", FullName = "", Email = "", AccType = "", Phone = "";
            int RememberMe = 0;
            LocalDate DateOfRegis = null;

            String line;
            int counter = 1;
            while ((line = reader.readLine()) != null) {
                switch (counter) {
                    case 1:
                        UserID = line.substring(16);
                        break;
                    case 2:
                        Username = line.substring(16);
                        break;
                    case 3:
                        Password = line.substring(16);
                        break;
                    case 4:
                        FullName = line.substring(16);
                        break;
                    case 5:
                        Email = line.substring(16);
                        break;
                    case 6:
                        Phone = line.substring(16);
                        break;
                    case 7:
                        AccType = line.substring(16);
                        break;
                    case 8:
                        DateOfRegis = LocalDate.parse(line.substring(16), df);
                        break;
                    case 9:
                        RememberMe = Integer.parseInt(line.substring(16));
                        break;
                    default:
                        counter = 0;
                        allUser.add(new User(UserID, Username, Password, FullName, Email,
                                Phone, AccType, DateOfRegis, RememberMe));
                        break;
                }
                counter += 1;
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        return allUser;
    }

    @Override
    public List<User> ListAllWithFilter(String filter) {
        List<User> user_list = ListAll();
        List<User> filter_list = new ArrayList<>();
        String temp = filter.toLowerCase().replace(" ", "");
        user_list.removeIf(user -> Objects.equals(user.UserID.substring(2), "0000000000"));
        if (filter.isEmpty()) {
            return user_list;
        }
        for (User user: user_list) {
            if ((user.UserID.toLowerCase().contains(temp) ||
                    user.Email.toLowerCase().replace(" ", "").contains(temp) ||
                    user.Phone.contains(temp) ||
                    user.DateOfRegis.toString().contains(temp) ||
                    user.Username.toLowerCase().contains(temp) ||
                    user.FullName.toLowerCase().replace(" ", "").contains(temp) ||
                    user.AccType.toLowerCase().replace(" ", "").contains(temp))) {
                filter_list.add(user);
            }
        }
        return filter_list;
    }

    @Override
    public List<User> ListAllWithType(String type) {
        List<User> user_list = ListAll();
        List<User> type_list = new ArrayList<>();
        user_list.removeIf(user -> Objects.equals(user.UserID.substring(2), "0000000000"));
        if (type.isEmpty()) {
            return user_list;
        }
        for (User user: user_list) {
            if (Objects.equals(user.AccType, type)) {
                type_list.add(user);
            }
        }
        return type_list;
    }

    public List<User> ListAllWithTypeFilter(String type, String filter) {
        List<User> type_list = ListAllWithType(type);
        List<User> filter_list = ListAllWithFilter(filter);
        List<User> result = new ArrayList<>();
        for (User type_u : type_list) {
            for (User filter_u : filter_list) {
                if (Objects.equals(type_u.UserID, filter_u.UserID)) {
                    result.add(type_u);
                    break;
                }
            }
        }
        return result;
    }


    @Override
    public String IdMaker() {
        return "";
    }

    @Override
    public String IdMakerWithType(String type) {
        List<User> allUser = ListAll();
        boolean repeated = false;
        boolean success = false;
        String role = switch (type) {
            case "Administrator" -> "AD";
            case "Sales Manager" -> "SM";
            case "Purchase Manager" -> "PM";
            case "Inventory Manager" -> "IM";
            case "Finance Manager" -> "FM";
            default -> "";
        };
        String newId = "";
        while (!success) {
            long newNum = (long) (Math.random() * 1E10);
            while (newNum == 0) {
                newNum = (long) (Math.random() * 1E10);
            }
            StringBuilder number = new StringBuilder(String.valueOf(newNum));
            while (number.length() < 10) {
                number.insert(0, "0");
            }
            newId = String.format("%s%s", role, number);
            for (User user : allUser) {
                if (Objects.equals(user.UserID, newId) && Objects.equals(user.AccType, AccType)) {
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
    public User GetObjectByID(String id) {
        List<User> user_list = ListAll();
        for (User user: user_list) {
            if (Objects.equals(user.UserID, id)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<User> GetObjectsByIDS(List<String> ids) {
        List<User> user_list = new ArrayList<>();
        for (String id: ids) {
            user_list.add(GetObjectByID(id));
        }
        return user_list;
    }

    @Override
    public void AddNew(User object) {
        try (FileWriter writer = new FileWriter(user_file, true)) {
            writer.write("UserID:         " + object.UserID + "\n");
            writer.write("Username:       " + object.Username + "\n");
            writer.write("Password:       " + object.Password + "\n");
            writer.write("FullName:       " + object.FullName + "\n");
            writer.write("Email:          " + object.Email + "\n");
            writer.write("Phone:          " + object.Phone + "\n");
            writer.write("AccType:        " + object.AccType + "\n");
            writer.write("DateOfRegis:    " + object.DateOfRegis.format(df) + "\n");
            writer.write("RememberMe:     0\n");
            writer.write("~~~~~\n");
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    @Override
    public void Overwrite(List<User> objects) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(user_file))) {
            for (User user : objects) {
                writer.write("UserID:         " + user.UserID + "\n");
                writer.write("Username:       " + user.Username + "\n");
                writer.write("Password:       " + user.Password + "\n");
                writer.write("FullName:       " + user.FullName + "\n");
                writer.write("Email:          " + user.Email + "\n");
                writer.write("Phone:          " + user.Phone + "\n");
                writer.write("AccType:        " + user.AccType + "\n");
                writer.write("DateOfRegis:    " + user.DateOfRegis.format(df) + "\n");
                writer.write("RememberMe:     " + user.RememberMe + "\n");
                writer.write("~~~~~\n");
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    @Override
    public void Modify(String id, User object) {
        List<User> allUser = ListAll();
        for (User user : allUser) {
            if (Objects.equals(user.UserID, id)) {
                user.UserID = object.UserID;
                user.Username = object.Username;
                user.FullName = object.FullName;
                user.Password = object.Password;
                user.Phone = object.Phone;
                user.Email = object.Email;
                user.AccType = object.AccType;
                user.DateOfRegis = object.DateOfRegis;
                user.RememberMe = object.RememberMe;
            }
        }
        Overwrite(allUser);
    }

    @Override
    public void Remove(User object) {
        List<User> allUser = ListAll();
        allUser.removeIf(user -> Objects.equals(user.UserID, object.UserID));
        Overwrite(allUser);
    }

    public User GetRememberedUser() {
        List<User> allUser = ListAll();
        for (User user : allUser) {
            if (Objects.equals(user.RememberMe, 1)) {
                return user;
            }
        }
        return null;
    }

    public void UnrememberAllUser() {
        List<User> allUser = ListAll();
        for (User user : allUser) {
            if (Objects.equals(user.RememberMe, 1)) {
                user.RememberMe = 0;
                break;
            }
        }
        Overwrite(allUser);
    }

    public boolean UsernameChecker(String Username) {
        List<User> allUser = ListAll();
        boolean repeated = false;
        for (User user : allUser) {
            if (Objects.equals(user.Username.toLowerCase(), Username.toLowerCase())) {
                repeated = true;
                break;
            }
        }
        return !repeated;
    }

    public boolean EmailChecker(String Email) {
        List<User> allUser = ListAll();
        boolean repeated = false;
        for (User user : allUser) {
            if (Objects.equals(user.Email, Email)) {
                repeated = true;
                break;
            }
        }
        return !repeated;
    }

    public boolean PhoneChecker(String Phone) {
        List<User> allUser = ListAll();
        boolean repeated = false;
        for (User user : allUser) {
            if (Objects.equals(user.Phone, Phone)) {
                repeated = true;
                break;
            }
        }
        return !repeated;
    }

    public boolean PasswordChecker(String password) {
        return password.matches(upper_regex) && password.matches(lower_regex) &&
                password.matches(digit_regex) && password.matches(special_regex);
    }

    @Override
    public String ValidityChecker(User object) {
        String indicator = "";
        if (object.Username.length() >= 8 && object.Username.length() <= 36) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        if (UsernameChecker(object.Username)) {
            indicator += "O";
        } else {
            indicator += "X";
        }
        if (object.Password.length() >= 8) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        if (PasswordChecker(object.Password)) {
            indicator += "O";
        } else {
            indicator += "X";
        }
        if (object.FullName.length() >= 8 && object.FullName.length() <= 48) {
            indicator += "1";
        } else {
            indicator += "0";
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
        return indicator;
    }

    @Override
    public String ValidityCheckerWithHistory(User object, User history) {
        String indicator = "";
        if (object.Username.length() >= 8 && object.Username.length() <= 36) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        if (UsernameChecker(object.Username)) {
            indicator += "O";
        } else {
            if (object.Username.equals(history.Username)) {
                indicator += "O";
            } else {
                indicator += "X";
            }
        }
        if (object.Password.length() >= 8) {
            indicator += "1";
        } else {
            indicator += "0";
        }
        if (PasswordChecker(object.Password)) {
            indicator += "O";
        } else {
            indicator += "X";
        }
        if (object.FullName.length() >= 8 && object.FullName.length() <= 48) {
            indicator += "1";
        } else {
            indicator += "0";
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
        return indicator;
    }

    public static List<Sales> checkSalesRecordByID(String UserID) {
        List<Sales> userRelatedRecords = new ArrayList<>();
        List<Sales> allSales = new Sales().ListAll();
        for (Sales sales: allSales) {
            if (Objects.equals(sales.getSalesMgrID(), UserID)) {
                userRelatedRecords.add(sales);
            }
        }
        return userRelatedRecords;
    }

    public static List<PurchaseRequisition> checkPRRecordByID(String UserID) {
        List<PurchaseRequisition> userRelatedRecords = new ArrayList<>();
        List<PurchaseRequisition> allPR = new PurchaseRequisition().ListAll();
        for (PurchaseRequisition pr: allPR) {
            if (Objects.equals(pr.getSalesMgrID(), UserID)) {
                userRelatedRecords.add(pr);
            }
        }
        return userRelatedRecords;
    }

    public static List<PurchaseOrder> checkPORecordByID(String UserID) {
        List<PurchaseOrder> userRelatedRecords = new ArrayList<>();
        List<PurchaseOrder> allPO = new PurchaseOrder().ListAll();
        for (PurchaseOrder po: allPO) {
            if (Objects.equals(po.getPurchaseMgrID(), UserID)) {
                userRelatedRecords.add(po);
            }
        }
        return userRelatedRecords;
    }

    public static List<Payment> checkPYRecordByID(String UserID) {
        List<Payment> userRelatedRecords = new ArrayList<>();
        List<Payment> allPY = new Payment().ListAll();
        for (Payment py: allPY) {
            if (Objects.equals(py.getFinanceMgrID(), UserID)) {
                userRelatedRecords.add(py);
            }
        }
        return userRelatedRecords;
    }
}
