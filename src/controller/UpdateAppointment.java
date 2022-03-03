package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class UpdateAppointment {
    public TextField upAppAppIDTxt;
    public TextField upAppCustIDTxt;
    public TextField upAppUserIDTxt;
    public TextField upAppTitleTxt;
    public TextArea upAppDescTxt;
    public TextField upAppLocTxt;
    public TextField upAppTypeTxt;
    public ComboBox upAppContNameCombo;
    public ComboBox upAppStartCombo;
    public DatePicker upAppStartDateCal;
    public ComboBox upAppEndCombo;
    public DatePicker upAppEndDateCal;

    Stage stage;
    Parent scene;

    public void onActionUpAppContNameCombo(ActionEvent actionEvent) {
    }

    public void onActionUpAppStartCombo(ActionEvent actionEvent) {
    }

    public void onActionUpAppStartDateCal(ActionEvent actionEvent) {
    }

    public void onActionUpAppEndCombo(ActionEvent actionEvent) {
    }

    public void onActionUpAppEndDateCal(ActionEvent actionEvent) {
    }

    public void onActionUpAppSaveBttn(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void onActionUpCustCancelBttn(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
