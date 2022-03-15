package controller;

import Interface.Counter;
import Interface.combString;
import com.mysql.cj.log.Log;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.DBAppointment;
import model.DataProvider;
import model.IOClass;
import model.TimeFunctions;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static DBA.JDBC.connection;

/**
 * Update Appointment Class.
 * @author Rene Gomez Student ID: 001467443
 */
public class UpdateAppointment {
    public TextField upAppAppIDTxt,upAppCustIDTxt,upAppUserIDTxt,upAppTitleTxt,upAppLocTxt;
    public TextArea upAppDescTxt;
    public ComboBox upAppContNameCombo,upAppStartCombo,upAppEndCombo,upAppTypeCombo;
    public DatePicker upAppStartDateCal,upAppEndDateCal;
    private LocalDate localDate;
    private ObservableList typeList = FXCollections.observableArrayList("Planning Session","De-Briefing", "Meeting", "Break");
    private ObservableList contList = FXCollections.observableArrayList();
    private ObservableList timeList = FXCollections.observableArrayList();
    private String sqlQuery = "UPDATE client_schedule.appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
    private boolean overLap = false;
    private boolean busHours = true;
    private ArrayList overlapList = new ArrayList<>();

    Stage stage;
    Parent scene;

    /**
     * Send Appointment.
     * Allows data transfer from the Appointment Menu so that the selected customer is shown. It also sets the appropriate values to the labels/combo boxes.
     * @param appointment the appointment is set to
     */
    public void sendUpApp(DBAppointment appointment){
        ResultSet rs = null;
        upAppAppIDTxt.setText(String.valueOf(appointment.getAppID()));
        upAppCustIDTxt.setText(String.valueOf(appointment.getCustID()));
        upAppUserIDTxt.setText(String.valueOf(appointment.getUserID()));
        upAppTitleTxt.setText(String.valueOf(appointment.getTitle()));
        upAppDescTxt.setText(String.valueOf(appointment.getDesc()));
        upAppLocTxt.setText(String.valueOf(appointment.getLoc()));
        upAppTypeCombo.setValue(String.valueOf(appointment.getType()));

        ZonedDateTime startLDTLoc = TimeFunctions.getUTCtoLoc(appointment.getStart());
        LocalTime startTimeloc = startLDTLoc.toLocalTime();
        LocalDate startDateLoc = startLDTLoc.toLocalDate();
        ZonedDateTime endLDTLoc = TimeFunctions.getUTCtoLoc(appointment.getEnd());
        LocalTime endTimeloc = endLDTLoc.toLocalTime();
        LocalDate endDateLoc = endLDTLoc.toLocalDate();

        upAppStartCombo.setValue(startTimeloc);
        upAppStartDateCal.setValue(startDateLoc);
        upAppEndCombo.setValue(endTimeloc);
        upAppEndDateCal.setValue(endDateLoc);

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

        upAppStartCombo.setItems(timeList);
        upAppEndCombo.setItems(timeList);
        upAppTypeCombo.setItems(typeList);

        try {
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT Contact_ID,Contact_Name FROM client_schedule.contacts");
            while(rs.next()){
                contList.add(rs.getString(2));
                if(rs.getInt(1) == appointment.getContact()){
                    upAppContNameCombo.setValue(rs.getString(2));
                }
            }
            upAppContNameCombo.setItems(contList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update Appointment Save Button.
     * Attempts to update the appointment and change stage back to the Appointment Menu FXML. Also checks for logical errors, if any are found it displays an error/warning dialog box.
     * @param actionEvent UpAppSaveBttn click
     * @throws IOException
     * <p><b>
     *     LAMBDA EXPRESSIONS
     * </b></p>
     * <p><b>
     *     The first expression combines a string and inserts it into the log. The second expression increase the Addition Counter.
     * </b></p>
     */
    public void onActionUpAppSaveBttn(ActionEvent actionEvent){
        //Lambda Expression
        Counter incUpCounter = c -> {
            c++;
            DataProvider.setUpCounter(c);
            return c;
        };
        try{
            overLap = false;
            overlapList.clear();
            PreparedStatement psti = connection.prepareStatement(sqlQuery);
            String strQuery = "SELECT Contact_ID FROM client_schedule.contacts WHERE Contact_Name = ?";
            PreparedStatement psti2 = connection.prepareStatement(strQuery);
            ResultSet rs = null;

            String custID = upAppCustIDTxt.getText();
            String userID = upAppUserIDTxt.getText();
            String title = upAppTitleTxt.getText();
            String desc = upAppDescTxt.getText();
            String loc = upAppLocTxt.getText();
            String type = String.valueOf(upAppTypeCombo.getSelectionModel().getSelectedItem());
            String cont = String.valueOf(upAppContNameCombo.getSelectionModel().getSelectedItem());
            String appID = upAppAppIDTxt.getText();

            LocalTime startT = LocalTime.parse(String.valueOf(upAppStartCombo.getSelectionModel().getSelectedItem()));
            LocalDate startD = LocalDate.parse(String.valueOf(upAppStartDateCal.getValue()));
            LocalDateTime startComb = TimeFunctions.getLoctoUTC(TimeFunctions.combDT(startD, startT)).toLocalDateTime();
            startT = startComb.toLocalTime();
            startD = startComb.toLocalDate();
            Timestamp finalStartTime = Timestamp.valueOf(startComb);

            LocalTime endT = LocalTime.parse(String.valueOf(upAppEndCombo.getSelectionModel().getSelectedItem()));
            LocalDate endD = LocalDate.parse(String.valueOf(upAppEndDateCal.getValue()));
            LocalDateTime endComb = TimeFunctions.getLoctoUTC(TimeFunctions.combDT(endD, endT)).toLocalDateTime();
            endT = endComb.toLocalTime();
            endD = endComb.toLocalDate();
            Timestamp finalEndTime = Timestamp.valueOf(endComb);

            int busStartind = timeList.indexOf(upAppStartCombo.getSelectionModel().getSelectedItem());
            int busEndind = timeList.indexOf(upAppEndCombo.getSelectionModel().getSelectedItem());

            if(busEndind < busStartind){
                busHours = false;
            } else{
                busHours = true;
            }

            psti2.setString(1, String.valueOf(cont));
            rs = psti2.executeQuery();
            int contID = 0;
            while (rs.next()){
                contID = rs.getInt(1);
            }
            if(title.isEmpty() || desc.isEmpty() || loc.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setContentText("Text fields are empty!");
                alert.showAndWait();
            }
            else if(!(busHours)){
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
                    int i = Integer.parseInt(appID);
                    LocalDate checkDateStart = app.getStart().toLocalDate();
                    LocalTime checkTimeStart = app.getStart().toLocalTime();
                    LocalDate checkDateEnd = app.getEnd().toLocalDate();
                    LocalTime checkTimeEnd = app.getEnd().toLocalTime();
                    if(startD.equals(checkDateStart) && !(app.getAppID() == Integer.parseInt(appID)) ){
                        if((startT.isAfter(checkTimeStart) || startT.equals(checkTimeStart)) && startT.isBefore(checkTimeEnd)){
                            overLap = true;
                        }
                        else if(endT.isAfter(checkTimeStart) && (endT.isBefore(checkTimeEnd) || endT.equals(checkTimeEnd))){
                            overLap = true;
                        }
                        else if((startT.isBefore(checkTimeStart) || startT.equals(checkTimeStart)) && (endT.isAfter(checkTimeEnd) || endT.equals(checkTimeEnd))){
                            overLap = true;
                        }
                        else if (overLap){

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
                    psti.setString(10,appID);
                    psti.execute();
                    //Lambda Expression
                    incUpCounter.addCounter(DataProvider.getUpCounter());

                    //Lambda Expression
                    combString stringComb = s -> {
                        s = TimeFunctions.getLoctoUTC(LocalDateTime.now()) + " User " + LoginPage.userNameUsed + ": " + s;
                        return s;
                    };
                    IOClass.insertLog(stringComb.cString("Appointment ID: " + appID + " has been updated!"));

                    stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/AppointmentMenu.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }

            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("Enter/select valid values in all fields!");
            alert.showAndWait();
        } catch (NumberFormatException Ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("Enter valid values in all text fields!");
            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cancel Button.
     * Changes stage to the Appointment Menu FXML, but first asks user to confirm action.
     * @param actionEvent cancelbttn click
     * @throws IOException
     * <p><b>
     *     LAMBDA EXPRESSION
     * </b></p>
     * <p><b>
     *     This expression combines a string and inserts it into the log.
     * </b></p>
     */
    public void onActionUpCustCancelBttn(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("All values will be cleared! Do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            //Lambda Expression
            combString stringComb = s -> {
                s = TimeFunctions.getLoctoUTC(LocalDateTime.now()) + " User " + LoginPage.userNameUsed + ": " + s;
                return s;
            };
            IOClass.insertLog(stringComb.cString("Cancel button hit, going back to Appointment Menu"));

            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/AppointmentMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }
}
