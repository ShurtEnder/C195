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
import model.DBProvider;
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
    private ObservableList test = FXCollections.observableArrayList();

    Stage stage;
    Parent scene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

            custTableView.setItems(DataProvider.getAllCustomers());
            custIDCol.setCellValueFactory(new PropertyValueFactory<>("custID"));
            custNameCol.setCellValueFactory(new PropertyValueFactory<>("custName"));
            custAddCol.setCellValueFactory(new PropertyValueFactory<>("custAdd"));
            custCodeCol.setCellValueFactory(new PropertyValueFactory<>("custPC"));
            custPhoneCol.setCellValueFactory(new PropertyValueFactory<>("custPhone"));
            custFDLCol.setCellValueFactory(new PropertyValueFactory<>("custDID"));



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

    public void onActionViewAppBttn(ActionEvent actionEvent) {
    }
}
