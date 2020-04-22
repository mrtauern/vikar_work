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
    public long bankNumber;
    public long zip;
    public long houseNumber;

    public String companyName;
    public String username;
    public String password;
    public String streetName;
    public String city;

    public Company() {
    }

    public Company(long id, long CVRNumber, long bankNumber, long zip, long houseNumber, String companyName, String username, String password, String streetName, String city) {
        Id = id;
        this.CVRNumber = CVRNumber;
        this.bankNumber = bankNumber;
        this.zip = zip;
        this.houseNumber = houseNumber;
        this.companyName = companyName;
        this.username = username;
        this.password = password;
        this.streetName = streetName;
        this.city = city;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
