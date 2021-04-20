package shop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import shop.Class.BucKet;


public class Main extends Application {

    private static BucKet bucKet;

    @Override
    public void start(Stage primaryStage) throws Exception{
        bucKet = new BucKet();
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("Logistics");
        primaryStage.setScene(new Scene(root, 500, 600));
        primaryStage.setResizable(false);
        primaryStage.show();
    }




    public static void main(String[] args) {
        launch(args);
    }
}
