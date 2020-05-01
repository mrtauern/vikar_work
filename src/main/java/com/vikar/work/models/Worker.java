package com.vikar.work.models;

import com.vikar.work.component.AttributeEncryptor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
public class Worker {
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long Id;

    @Nullable
    @Convert(converter = AttributeEncryptor.class)
    public String CVRNumber; //Optional

    @Nullable
    @Convert(converter = AttributeEncryptor.class)
    public String bankNumber;

    @Nullable
    @Convert(converter = AttributeEncryptor.class)
    public String zip;

    @Nullable
    @Convert(converter = AttributeEncryptor.class)
    public String houseNumber;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<CV> cvs = new HashSet<>();

    public String firstname;
    public String lastname;

    @Convert(converter = AttributeEncryptor.class)
    public String email;

    @Convert(converter = AttributeEncryptor.class)
    public String password;

    @Convert(converter = AttributeEncryptor.class)
    public String username;

    @Nullable
    @Convert(converter = AttributeEncryptor.class)
    public String streetName;

    @Nullable
    @Convert(converter = AttributeEncryptor.class)
    public String city;

    public long phoneNumber;
  
    @ManyToMany(mappedBy = "assignmentRequests")
    private Set<Assignment> requestedAssignments = new HashSet<>();

    public Worker(long id, String CVRNumber, String bankNumber, String zip, String houseNumber, Set<CV> cvs, String firstname, String lastname, String email, String password, String username, String streetName, String city, long phoneNumber, Set<Assignment> requestedAssignments) {
        Id = id;
        this.CVRNumber = ""+CVRNumber;
        this.bankNumber = ""+bankNumber;
        this.zip = ""+zip;
        this.houseNumber = ""+houseNumber;
        this.cvs = cvs;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.username = username;
        this.streetName = streetName;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.requestedAssignments = requestedAssignments;
    }



    public Worker() {
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

    public Set<CV> getCvs() {
        return cvs;
    }

    public void setCvs(Set<CV> cvs) {
        this.cvs = cvs;
    }
  
    public Set<Assignment> getRequestedAssignments() {
        return requestedAssignments;
    }

    public void setRequestedAssignments(Set<Assignment> requestedAssignments) {
        this.requestedAssignments = requestedAssignments;
    }
}

