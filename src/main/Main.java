package main;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import DBA.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static java.util.ResourceBundle.getBundle;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/LoginPage.fxml")));
        primaryStage.setTitle("First View");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();


    }

    @Override
    public void init(){


    }

    public static void main(String[] args){
        JDBC.openConnection();


        launch(args);

    }

}
