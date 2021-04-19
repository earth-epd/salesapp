package shop;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.ConnectionUtil;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Register extends Component {

    public TextField Phonenumber;

    public TextField Creditnumber;

    private int Count = 0;

    public TextField Username;

    public PasswordField Password;

    public TextField Address;


    public Text Idiot;

    public void Back(ActionEvent event) throws Exception{
        FXMLLoader one = new FXMLLoader(getClass().getResource("login.fxml"));
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        Scene ones = new Scene(one.load());
        stage.setScene(ones);
    }


    public void ReGis(ActionEvent event) throws SQLException,Exception {
        ConnectionUtil connectionClass = new ConnectionUtil();
        Connection connection = connectionClass.conDB();

        if(Username.getText().equals("") || Password.getText().equals("") || Address.getText().equals("")){
            Idiot.setText("Please fill everything");
            Idiot.setVisible(true);
        }
        else if ((!Phonenumber.getText().matches("[0-9]*")) || (Phonenumber.getText().length()!=10)){
                Idiot.setText("is not number");
                Idiot.setVisible(true);
        }


        else {
            String sql1 = "SELECT * FROM `user`";
            Statement statement1 = connection.createStatement();
            ResultSet resultSet=statement1.executeQuery(sql1);

            while (resultSet.next()){
                if(resultSet.getString(1).equals(Username.getText())){
                    Count+=1;
                }
            }
            if(Count==0){
                String sql2="INSERT INTO user (username,password,address,phonenumber,creditnumber) VALUES ('"+Username.getText()+"','"+Password.getText()+"','"+Address.getText()+"','"+Phonenumber.getText()+"','"+Creditnumber.getText()+"')";
                Statement statement2 = connection.createStatement();
                statement2.executeUpdate(sql2);
                JOptionPane.showMessageDialog(this,"success", "Alert", JOptionPane.INFORMATION_MESSAGE);
                FXMLLoader one = new FXMLLoader(getClass().getResource("login.fxml"));
                Button b = (Button) event.getSource();
                Stage stage = (Stage) b.getScene().getWindow();
                Scene ones = new Scene(one.load());
                stage.setScene(ones);
            }
            else{
                Idiot.setText("this Username is taken");
                Idiot.setVisible(true);
                Count=0;
            }
        }
    }
}
