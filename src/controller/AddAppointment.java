package controller;

import Interface.Counter;
import Interface.combString;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static DBA.JDBC.connection;

public class AddAppointment{
    public TextField addAppAppIDTxt,addAppCustIDTxt,addAppUserIDTxt,addAppTitleTxt,addAppLocTxt;
    public TextArea addAppDescTxt;
    public ComboBox addAppContNameCombo,addAppStartCombo,addAppEndCombo,addAppTypeCombo;
    public DatePicker addAppStartDateCal,addAppEndDateCal;
    private ObservableList typeList = FXCollections.observableArrayList("Planning Session","De-Briefing", "Meeting", "Break");
    private ObservableList contList = FXCollections.observableArrayList();
    private ObservableList timeList = FXCollections.observableArrayList();
    private ArrayList overlapList = new ArrayList<>();
    private LocalDate localDate;
    private String sqlQuery = "INSERT INTO client_schedule.appointments(Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES (?,?,?,?,?,?,?,?,?)";
    private boolean overLap = false;
    private boolean busHours = true;

    Stage stage;
    Parent scene;

    public void sendAddApp(DBCustomer customer){
        localDate = LocalDate.now();
        LocalDateTime timeDateLoc = LocalDateTime.now();
        LocalTime firstTime = LocalTime.parse("08:00");
        LocalDateTime firstDT = LocalDateTime.of(localDate, firstTime);
        LocalTime firstTime1 = TimeFunctions.getESTtoLoc(firstDT).toLocalTime();
        LocalTime lastTime = firstTime1.plusHours(14);
        timeList.add(firstTime1);
        while(!(firstTime1 == lastTime)){
            firstTime1 = firstTime1.plusMinutes(30);
            timeList.add(firstTime1);
        }

        addAppStartCombo.setItems(timeList);
        addAppEndCombo.setItems(timeList);
        int custID = customer.getCustID();
        addAppCustIDTxt.setText(String.valueOf(custID));
        addAppUserIDTxt.setText(String.valueOf(LoginPage.userID));
        addAppLocTxt.setText(String.valueOf(customer.getCustAdd()));
        addAppTypeCombo.setItems(typeList);
        ResultSet rs = null;
        try {
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT Contact_Name FROM client_schedule.contacts");
            while(rs.next()){
                contList.add(rs.getString(1));
            }
            addAppContNameCombo.setItems(contList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onActionAddAppSaveBttn(ActionEvent actionEvent) {
        //Lambda Expression
        Counter incAddCounter = c -> {
            c++;
            DataProvider.setNewCounter(c);
            return c;
        };
        try {
            overLap = false;
            overlapList.clear();
            PreparedStatement psti = connection.prepareStatement(sqlQuery);
            String strQuery = "SELECT Contact_ID FROM client_schedule.contacts WHERE Contact_Name = ?";
            PreparedStatement psti2 = connection.prepareStatement(strQuery);
            ResultSet rs = null;

            String custID = addAppCustIDTxt.getText();
            String userID = addAppUserIDTxt.getText();
            String title = addAppTitleTxt.getText();
            String desc = addAppDescTxt.getText();
            String loc = addAppLocTxt.getText();
            String type = String.valueOf(addAppTypeCombo.getSelectionModel().getSelectedItem());
            String cont = String.valueOf(addAppContNameCombo.getSelectionModel().getSelectedItem());
            LocalTime startT = LocalTime.parse(String.valueOf(addAppStartCombo.getSelectionModel().getSelectedItem()));
            LocalDate startD = LocalDate.parse(String.valueOf(addAppStartDateCal.getValue()));
            LocalDateTime startComb = TimeFunctions.getLoctoUTC(TimeFunctions.combDT(startD, startT)).toLocalDateTime();
            startT = startComb.toLocalTime();
            startD = startComb.toLocalDate();
            Timestamp finalStartTime = Timestamp.valueOf(startComb);
            LocalTime endT = LocalTime.parse(String.valueOf(addAppEndCombo.getSelectionModel().getSelectedItem()));
            LocalDate endD = LocalDate.parse(String.valueOf(addAppEndDateCal.getValue()));
            LocalDateTime endComb = TimeFunctions.getLoctoUTC(TimeFunctions.combDT(endD, endT)).toLocalDateTime();
            endT = endComb.toLocalTime();
            endD = endComb.toLocalDate();
            Timestamp finalEndTime = Timestamp.valueOf(endComb);
            int busStartind = timeList.indexOf(addAppStartCombo.getSelectionModel().getSelectedItem());
            int busEndind = timeList.indexOf(addAppEndCombo.getSelectionModel().getSelectedItem());

            if (busEndind < busStartind) {
                busHours = false;
            } else {
                busHours = true;
            }
            psti2.setString(1, String.valueOf(cont));
            rs = psti2.executeQuery();
            int contID = 0;
            while (rs.next()) {
                contID = rs.getInt(1);
            }
            if(title.isEmpty() || desc.isEmpty() || loc.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setContentText("Text fields are empty!");
                alert.showAndWait();
            }
            else if (!(busHours)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setContentText("Selected date/times are not within business hours!");
                alert.showAndWait();
            } else if (startT.equals(endT)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setContentText("Start time and end time are the same!");
                alert.showAndWait();
            } else if (startComb.isAfter(endComb)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setContentText("End Date/Time is before start date/time!");
                alert.showAndWait();
            } else if (startT.isAfter(endT) && !(startComb.isBefore(endComb))) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setContentText("End time starts before start time!");
                alert.showAndWait();
            } else if (startD.isAfter(endD)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setContentText("End date starts before start date!");
                alert.showAndWait();
            } else if (startD.isBefore(LocalDate.now()) || endD.isBefore(LocalDate.now())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setContentText("Start/End Date before today!");
                alert.showAndWait();
            } else if ((startT.isBefore(LocalTime.now()) || endT.isBefore(LocalTime.now())) && !(startD.isAfter(LocalDate.now()) || endD.isAfter(LocalDate.now()))) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setContentText("Start/End Time before current time!");
                alert.showAndWait();
            } else {
                for (DBAppointment app : DataProvider.getAllAppointments()) {
                    LocalDate checkDateStart = app.getStart().toLocalDate();
                    LocalTime checkTimeStart = app.getStart().toLocalTime();
                    LocalDate checkDateEnd = app.getEnd().toLocalDate();
                    LocalTime checkTimeEnd = app.getEnd().toLocalTime();
                    if (startD.equals(checkDateStart)) {
                        if ((startT.isAfter(checkTimeStart) || startT.equals(checkTimeStart)) && startT.isBefore(checkTimeEnd)) {
                            overLap = true;
                        } else if (endT.isAfter(checkTimeStart) && (endT.isBefore(checkTimeEnd) || endT.equals(checkTimeEnd))) {
                            overLap = true;
                        } else if ((startT.isBefore(checkTimeStart) || startT.equals(checkTimeStart)) && (endT.isAfter(checkTimeEnd) || endT.equals(checkTimeEnd))) {
                            overLap = true;
                        } else if (overLap) {
                            overlapList.add(app.getAppID());
                        }
                    }
                }
                if (overLap) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning!!");
                    alert.setContentText("There is appoint overlaps with appointment(s) " + overlapList);
                    alert.showAndWait();
                } else {
                    psti.setString(1, title);
                    psti.setString(2, desc);
                    psti.setString(3, loc);
                    psti.setString(4, type);
                    psti.setString(5, String.valueOf(finalStartTime));
                    psti.setString(6, String.valueOf(finalEndTime));
                    psti.setString(7, custID);
                    psti.setString(8, userID);
                    psti.setString(9, String.valueOf(contID));
                    psti.execute();
                    //Lambda Expression
                    combString stringComb = s -> {
                        s = TimeFunctions.getLoctoUTC(LocalDateTime.now()) + " User " + LoginPage.userID + ": " + s;
                        return s;
                    };
                    IOClass.insertLog(stringComb.cString("New appointment has been added!"));
                    //Lambda Expression
                    incAddCounter.addCounter(DataProvider.getNewCounter());

                    stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/CustomerMenu.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("Enter/select valid values in all fields!");
            alert.showAndWait();
        } catch (NumberFormatException Ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("Enter valid values in all text fields!");
            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DateTimeParseException et){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("Select valid values in all time/date sections!");
            alert.showAndWait();
        }
    }

    public void onActionAddCustCancelBttn(ActionEvent actionEvent) throws IOException {
        //Lambda Expression
        combString stringComb = s -> {
            s = TimeFunctions.getLoctoUTC(LocalDateTime.now()) + " User " + LoginPage.userID + ": " + s;
            return s;
        };
        IOClass.insertLog(stringComb.cString("Cancel button hit, going back to Customer Menu"));
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomerMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

}
