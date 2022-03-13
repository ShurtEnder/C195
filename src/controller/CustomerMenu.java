package controller;

import Interface.combString;
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
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

import static DBA.JDBC.connection;

/**
 * Customer Menu Class.
 * @author Rene Gomez Student ID: 001467443
 */
public class CustomerMenu implements Initializable {
    public TableView custTableView;
    public TableColumn custIDCol,custNameCol,custAddCol,custCodeCol,custPhoneCol,custFDLCol;
    private String sqlQuery = "SELECT Appointment_ID, Customer_ID, Title, Location, Type, Start, End FROM appointments WHERE Customer_ID = ?";
    private String sqlQuery2 = "DELETE FROM client_schedule.customers WHERE Customer_ID = ?";

    Stage stage;
    Parent scene;

    /**
     * Initialize.
     * Searches the database for customer information and sets the tableviews/columns.
     */
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
    }

    /**
     * Add Customer Button.
     * Changes stage to the Add Customer FXML.
     * @param actionEvent AddCustBttn click
     * @throws IOException
     * <p><b>
     *     LAMBDA EXPRESSION
     * </b></p>
     * <p><b>
     *     This expression combines a string and inserts it into the log.
     * </b></p>
     */
    public void onActionAddCustBttn(ActionEvent actionEvent) throws IOException {
        combString stringComb = s -> {
            s = TimeFunctions.getLoctoUTC(LocalDateTime.now()) + " User " + LoginPage.userID + ": " + s;
            return s;
        };
        IOClass.insertLog(stringComb.cString("Add customer button hit"));
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Update Customer Button.
     * Changes stage to the Update Customer FXML and sends the selected customer to that controller. If nothing is selected it shows an error pop up.
     * @param actionEvent UpCustBttn click
     * @throws IOException
     * <p><b>
     *     LAMBDA EXPRESSION
     * </b></p>
     * <p><b>
     *     This expression combines a string and inserts it into the log.
     * </b></p>
     */
    public void onActionUpCustBttn(ActionEvent actionEvent) throws IOException {
        try {
            combString stringComb = s -> {
                s = TimeFunctions.getLoctoUTC(LocalDateTime.now()) + " User " + LoginPage.userID + ": " + s;
                return s;
            };
            IOClass.insertLog(stringComb.cString("Update customer button hit"));
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

    /**
     * Delete Customer Button.
     * Deletes a selected customer on the customer table, showing a confirmation dialog box. If nothing is selected it shows an error pop up.
     * @param actionEvent
     * <p><b>
     *     LAMBDA EXPRESSION
     * </b></p>
     * <p><b>
     *     This expression combines a string and inserts it into the log.
     * </b></p>
     */
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
                PreparedStatement psti2 = connection.prepareStatement(sqlQuery2);
                ResultSet rs = null;
                psti2.setInt(1, custID2);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Are you sure you want to delete customer?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    psti2.execute();
                    combString stringComb = s -> {
                        s = TimeFunctions.getLoctoUTC(LocalDateTime.now()) + " User " + LoginPage.userID + ": " + s;
                        return s;
                    };
                    IOClass.insertLog(stringComb.cString("Customer ID: " + custID2 + "has been removed!"));

                    DataProvider.getAllCustomers().clear();
                    Statement stmt = connection.createStatement();
                    rs = stmt.executeQuery("SELECT * FROM client_schedule.customers");
                    while(rs.next()) {
                        int custID = Integer.parseInt(rs.getString(1));
                        String custName = rs.getString(2);
                        String custAdd = rs.getString(3);
                        String custPC = rs.getString(4);
                        String custPhone = rs.getString(5);
                        int custDID = Integer.parseInt(rs.getString(10));
                        DBCustomer dbInfo = new DBCustomer(custID, custName, custAdd, custPC, custPhone, custDID);
                        DataProvider.addCustomer(dbInfo);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Add Appointment Button.
     * Changes stage to the Add Appointment FXML and sends the selected customer to that controller. If nothing is selected it shows an error pop up.
     * @param actionEvent
     * @throws IOException
     * <p><b>
     *     LAMBDA EXPRESSION
     * </b></p>
     * <p><b>
     *     This expression combines a string and inserts it into the log.
     * </b></p>
     */
    public void onActionAddAppBttn(ActionEvent actionEvent) throws IOException {
        try {
            combString stringComb = s -> {
                s = TimeFunctions.getLoctoUTC(LocalDateTime.now()) + " User " + LoginPage.userID + ": " + s;
                return s;
            };
            IOClass.insertLog(stringComb.cString("Add appointment button hit"));
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

    /**
     * Back Button.
     * Changes stage to the Start Menu FXML.
     * @param actionEvent back button click
     * @throws IOException
     * <p><b>
     *     LAMBDA EXPRESSION
     * </b></p>
     * <p><b>
     *     This expression combines a string and inserts it into the log.
     * </b></p>
     */
    public void onActionCustBackBttn(ActionEvent actionEvent) throws IOException {
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
