package shop;

import javafx.application.Platform;
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
import java.sql.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Cart extends Component implements Initializable {

    ArrayList<Product> list = new ArrayList<>();
    ArrayList<Product> list1 = new ArrayList<>();

    ObservableList<ProductTable> observableList = FXCollections.observableArrayList();

    private int count = 0,i=0,c= 0;

    @FXML
    TableView Table;

    @FXML
    Text totals;
    @FXML
    TableColumn<ProductTable, String> p_name, price, total;

    private String user;

    public void read() throws IOException {
        File file = new File("Customer_Cart.csv");
        file.createNewFile();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("Customer_Cart.csv")));
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
            String[] info = line.split("@");
            Product newProduct = new Product(info[0], info[1], info[2], info[3], info[4]);
            if (newProduct.getUser().equals(user)) {
                list.add(newProduct);
            }
            else{
                list1.add(newProduct);
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    read();
                    p_name.setCellValueFactory(new PropertyValueFactory<>("name"));
                    price.setCellValueFactory(new PropertyValueFactory<>("price"));
                    total.setCellValueFactory(new PropertyValueFactory<>("total"));
                    for (Product product : list) {
                        NumberFormat numberFormat = NumberFormat.getNumberInstance();
                        String PieceFormat = numberFormat.format(Integer.parseInt(product.getPrice()));
                        System.out.println(PieceFormat);
                        observableList.add(new ProductTable(product.getName(), PieceFormat, product.getTotal()));
                        count += Integer.parseInt(product.getTotal().replace(",",""));
                        totals.setText(""+count);
                    }
                    Table.setItems(observableList);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void Back(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("order.fxml"));
        Button button = (Button) event.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        Order order = fxmlLoader.getController();
        order.setUser(user);
        stage.setScene(scene);
    }

    public void setUser(String user) {
        this.user = user;
    }


    public void Remove(ActionEvent event) throws IOException {
        File file = new File("Customer_Cart.csv");
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter writer = new BufferedWriter(fileWriter);
        ProductTable tp = (ProductTable) Table.getSelectionModel().getSelectedItem();
        for (int i = 0 ; i < list.size(); i++){
            if (tp.getName().equals(list.get(i).getName())){
                count -= Integer.parseInt(tp.getTotal().replace(",",""));
                list.remove(i);
                i-=1;
            }
        }
        ObservableList<ProductTable> foodTables, SingleProduct;
        foodTables = Table.getItems();
        SingleProduct = Table.getSelectionModel().getSelectedItems();
        SingleProduct.forEach(foodTables::remove);
        for(Product product:list){
            writer.write(product.getUser() + "@" + product.getName() + "@" + product.getPrice() + "@" + product.getTotal() + "@" + product.getStatus());
            writer.newLine();
        }
        for(Product product:list1){
            writer.write(product.getUser() + "@" + product.getName() + "@" + product.getPrice() + "@" + product.getTotal() + "@" + product.getStatus());
            writer.newLine();
        }
        writer.close();
        totals.setText(""+count);
    }

    public void Confirm(ActionEvent event) throws SQLException, IOException {
        ConnectionUtil connectionClass = new ConnectionUtil();
        Connection connection = connectionClass.conDB();
        String sql1 = "SELECT * FROM `product`";
        Statement statement = connection.createStatement();
        ResultSet resultSet=statement.executeQuery(sql1);

        for(Product product:list){
            String sql="INSERT INTO orderlist (username,P_name,O_quantity,O_price,status) VALUES (?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, product.getUser());
            ps.setString(2, product.getName());
            ps.setInt(3, Integer.parseInt(product.getPrice().replace(",","")));
            ps.setInt(4, Integer.parseInt(product.getTotal().replace(",","")));
            ps.setString(5, product.getStatus());
            ps.executeUpdate();
        }
        while (resultSet.next()){
            for (Product product : list){
                if (product.getName().equals(resultSet.getString(1))){
                    i = resultSet.getInt(5) - Integer.parseInt(product.getPrice());
                    if (i >= 0){
                        String sql3 = "UPDATE product SET quantity = '"+i+"' WHERE P_name = '"+ product.getName() +"'";
                        Statement statement1 = connection.createStatement();
                        statement1.executeUpdate(sql3);
                    }
                }
            }
        }

        list.clear();
        File file = new File("Customer_Cart.csv");
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter writer = new BufferedWriter(fileWriter);
        for(Product product:list1){
            writer.write(product.getUser() + "@" + product.getName() + "@" + product.getPrice() + "@" + product.getTotal() + "@" + product.getStatus());
            writer.newLine();
        }
        writer.close();
        JOptionPane.showMessageDialog(this,"Confirmed", "Alert",JOptionPane.INFORMATION_MESSAGE);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("order.fxml"));
        Button button = (Button) event.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        Order order = fxmlLoader.getController();
        order.setUser(user);
        stage.setScene(scene);
    }
}