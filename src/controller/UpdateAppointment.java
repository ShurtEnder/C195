package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.DBAppointment;
import model.TimeFunctions;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;

import static DBA.JDBC.connection;

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
    private LocalDate localDate;
    private ObservableList typeList = FXCollections.observableArrayList("Planning Session","De-Briefing", "Meeting", "Break");
    private ObservableList contList = FXCollections.observableArrayList();
    private ObservableList timeList = FXCollections.observableArrayList();
    private String sqlQuery = "UPDATE client_schedule.appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";


    Stage stage;
    Parent scene;

    public void sendUpApp(DBAppointment appointment){
        ResultSet rs = null;
        upAppAppIDTxt.setText(String.valueOf(appointment.getAppID()));
        upAppCustIDTxt.setText(String.valueOf(appointment.getCustID()));
        upAppUserIDTxt.setText(String.valueOf(appointment.getUserID()));
        upAppTitleTxt.setText(String.valueOf(appointment.getTitle()));
        upAppDescTxt.setText(String.valueOf(appointment.getDesc()));
        upAppLocTxt.setText(String.valueOf(appointment.getLoc()));
        upAppTypeCombo.setValue(String.valueOf(appointment.getType()));
        /*
        upAppContNameCombo.setValue(String.valueOf(appointment.getContact()));

         */
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

        localDate = LocalDate.now();
        LocalDateTime timeDateLoc = LocalDateTime.now();
        LocalDateTime timeDateEST = TimeFunctions.getLoctoEST(timeDateLoc).toLocalDateTime();
        LocalTime firstTime = LocalTime.parse("08:00");
        LocalDateTime firstDT = LocalDateTime.of(localDate, firstTime);
        LocalTime firstTime1 = TimeFunctions.getESTtoLoc(firstDT).toLocalTime();
        System.out.println(firstTime1);
        LocalTime lastTime = firstTime1.plusHours(14);
        timeList.add(firstTime1);
        while(!(firstTime1 == lastTime)){
            System.out.println(firstTime1);
            firstTime1 = firstTime1.plusMinutes(30);
            timeList.add(firstTime1);
        }

        upAppStartCombo.setItems(timeList);
        upAppEndCombo.setItems(timeList);
        upAppTypeCombo.setItems(typeList);

        try {
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT Contact_ID,Contact_Name FROM client_schedule.contacts");
            while(rs.next()){
                contList.add(rs.getString(2));
                if(rs.getInt(1) == appointment.getContact()){
                    upAppContNameCombo.setValue(rs.getString(2));
                }
            }
            upAppContNameCombo.setItems(contList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
        try{
            PreparedStatement psti = connection.prepareStatement(sqlQuery);
            String strQuery = "SELECT Contact_ID FROM client_schedule.contacts WHERE Contact_Name = ?";
            PreparedStatement psti2 = connection.prepareStatement(strQuery);
            ResultSet rs = null;

            String custID = upAppCustIDTxt.getText();
            String userID = upAppUserIDTxt.getText();
            String title = upAppTitleTxt.getText();
            String desc = upAppDescTxt.getText();
            String loc = upAppLocTxt.getText();
            String type = String.valueOf(upAppTypeCombo.getSelectionModel().getSelectedItem());
            String cont = String.valueOf(upAppContNameCombo.getSelectionModel().getSelectedItem());
            String appID = upAppAppIDTxt.getText();

            LocalTime startT = LocalTime.parse(String.valueOf(upAppStartCombo.getSelectionModel().getSelectedItem()));
            LocalDate startD = LocalDate.parse(String.valueOf(upAppStartDateCal.getValue()));
            LocalDateTime startComb = TimeFunctions.getLoctoUTC(TimeFunctions.combDT(startD, startT)).toLocalDateTime();
            Timestamp finalStartTime = Timestamp.valueOf(startComb);

            LocalTime endT = LocalTime.parse(String.valueOf(upAppEndCombo.getSelectionModel().getSelectedItem()));
            LocalDate endD = LocalDate.parse(String.valueOf(upAppEndDateCal.getValue()));
            LocalDateTime endComb = TimeFunctions.getLoctoUTC(TimeFunctions.combDT(endD, endT)).toLocalDateTime();
            Timestamp finalEndTime = Timestamp.valueOf(endComb);


            psti2.setString(1, String.valueOf(cont));
            rs = psti2.executeQuery();
            int contID = 0;
            while (rs.next()){
                contID = rs.getInt(1);
            }

            /*
            INSERT INTO client_schedule.appointments(Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES (?,?,?,?,?,?,?,?,?)
             */
            psti.setString(1, title);
            psti.setString(2, desc);
            psti.setString(3, loc);
            psti.setString(4, type);
            psti.setString(5, String.valueOf(finalStartTime));
            psti.setString(6, String.valueOf(finalEndTime));
            psti.setString(7, custID);
            psti.setString(8, userID);
            psti.setString(9, String.valueOf(contID));
            psti.setString(10,appID);
            psti.execute();

            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/AppointmentMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException Ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("Enter valid values in all text fields!");
            alert.showAndWait();
        }

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
