package com.vikar.work.models;

public class User {
    private long CVRNumber;
    private long bankNumber;
    private long zip;
    private long houseNumber;
    private long phoneNumber;

    private String type;
    private String companyName;
    private String username;
    private String password1;
    private String password2;
    private String streetName;
    private String city;
    public String firstname;
    private String lastname;
    private String email;



    public User() {
    }

    public User(long CVRNumber, long bankNumber, long zip, long houseNumber, long phonenumber, String type, String companyName, String username, String password1, String password2, String streetName, String city, String firstname, String lastname, String email) {
        this.CVRNumber = CVRNumber;
        this.bankNumber = bankNumber;
        this.zip = zip;
        this.houseNumber = houseNumber;
        this.phoneNumber = phonenumber;
        this.type = type;
        this.companyName = companyName;
        this.username = username;
        this.password1 = password1;
        this.password2 = password2;
        this.streetName = streetName;
        this.city = city;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public User(String username, String password1) {
        this.username = username;
        this.password1 = password1;
    }

    public User(long phoneNumber, String username, String password1, String password2, String firstname, String lastname, String email) {
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password1 = password1;
        this.password2 = password2;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;

        this.CVRNumber = 0;
        this.houseNumber = 0;
        this.zip = 0;
        this.bankNumber = 0;
    }


    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public long getCVRNumber() {
        return CVRNumber;
    }

    public void setCVRNumber(long CVRNumber) {
        this.CVRNumber = CVRNumber;
    }

    public long getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(long bankNumber) {
        this.bankNumber = bankNumber;
    }

    public long getZip() {
        return zip;
    }

    public void setZip(long zip) {
        this.zip = zip;
    }

    public long getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(long houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
