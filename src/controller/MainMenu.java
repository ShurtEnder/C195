package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenu {
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
