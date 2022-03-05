package controller;

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
import model.DBProviderDID;
import model.DataProvider;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static DBA.JDBC.connection;

public class AddCustomer implements Initializable {
    public TextField addCustIDTxt;
    public TextField addCustNameTxt;
    public TextField addCustAddressTxt;
    public TextField addCustPCodeTxt;
    public TextField addCustPNumberTxt;
    public ComboBox addCustCountyCombo;
    public ComboBox addCustFLDCombo;

    Stage stage;
    Parent scene;

    public void onActionAddCustCountyCombo(ActionEvent actionEvent) {
        int countryID = 0;
        ObservableList list = FXCollections.observableArrayList();


        for(DBProviderDID country : DataProvider.getAllCountry()){
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

    public void onActionAddCustFLDCombo(ActionEvent actionEvent) {
    }

    public void onActionAddCustSaveBttn(ActionEvent actionEvent) throws IOException {
        try{
            String custName = addCustNameTxt.getText();
            String custAdd = addCustAddressTxt.getText();
            String custPC = addCustPCodeTxt.getText();
            String custPhone = addCustPNumberTxt.getText();
            int custDID = (int) addCustFLDCombo.getSelectionModel().getSelectedItem();

        }
        catch (NumberFormatException Ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("Enter valid values in all text fields!");
            alert.showAndWait();
        }

        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void onActionAddCustCancelBttn(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList list = FXCollections.observableArrayList();
        for(DBProviderDID country : DataProvider.getAllCountry()){
            list.add(country.getCountry());
        }

        addCustCountyCombo.setItems(list);
    }
}
