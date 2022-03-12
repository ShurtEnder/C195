package controller;

import Interface.combString;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.DBAppointment;
import model.DataProvider;
import model.IOClass;
import model.TimeFunctions;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.Optional;
import java.util.ResourceBundle;

import static DBA.JDBC.connection;

public class AppointmentMenu implements Initializable {
    public Label selectionMWLbl;
    public ToggleGroup MonthWeek;
    public TableColumn appVAppIDCol,appVTitleCol,appVDescCol,appVLocCol,appVContactCol,appVTypeCol,appVStartCol,appVEndCol,appVCustIDCol,appVUserIDCol;;
    public TableView appVAppTableView;
    public RadioButton noFilterToggleBttn;
    private String sqlQuery = "DELETE FROM client_schedule.appointments WHERE Appointment_ID = ?";
    private String sqlQuery2 = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID FROM client_schedule.appointments WHERE Start BETWEEN ? AND ?";
    private LocalDate localDate, localDateWStart, localDateWEnd,localDateMStart, localDateMEnd;

    Stage stage;
    Parent scene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ResultSet rs = null;
        appVAppTableView.setItems(DataProvider.getAllAppointments());
        appVCustIDCol.setCellValueFactory(new PropertyValueFactory<>("CustID"));
        appVUserIDCol.setCellValueFactory(new PropertyValueFactory<>("UserID"));
        appVContactCol.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        appVTitleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
        appVDescCol.setCellValueFactory(new PropertyValueFactory<>("Desc"));
        appVLocCol.setCellValueFactory(new PropertyValueFactory<>("Loc"));
        appVTypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        appVStartCol.setCellValueFactory(new PropertyValueFactory<>("localStart"));
        appVEndCol.setCellValueFactory(new PropertyValueFactory<>("localEnd"));
        appVAppIDCol.setCellValueFactory(new PropertyValueFactory<>("AppID"));

