package model;

/**
 * DB Coutnry Division ID class.
 * @author Rene Gomez Student ID: 001467443
 */
public class DBCountryDID {
    /** Country ID. Creates a private integer for countryID.*/
    private int countryID;
    /** Country. Creates a private string for country.*/
    private String country;

    /**
     * Country Division ID constructor. For constructing new country divisions.
     * @param countryID the country id to set
     * @param country the country to set
     */
    public DBCountryDID(int countryID, String country){
        this.countryID = countryID;
        this.country = country;
    }

    /**
     * @return The Country ID.
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * @param countryID the countryID to set
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * @return The Country.
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }
}
