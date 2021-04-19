package shop;

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
import javafx.stage.Stage;
import shop.Class.ProductTable;
import utils.ConnectionUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ResourceBundle;

public class Order_list implements Initializable {

    @FXML
    TableColumn<ProductTable,String> User,name,quantity,price,status,O_num;
    @FXML
    TableView Table;

    private ObservableList<ProductTable> observableList = FXCollections.observableArrayList();

    ConnectionUtil connectionClass = new ConnectionUtil();
    Connection connection = connectionClass.conDB();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            ObservableList<ProductTable> observableList = UpdateTable();
            Table.setItems(observableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<ProductTable> UpdateTable() throws SQLException {
        String sql = "SELECT * FROM `orderlist`";
        Statement statement = connection.createStatement();
        ResultSet resultSet=statement.executeQuery(sql);
        User.setCellValueFactory(new PropertyValueFactory<>("user"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        price.setCellValueFactory(new PropertyValueFactory<>("total"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        O_num.setCellValueFactory(new PropertyValueFactory<>("num"));
        while (resultSet.next()){
            NumberFormat numberFormat = NumberFormat.getNumberInstance();
            String QuantityFormat = numberFormat.format(resultSet.getInt(3));
            String PriceFormat = numberFormat.format(resultSet.getInt(4));
            observableList.add(new ProductTable(resultSet.getString(1),resultSet.getString(2),QuantityFormat,PriceFormat,resultSet.getString(5),resultSet.getInt(6)));
        }
        return observableList;
    }

    public void Update(ActionEvent event) throws SQLException {
        String sql3 = "UPDATE orderlist SET status = 'Done'";
        Statement statement1 = connection.createStatement();
        statement1.executeUpdate(sql3);
        observableList.clear();
        UpdateTable();
        Table.setItems(observableList);
    }

    public void Back(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AdminMenu.fxml"));
        Button button = (Button) event.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
    }
}
