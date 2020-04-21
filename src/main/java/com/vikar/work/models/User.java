package com.vikar.work.models;

public class User {
    public long CVRNumber;
    public long bankNumber;
    public long zip;
    public long houseNumber;

    public String companyName;
    public String username;
    public String password1;
    public String password2;
    public String streetName;
    public String city;
    public String firstname;
    public String lastname;
    public String email;

    public User(long CVRNumber, long bankNumber, long zip, long houseNumber, String companyName, String username, String password1, String password2, String streetName, String city, String firstname, String lastname, String email) {
        this.CVRNumber = CVRNumber;
        this.bankNumber = bankNumber;
        this.zip = zip;
        this.houseNumber = houseNumber;
        this.companyName = companyName;
        this.username = username;
        this.password1 = password1;
        this.password1 = password1;
        this.streetName = streetName;
        this.city = city;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }
}
