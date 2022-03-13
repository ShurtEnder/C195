package main;

import java.util.*;

import DBA.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class for the program.
 * @author Rene Gomez Student ID: 001467443
 */

public class Main extends Application {

    /**
     * Primary stage.
     * This sets the stage for the program.
     * @param primaryStage For the stage
     * @throws Exception For error catching
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/LoginPage.fxml")));
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    /**
     * Opens Connected to DB
     * Opens the connection to the database for the program
     * @param args Launches program
     */
    public static void main(String[] args){
        JDBC.openConnection();
        launch(args);

    }
}
