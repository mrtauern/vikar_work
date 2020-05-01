package com.vikar.work.models;

import com.vikar.work.component.AttributeEncryptor;

import javax.persistence.*;

@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long Id;

    @Convert(converter = AttributeEncryptor.class)
    public String CVRNumber;

    @Convert(converter = AttributeEncryptor.class)
    public String bankNumber;

    @Convert(converter = AttributeEncryptor.class)
    public String zip;

    @Convert(converter = AttributeEncryptor.class)
    public String houseNumber;

    @Convert(converter = AttributeEncryptor.class)
    public String companyName;

    @Convert(converter = AttributeEncryptor.class)
    public String username;

    @Convert(converter = AttributeEncryptor.class)
    public String password;

    @Convert(converter = AttributeEncryptor.class)
    public String streetName;

    @Convert(converter = AttributeEncryptor.class)
    public String city;

    public long phoneNumber;

    public Company() {
    }

    public Company(long id, long CVRNumber, long bankNumber, long zip, long houseNumber, String companyName, String username, String password, String streetName, String city, long phoneNumber) {
        Id = id;
        this.CVRNumber = ""+CVRNumber;
        this.bankNumber = ""+bankNumber;
        this.zip = ""+zip;
        this.houseNumber = ""+houseNumber;
        this.companyName = companyName;
        this.username = username;
        this.password = password;
        this.streetName = streetName;
        this.city = city;
        this.phoneNumber = phoneNumber;
    }


    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public long getCVRNumber() {
        return Long.valueOf(CVRNumber).longValue();
    }

    public void setCVRNumber(long CVRNumber) {
        this.CVRNumber = ""+CVRNumber;
    }

    public long getBankNumber() {
        return Long.valueOf(bankNumber).longValue();
    }

    public void setBankNumber(long bankNumber) {
        this.bankNumber = ""+bankNumber;
    }

    public long getZip() {
        return Long.valueOf(zip).longValue();
    }

    public void setZip(long zip) {
        this.zip = ""+zip;
    }

    public long getHouseNumber() {
        return Long.valueOf(houseNumber).longValue();
    }

    public void setHouseNumber(long houseNumber) {
        this.houseNumber = ""+houseNumber;
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
