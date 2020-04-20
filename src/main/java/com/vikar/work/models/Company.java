package com.vikar.work.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long Id;

    public long CVRNumber;
    public long BankNumber;
    public long ZIP;
    public long HouseNumber;

    public String CompanyName;
    public String Username;
    public String Password;
    public String StreetName;
    public String City;

    public Company(){

    }

    public Company(long id, long CVRNumber, long bankNumber, long ZIP, long houseNumber, String companyName, String username, String password, String streetName, String city) {
        Id = id;
        this.CVRNumber = CVRNumber;
        BankNumber = bankNumber;
        this.ZIP = ZIP;
        HouseNumber = houseNumber;
        CompanyName = companyName;
        Username = username;
        Password = password;
        StreetName = streetName;
        City = city;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public long getCVRNumber() {
        return CVRNumber;
    }

    public void setCVRNumber(long CVRNumber) {
        this.CVRNumber = CVRNumber;
    }

    public long getBankNumber() {
        return BankNumber;
    }

    public void setBankNumber(long bankNumber) {
        BankNumber = bankNumber;
    }

    public long getZIP() {
        return ZIP;
    }

    public void setZIP(long ZIP) {
        this.ZIP = ZIP;
    }

    public long getHouseNumber() {
        return HouseNumber;
    }

    public void setHouseNumber(long houseNumber) {
        HouseNumber = houseNumber;
    }

    public String getCompanyName() {
        return this.CompanyName;
    }

    public void setCompanyName(String CompanyName) {
        this.CompanyName = CompanyName;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getStreetName() {
        return StreetName;
    }

    public void setStreetName(String streetName) {
        StreetName = streetName;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }
}
