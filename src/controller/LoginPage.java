package controller;

import DBA.JDBC;
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
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import static DBA.JDBC.connection;
import static java.util.ResourceBundle.getBundle;

/**
 * Login Page Class.
 * @author Rene Gomez Student ID: 001467443
 */
public class LoginPage implements Initializable {

    public Label loginPageLbl,userNameLbl,passwordLbl,zoneIDLbl;
    public TextField userNameTxt,passwordTxt;
    public Button loginBttnTxt,exitBttnTxt;
    public static int userID;
    private String sqlQuery = "SELECT Appointment_ID, Start FROM client_schedule.appointments WHERE Start BETWEEN ? AND ? AND USER_ID = ?";
    private ArrayList wordList = new ArrayList<>();

    Stage stage;
    Parent scene;

    /**
     * Initialize.
     * On the start of the program, all the labels/text are translated and the zone ID is set.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DataProvider.setNewCounter(0);
        DataProvider.setUpCounter(0);
        userID = 0;
        ZoneId zoneID = ZoneId.systemDefault();
        String strZoneID = zoneID.getId();
        zoneIDLbl.setText(strZoneID);
        String loginPage =loginPageLbl.getText();
        String userName = userNameLbl.getText();
        String password = passwordLbl.getText();
        String button = loginBttnTxt.getText();
        String exit = exitBttnTxt.getText();


        if(Locale.getDefault().getLanguage().equals("en")) {
            loginPageLbl.setText(sentenceTrans(loginPage));
            userNameLbl.setText(sentenceTrans(userName));
            passwordLbl.setText(sentenceTrans(password));
            loginBttnTxt.setText(sentenceTrans(button));
            exitBttnTxt.setText(sentenceTrans(exit));

        }
        else if(Locale.getDefault().getLanguage().equals("es")){
            loginPageLbl.setText(sentenceTrans(loginPage));
            userNameLbl.setText(sentenceTrans(userName));
            passwordLbl.setText(sentenceTrans(password));
            loginBttnTxt.setText(sentenceTrans(button));
            exitBttnTxt.setText(sentenceTrans(exit));
        }
        else if (Locale.getDefault().getLanguage().equals("fr")){
            loginPageLbl.setText(sentenceTrans(loginPage));
            userNameLbl.setText(sentenceTrans(userName));
            passwordLbl.setText(sentenceTrans(password));
            loginBttnTxt.setText(sentenceTrans(button));
            exitBttnTxt.setText(sentenceTrans(exit));
        }

    }


    /**
     * String List.
     * Separates a string into a char list.
     * @param str the string is set to
     * @return The char list.
     */
    public String[] stringList(String str){
       String[] splited = str.split("\\s+");
       return splited;
    }

