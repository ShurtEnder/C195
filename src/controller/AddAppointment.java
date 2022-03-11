package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.DBAppointment;
import model.DBCustomer;
import model.DataProvider;
import model.TimeFunctions;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.*;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static DBA.JDBC.connection;

public class AddAppointment implements Initializable {
    public TextField addAppAppIDTxt;
    public TextField addAppCustIDTxt;
    public TextField addAppUserIDTxt;
    public TextField addAppTitleTxt;
    public TextArea addAppDescTxt;
    public TextField addAppLocTxt;
    public ComboBox addAppContNameCombo;
    public ComboBox addAppStartCombo;
    public DatePicker addAppStartDateCal;
    public ComboBox addAppEndCombo;
    public DatePicker addAppEndDateCal;
    public ComboBox addAppTypeCombo;
    private ObservableList typeList = FXCollections.observableArrayList("Planning Session","De-Briefing", "Meeting", "Break");
    private ObservableList contList = FXCollections.observableArrayList();
    private ObservableList timeList = FXCollections.observableArrayList();
    private LocalDate localDate;
    private LocalDateTime businessDTStart;
    private LocalDateTime businessDTEnd;
    private String sqlQuery = "INSERT INTO client_schedule.appointments(Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES (?,?,?,?,?,?,?,?,?)";
    private String sqlQuery2 = "SELECT * FROM client_schedule.appointments WHERE ? BETWEEN ? AND ?";
    private boolean overLap = false;
    private boolean busHours = true;
    private ArrayList overlapList = new ArrayList<>();

