package controller;

import Interface.Counter;
import Interface.combString;
import com.mysql.cj.log.Log;
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
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

import static DBA.JDBC.connection;

/**
 * Update Customer Class.
 * @author Rene Gomez Student ID: 001467443
 */
public class UpdateCustomer{
    public TextField upCustIDTxt,upCustNameTxt,upCustAddressTxt,upCustPCodeTxt,upCustPNumberTxt;
    public ComboBox upCustCountyCombo,upCustFLDCombo;
    private int intDID;
    private String sqlQuery1 = "SELECT Division,Country_ID FROM client_schedule.first_level_divisions WHERE Division_ID = ?";
    private String sqlQuery2 = "UPDATE client_schedule.customers SET Customer_name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";

    Stage stage;
    Parent scene;

    /**
     * Send Customer.
     * Allows data transfer from the Customer Menu so that the selected customer is shown. It also sets the appropriate values to the labels/combo boxes.
     * @param cust the customer is set to
     */
    public void sendCust(DBCustomer cust){
        upCustIDTxt.setText(String.valueOf(cust.getCustID()));
        upCustNameTxt.setText(String.valueOf(cust.getCustName()));
        upCustAddressTxt.setText(String.valueOf(cust.getCustAdd()));
        upCustPCodeTxt.setText(String.valueOf(cust.getCustPC()));
        upCustPNumberTxt.setText(String.valueOf(cust.getCustPhone()));
        intDID = cust.getCustDID();

        try {
            int custCount = 0;
            String custDIDo = null;
            PreparedStatement psti = connection.prepareStatement(sqlQuery1);
            ResultSet rs = null;
            psti.setInt(1, intDID);
            rs = psti.executeQuery();
            while (rs.next()) {
                custDIDo = rs.getString(1);
                custCount = rs.getInt(2);
            }
            ObservableList list = FXCollections.observableArrayList();
            ObservableList list2 = FXCollections.observableArrayList();

            int selCountry = 0;
            for (DBCountryDID country : DataProvider.getAllCountry()) {
                list.add(country.getCountry());
                if (country.getCountryID() == custCount) {
                    selCountry = country.getCountryID();
                    upCustCountyCombo.setValue(country.getCountry());
                }
            }
            upCustCountyCombo.setItems(list);
            upCustFLDCombo.setValue(custDIDo);
            String strQuery = "SELECT Division_ID, Division FROM client_schedule.first_level_divisions WHERE Country_ID='" + selCountry + "'";
            ResultSet rs1 = null;
            try {
                Statement stmt = connection.createStatement();
                rs1 = stmt.executeQuery(strQuery);
                while (rs1.next()) {
                    list2.add(rs1.getString(2));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            upCustFLDCombo.setItems(list2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Customer Country Combo.
     * On selection of a country, the customer First Level Division combo box is populated.
     * @param actionEvent on select country
     */
    public void onActionUpCustCountyCombo(ActionEvent actionEvent) {
        int countryID = 0;
        ObservableList list = FXCollections.observableArrayList();

        for (DBCountryDID country : DataProvider.getAllCountry()) {
            if (country.getCountry() == upCustCountyCombo.getValue()) {
                countryID = country.getCountryID();
            }
        }
        String strQuery = "SELECT Division_ID, Division FROM client_schedule.first_level_divisions WHERE Country_ID='" + countryID + "'";
        ResultSet rs = null;
        try {
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery(strQuery);
            while (rs.next()) {
                list.add(rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        upCustFLDCombo.setItems(list);

    }

    /**
     * Customer Update Button.
     * Attempts to save the updated customer and change stage back to the Customer Menu FXML. Also checks for logical errors, if any are found it displays an error/warning dialog box.
     * @param actionEvent UpCustSaveBttn click
     * @throws IOException
     * <p><b>
     *     LAMBDA EXPRESSIONS
     * </b></p>
     * <p><b>
     *     The first expression combines a string and inserts it into the log. The second expression increase the Update Counter.
     * </b></p>
     */
    public void onActionUpCustSaveBttn(ActionEvent actionEvent) throws IOException {
        //Lambda Expression
        Counter incUpCounter = c -> {
            c++;
            DataProvider.setUpCounter(c);
            return c;
        };
        try {
            String strQuery = "SELECT Division_ID FROM client_schedule.first_level_divisions where Division = ?";
            PreparedStatement psti2 = connection.prepareStatement(strQuery);
            ResultSet rs = null;

            PreparedStatement psti = connection.prepareStatement(sqlQuery2);
            int custID = Integer.parseInt(upCustIDTxt.getText());
            String custName = upCustNameTxt.getText();
            String custAdd = upCustAddressTxt.getText();
            String custPC =upCustPCodeTxt.getText();
            String custPhone = upCustPNumberTxt.getText();
            String custDID = String.valueOf(upCustFLDCombo.getSelectionModel().getSelectedItem());

            if(custName.isEmpty() || custAdd.isEmpty() || custPC.isEmpty() || custPhone.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setContentText("Text fields are empty!");
                alert.showAndWait();
            }
            else{
                psti2.setString(1, String.valueOf(custDID));
                rs = psti2.executeQuery();
                int custDIDo = 0;
                while (rs.next()) {
                    custDIDo = rs.getInt(1);
                }
                psti.setString(1, custName);
                psti.setString(2, custAdd);
                psti.setString(3, custPC);
                psti.setString(4, custPhone);
                psti.setInt(5, custDIDo);
                psti.setInt(6,custID);
                psti.execute();
                //Lambda Expression
                combString stringComb = s -> {
                    s = TimeFunctions.getLoctoUTC(LocalDateTime.now()) + " User " + LoginPage.userNameUsed + ": " + s;
                    return s;
                };
                IOClass.insertLog(stringComb.cString("Customer ID: " + custID + " has been updated!"));
                //Lambda Expression
                incUpCounter.addCounter(DataProvider.getUpCounter());

                stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/CustomerMenu.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("Enter/select valid values in all fields!");
            alert.showAndWait();
        }
    }

    /**
     * Cancel Button.
     * Changes stage to the Customer Menu FXML, but first asks user to confirm action.
     * @param actionEvent cancelbttn click
     * @throws IOException
     * <p><b>
     *     LAMBDA EXPRESSION
     * </b></p>
     * <p><b>
     *     This expression combines a string and inserts it into the log.
     * </b></p>
     */
    public void onActionUpCustCancelBttn(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("All values will not be updated! Do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            combString stringComb = s -> {
                s = TimeFunctions.getLoctoUTC(LocalDateTime.now()) + " User " + LoginPage.userNameUsed + ": " + s;
                return s;
            };
            IOClass.insertLog(stringComb.cString("Cancel button hit, going back to customer Menu"));

            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/CustomerMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }
}

