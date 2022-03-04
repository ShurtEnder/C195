package main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

import DBA.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.DBProvider;
import model.DBProviderDID;
import model.DataProvider;

import static DBA.JDBC.connection;
import static java.util.ResourceBundle.getBundle;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainMenu.fxml")));
        primaryStage.setTitle("First View");
        primaryStage.setScene(new Scene(root, 1200, 400));
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

        System.out.println(zoneStrEST);*/

        ResultSet rs = null;
        try {
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM client_schedule.customers");
            while(rs.next()){
                int custID = Integer.parseInt(rs.getString(1));
                String custName = rs.getString(2);
                String custAdd = rs.getString(3);
                String custPC = rs.getString(4);
                String custPhone = rs.getString(5);
                int custDID = Integer.parseInt(rs.getString(10));
                DBProvider dbInfo = new DBProvider(custID,custName,custAdd,custPC, custPhone, custDID);
                DataProvider.addCustomer(dbInfo);

            }
            rs = stmt.executeQuery("SELECT * FROM client_schedule.countries");
            while(rs.next()){
                int countryID = Integer.parseInt(rs.getString(1));
                String country = rs.getString(2);
                DBProviderDID dbCountry = new DBProviderDID(countryID,country);
                DataProvider.addCountry(dbCountry);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }




        launch(args);

    }

}
