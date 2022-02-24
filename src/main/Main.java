package main;

import java.util.ArrayList;
import java.util.Objects;

import DBA.JDBC;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import javax.xml.crypto.Data;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        /*
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainMenu.fxml")));
        primaryStage.setTitle("First View");
        primaryStage.setScene(new Scene(root, 1200, 400));
        primaryStage.show();
         */

    }

    @Override
    public void init(){


    }

    public static void main(String[] args){
        JDBC.openConnection();

        launch(args);

    }

}
