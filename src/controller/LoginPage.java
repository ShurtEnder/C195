package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginPage implements Initializable {

    public Label loginPageLbl;
    public TextField userNameTxt;
    public TextField passwordTxt;
    public Label userNameLbl;
    public Label passwordLbl;
    public Button loginBttnTxt;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onActionLoginBttn(ActionEvent actionEvent) {
    }
}
