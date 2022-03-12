package controller;

import Interface.combString;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.DataProvider;
import model.IOClass;
import model.TimeFunctions;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

import static DBA.JDBC.connection;
import static java.util.ResourceBundle.getBundle;

public class LoginPage implements Initializable {

    public Label loginPageLbl;
    public TextField userNameTxt;
    public TextField passwordTxt;
    public Label userNameLbl;
    public Label passwordLbl;
    public Button loginBttnTxt;
    public Label zoneIDLbl;
    public static int userID;


    Stage stage;
    Parent scene;

    public String[] stringList(String str){
       String[] splited = str.split("\\s+");
       return splited;
    }
    
    public String stringComb(String[] strL){
        String sent = "";
        for(int i=0; i < strL.length; i++ ){
            if(i == 0){
                sent = strL[i];
            }
            else {
                sent = sent + " " + strL[i];
            }
        }
        System.out.println(sent);
        sent.trim();
        return sent;
    }
    public boolean isUpperCase(String s) {
        if (!Character.isUpperCase(s.charAt(0))) {
            return false;
        }
        return true;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DataProvider.setNewCounter(0);
        DataProvider.setUpCounter(0);
        userID = 0;
        ResourceBundle rb = getBundle("Lan/Nat", Locale.getDefault());
        ZoneId zoneID = ZoneId.systemDefault();
        String strZoneID = zoneID.getId();
        zoneIDLbl.setText(strZoneID);
        String loginPage =loginPageLbl.getText();
        String userName = userNameLbl.getText();
        String password = passwordLbl.getText();
        String button = loginBttnTxt.getText();

        if(Locale.getDefault().getLanguage().equals("en")) {
            System.out.println(rb.getString("hello") + " " + rb.getString("world"));
            String test = "";
            String cap = "";
            cap = loginPage;
            test = loginPage.toLowerCase();
            /*
            System.out.println(test);

             */
            String[] loginPageList = stringList(test);
            String[] loginPageCapList = stringList(cap);

            for(int i=0; i < loginPageList.length; i++ ){
                String s = loginPageList[i];
                String c = loginPageCapList[i];
                if(isUpperCase(c)){
                    /*
                    System.out.println("Upper Case!");

                     */
                    String trans = rb.getString(s);
                    char capLet = trans.toUpperCase().charAt(0);
                    char first = trans.charAt(0);
                    /*
                    System.out.println(capLet);
                    System.out.println(first);

                     */
                    loginPageList[i] = trans.replace(first, capLet);;
                }
                else{
                    loginPageList[i] = rb.getString(s);
                }
            }

            String newStr = stringComb(loginPageList);
            loginPageLbl.setText(newStr);
            System.out.println(newStr);

        }
        else if(Locale.getDefault().getLanguage().equals("es")){
            System.out.println(rb.getString("hello") + " " + rb.getString("world"));
            System.out.println(rb.getString("hello") + " " + rb.getString("world"));
            String test = "";
            String cap = "";
            cap = loginPage;
            test = loginPage.toLowerCase();
            System.out.println(test);
            String[] loginPageList = stringList(test);
            String[] loginPageCapList = stringList(cap);

            for(int i=0; i < loginPageList.length; i++ ){
                String s = loginPageList[i];
                String c = loginPageCapList[i];
                if(isUpperCase(c)){
                    /*
                    System.out.println("Upper Case!");

                     */
                    String trans = rb.getString(s);
                    char capLet = trans.toUpperCase().charAt(0);
                    char first = trans.charAt(0);
                    /*
                    System.out.println(capLet);
                    System.out.println(first);

                     */
                    loginPageList[i] = trans.replace(first, capLet);;
                }
                else{
                    loginPageList[i] = rb.getString(s);
                }
            }
            String newStr = stringComb(loginPageList);
            loginPageLbl.setText(newStr);
            System.out.println(newStr);
        }
        else if (Locale.getDefault().getLanguage().equals("fr")){
            System.out.println(rb.getString("hello") + " " + rb.getString("world"));
        }

    }

    public void onActionLoginBttn(ActionEvent actionEvent) throws SQLException, IOException {

        //Lambda Expression
        combString stringComb = s -> {
            s = TimeFunctions.getLoctoUTC(LocalDateTime.now()) + " User " + userID + ": " + s;
            return s;
        };
        ResourceBundle rb = getBundle("Lan/Nat", Locale.getDefault());
        String message = "No user or password found!";

        boolean userFound = false;
        ResultSet rs = null;
        Statement stmt = connection.createStatement();
        rs = stmt.executeQuery("SELECT * FROM client_schedule.users");
        while(rs.next()) {
            int user_ID = rs.getInt(1);
            String usernameDB = rs.getString(2);
            String passwordDB = rs.getString(3);
            /*
            System.out.println("DB username: "+ usernameDB);
            System.out.println("DB password: "+ passwordDB);
            System.out.println("Text username: "+ userNameTxt.getText());
            System.out.println("Text password: "+ passwordTxt.getText());

             */


            if(usernameDB.equals(userNameTxt.getText()) && passwordDB.equals(passwordTxt.getText())){
                userFound = true;
                userID = user_ID;
                IOClass.insertLog(stringComb.cString("Login attempt success!"));
                stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/StartMenu.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }

        }
        if(!userFound){
            if(Locale.getDefault().getLanguage().equals("en")) {
                IOClass.insertLog(stringComb.cString("Login attempt failed"));
                String okStr = rb.getString("OK");
                ButtonType ok = new ButtonType(okStr, ButtonBar.ButtonData.OK_DONE);
                Alert alert = new Alert(Alert.AlertType.NONE, message, ok);
                alert.setTitle("");
                alert.showAndWait();
            }
            else if(Locale.getDefault().getLanguage().equals("es")) {
                String okStr = rb.getString("OK");
                ButtonType ok = new ButtonType(okStr, ButtonBar.ButtonData.OK_DONE);
                Alert alert = new Alert(Alert.AlertType.NONE, message, ok);
                alert.setTitle("");
                alert.showAndWait();
            }
            else if(Locale.getDefault().getLanguage().equals("fr")) {
                ButtonType ok = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                Alert alert = new Alert(Alert.AlertType.NONE, message, ok);
                alert.setTitle("");
                alert.showAndWait();
            }
        }

    }
}
