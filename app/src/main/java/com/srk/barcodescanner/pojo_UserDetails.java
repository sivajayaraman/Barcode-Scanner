package com.srk.barcodescanner;

import java.io.Serializable;

public class pojo_UserDetails implements Serializable {
    public String userName;
    public String emailId;
    public String collegeName;
    public String phoneNumber;
    public boolean registered;
    public String uniqueId;
    public boolean veg;
    public String barCodeValue;

    public pojo_UserDetails() {
    }

    public pojo_UserDetails(String userName, String emailId, String collegeName, String phoneNumber, String uniqueId, boolean veg) {
        this.userName = userName;
        this.emailId = emailId;
        this.collegeName = collegeName;
        this.phoneNumber = phoneNumber;
        this.uniqueId = uniqueId;
        this.veg = veg;
        this.registered=false;
    }
}
