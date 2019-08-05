package com.srk.barcodescanner;

public class pojo_UserDetails {
    public String userName;
    public String emailId;
    public String collegeName;
    public String phoneNumber;
    public String uniqueId;
    public boolean veg;

    public pojo_UserDetails(String userName, String emailId, String collegeName, String phoneNumber, String uniqueId, boolean veg) {
        this.userName = userName;
        this.emailId = emailId;
        this.collegeName = collegeName;
        this.phoneNumber = phoneNumber;
        this.uniqueId = uniqueId;
        this.veg = veg;
    }
}