    Stage stage;
    Parent scene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void sendAddApp(DBCustomer customer){
        localDate = LocalDate.now();
        LocalDateTime timeDateLoc = LocalDateTime.now();
        LocalDateTime timeDateEST = TimeFunctions.getLoctoEST(timeDateLoc).toLocalDateTime();
        LocalTime firstTime = LocalTime.parse("08:00");
        LocalDateTime firstDT = LocalDateTime.of(localDate, firstTime);
        LocalTime firstTime1 = TimeFunctions.getESTtoLoc(firstDT).toLocalTime();


        LocalTime lastTime = firstTime1.plusHours(14);
        timeList.add(firstTime1);
        while(!(firstTime1 == lastTime)){
            firstTime1 = firstTime1.plusMinutes(30);
            timeList.add(firstTime1);
        }

        addAppStartCombo.setItems(timeList);
        addAppEndCombo.setItems(timeList);


        int custID = customer.getCustID();
        /*
        String custLocFull = customer.getCustAdd()
         */
        addAppCustIDTxt.setText(String.valueOf(custID));
        addAppUserIDTxt.setText(String.valueOf(LoginPage.userID));
        addAppLocTxt.setText(String.valueOf(customer.getCustAdd()));
        addAppTypeCombo.setItems(typeList);
        ResultSet rs = null;
        try {
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT Contact_Name FROM client_schedule.contacts");
            while(rs.next()){
                contList.add(rs.getString(1));
            }
            addAppContNameCombo.setItems(contList);
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }

    public void onActionAddAppContNameCombo(ActionEvent actionEvent) {
    }

    public void onActionAddAppStartCombo(ActionEvent actionEvent) {
    }

    public void onActionAddAppStartDateCal(ActionEvent actionEvent) {
    }

    public void onActionAddAppEndCombo(ActionEvent actionEvent) {
    }

    public void onActionAddAppEndDateCal(ActionEvent actionEvent) {
    }

    public void onActionAddAppSaveBttn(ActionEvent actionEvent) throws IOException {
        try{

            overLap = false;
            overlapList.clear();
            PreparedStatement psti = connection.prepareStatement(sqlQuery);
            String strQuery = "SELECT Contact_ID FROM client_schedule.contacts WHERE Contact_Name = ?";
            PreparedStatement psti2 = connection.prepareStatement(strQuery);
            ResultSet rs = null;

            String custID = addAppCustIDTxt.getText();
            String userID = addAppUserIDTxt.getText();
            String title = addAppTitleTxt.getText();
            String desc = addAppDescTxt.getText();
            String loc = addAppLocTxt.getText();
            String type = String.valueOf(addAppTypeCombo.getSelectionModel().getSelectedItem());
            String cont = String.valueOf(addAppContNameCombo.getSelectionModel().getSelectedItem());

            LocalTime startT = LocalTime.parse(String.valueOf(addAppStartCombo.getSelectionModel().getSelectedItem()));
            /*
            if (startT.isAfter(LocalTime.parse("24:00")) || startT.equals(LocalTime.parse("00:00"))){
                    nextDayStart = true;
            }

             */
            LocalDate startD = LocalDate.parse(String.valueOf(addAppStartDateCal.getValue()));
            LocalDateTime startComb = TimeFunctions.getLoctoUTC(TimeFunctions.combDT(startD, startT)).toLocalDateTime();
            startT = startComb.toLocalTime();
            startD = startComb.toLocalDate();
            Timestamp finalStartTime = Timestamp.valueOf(startComb);

            LocalTime endT = LocalTime.parse(String.valueOf(addAppEndCombo.getSelectionModel().getSelectedItem()));
            /*
            if (endT.isAfter(LocalTime.parse("24:00")) || endT.equals(LocalTime.parse("00:00"))){
                nextDayEnd = true;
            }

             */
            LocalDate endD = LocalDate.parse(String.valueOf(addAppEndDateCal.getValue()));
            LocalDateTime endComb = TimeFunctions.getLoctoUTC(TimeFunctions.combDT(endD, endT)).toLocalDateTime();
            endT = endComb.toLocalTime();
            endD = endComb.toLocalDate();
            Timestamp finalEndTime = Timestamp.valueOf(endComb);

            /*
            LocalTime.parse((CharSequence) timeList.get(0));
            LocalTime.parse((CharSequence) timeList.get(timeList.size() - 1));

             */

            int busStartind = timeList.indexOf(addAppStartCombo.getSelectionModel().getSelectedItem());
            int busEndind = timeList.indexOf(addAppEndCombo.getSelectionModel().getSelectedItem());

            if(busEndind < busStartind){
                busHours = false;
            } else{
                busHours = true;
            }

            /*
            businessDTStart = TimeFunctions.getLoctoUTC(TimeFunctions.combDT(startD, LocalTime.parse((CharSequence) timeList.get(0)))).toLocalDateTime();
            businessDTEnd = businessDTStart.minusHours(10);

             */


            psti2.setString(1, String.valueOf(cont));
            rs = psti2.executeQuery();
            int contID = 0;
            while (rs.next()){
                contID = rs.getInt(1);
            }

            /*
            INSERT INTO client_schedule.appointments(Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES (?,?,?,?,?,?,?,?,?)
             */
            if(!(busHours)){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setContentText("Selected date/times are not within business hours!");
                alert.showAndWait();
            }
            else if(startT.equals(endT)){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setContentText("Start time and end time are the same!");
                alert.showAndWait();
            }
            else if(startComb.isAfter(endComb)){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setContentText("End Date/Time is before start date/time!");
                alert.showAndWait();
            }
            else if(startT.isAfter(endT) && !(startComb.isBefore(endComb))){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setContentText("End time starts before start time!");
                alert.showAndWait();
            } else if(startD.isAfter(endD)){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setContentText("End date starts before start date!");
                alert.showAndWait();
            } else if(startD.isBefore(LocalDate.now()) || endD.isBefore(LocalDate.now())){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setContentText("Start/End Date before today!");
                alert.showAndWait();
            } else if((startT.isBefore(LocalTime.now()) || endT.isBefore(LocalTime.now())) && !(startD.isAfter(LocalDate.now()) || endD.isAfter(LocalDate.now()))){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setContentText("Start/End Time before current time!");
                alert.showAndWait();
            }
            else{
                for (DBAppointment app: DataProvider.getAllAppointments()){
                    //if(app.getAppID() == )
                    LocalDate checkDateStart = app.getStart().toLocalDate();
                    LocalTime checkTimeStart = app.getStart().toLocalTime();

                    LocalDate checkDateEnd = app.getEnd().toLocalDate();
                    LocalTime checkTimeEnd = app.getEnd().toLocalTime();
                    if(startD.equals(checkDateStart)){
                        if((startT.isAfter(checkTimeStart) || startT.equals(checkTimeStart)) && startT.isBefore(checkTimeEnd)){
                            System.out.println("Overlap!");
                            overLap = true;
                        }
                        else if(endT.isAfter(checkTimeStart) && (endT.isBefore(checkTimeEnd) || endT.equals(checkTimeEnd))){
                            System.out.println("Overlap!");
                            overLap = true;
                        }
                        else if((startT.isBefore(checkTimeStart) || startT.equals(checkTimeStart)) && (endT.isAfter(checkTimeEnd) || endT.equals(checkTimeEnd))){
                            System.out.println("Overlap!");
                            overLap = true;
                        }
                        else if (overLap){
                            System.out.println(app.getAppID());
                            overlapList.add(app.getAppID());
                        }
                    }
                }
                if (overLap){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning!!");
                    alert.setContentText("There is appoint overlaps with appointment(s) " + overlapList);
                    alert.showAndWait();
                }
                /*
                else if(nextDayStart){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning!!");
                    alert.setContentText("Selected Start time is at or past midnight! Please select next day!");
                    alert.showAndWait();
                }
                else if(nextDayEnd) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning!!");
                    alert.setContentText("Selected End time is at or past midnight! Please select next day!");
                    alert.showAndWait();
                }

                 */
                else{
                    psti.setString(1, title);
                    psti.setString(2, desc);
                    psti.setString(3, loc);
                    psti.setString(4, type);
                    psti.setString(5, String.valueOf(finalStartTime));
                    psti.setString(6, String.valueOf(finalEndTime));
                    psti.setString(7, custID);
                    psti.setString(8, userID);
                    psti.setString(9, String.valueOf(contID));
                    psti.execute();

                    stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/CustomerMenu.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }

            }


        } catch (SQLException e) {
            //e.printStackTrace();
        } catch (NumberFormatException Ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("Enter valid values in all text fields!");
            alert.showAndWait();
        }


    }

    public void onActionAddCustCancelBttn(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomerMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void onActionAddAppTypeCombo(ActionEvent actionEvent) {
    }


}