    /**
     * String Combine.
     * Combines a char list together.
     * @param strL the string list is set to
     * @return The combined string
     */
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
        sent.trim();
        return sent;
    }

    /**
     * Is Upper Case.
     * Checks to see if a char is upcase or not.
     * @param s the string is set to
     * @return True or false
     */
    public boolean isUpperCase(String s) {
        if (!Character.isUpperCase(s.charAt(0))) {
            return false;
        }
        return true;
    }

    /**
     * Sentence Translator.
     * Translates a sentence using the resource bundle.
     * @param sentence the sentence is set to
     * @return The translated sentence.
     */
    public String sentenceTrans(String sentence){
        ResourceBundle rb = getBundle("Lan/Nat", Locale.getDefault());
        String lowerCase = "";
        String upperCase = "";
        upperCase = sentence;
        lowerCase = sentence.toLowerCase();
        String[] sentenceList = stringList(lowerCase);
        String[] sentenceCapList = stringList(upperCase);
        for(int i=0; i < sentenceList.length; i++ ) {
            String s = sentenceList[i];
            String c = sentenceCapList[i];
            if(!(sentenceList[i].equals(":")) && !(sentenceList[i].equals("?")) && !(sentenceList[i].equals("!")) && !(sentenceList[i].equals("-")) && !(sentenceList[i].equals("'"))){
                if (isUpperCase(c)) {
                    String trans = rb.getString(s);
                    char capLet = trans.toUpperCase().charAt(0);
                    char first = trans.charAt(0);
                    sentenceList[i] = trans.replace(first, capLet);
                } else {
                    sentenceList[i] = rb.getString(s);
                }
            } else {
                sentenceList[i] = s;
            }
        }
        String newStr = stringComb(sentenceList);
        return newStr;
    }


    /**
     * Login Button.
     * First checks if user name an password are correct then changes stage to the Start Menu FXML.
     * @param actionEvent
     * @throws SQLException
     * @throws IOException
     */
    public void onActionLoginBttn(ActionEvent actionEvent) throws SQLException, IOException {
        //Lambda Expression
        combString stringComb = s -> {
            s = TimeFunctions.getLoctoUTC(LocalDateTime.now()) + " User " + userID + ": " + s;
            return s;
        };
        ResourceBundle rb = getBundle("Lan/Nat", Locale.getDefault());
        String noPass = sentenceTrans("No user or password found !");


        boolean userFound = false;
        PreparedStatement psti = connection.prepareStatement(sqlQuery);
        ResultSet rs = null;
        Statement stmt = connection.createStatement();

        rs = stmt.executeQuery("SELECT * FROM client_schedule.users");
        while(rs.next()) {
            int user_ID = rs.getInt(1);
            String usernameDB = rs.getString(2);
            String passwordDB = rs.getString(3);

            if(usernameDB.equals(userNameTxt.getText()) && passwordDB.equals(passwordTxt.getText())){
                String printAppID = null;
                String printTime = null;
                userFound = true;
                userID = user_ID;
                IOClass.insertLog(stringComb.cString("Login attempt success !"));

                Timestamp.valueOf(TimeFunctions.getLoctoUTC(LocalDateTime.now()).toLocalDateTime().minusMinutes(1));

                psti.setString(1, String.valueOf(Timestamp.valueOf(TimeFunctions.getLoctoUTC(LocalDateTime.now()).toLocalDateTime().minusMinutes(1))));
                psti.setString(2,String.valueOf(Timestamp.valueOf(TimeFunctions.getLoctoUTC(LocalDateTime.now()).toLocalDateTime().plusMinutes(16))));
                psti.setInt(3,user_ID);

                rs = psti.executeQuery();
                while(rs.next()) {
                    printAppID = rs.getString(1);
                    printTime = rs.getString(2);
                }

                if(!(printAppID == null)) {
                    printTime = TimeFunctions.formDTF(TimeFunctions.getUTCtoLoc(Timestamp.valueOf(printTime).toLocalDateTime()).toLocalDateTime());
                    String appNear = sentenceTrans("There is an appointment within 15 minutes ! ") + "\n" + sentenceTrans("Appointment ID : ")+ " " + printAppID + "\n" +sentenceTrans("Date and Time : ") + " " + printTime;
                    String okStr = sentenceTrans("OK");
                    ButtonType ok = new ButtonType(okStr, ButtonBar.ButtonData.OK_DONE);
                    Alert alert = new Alert(Alert.AlertType.NONE,appNear , ok);
                    alert.setTitle("");
                    alert.showAndWait();
                }
                else {
                    String okStr = sentenceTrans("OK");
                    String noNear = sentenceTrans("There are no upcoming appointments !");
                    ButtonType ok = new ButtonType(okStr, ButtonBar.ButtonData.OK_DONE);
                    Alert alert = new Alert(Alert.AlertType.NONE, noNear, ok);
                    alert.setTitle("");
                    alert.showAndWait();
                }

                stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/StartMenu.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }

        }
        if(!userFound){
            if(Locale.getDefault().getLanguage().equals("en")) {
                IOClass.insertLog(stringComb.cString("Login attempt failed"));
                String okStr = sentenceTrans("OK");;
                ButtonType ok = new ButtonType(okStr, ButtonBar.ButtonData.OK_DONE);
                Alert alert = new Alert(Alert.AlertType.NONE, noPass, ok);
                alert.setTitle("");
                alert.showAndWait();
            }
            else if(Locale.getDefault().getLanguage().equals("es")) {
                String okStr = sentenceTrans("OK");
                ButtonType ok = new ButtonType(okStr, ButtonBar.ButtonData.OK_DONE);
                Alert alert = new Alert(Alert.AlertType.NONE, noPass, ok);
                alert.setTitle("");
                alert.showAndWait();
            }
            else if(Locale.getDefault().getLanguage().equals("fr")) {
                String okStr = sentenceTrans("OK");
                ButtonType ok = new ButtonType(okStr, ButtonBar.ButtonData.OK_DONE);
                Alert alert = new Alert(Alert.AlertType.NONE, noPass, ok);
                alert.setTitle("");
                alert.showAndWait();
            }
        }

    }

    /**
     * Exit Button.
     * Closes the program, first asking if the user confirms they want to exit.
     * @param actionEvent on ExitButton click
     */
    public void onActionExit(ActionEvent actionEvent) {
        String exitMsg = sentenceTrans("Are you sure you want to exit ?");
        String okStr = sentenceTrans("OK");
        ButtonType ok = new ButtonType(okStr, ButtonBar.ButtonData.OK_DONE);
        String cancelStr = sentenceTrans("Back");
        ButtonType cancel = new ButtonType(cancelStr, ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.NONE, exitMsg, ok, cancel);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ok) {
            System.exit(0);
            JDBC.closeConnection();
        }
    }
}
