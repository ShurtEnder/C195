package controller;

import Interface.combString;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.DBAppointment;
import model.DataProvider;
import model.IOClass;
import model.TimeFunctions;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import static DBA.JDBC.connection;

public class ReportMenu implements Initializable {
    public ComboBox MonthComboLbl, TypeComboLbl, contactComboLbl;
    public Label reportOneLbl,newCACounter,upCACounter;
    public TableColumn appIDCol, descCol,startCol,endCol,custIDCol;
    public TableView contactTableView;
    private ObservableList monthList = FXCollections.observableArrayList("Jan", "Feb","Mar", "Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec");
    private ObservableList typeListList = FXCollections.observableArrayList("Planning Session","De-Briefing", "Meeting", "Break");
    private ObservableList contList = FXCollections.observableArrayList();
    private ObservableList<DBAppointment> contTableList = FXCollections.observableArrayList();
    private String sqlQuery = "SELECT Start FROM client_schedule.appointments WHERE MONTH(Start) = ? AND Type = ?";
    private String sqlQuery1 = "SELECT Contact_ID FROM client_schedule.contacts WHERE Contact_Name = ?";

    Stage stage;
    Parent scene;


    public void OnActionMonthCombo(ActionEvent actionEvent) throws SQLException {
        PreparedStatement psti = connection.prepareStatement(sqlQuery);
        ResultSet rs = null;
        int counter = 0;
        if(TypeComboLbl.getValue() == null){
            return;
        }
        else {
            psti.setInt(1,monthList.indexOf(MonthComboLbl.getSelectionModel().getSelectedItem()) + 1);
            psti.setString(2, (String) TypeComboLbl.getSelectionModel().getSelectedItem());
            rs = psti.executeQuery();
            while (rs.next()){
                counter++;
            }
            reportOneLbl.setText(String.valueOf(counter));
        }
    }

    public void onActionTypeCombo(ActionEvent actionEvent) throws SQLException {
        PreparedStatement psti = connection.prepareStatement(sqlQuery);
        ResultSet rs = null;
        int counter = 0;
        if(MonthComboLbl.getValue() == null){
            return;
        }
        else {

            psti.setInt(1,monthList.indexOf(MonthComboLbl.getSelectionModel().getSelectedItem()) + 1);
            psti.setString(2, (String) TypeComboLbl.getSelectionModel().getSelectedItem());
            rs = psti.executeQuery();
            while (rs.next()){
                counter++;
            }
            reportOneLbl.setText(String.valueOf(counter));
        }
    }

    public void onActionContactCombo(ActionEvent actionEvent) throws SQLException {
        contTableList.clear();
        PreparedStatement psti2 = connection.prepareStatement(sqlQuery1);
        ResultSet rs = null;
        psti2.setString(1, String.valueOf(contactComboLbl.getSelectionModel().getSelectedItem()));
        int contID = 0;
        rs = psti2.executeQuery();
        while (rs.next()){
            contID = rs.getInt(1);
        }
        for (DBAppointment app : DataProvider.getAllAppointments()){
            if(app.getContact() == contID){
                contTableList.add(app);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contTableList.clear();
        ResultSet rs = null;
        contactTableView.setItems(contTableList);
        custIDCol.setCellValueFactory(new PropertyValueFactory<>("CustID"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("Desc"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("localStart"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("localEnd"));
        appIDCol.setCellValueFactory(new PropertyValueFactory<>("AppID"));

        newCACounter.setText(String.valueOf(DataProvider.getNewCounter()));
        upCACounter.setText(String.valueOf(DataProvider.getUpCounter()));
        MonthComboLbl.setItems(monthList);
        TypeComboLbl.setItems(typeListList);

        try {
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT Contact_Name FROM client_schedule.contacts");
            while(rs.next()){
                contList.add(rs.getString(1));
            }
            contactComboLbl.setItems(contList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(!(DataProvider.getAllAppointments().isEmpty())){
            DataProvider.getAllAppointments().clear();
        }
        try {
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID FROM client_schedule.appointments;");
            while(rs.next()){
                int appID = rs.getInt(1);
                int custID = rs.getInt(8);
                int userID = rs.getInt(9);
                int contID = rs.getInt(10);
                String title = rs.getString(2);
                String desc = rs.getString(3);
                String loc = rs.getString(4);
                String type = rs.getString(5);
                LocalDateTime start = Timestamp.valueOf(rs.getString(6)).toLocalDateTime();
                LocalDateTime end = Timestamp.valueOf(rs.getString(7)).toLocalDateTime();
                String localStart = TimeFunctions.formDTF(TimeFunctions.getUTCtoLoc(start).toLocalDateTime());
                String localEnd = TimeFunctions.formDTF(TimeFunctions.getUTCtoLoc(end).toLocalDateTime());
                DBAppointment appointment = new DBAppointment(appID,custID,userID,contID,title,desc,loc,type,start,end, localStart,localEnd);
                DataProvider.addAppointment(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onActionBackBttn(ActionEvent actionEvent) throws IOException {
        combString stringComb = s -> {
            s = TimeFunctions.getLoctoUTC(LocalDateTime.now()) + " User " + LoginPage.userID + ": " + s;
            return s;
        };
        IOClass.insertLog(stringComb.cString("Back button hit, going back to Start Menu"));
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/StartMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
