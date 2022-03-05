package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;

public class AppointmentWeekMonth {
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

    Stage stage;
    Parent scene;

    public void onActionMonthRBttn(ActionEvent actionEvent) {
    }

    public void onActionWeekRBttn(ActionEvent actionEvent) {
    }

    public void onActionBackBttn(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
