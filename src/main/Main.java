package main;

import java.util.*;

import DBA.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static java.util.ResourceBundle.getBundle;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/StartMenu.fxml")));
        primaryStage.setTitle("First View");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();


    }

    @Override
    public void init(){


    }

    public static void main(String[] args){
        JDBC.openConnection();

      /*  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyy HH:mm");
        ZoneId zoneID = ZoneId.systemDefault();
        ZoneId zoneIDEST = ZoneId.of("UTC");

        LocalDateTime test = LocalDateTime.now();

        *//* Sets zone date time depending on the Zone(UTC/EST) *//*
        ZonedDateTime zoneStr = test.atZone(zoneID);
        ZonedDateTime zoneStrUTC = zoneStr.withZoneSameInstant(ZoneOffset.UTC);

        ZonedDateTime zoneStrEST = zoneStr.withZoneSameInstant(ZoneId.of("America/New_York").getRules().getOffset(Instant.now()));


        *//* Sets Local Time/Date to separate lines *//*
        LocalTime locTime = test.toLocalTime();
        LocalDate locDate = test.toLocalDate();

        *//* Combines two date/time objs back to a LocalDateTime obj *//*
        LocalDateTime.of(locDate, locTime);

        *//* Sets a string to a human-readable form depending on the dtf Format of above *//*
        String newForm = test.format(dtf);

        *//* Converts date string back to an LocalDateTime obj using the dtf formatting *//*
        LocalDateTime.parse(newForm, dtf);

        *//* Converts zone back to a LocalDateTime obj *//*
        zoneStr.toLocalDate();
        System.out.println(zoneStr);
        System.out.println(zoneStrUTC);

        System.out.println(zoneStrEST);


        String test = "2020-05-28 12:00:00";
        LocalDateTime testLDT = Timestamp.valueOf(test).toLocalDateTime();
        System.out.println(testLDT);
        LocalDate dateStr = TimeFunctions.zdtToDate(testLDT);
        LocalTime timeStr = TimeFunctions.zdtToTime(testLDT);
        System.out.println(dateStr + " " +timeStr);
        */



        launch(args);

    }

}
