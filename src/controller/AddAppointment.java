package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class AddAppointment {
    public TextField addAppAppIDTxt;
    public TextField addAppCustIDTxt;
    public TextField addAppUserIDTxt;
    public TextField addAppTitleTxt;
    public TextArea addAppDescTxt;
    public TextField addAppLocTxt;
    public TextField addAppTypeTxt;
    public ComboBox addAppContNameCombo;
    public ComboBox addAppStartCombo;
    public DatePicker addAppStartDateCal;
    public ComboBox addAppEndCombo;
    public DatePicker addAppEndDateCal;

    Stage stage;
    Parent scene;

    public void onActionAddAppContNameCombo(ActionEvent actionEvent) {
    }

    public void onActionAddAppStartCombo(ActionEvent actionEvent) {
    }

    public void onActionAddAppStartDateCal(ActionEvent actionEvent) {
    }

    public void onActionAddAppEndCombo(ActionEvent actionEvent) {
    }

    public void onActionAddAppEndDateCal(ActionEvent actionEvent) {
    }

    public void onActionAddAppSaveBttn(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomerMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void onActionAddCustCancelBttn(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomerMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
