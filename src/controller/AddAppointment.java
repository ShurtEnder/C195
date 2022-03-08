package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.DBAppointment;
import model.DBCustomer;
import model.TimeFunctions;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.*;
import java.time.temporal.ChronoField;
import java.util.ResourceBundle;

import static DBA.JDBC.connection;

public class AddAppointment implements Initializable {
    public TextField addAppAppIDTxt;
    public TextField addAppCustIDTxt;
    public TextField addAppUserIDTxt;
    public TextField addAppTitleTxt;
    public TextArea addAppDescTxt;
    public TextField addAppLocTxt;
    public ComboBox addAppContNameCombo;
    public ComboBox addAppStartCombo;
    public DatePicker addAppStartDateCal;
    public ComboBox addAppEndCombo;
    public DatePicker addAppEndDateCal;
    public ComboBox addAppTypeCombo;
    private ObservableList typeList = FXCollections.observableArrayList("Planning Session","De-Briefing", "Meeting", "Break");
    private ObservableList contList = FXCollections.observableArrayList();
    private ObservableList timeList = FXCollections.observableArrayList();
    private LocalDate localDate;
    private String sqlQuery = "INSERT INTO client_schedule.appointments(Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES (?,?,?,?,?,?,?,?,?)";

    Stage stage;
    Parent scene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void sendAddApp(DBCustomer customer){
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

        addAppStartCombo.setItems(timeList);
        addAppEndCombo.setItems(timeList);
        System.out.println(timeList);


        int custID = customer.getCustID();
        /*
        String custLocFull = customer.getCustAdd()
         */
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
        try{
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
            Timestamp finalStartTime = Timestamp.valueOf(startComb);

            LocalTime endT = LocalTime.parse(String.valueOf(addAppEndCombo.getSelectionModel().getSelectedItem()));
            LocalDate endD = LocalDate.parse(String.valueOf(addAppEndDateCal.getValue()));
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
            psti.execute();






        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException Ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("Enter valid values in all text fields!");
            alert.showAndWait();
        }

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

    public void onActionAddAppTypeCombo(ActionEvent actionEvent) {
    }


}