        if(!(DataProvider.getAllAppointments().isEmpty())){
            DataProvider.getAllAppointments().clear();
        }
        try {
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID FROM client_schedule.appointments;");
            while(rs.next()){
                int appID = rs.getInt(1);
                int custID = rs.getInt(8);
                int userID = rs.getInt(9);
                int contID = rs.getInt(10);
                String title = rs.getString(2);
                String desc = rs.getString(3);
                String loc = rs.getString(4);
                String type = rs.getString(5);
                LocalDateTime start = Timestamp.valueOf(rs.getString(6)).toLocalDateTime();
                LocalDateTime end = Timestamp.valueOf(rs.getString(7)).toLocalDateTime();
                String localStart = TimeFunctions.formDTF(TimeFunctions.getUTCtoLoc(start).toLocalDateTime());
                String localEnd = TimeFunctions.formDTF(TimeFunctions.getUTCtoLoc(end).toLocalDateTime());
                DBAppointment appointment = new DBAppointment(appID,custID,userID,contID,title,desc,loc,type,start,end, localStart,localEnd);
                DataProvider.addAppointment(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        localDate = LocalDate.now();
        localDateWEnd = localDate.plusDays(6-localDate.get(ChronoField. DAY_OF_WEEK));
        localDateWStart = localDateWEnd.minusDays(6);
        localDateMStart = localDate.withDayOfMonth(1);
        localDateMEnd = localDate.withDayOfMonth(localDate.lengthOfMonth());
    }

    public void onActionMonthRBttn(ActionEvent actionEvent) {
        ResultSet rs = null;
        if(!(DataProvider.getAllAppointments().isEmpty())){
            DataProvider.getAllAppointments().clear();
        }
        try {
            PreparedStatement psti = connection.prepareStatement(sqlQuery2);
            psti.setString(1, String.valueOf(localDateMStart));
            psti.setString(2, String.valueOf(localDateMEnd));
            rs = psti.executeQuery();
            while(rs.next()){
                int appID = rs.getInt(1);
                int custID = rs.getInt(8);
                int userID = rs.getInt(9);
                int contID = rs.getInt(10);
                String title = rs.getString(2);
                String desc = rs.getString(3);
                String loc = rs.getString(4);
                String type = rs.getString(5);
                LocalDateTime start = Timestamp.valueOf(rs.getString(6)).toLocalDateTime();
                LocalDateTime end = Timestamp.valueOf(rs.getString(7)).toLocalDateTime();
                String localStart = TimeFunctions.formDTF(TimeFunctions.getUTCtoLoc(start).toLocalDateTime());
                String localEnd = TimeFunctions.formDTF(TimeFunctions.getUTCtoLoc(end).toLocalDateTime());
                DBAppointment appointment = new DBAppointment(appID,custID,userID,contID,title,desc,loc,type,start,end, localStart,localEnd);
                DataProvider.addAppointment(appointment);
                //Lambda Expression
                combString stringComb = s -> {
                    s = TimeFunctions.getLoctoUTC(LocalDateTime.now()) + " User " + LoginPage.userID + ": " + s;
                    return s;
                };
                IOClass.insertLog(stringComb.cString("Month radio button selected"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onActionWeekRBttn(ActionEvent actionEvent) {
        ResultSet rs = null;
        if(!(DataProvider.getAllAppointments().isEmpty())){
            DataProvider.getAllAppointments().clear();
        }
        try {
            PreparedStatement psti = connection.prepareStatement(sqlQuery2);
            psti.setString(1, String.valueOf(localDateWStart));
            psti.setString(2, String.valueOf(localDateWEnd));
            rs = psti.executeQuery();
            while(rs.next()){
                int appID = rs.getInt(1);
                int custID = rs.getInt(8);
                int userID = rs.getInt(9);
                int contID = rs.getInt(10);
                String title = rs.getString(2);
                String desc = rs.getString(3);
                String loc = rs.getString(4);
                String type = rs.getString(5);
                LocalDateTime start = Timestamp.valueOf(rs.getString(6)).toLocalDateTime();
                LocalDateTime end = Timestamp.valueOf(rs.getString(7)).toLocalDateTime();
                String localStart = TimeFunctions.formDTF(TimeFunctions.getUTCtoLoc(start).toLocalDateTime());
                String localEnd = TimeFunctions.formDTF(TimeFunctions.getUTCtoLoc(end).toLocalDateTime());
                DBAppointment appointment = new DBAppointment(appID,custID,userID,contID,title,desc,loc,type,start,end, localStart,localEnd);
                DataProvider.addAppointment(appointment);
                //Lambda Expression
                combString stringComb = s -> {
                    s = TimeFunctions.getLoctoUTC(LocalDateTime.now()) + " User " + LoginPage.userID + ": " + s;
                    return s;
                };
                IOClass.insertLog(stringComb.cString("Week radio button selected"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onActionNoFilterRBttn(ActionEvent actionEvent) {
        ResultSet rs = null;
        if(!(DataProvider.getAllAppointments().isEmpty())){
            DataProvider.getAllAppointments().clear();
        }
        try {
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID FROM client_schedule.appointments;");
            while(rs.next()){
                int appID = rs.getInt(1);
                int custID = rs.getInt(8);
                int userID = rs.getInt(9);
                int contID = rs.getInt(10);
                String title = rs.getString(2);
                String desc = rs.getString(3);
                String loc = rs.getString(4);
                String type = rs.getString(5);
                LocalDateTime start = Timestamp.valueOf(rs.getString(6)).toLocalDateTime();
                LocalDateTime end = Timestamp.valueOf(rs.getString(7)).toLocalDateTime();
                String localStart = TimeFunctions.formDTF(TimeFunctions.getUTCtoLoc(start).toLocalDateTime());
                String localEnd = TimeFunctions.formDTF(TimeFunctions.getUTCtoLoc(end).toLocalDateTime());
                DBAppointment appointment = new DBAppointment(appID,custID,userID,contID,title,desc,loc,type,start,end, localStart,localEnd);
                DataProvider.addAppointment(appointment);
                //Lambda Expression
                combString stringComb = s -> {
                    s = TimeFunctions.getLoctoUTC(LocalDateTime.now()) + " User " + LoginPage.userID + ": " + s;
                    return s;
                };
                IOClass.insertLog(stringComb.cString("No filter radio button selected"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onActionUpdateAppBttn(ActionEvent actionEvent) throws IOException {
        try {
            //Lambda Expression
            combString stringComb = s -> {
                s = TimeFunctions.getLoctoUTC(LocalDateTime.now()) + " User " + LoginPage.userID + ": " + s;
                return s;
            };
            IOClass.insertLog(stringComb.cString("Update application button hit"));
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/UpdateAppointment.fxml"));
            loader.load();
            UpdateAppointment UPAppointment = loader.getController();
            UPAppointment.sendUpApp((DBAppointment) appVAppTableView.getSelectionModel().getSelectedItem());
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NullPointerException Te){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("No appointment selected!");
            alert.showAndWait();
        }
    }

    public void onActionRemoveAppBttn(ActionEvent actionEvent) {
        try {
            ResultSet rs = null;
            if (appVAppTableView.getSelectionModel().getSelectedItem() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error!");
                alert.setContentText("No appointment selected!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Are you sure you want to delete the appointment?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    int appID = ((DBAppointment) appVAppTableView.getSelectionModel().getSelectedItem()).getAppID();
                    PreparedStatement psti = connection.prepareStatement(sqlQuery);
                    psti.setInt(1, appID);
                    psti.execute();
                    //Lambda Expression
                    combString stringComb = s -> {
                        s = TimeFunctions.getLoctoUTC(LocalDateTime.now()) + " User " + LoginPage.userID + ": " + s;
                        return s;
                    };
                    IOClass.insertLog(stringComb.cString("Appointment ID: " + appID + " was removed!"));
                    DataProvider.getAllAppointments().clear();
                    Statement stmt = connection.createStatement();
                    rs = stmt.executeQuery("SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID FROM client_schedule.appointments;");
                    while (rs.next()) {
                        appID = rs.getInt(1);
                        int custID = rs.getInt(8);
                        int userID = rs.getInt(9);
                        int contID = rs.getInt(10);
                        String title = rs.getString(2);
                        String desc = rs.getString(3);
                        String loc = rs.getString(4);
                        String type = rs.getString(5);
                        LocalDateTime start = Timestamp.valueOf(rs.getString(6)).toLocalDateTime();
                        LocalDateTime end = Timestamp.valueOf(rs.getString(7)).toLocalDateTime();
                        String localStart = TimeFunctions.formDTF(TimeFunctions.getUTCtoLoc(start).toLocalDateTime());
                        String localEnd = TimeFunctions.formDTF(TimeFunctions.getUTCtoLoc(end).toLocalDateTime());
                        DBAppointment appointment = new DBAppointment(appID,custID,userID,contID,title,desc,loc,type,start,end, localStart,localEnd);
                        DataProvider.addAppointment(appointment);
                    }
                    noFilterToggleBttn.setSelected(true);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onActionBackBttn(ActionEvent actionEvent) throws IOException {
        //Lambda Expression
        combString stringComb = s -> {
            s = TimeFunctions.getLoctoUTC(LocalDateTime.now()) + " User " + LoginPage.userID + ": " + s;
            return s;
        };
        IOClass.insertLog(stringComb.cString("Back button hit, going back to Start Menu"));
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/StartMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }


}
