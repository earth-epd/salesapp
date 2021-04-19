package shop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class Login {
    public Text Idiot;
    private int Count = 0;

    @FXML
    TextField txtUsername;

    @FXML
    PasswordField txtPassword;

    @FXML
    Text Regis;

    private String name;

    public void ReGis(javafx.scene.input.MouseEvent mouseEvent) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Register.fxml"));
        Text Text = (javafx.scene.text.Text) mouseEvent.getSource();
        Stage stage = (Stage) Regis.getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
    }

    public void LoGin(ActionEvent event) throws Exception {

        ConnectionUtil connectionClass = new ConnectionUtil();
        Connection connection = connectionClass.conDB();

        if((txtUsername.getText().equals("") || txtPassword.getText().equals(""))) {
            Idiot.setText("Missing Username or Password");
            Idiot.setVisible(true);
        }
        else if(txtUsername.getText().equals("admin") && txtPassword.getText().equals("admin")){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AdminMenu.fxml"));
            Button button = (Button) event.getSource();
            Stage stage = (Stage) button.getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
        }
        else{
            String sql = "SELECT * FROM `user`";
            Statement statement = connection.createStatement();
            ResultSet resultSet=statement.executeQuery(sql);
            while (resultSet.next()){
                if(resultSet.getString(1).equals(txtUsername.getText())&&resultSet.getString(2).equals(txtPassword.getText())){
                    Count+=1;
                    name = resultSet.getString(1);
                }
            }
            if(Count>0){
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("order.fxml"));
                Button button = (Button) event.getSource();
                Stage stage = (Stage) button.getScene().getWindow();
                Scene scene = new Scene(fxmlLoader.load());
                Order order = fxmlLoader.getController();
                order.setUser(name);
                stage.setScene(scene);
            }
            else{
                Idiot.setText("Wrong Username or Password");
                Idiot.setVisible(true);
            }

        }
    }

}
