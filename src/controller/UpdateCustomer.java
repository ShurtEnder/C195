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

public class UpdateCustomer {
    public TextField upCustIDTxt;
    public TextField upCustNameTxt;
    public TextField upCustAddressTxt;
    public TextField upCustPCodeTxt;
    public TextField upCustPNumberTxt;
    public ComboBox upCustCountyCombo;
    public ComboBox upCustFLDCombo;

    Stage stage;
    Parent scene;

    public void onActionUpCustCountyCombo(ActionEvent actionEvent) {
    }

    public void onActionUpCustFLDCombo(ActionEvent actionEvent) {
    }

    public void onActionUpCustSaveBttn(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void onActionUpCustCancelBttn(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
