package com.vikar.work.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String Name;
    String Description;
    String StreetName;
    String City;
    String NeededExperience;

    Long HouseNumber;

    Double HourlyWage;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "assignment_job",
            joinColumns = @JoinColumn(name = "assignment_id"),
            inverseJoinColumns = @JoinColumn(name = "job_id"))
    private Set<Job> jobTitles = new HashSet<>();



    public Assignment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
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

    public String getNeededExperience() {
        return NeededExperience;
    }

    public void setNeededExperience(String neededExperience) {
        NeededExperience = neededExperience;
    }

    public Long getHouseNumber() {
        return HouseNumber;
    }

    public void setHouseNumber(Long houseNumber) {
        HouseNumber = houseNumber;
    }

    public Double getHourlyWage() {
        return HourlyWage;
    }

    public void setHourlyWage(Double hourlyWage) {
        HourlyWage = hourlyWage;
    }

    public Set<Job> getJobTitles() {
        return jobTitles;
    }

    public void setJobTitles(Set<Job> jobTitles) {
        this.jobTitles = jobTitles;
    }
}
