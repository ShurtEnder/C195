package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Date Provider Class.
 * @author Rene Gomez Student ID: 001467443
 */
public class DataProvider {
    /** All Customers. Creates a lists for all customers to be stored in. */
    private static ObservableList<DBCustomer> allCustomers = FXCollections.observableArrayList();
    /** All Country Division ID. Creates a lists for all country Division ID to be stored in. */
    private static ObservableList<DBCountryDID> allCountry = FXCollections.observableArrayList();
    /** All Appointments. Creates a lists for all appointments to be stored in. */
    private static ObservableList<DBAppointment> allAppointments = FXCollections.observableArrayList();
    /** New/Add Counter. Keeps track of new customer/appointment additions for the lambda expression*/
    private static int newCounter;
    /** Update Counter. Keeps track of customer/appointment updates for the lambda expression*/
    private static int upCounter;

    /**
     * Add Customer.
     * Adds a new customer into the all customer list.
     * @param cust the new customer is set to
     */
    public static void addCustomer(DBCustomer cust){
        allCustomers.add(cust);
    }

    /**
     * Get Customers.
     * Returns all customers in all customers list.
     * @return all customers.
     */
    public static ObservableList<DBCustomer> getAllCustomers(){
        return allCustomers;
    }

    /**
     * Add Country.
     * Adds a new Country into the all country Division ID list.
     * @param country the new country is set to
     */
    public static void addCountry(DBCountryDID country){
        allCountry.add(country);
    }

    /**
     * Get Country Division ID.
     * Returns all country Division ID in all country Division ID list.
     * @return all country Division ID.
     */
    public static ObservableList<DBCountryDID> getAllCountry() {
        return allCountry;
    }

    /**
     * Add Appointment.
     * Adds a new appointment into the all appointment list.
     * @param appointment the new appointment is set to
     */
    public static void addAppointment(DBAppointment appointment){
        allAppointments.add(appointment);
    }

    /**
     * Get Appointments.
     * Returns all appointments in all appointments list.
     * @return all appointments.
     */
    public static ObservableList<DBAppointment> getAllAppointments() {
        return allAppointments;
    }

    /**
     *  Get New Counter.
     *  Returns new counter int.
     * @return NewCounter
     */
    public static int getNewCounter() {
        return newCounter;
    }

    /**
     * Sets new counter.
     * @param newCounter set NewCounter to
     */
    public static void setNewCounter(int newCounter) {
        DataProvider.newCounter = newCounter;
    }

    /**
     *  Get Update Counter.
     *  Returns new update int.
     * @return UpCounter
     */
    public static int getUpCounter() {
        return upCounter;
    }

    /**
     * Sets update counter.
     * @param upCounter set UpCounter to
     */
    public static void setUpCounter(int upCounter) {
        DataProvider.upCounter = upCounter;
    }
}
