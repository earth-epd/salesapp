package shop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import shop.Class.ProductTable;
import utils.ConnectionUtil;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ResourceBundle;

public class Update_Storage extends Component implements Initializable {

    public Button Back;

    public Button Add;

    public TextField Name;

    public TextField Number;

    public TextField Detail;

    public TextField Price;

    public TextField Remain;

    public int Count = 0;

    public Text Alert;

    @FXML
    TableView Table;
    @FXML
    TableColumn<ProductTable, String> Collum_name, Collum_Number, Collum_Detail, Collum_Price, Collum_Remain;

    private ObservableList<ProductTable> observableList = FXCollections.observableArrayList();


    ConnectionUtil connectionClass = new ConnectionUtil();
    Connection connection = connectionClass.conDB();


    public ObservableList<ProductTable> Update_Storage() throws SQLException {
        String sql = "SELECT * FROM `product`";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        Collum_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        Collum_Number.setCellValueFactory(new PropertyValueFactory<>("number"));
        Collum_Detail.setCellValueFactory(new PropertyValueFactory<>("detail"));
        Collum_Price.setCellValueFactory(new PropertyValueFactory<>("price"));
        Collum_Remain.setCellValueFactory(new PropertyValueFactory<>("remain"));
        while (resultSet.next()) {
            NumberFormat numberFormat = NumberFormat.getNumberInstance();
            String PriceFormat = numberFormat.format(resultSet.getInt(4));
            String RemainFormat = numberFormat.format(resultSet.getInt(5));
            observableList.add(new ProductTable(resultSet.getString(1), resultSet.getInt(2), resultSet.getString(3), PriceFormat, RemainFormat));
        }
        return observableList;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            ObservableList<ProductTable> observableList = Update_Storage();
            Table.setItems(observableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void BackToAdmin(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AdminMenu.fxml"));
        Button button = (Button) event.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
    }

    public void AddProduct(ActionEvent event) throws SQLException {
        String sql = "SELECT * FROM `product`";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        if (Name.getText().equals("") || Number.getText().equals("") || Detail.getText().equals("") || Price.getText().equals("") || Remain.getText().equals("")) {
            Alert.setText("Please fill Everything");
            Alert.setVisible(true);
        }
        else if (Double.parseDouble(Price.getText()) < 0 || Double.parseDouble(Remain.getText()) < 0 || Double.parseDouble(Remain.getText()) % 1 != 0 || Double.parseDouble(Price.getText()) % 1 != 0) {
            Alert.setText("ข้อมูลผิดผลาด");
            Alert.setVisible(true);
            Count = 2;
        }
        else if (Price.getText().matches("\\d{6,50}") || Remain.getText().matches("\\d{5,50}")){
            Alert.setText("Too much price or amount of product");
            Alert.setVisible(true);
            Count = 2;
        }
        else if (Double.parseDouble(Price.getText()) > 99999 || Double.parseDouble(Remain.getText()) > 5000) {
            Alert.setText("ใส่จำนวนมากเกินไป");
            Alert.setVisible(true);
            Count = 2;
        }
        else {
            while (resultSet.next()) {
                if (resultSet.getString(2).equals(Number.getText()) && !resultSet.getString(1).equals(Name.getText())) {
                    Count = 1;
                } else if (resultSet.getString(1).equals(Name.getText()) && resultSet.getString(2).equals(Number.getText()) ) {
                    Count = 2;
                    String sql3 = "UPDATE product SET quantity = '" + Remain.getText() + "', price = '" + Price.getText() + "' WHERE number = '" + Number.getText() + "'";
                    Statement statement1 = connection.createStatement();
                    statement1.executeUpdate(sql3);
                    observableList.clear();
                    Update_Storage();
                    Table.setItems(observableList);
                    Alert.setVisible(false);
                }

            }
        }
        if (Count == 0) {
            String sql2 = "INSERT INTO product (P_name,number,detail,price,quantity) VALUES ('" + Name.getText() + "','" + Number.getText() + "','" + Detail.getText() + "','" + Price.getText() + "','" + Remain.getText() + "')";
            Statement statement2 = connection.createStatement();
            statement2.executeUpdate(sql2);
            observableList.add(new ProductTable(Name.getText(), Integer.parseInt(Number.getText()), Detail.getText(), (Price.getText()), Remain.getText()));
            Table.setItems(observableList);
        }
        else if (Name.getText().equals("") || Number.getText().equals("") || Detail.getText().equals("") || Price.getText().equals("") || Remain.getText().equals("")) {
            Alert.setText("Please fill Everythings");
            Alert.setVisible(true);
        }
        else if (Count == 1) {
            Alert.setText("ID ซ้ำกรุณากรอกใหม่");
            Alert.setVisible(true);
        }
        Count = 0;
        Name.clear();
        Number.clear();
        Detail.clear();
        Price.clear();
        Remain.clear();
    }

    public void TableToText(MouseEvent mouseEvent) {
        ProductTable tp = (ProductTable) Table.getSelectionModel().getSelectedItem();
        Name.setText(tp.getName());
        Number.setText(tp.getNumber());
        Detail.setText(tp.getDetail());
        Price.setText(tp.getPrice());
        Remain.setText(tp.getRemain());
    }
}
