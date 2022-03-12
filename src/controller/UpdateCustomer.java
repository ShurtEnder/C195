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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import static DBA.JDBC.connection;

public class UpdateCustomer{
    public TextField upCustIDTxt,upCustNameTxt,upCustAddressTxt,upCustPCodeTxt,upCustPNumberTxt;
    public ComboBox upCustCountyCombo,upCustFLDCombo;
    private int intDID;
    private String sqlQuery1 = "SELECT Division,Country_ID FROM client_schedule.first_level_divisions WHERE Division_ID = ?";
    private String sqlQuery2 = "UPDATE client_schedule.customers SET Customer_name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";

    Stage stage;
    Parent scene;

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
                s = TimeFunctions.getLoctoUTC(LocalDateTime.now()) + " User " + LoginPage.userID + ": " + s;
                return s;
            };
            IOClass.insertLog(stringComb.cString("Customer ID: " + custID + " has been updated!"));
            //Lambda Expression
            incUpCounter.addCounter(DataProvider.getUpCounter());

            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/CustomerMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onActionUpCustCancelBttn(ActionEvent actionEvent) throws IOException {
        combString stringComb = s -> {
            s = TimeFunctions.getLoctoUTC(LocalDateTime.now()) + " User " + LoginPage.userID + ": " + s;
            return s;
        };
        IOClass.insertLog(stringComb.cString("Cancel button hit, going back to customer Menu"));

        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomerMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}

