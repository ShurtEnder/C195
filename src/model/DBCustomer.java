package model;

public class DBCustomer {
    /** Customer ID, Customer Division ID. Create IDs for all values*/
    private int custID,custDID;
    /** Customer Name, Customer Address, Customer Postal Code, Customer Phone. Create strings for all values*/
    private String custName, custAdd, custPC, custPhone;

    /**
     * DB Customer constructor. For constructing new customers.
     * @param custID the country id to set
     * @param custName the customer name to set
     * @param custAdd customer address to set
     * @param custPC customer postal code to set
     * @param custPhone the customer phone to set
     * @param custDID the customer division ID to set
     */
    public DBCustomer(int custID, String custName, String custAdd, String custPC, String custPhone, int custDID){
        this.custID = custID;
        this.custName = custName;
        this.custAdd = custAdd;
        this.custPC = custPC;
        this.custPhone = custPhone;
        this.custDID = custDID;
    }

    /**
     * @return The customer ID.
     */
    public int getCustID() {
        return custID;
    }

    /**
     * @param custID the custID to set
     */
    public void setCustID(int custID) {
        this.custID = custID;
    }

    /**
     * @return The customer division ID.
     */
    public int getCustDID() {
        return custDID;
    }

    /**
     * @param custDID the customerDID to set
     */
    public void setCustDID(int custDID) {
        this.custDID = custDID;
    }

    /**
     * @return THe customer name.
     */
    public String getCustName() {
        return custName;
    }

    /**
     * @param custName the custName to set
     */
    public void setCustName(String custName) {
        this.custName = custName;
    }

    /**
     * @return The customer address.
     */
    public String getCustAdd() {
        return custAdd;
    }

    /**
     * @param custAdd the custAdd to set
     */
    public void setCustAdd(String custAdd) {
        this.custAdd = custAdd;
    }

    /**
     * @return The customer postal code.
     */
    public String getCustPC() {
        return custPC;
    }

    /**
     * @param custPC the custPC to set
     */
    public void setCustPC(String custPC) {
        this.custPC = custPC;
    }

    /**
     * @return The customer phone.
     */
    public String getCustPhone() {
        return custPhone;
    }

    /**
     * @param custPhone custPhone to set
     */
    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }
}
