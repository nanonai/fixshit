package Classes;

import java.time.format.DateTimeFormatter;
import java.util.List;

public interface SystemEntities<T> {
    final String user_file = "datafile/user.txt";
    final String supplier_file = "datafile/supplier.txt";
    final String item_file = "datafile/item.txt";
    final String item_supplier_file = "datafile/item_supplier.txt";
    final String pr_file = "datafile/purchase_req.txt";
    final String po_file = "datafile/purchase_order.txt";
    final String payment_file = "datafile/payment.txt";
    final String sales_file = "datafile/sales.txt";
    final String item_sales_file = "datafile/item_sales.txt";
    final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    final String email_regex =
            "^(?!\\.)(?!.*\\.\\.)([a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*)"
                    + "@([a-zA-Z0-9.-]+)\\.([a-zA-Z]{2,})$";
    final String phone_regex = "^01[0-9]{8}$";
    final String upper_regex = ".*[A-Z].*";
    final String lower_regex = ".*[a-z].*";
    final String digit_regex = ".*\\d.*";
    final String special_regex = ".*[!@#$%^&*()\\-+].*";

    public String IdMaker();
    public String IdMakerWithType(String type);
    public List<T> ListAll();
    public List<T> ListAllWithFilter(String filter);
    public List<T> ListAllWithType(String type);
    public T GetObjectByID(String id);
    public List<T> GetObjectsByIDS(List<String> ids);
    public String ValidityChecker(T object);
    public String ValidityCheckerWithHistory(T object, T history);
    public void AddNew(T object);
    public void Remove(T object);
    public void Modify(String id, T object);
    public void Overwrite(List<T> objects);
}
