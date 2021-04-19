package shop;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import shop.Class.Product;
import shop.Class.ProductTable;
import utils.ConnectionUtil;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Order extends Component implements Initializable {
    @FXML
    TableView Table;

    @FXML
    TableColumn<ProductTable,String> p_name,p_detail,price,remain;

    @FXML
    TextField order;

    private String user;

    private int count = 0;

    ObservableList<ProductTable> observableList = FXCollections.observableArrayList();

    ArrayList<Product> list3 = new ArrayList<>();

    ConnectionUtil connectionClass = new ConnectionUtil();
    Connection connection = connectionClass.conDB();

    public ObservableList<ProductTable> Show_item() throws SQLException {
        String sql = "SELECT * FROM `product`";
        Statement statement = connection.createStatement();
        ResultSet resultSet=statement.executeQuery(sql);
        p_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        p_detail.setCellValueFactory(new PropertyValueFactory<>("detail"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        remain.setCellValueFactory(new PropertyValueFactory<>("remain"));
        while (resultSet.next()){
            NumberFormat numberFormat = NumberFormat.getNumberInstance();
            String PriceFormat = numberFormat.format(resultSet.getInt(4));
            String RemainFormat = numberFormat.format(resultSet.getInt(5));
            observableList.add(new ProductTable(resultSet.getString(1),resultSet.getString(3),PriceFormat,RemainFormat));
        }
        return observableList;
    }
    public void read() throws IOException {
        File file = new File("Customer_Cart.csv");
        file.createNewFile();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("Customer_Cart.csv")));
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
            String[] info = line.split("@");
            Product newProduct = new Product(info[0], info[1], info[2], info[3], info[4]);
            if (newProduct.getUser().equals(user)) {
                list3.add(newProduct);
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Show_item();
            Table.setItems(observableList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addItem(ActionEvent event) throws IOException, SQLException {
        read();
        ProductTable pdt = (ProductTable) Table.getSelectionModel().getSelectedItem();
        File file = new File("Customer_Cart.csv");
        file.createNewFile();
        FileWriter fileWriter = new FileWriter(file, true);
        BufferedWriter writer = new BufferedWriter(fileWriter);
        String sql = "SELECT * FROM `product`";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        for (Product product : list3){
            if (pdt.getName().equals(product.getName())){
                count = 1;
                break;

            }
        }
        if (order.getText().equals("")){
            JOptionPane.showMessageDialog(this, "โปรดระบุจำนวนใหม่", "Alert", JOptionPane.INFORMATION_MESSAGE);
        }
        else if (Double.parseDouble(order.getText()) <= 0 || Double.parseDouble(order.getText()) % 1 != 0){
            JOptionPane.showMessageDialog(this, "โปรดระบุจำนวนใหม่", "Alert", JOptionPane.INFORMATION_MESSAGE);
        }
        else if (order.getText().matches("\\d{5,50}")){
            JOptionPane.showMessageDialog(this, "Too Much Amount", "Alert", JOptionPane.INFORMATION_MESSAGE);
        }

        else if (count == 1) {
            JOptionPane.showMessageDialog(this, "มีรายการซ้ำ", "Alert", JOptionPane.INFORMATION_MESSAGE);
            count = 0;
        }

        else {
            while (resultSet.next()) {
                if (resultSet.getString(1).equals(pdt.getName())) {
                    if (Integer.parseInt(order.getText()) > Integer.parseInt(resultSet.getString(5))) {
                        JOptionPane.showMessageDialog(this, "Not enough item", "Alert", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    } else {
                        NumberFormat numberFormat = NumberFormat.getNumberInstance();
                        int total = Integer.parseInt(pdt.getPrice().replace(",", "")) * Integer.parseInt(order.getText().replace(",", ""));
                        String total2 = numberFormat.format(total);
                        writer.append(user + "@" + pdt.getName() + "@" + order.getText() + "@" + total2 + "@" + "wait");
                        writer.newLine();
                        writer.close();
                        read();
                        JOptionPane.showMessageDialog(this, "Added", "Alert", JOptionPane.INFORMATION_MESSAGE);
                        order.clear();
                    }
                }
            }
        }
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void Logout(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Button button = (Button) event.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
    }

    public void Cart(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Cart.fxml"));
        Button button = (Button) event.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        Cart cart = fxmlLoader.getController();
        cart.setUser(user);
        stage.setScene(scene);
    }
}
