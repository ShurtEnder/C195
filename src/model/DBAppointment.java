package model;

import java.time.LocalDateTime;

/**
 * DB Appointment class.
 * @author Rene Gomez Student ID: 001467443
 */
public class DBAppointment {
    /** Appointment ID, Customer ID, User ID, Contact. Create IDs for all values*/
    private int AppID, CustID, UserID, Contact;
    /** Title, Location, Type, Description, Start Date/Time, End Date/Time. Create strings variables for all values.*/
    private String Title, Loc, Type, Desc, localStart, localEnd;
    /** Start Date/Time, End Date/Time. Creates LocalDateTime variables for all values.*/
    private LocalDateTime Start, End;

    /**
     * Appointment CConstructor. For constructing new appointments.
     * @param AppID the Appointment ID to set.
     * @param CustID the Customer ID to set.
     * @param UserID the User ID to set.
     * @param Contact the Contact to set.
     * @param Title the Title to set.
     * @param Desc the Description to set.
     * @param Loc the Location to set.
     * @param Type the Type to set.
     * @param Start the LDT Start Date/Time to set.
     * @param End the End LDT Date/Time to set.
     * @param localStart the Start Date/Time to set.
     * @param localEnd the End Date/Time to set.
     */
    public DBAppointment(int AppID, int CustID, int UserID ,int Contact,String Title, String Desc, String Loc, String Type, LocalDateTime Start, LocalDateTime End, String localStart, String localEnd ){
        this.AppID = AppID;
        this.CustID = CustID;
        this.Title = Title;
        this.Loc = Loc;
        this.Type = Type;
        this.Start = Start;
        this.End = End;
        this.Desc = Desc;
        this.UserID = UserID;
        this.Contact = Contact;
        this.localStart = localStart;
        this.localEnd = localEnd;
    }

    /**
     * @return The AppID.
     */
    public int getAppID() {
        return AppID;
    }

    /**
     * @param appID the appID to set
     */
    public void setAppID(int appID) {
        AppID = appID;
    }

    /**
     * @return The CustID.
     */
    public int getCustID() {
        return CustID;
    }

    /**
     * @param custID the custID to set
     */
    public void setCustID(int custID) {
        CustID = custID;
    }

    /**
     * @return The Title.
     */
    public String getTitle() {
        return Title;
    }

    /**
     *
     * @param title the title to set
     */
    public void setTitle(String title) {
        Title = title;
    }

    /**
     * @return the Location.
     */
    public String getLoc() {
        return Loc;
    }

    /**
     *
     * @param loc the loc to set.
     */
    public void setLoc(String loc) {
        Loc = loc;
    }

    /**
     * @return the type.
     */
    public String getType() {
        return Type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        Type = type;
    }

    /**
     * @return The description.
     */
    public String getDesc() {
        return Desc;
    }

    /**
     * @param desc the desc to set
     */
    public void setDesc(String desc) {
        this.Desc = desc;
    }

    /**
     * @return The Start.
     */
    public LocalDateTime getStart() {
        return Start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(LocalDateTime start) {
        Start = start;
    }

    /**
     * @return The End.
     */
    public LocalDateTime getEnd() {
        return End;
    }

    /**
     * @param end the end to set
     */
    public void setEnd(LocalDateTime end) {
        End = end;
    }

    /**
     * @return The User ID.
     */
    public int getUserID() {
        return UserID;
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(int userID) {
        UserID = userID;
    }

    /**
     * @return The Contact.
     */
    public int getContact() {
        return Contact;
    }

    /**
     * @param contact the contact to set
     */
    public void setContact(int contact) {
        Contact = contact;
    }

    /**
     * @return The Local Start
     */
    public String getLocalStart() {
        return localStart;
    }

    /**
     * @param localStart the localStart to set
     */
    public void setLocalStart(String localStart) {
        this.localStart = localStart;
    }

    /**
     * @return The Lcaol End.
     */
    public String getLocalEnd() {
        return localEnd;
    }

    /**
     * @param localEnd localEnd to set
     */
    public void setLocalEnd(String localEnd) {
        this.localEnd = localEnd;
    }
}
