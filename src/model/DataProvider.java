package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataProvider {
    private static ObservableList<DBCustomer> allCustomers = FXCollections.observableArrayList();
    private static ObservableList<DBCountryDID> allCountry = FXCollections.observableArrayList();
    private static ObservableList<DBAppointment> allAppointments = FXCollections.observableArrayList();

    public static void addCustomer(DBCustomer cust){
        allCustomers.add(cust);
    }

    public static ObservableList<DBCustomer> getAllCustomers(){
        return allCustomers;
    }

    public static void addCountry(DBCountryDID country){
        allCountry.add(country);
    }

    public static ObservableList<DBCountryDID> getAllCountry() {
        return allCountry;
    }

    public static void addAppointment(DBAppointment appointment){
        allAppointments.add(appointment);
    }

    public static ObservableList<DBAppointment> getAllAppointments() {
        return allAppointments;
    }
}
