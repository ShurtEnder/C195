package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.DBAppointment;
import model.DBCustomer;
import model.DataProvider;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import static DBA.JDBC.connection;

public class AppointmentMenu implements Initializable {
    public Label selectionMWLbl;
    public ToggleGroup MonthWeek;
    public TableColumn appVAppIDCol;
    public TableColumn appVTitleCol;
    public TableColumn appVDescCol;
    public TableColumn appVLocCol;
    public TableColumn appVContactCol;
    public TableColumn appVTypeCol;
    public TableColumn appVStartCol;
    public TableColumn appVEndCol;
    public TableColumn appVCustIDCol;
    public TableColumn appVUserIDCol;
    public TableView appVAppTableView;
    private String sqlQuery = "";

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
        appVStartCol.setCellValueFactory(new PropertyValueFactory<>("Start"));
        appVEndCol.setCellValueFactory(new PropertyValueFactory<>("End"));
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
                DBAppointment appointment = new DBAppointment(appID,custID,userID,contID,title,desc,loc,type,start,end);
                DataProvider.addAppointment(appointment);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void onActionMonthRBttn(ActionEvent actionEvent) {
    }

    public void onActionWeekRBttn(ActionEvent actionEvent) {
    }

    public void onActionNoFilterRBttn(ActionEvent actionEvent) {
    }

    public void onActionUpdateAppBttn(ActionEvent actionEvent) throws IOException {
        try {
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
    }

    public void onActionBackBttn(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/StartMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }


}
