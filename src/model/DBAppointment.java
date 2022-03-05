package model;

import java.time.LocalDateTime;

public class DBAppointment {
    private int AppID, CustID;
    private String Title, Loc, Type, combSE;
    private LocalDateTime Start, End;

    public DBAppointment(int AppID, int CustID,String Title, String Loc, String Type, LocalDateTime Start, LocalDateTime End, String combSE){
        this.AppID = AppID;
        this.CustID = CustID;
        this.Title = Title;
        this.Loc = Loc;
        this.Type = Type;
        this.Start = Start;
        this.End = End;
        this.combSE = combSE;
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

    public String getCombSE() {
        return combSE;
    }

    public void setCombSE(String combSE) {
        this.combSE = combSE;
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
}
