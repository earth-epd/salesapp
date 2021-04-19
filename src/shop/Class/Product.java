package shop.Class;

public class Product {
    private String Name;
    private String Detail;
    private String Price;
    private int Remain;
    private int ProductId;
    private String Status;
    private String User;
    private int quantity;
    private String total;
    private int O_num;


    public Product(String user,String name, String price, String total,String status) {
        this.User = user;
        Name = name;
        Price = price;
        this.total = total;
        this.Status = "wait";
        this.O_num = 0;
    }

    public Product(String name, String detail, String price, int remain, int productId) {
        Name = name;
        ProductId = productId ;
        Detail = detail;
        Price = price;
        Remain = remain;
    }

    public Product(String name,  String user,String price ,int quantity) {
        User = user;
        Name = name;
        Price = price;
        this.quantity = quantity;
    }

    public int getProductId() {
        return ProductId;
    }

    public String getStatus() {
        return Status;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDetail() {
        return Detail;
    }

    public void setDetail(String detail) {
        Detail = detail;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public int getRemain() {
        return Remain;
    }

    public void setRemain(int remain) {
        Remain = remain;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getUser() {
        return User;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

}
