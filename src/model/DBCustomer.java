package model;

public class DBCustomer {
    private int custID,custDID;
    private String custName, custAdd, custPC, custPhone;

    public DBCustomer(int custID, String custName, String custAdd, String custPC, String custPhone, int custDID){
        this.custID = custID;
        this.custName = custName;
        this.custAdd = custAdd;
        this.custPC = custPC;
        this.custPhone = custPhone;
        this.custDID = custDID;
    }

    public int getCustID() {
        return custID;
    }

    public void setCustID(int custID) {
        this.custID = custID;
    }

    public int getCustDID() {
        return custDID;
    }

    public void setCustDID(int custDID) {
        this.custDID = custDID;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustAdd() {
        return custAdd;
    }

    public void setCustAdd(String custAdd) {
        this.custAdd = custAdd;
    }

    public String getCustPC() {
        return custPC;
    }

    public void setCustPC(String custPC) {
        this.custPC = custPC;
    }

    public String getCustPhone() {
        return custPhone;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }
}
