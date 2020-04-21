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

    public long BankNumber;
    public long ZIP;
    public long HouseNumber;

    public String firstname;
    public String lastname;
    public String email;
    public String Password;
    public String streetName;
    public String City;

    public Worker(long id, long CVRNumber, long bankNumber, long ZIP, long houseNumber, String firstname, String lastname, String email, String password, String streetName, String city) {
        Id = id;
        this.CVRNumber = CVRNumber;
        BankNumber = bankNumber;
        this.ZIP = ZIP;
        HouseNumber = houseNumber;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        Password = password;
        this.streetName = streetName;
        City = city;
    }

    public Worker() {
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

/*    public Optional<Long> getCVRNumber(){
        return Optional.ofNullable(CVRNumber);

    }*/

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
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }
}
