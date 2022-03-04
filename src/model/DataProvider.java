package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataProvider {
    private static ObservableList<DBProvider> allCustomers = FXCollections.observableArrayList();
    private static ObservableList<DBProviderDID> allCountry = FXCollections.observableArrayList();

    public static void addCustomer(DBProvider cust){
        allCustomers.add(cust);
    }

    public static ObservableList<DBProvider> getAllCustomers(){
        return allCustomers;
    }
    public static void addCountry(DBProviderDID country){
        allCountry.add(country);
    }

    public static ObservableList<DBProviderDID> getAllCountry() {
        return allCountry;
    }
}
