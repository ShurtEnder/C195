package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        ResourceBundle rb = getBundle("Lan/Nat", Locale.getDefault());

        if(Locale.getDefault().getLanguage().equals("en")) {
            System.out.println(rb.getString("hello") + " " + rb.getString("world"));
            String loginPage =loginPageLbl.getText();
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
                    System.out.println("Upper Case!");
                    String trans = rb.getString(s);
                    char capLet = trans.toUpperCase().charAt(0);
                    char first = trans.charAt(0);
                    System.out.println(capLet);
                    System.out.println(first);
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
            String loginPage =loginPageLbl.getText();
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
                    System.out.println("Upper Case!");
                    String trans = rb.getString(s);
                    char capLet = trans.toUpperCase().charAt(0);
                    char first = trans.charAt(0);
                    System.out.println(capLet);
                    System.out.println(first);
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

    public void onActionLoginBttn(ActionEvent actionEvent) throws SQLException {
        ResultSet rs = null;
        Statement stmt = connection.createStatement();
        rs = stmt.executeQuery("SELECT * FROM client_schedule.users");
        while(rs.next()) {
            String test = rs.getString(2);
            System.out.println(test);
        }
    }
}
