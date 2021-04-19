package shop.Class;

import javafx.beans.property.SimpleStringProperty;

public class ProductTable {
    private final SimpleStringProperty name;
    private final SimpleStringProperty number;
    private final SimpleStringProperty detail;
    private final SimpleStringProperty price;
    private final SimpleStringProperty remain;
    private final SimpleStringProperty total;
    private final SimpleStringProperty user;
    private final SimpleStringProperty status;
    private final SimpleStringProperty quantity;
    private final SimpleStringProperty num;

    public void setTotal(String total) {
        this.total.set(total);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getNumber() {
        return number.get();
    }

    public SimpleStringProperty numberProperty() {
        return number;
    }

    public void setNumber(String number) {
        this.number.set(number);
    }

    public String getDetail() {
        return detail.get();
    }

    public SimpleStringProperty detailProperty() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail.set(detail);
    }

    public String getPrice() {
        return price.get();
    }

    public SimpleStringProperty priceProperty() {
        return price;
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public String getRemain() {
        return remain.get();
    }

    public SimpleStringProperty remainProperty() {
        return remain;
    }

    public void setRemain(String remain) {
        this.remain.set(remain);
    }

    public String getUser() {
        return user.get();
    }

    public SimpleStringProperty userProperty() {
        return user;
    }

    public void setUser(String user) {
        this.user.set(user);
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public String getQuantity() {
        return quantity.get();
    }

    public SimpleStringProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity.set(quantity);
    }

    public String getNum() {
        return num.get();
    }

    public SimpleStringProperty numProperty() {
        return num;
    }

    public void setNum(String num) {
        this.num.set(num);
    }

    public ProductTable(String name, int number, String detail, String price, String remain) {
        this.name = new SimpleStringProperty(name);
        this.number = new SimpleStringProperty(""+number);
        this.detail = new SimpleStringProperty(detail);
        this.price = new SimpleStringProperty(""+price);
        this.remain = new SimpleStringProperty(""+remain);
        total = null;
        status = null;
        quantity = null;
        user = null;
        num = null;
   }

    public ProductTable(String name,String detail, String price, String remain) {
        this.name = new SimpleStringProperty(name);
        this.detail = new SimpleStringProperty(detail);
        this.price = new SimpleStringProperty(price);
        this.remain = new SimpleStringProperty(remain);
        this.number = null;
        total = null;
        status = null;
        quantity = null;
        user = null;
        num = null;
    }

    public ProductTable(String name, String price, String total) {
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleStringProperty(""+price);
        this.total = new SimpleStringProperty(""+total);
        number = null;
        detail = null;
        remain = null;
        status = null;
        quantity = null;
        user = null;
        num = null;
    }
    public ProductTable(String user,String name,String quantity,String total,String status,int num){
        this.user = new SimpleStringProperty(user);
        this.name = new SimpleStringProperty(name);
        this.quantity = new SimpleStringProperty(""+quantity);
        this.total = new SimpleStringProperty(""+total);
        this.status = new SimpleStringProperty(status);
        this.num = new SimpleStringProperty(""+ num);
        remain = null;
        detail = null;
        number = null;
        price = null;
    }

    public String getTotal() {
        return total.get();
    }

    public SimpleStringProperty totalProperty() {
        return total;
    }
}
