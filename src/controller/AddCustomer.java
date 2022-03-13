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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.DBCountryDID;
import model.DataProvider;
import model.IOClass;
import model.TimeFunctions;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import static DBA.JDBC.connection;

public class AddCustomer implements Initializable {
    public TextField addCustIDTxt,addCustNameTxt,addCustAddressTxt,addCustPCodeTxt,addCustPNumberTxt;
    public ComboBox addCustCountyCombo,addCustFLDCombo;
    private String sqlQuery = "INSERT INTO client_schedule.customers(Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?,?,?,?,?)";

    Stage stage;
    Parent scene;

    public void onActionAddCustCountyCombo(ActionEvent actionEvent) {
        int countryID = 0;
        ObservableList list = FXCollections.observableArrayList();
        for(DBCountryDID country : DataProvider.getAllCountry()){
            if(country.getCountry() ==addCustCountyCombo.getValue()){
                countryID = country.getCountryID();
            }
        }
        String strQuery = "SELECT Division_ID, Division FROM client_schedule.first_level_divisions WHERE Country_ID='" + countryID + "'";
        ResultSet rs = null;
        try {
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery(strQuery);
            while(rs.next()){
                list.add(rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        addCustFLDCombo.setItems(list);

    }

    public void onActionAddCustSaveBttn(ActionEvent actionEvent) throws IOException {
        //Lambda expression
        Counter incAddCounter = c -> {
            c++;
            DataProvider.setNewCounter(c);
            return c;
        };
        try {
            PreparedStatement psti = connection.prepareStatement(sqlQuery);
            String strQuery = "SELECT Division_ID FROM client_schedule.first_level_divisions where Division = ?";
            PreparedStatement psti2 = connection.prepareStatement(strQuery);
            ResultSet rs = null;

            String custName = addCustNameTxt.getText();
            String custAdd = addCustAddressTxt.getText();
            String custPC = addCustPCodeTxt.getText();
            String custPhone = addCustPNumberTxt.getText();
            String custDID = String.valueOf(addCustFLDCombo.getSelectionModel().getSelectedItem());
            if(custName.isEmpty() || custAdd.isEmpty() || custPC.isEmpty() || custPhone.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setContentText("Text fields are empty!");
                alert.showAndWait();
            }
            else {
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
                psti.execute();
                //Lambda Expression
                combString stringComb = s -> {
                    s = TimeFunctions.getLoctoUTC(LocalDateTime.now()) + " User " + LoginPage.userID + ": " + s;
                    return s;
                };
                IOClass.insertLog(stringComb.cString("New customer has been added!" ));
                //Lambda Expression
                incAddCounter.addCounter(DataProvider.getNewCounter());

                stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/CustomerMenu.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        } catch (NumberFormatException Ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("Enter valid values in all text fields!");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("Enter/select valid values in all fields!");
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList list = FXCollections.observableArrayList();
        for(DBCountryDID country : DataProvider.getAllCountry()){
            list.add(country.getCountry());
        }
        addCustCountyCombo.setItems(list);
    }
}
