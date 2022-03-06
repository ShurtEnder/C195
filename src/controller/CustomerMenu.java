package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

import static DBA.JDBC.connection;

public class CustomerMenu implements Initializable {
    public TableView custTableView;
    public TableColumn custIDCol;
    public TableColumn custNameCol;
    public TableColumn custAddCol;
    public TableColumn custCodeCol;
    public TableColumn custPhoneCol;
    public TableColumn custFDLCol;
    private String sqlQuery = "SELECT Appointment_ID, Customer_ID, Title, Location, Type, Start, End FROM appointments WHERE Customer_ID = ?";
    private String sqlQuery2 = "DELETE FROM client_schedule.customers WHERE Customer_ID = ?";

    Stage stage;
    Parent scene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ResultSet rs = null;
        try {
            DataProvider.getAllCustomers().clear();
            DataProvider.getAllCountry().clear();
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM client_schedule.customers");
            while(rs.next()){
                int custID = Integer.parseInt(rs.getString(1));
                String custName = rs.getString(2);
                String custAdd = rs.getString(3);
                String custPC = rs.getString(4);
                String custPhone = rs.getString(5);
                int custDID = Integer.parseInt(rs.getString(10));
                DBCustomer dbInfo = new DBCustomer(custID,custName,custAdd,custPC, custPhone, custDID);
                DataProvider.addCustomer(dbInfo);

            }
            rs = stmt.executeQuery("SELECT * FROM client_schedule.countries");
            while(rs.next()){
                int countryID = Integer.parseInt(rs.getString(1));
                String country = rs.getString(2);
                DBCountryDID dbCountry = new DBCountryDID(countryID,country);
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

        /*
        appTableView.setItems(DataProvider.getAllAppointments());
        appIDCol.setCellValueFactory(new PropertyValueFactory<>("AppID"));
        appCustIDCol.setCellValueFactory(new PropertyValueFactory<>("CustID"));
        appTitleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
        appLocCol.setCellValueFactory(new PropertyValueFactory<>("Loc"));
        appTypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        appSECol.setCellValueFactory(new PropertyValueFactory<>("combSE"));

         */



    }

    public void onActionAddCustBttn(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void onActionUpCustBttn(ActionEvent actionEvent) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/UpdateCustomer.fxml"));
            loader.load();
            UpdateCustomer UPController = loader.getController();
            UPController.sendCust((DBCustomer) custTableView.getSelectionModel().getSelectedItem());
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

        } catch (NullPointerException Te){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("No customer selected!");
            alert.showAndWait();
        }

    }

    public void onActionDelCustBttn(ActionEvent actionEvent) {
        boolean appThere = false;
        try {
            if(custTableView.getSelectionModel().getSelectedItem() == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error!");
                alert.setContentText("No customer selected!");
                alert.showAndWait();
            }
            else {
                int custID2 = ((DBCustomer) custTableView.getSelectionModel().getSelectedItem()).getCustID();
                PreparedStatement psti = connection.prepareStatement(sqlQuery);
                PreparedStatement psti2 = connection.prepareStatement(sqlQuery2);
                ResultSet rs = null;
                psti.setInt(1,custID2);
                psti2.setInt(1, custID2);
                rs = psti.executeQuery();
                while (rs.next()){
                    appThere = true;
                }

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Are you sure you want to delete part?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    if(!appThere){
                        psti2.execute();
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
                            DBCustomer dbInfo = new DBCustomer(custID,custName,custAdd,custPC, custPhone, custDID);
                            DataProvider.addCustomer(dbInfo);

                        }
                    }
                    else {
                        Alert alert1 = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("WARNING!");
                        alert.setContentText("Customer has appointments!");
                        alert.showAndWait();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void onActionAddAppBttn(ActionEvent actionEvent) throws IOException {
        /*
        try{
            DataProvider.getAllAppointments().clear();
            DBCustomer selected = (DBCustomer) custTableView.getSelectionModel().getSelectedItem();
            String strQuery = "SELECT Appointment_ID, Customer_ID, Title, Location, Type, Start, End FROM appointments WHERE Customer_ID = '" + selected.getCustID() + "'";
            ResultSet rs = null;
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery(strQuery);
            while(rs.next()) {
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

                String combSE = date + " " + time1 + "/" + time2;
                DBAppointment appointment = new DBAppointment(AppID, CustID, Title, Loc, Type, Start, End, combSE);
                DataProvider.addAppointment(appointment);
                custInfoLbl.setText("Showing Info of Customer: " + selected.getCustName());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            catch (NullPointerException Te) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error!");
                alert.setContentText("No customer selected!");
                alert.showAndWait();
            }

         */
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/AddAppointment.fxml"));
            loader.load();
            AddAppointment AAController = loader.getController();
            AAController.sendAddApp((DBCustomer) custTableView.getSelectionModel().getSelectedItem());
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

        } catch (NullPointerException Te){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("No customer selected!");
            alert.showAndWait();
        }


    }

    public void onActionCustBackBttn(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/StartMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
