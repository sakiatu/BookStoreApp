package com.beecoder.bookstore;

public class Users {

    String name;
    String userid;
    String phoneNumber;

   /* public Users(String name,String userid,String phoneNumber) {
        this.name = name;
        this.userid=userid;
        this.phoneNumber=phoneNumber;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
