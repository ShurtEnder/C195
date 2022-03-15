package controller;

import Interface.combString;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import model.IOClass;
import model.TimeFunctions;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Start Menu Class.
 * @author Rene Gomez Student ID: 001467443
 */
public class StartMenu {
    Stage stage;
    Parent scene;

    /**
     * Customer Menu Button.
     * Changes stage to the Customer Menu FXML.
     * @param actionEvent on Customer Menu click
     * @throws IOException
     * <p><b>
     *     LAMBDA EXPRESSION
     * </b></p>
     * <p><b>
     *     This expression combines a string and inserts it into the log
     * </b></p>
     */
    public void onActionCustMenuBttn(ActionEvent actionEvent) throws IOException {
        combString stringComb = s -> {
            s = TimeFunctions.getLoctoUTC(LocalDateTime.now()) + " User " + LoginPage.userNameUsed + ": " + s;
            return s;
        };
        IOClass.insertLog(stringComb.cString("Customer menu button hit!"));
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomerMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Appointment Menu Button.
     * Changes stage to the Appointment Menu FXML.
     * @param actionEvent on Appointment Menu Click
     * @throws IOException
     * <p><b>
     *     LAMBDA EXPRESSION
     * </b></p>
     * <p><b>
     *     This expression combines a string and inserts it into the log
     * </b></p>
     */
    public void onActionAppMenuBttn(ActionEvent actionEvent) throws IOException {
        combString stringComb = s -> {
            s = TimeFunctions.getLoctoUTC(LocalDateTime.now()) + " User " + LoginPage.userNameUsed + ": " + s;
            return s;
        };
        IOClass.insertLog(stringComb.cString("Appointment menu button hit!"));
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AppointmentMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Reports Button.
     * Changes stage to the Report Menu FXML.
     * @param actionEvent on Reports click
     * @throws IOException
     * <p><b>
     *     LAMBDA EXPRESSION
     * </b></p>
     * <p><b>
     *     This expression combines a string and inserts it into the log
     * </b></p>
     */
    public void onActionReportsBttn(ActionEvent actionEvent) throws IOException {
        combString stringComb = s -> {
            s = TimeFunctions.getLoctoUTC(LocalDateTime.now()) + " User " + LoginPage.userNameUsed + ": " + s;
            return s;
        };
        IOClass.insertLog(stringComb.cString("Report menu button hit!"));
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ReportMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Log out Button.
     * Changes stage to the Login Page FXML, first asking if the user confirms they want to log out.
     * @param actionEvent on LogOutButton click
     * <p><b>
     *     LAMBDA EXPRESSION
     * </b></p>
     * <p><b>
     *     This expression combines a string and inserts it into the log
     * </b></p>
     */
    public void onActionLogOutBttn(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to logout?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            combString stringComb = s -> {
                s = TimeFunctions.getLoctoUTC(LocalDateTime.now()) + " User " + LoginPage.userNameUsed + ": " + s;
                return s;
            };
            IOClass.insertLog(stringComb.cString("Logout button hit!"));
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }
}
