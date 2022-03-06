package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.DBAppointment;
import model.TimeFunctions;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;

public class UpdateAppointment {
    public TextField upAppAppIDTxt;
    public TextField upAppCustIDTxt;
    public TextField upAppUserIDTxt;
    public TextField upAppTitleTxt;
    public TextArea upAppDescTxt;
    public TextField upAppLocTxt;
    public ComboBox upAppContNameCombo;
    public ComboBox upAppStartCombo;
    public DatePicker upAppStartDateCal;
    public ComboBox upAppEndCombo;
    public DatePicker upAppEndDateCal;
    public ComboBox upAppTypeCombo;

    Stage stage;
    Parent scene;

    public void sendUpApp(DBAppointment appointment){
        upAppAppIDTxt.setText(String.valueOf(appointment.getAppID()));
        upAppCustIDTxt.setText(String.valueOf(appointment.getCustID()));
        upAppUserIDTxt.setText(String.valueOf(appointment.getUserID()));
        upAppTitleTxt.setText(String.valueOf(appointment.getTitle()));
        upAppDescTxt.setText(String.valueOf(appointment.getDesc()));
        upAppLocTxt.setText(String.valueOf(appointment.getLoc()));
        upAppTypeCombo.setValue(String.valueOf(appointment.getType()));
        upAppContNameCombo.setValue(String.valueOf(appointment.getContact()));
        ZonedDateTime startLDTLoc = TimeFunctions.getUTCtoLoc(appointment.getStart());
        LocalTime startTimeloc = startLDTLoc.toLocalTime();
        LocalDate startDateLoc = startLDTLoc.toLocalDate();
        ZonedDateTime endLDTLoc = TimeFunctions.getUTCtoLoc(appointment.getEnd());
        LocalTime endTimeloc = endLDTLoc.toLocalTime();
        LocalDate endDateLoc = endLDTLoc.toLocalDate();



        upAppStartCombo.setValue(startTimeloc);
        upAppStartDateCal.setValue(startDateLoc);
        upAppEndCombo.setValue(endTimeloc);
        upAppEndDateCal.setValue(endDateLoc);

    }

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
        scene = FXMLLoader.load(getClass().getResource("/view/AppointmentMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void onActionUpCustCancelBttn(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AppointmentMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void onActionUpAppTypeCombo(ActionEvent actionEvent) {
    }
}
