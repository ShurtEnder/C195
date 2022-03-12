package model;

import java.time.LocalDateTime;

public class DBAppointment {
    private int AppID, CustID, UserID, Contact;
    private String Title, Loc, Type, Desc, localStart, localEnd;
    private LocalDateTime Start, End;

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

    public int getAppID() {
        return AppID;
    }

    public void setAppID(int appID) {
        AppID = appID;
    }

    public int getCustID() {
        return CustID;
    }

    public void setCustID(int custID) {
        CustID = custID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getLoc() {
        return Loc;
    }

    public void setLoc(String loc) {
        Loc = loc;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        this.Desc = desc;
    }

    public LocalDateTime getStart() {
        return Start;
    }

    public void setStart(LocalDateTime start) {
        Start = start;
    }

    public LocalDateTime getEnd() {
        return End;
    }

    public void setEnd(LocalDateTime end) {
        End = end;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public int getContact() {
        return Contact;
    }

    public void setContact(int contact) {
        Contact = contact;
    }

    public String getLocalStart() {
        return localStart;
    }

    public void setLocalStart(String localStart) {
        this.localStart = localStart;
    }

    public String getLocalEnd() {
        return localEnd;
    }

    public void setLocalEnd(String localEnd) {
        this.localEnd = localEnd;
    }
}
