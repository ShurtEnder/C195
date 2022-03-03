package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddCustomer {
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
    }

    public void onActionAddCustFLDCombo(ActionEvent actionEvent) {
    }

    public void onActionAddCustSaveBttn(ActionEvent actionEvent) throws IOException {
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

}
