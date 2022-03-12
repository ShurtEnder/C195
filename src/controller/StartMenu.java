package controller;

import Interface.combString;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.IOClass;
import model.TimeFunctions;

import java.io.IOException;
import java.time.LocalDateTime;


public class StartMenu {


    Stage stage;
    Parent scene;

    public void onActionCustMenuBttn(ActionEvent actionEvent) throws IOException {
        combString stringComb = s -> {
            s = TimeFunctions.getLoctoUTC(LocalDateTime.now()) + " User " + LoginPage.userID + ": " + s;
            return s;
        };
        IOClass.insertLog(stringComb.cString("Customer menu button hit!"));
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomerMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void onActionAppMenuBttn(ActionEvent actionEvent) throws IOException {
        combString stringComb = s -> {
            s = TimeFunctions.getLoctoUTC(LocalDateTime.now()) + " User " + LoginPage.userID + ": " + s;
            return s;
        };
        IOClass.insertLog(stringComb.cString("Appointment menu button hit!"));
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AppointmentMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void onActionReportsBttn(ActionEvent actionEvent) throws IOException {
        combString stringComb = s -> {
            s = TimeFunctions.getLoctoUTC(LocalDateTime.now()) + " User " + LoginPage.userID + ": " + s;
            return s;
        };
        IOClass.insertLog(stringComb.cString("Report menu button hit!"));
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ReportMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void onActionLogOutBttn(ActionEvent actionEvent) throws IOException {
        combString stringComb = s -> {
            s = TimeFunctions.getLoctoUTC(LocalDateTime.now()) + " User " + LoginPage.userID + ": " + s;
            return s;
        };
        IOClass.insertLog(stringComb.cString("Logout button hit!"));
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
