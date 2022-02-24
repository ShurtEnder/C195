package main;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

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
        primaryStage.setScene(new Scene(root, 1200, 400));
        primaryStage.show();


    }

    @Override
    public void init(){


    }

    public static void main(String[] args){
        /*JDBC.openConnection();*/
        ResourceBundle rb = getBundle("test/Nat", Locale.getDefault());

        if(Locale.getDefault().getLanguage().equals("en")
                || Locale.getDefault().getLanguage().equals("es")
                || Locale.getDefault().getLanguage().equals("fr"))
            System.out.println(rb.getString("hello") + " " + rb.getString("world"));

        launch(args);

    }

}
