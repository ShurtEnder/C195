package controller;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.*;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static DBA.JDBC.connection;

public class MainMenu implements Initializable {
    public TableView custTableView;
    public TableColumn custIDCol;
    public TableColumn custNameCol;
    public TableColumn custAddCol;
    public TableColumn custCodeCol;
    public TableColumn custPhoneCol;
    public TableColumn custFDLCol;
    public TableView appTableView;
    public TableColumn appIDCol;
    public TableColumn appCustIDCol;
    public TableColumn appTitleCol;
    public TableColumn appLocCol;
    public TableColumn appTypeCol;
    public TableColumn appSECol;
    public Label custInfoLbl;

    Stage stage;
    Parent scene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ResultSet rs = null;
        try {
            DataProvider.getAllCustomers().clear();
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM client_schedule.customers");
            while(rs.next()){
                int custID = Integer.parseInt(rs.getString(1));
                String custName = rs.getString(2);
                String custAdd = rs.getString(3);
                String custPC = rs.getString(4);
                String custPhone = rs.getString(5);
                int custDID = Integer.parseInt(rs.getString(10));
                DBProvider dbInfo = new DBProvider(custID,custName,custAdd,custPC, custPhone, custDID);
                DataProvider.addCustomer(dbInfo);

            }
            rs = stmt.executeQuery("SELECT * FROM client_schedule.countries");
            while(rs.next()){
                int countryID = Integer.parseInt(rs.getString(1));
                String country = rs.getString(2);
                DBProviderDID dbCountry = new DBProviderDID(countryID,country);
                DataProvider.addCountry(dbCountry);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }


        custTableView.setItems(DataProvider.getAllCustomers());
        custIDCol.setCellValueFactory(new PropertyValueFactory<>("custID"));
        custNameCol.setCellValueFactory(new PropertyValueFactory<>("custName"));
        custAddCol.setCellValueFactory(new PropertyValueFactory<>("custAdd"));
        custCodeCol.setCellValueFactory(new PropertyValueFactory<>("custPC"));
        custPhoneCol.setCellValueFactory(new PropertyValueFactory<>("custPhone"));
        custFDLCol.setCellValueFactory(new PropertyValueFactory<>("custDID"));

        appTableView.setItems(DataProvider.getAllAppointments());
        appIDCol.setCellValueFactory(new PropertyValueFactory<>("AppID"));
        appCustIDCol.setCellValueFactory(new PropertyValueFactory<>("CustID"));
        appTitleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
        appLocCol.setCellValueFactory(new PropertyValueFactory<>("Loc"));
        appTypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        appSECol.setCellValueFactory(new PropertyValueFactory<>("combSE"));



    }

    public void onActionAddCustBttn(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void onActionUpCustBttn(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/UpdateCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void onActionDelCustBttn(ActionEvent actionEvent) {
    }

    public void onActionSelCustBttn(ActionEvent actionEvent) {
            DataProvider.getAllAppointments().clear();
            DBProvider selected = (DBProvider) custTableView.getSelectionModel().getSelectedItem();
            String strQuery = "SELECT Appointment_ID, Customer_ID, Title, Location, Type, Start, End FROM appointments WHERE Appointment_ID = '" + selected.getCustID() + "'";
            ResultSet rs = null;
            try{
                Statement stmt = connection.createStatement();
                rs = stmt.executeQuery(strQuery);
                while(rs.next()){
                    int AppID = Integer.parseInt(rs.getString(1));
                    int CustID = Integer.parseInt(rs.getString(2));
                    String Title = rs.getString(3);
                    String Loc = rs.getString(4);
                    String Type = rs.getString(5);
                    LocalDateTime Start = Timestamp.valueOf(rs.getString(6)).toLocalDateTime();
                    LocalDate date = TimeFunctions.zdtToDate(Start);
                    LocalTime time1 = TimeFunctions.zdtToTime(Start);
                    LocalDateTime End = Timestamp.valueOf(rs.getString(7)).toLocalDateTime();
                    LocalTime time2 = TimeFunctions.zdtToTime(End);

                    String combSE = date + " " + time1 + " " + time2;
                    DBAppointment appointment = new DBAppointment(AppID,CustID,Title,Loc,Type,Start,End,combSE);
                    DataProvider.addAppointment(appointment);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            custInfoLbl.setText("Showing Info of Customer: " + selected.getCustName());


    }

    public void onActionAddAppBttn(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddAppointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void onActionUpAppBttn(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/UpdateAppointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void onActionRemAppBttn(ActionEvent actionEvent) {
    }

    public void onActionViewAppBttn(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AppointmentWeekMonth.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
