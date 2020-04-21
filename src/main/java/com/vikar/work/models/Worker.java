package com.vikar.work.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Optional;

@Entity
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long Id;

    public long CVRNumber; //Optional

    public long bankNumber;
    public long zip;
    public long houseNumber;

    public String firstname;
    public String lastname;
    public String email;
    public String password;
    public String username;
    public String streetName;
    public String city;

    public Worker(long id, long CVRNumber, long bankNumber, long zip, long houseNumber, String firstname, String lastname, String email, String password, String username, String streetName, String city) {
        Id = id;
        this.CVRNumber = CVRNumber;
        this.bankNumber = bankNumber;
        this.zip = zip;
        this.houseNumber = houseNumber;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.username = username;
        this.streetName = streetName;
        this.city = city;
    }

    public Worker() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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