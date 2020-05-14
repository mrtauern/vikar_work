package com.vikar.work.models;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column( length = 100000 )
    private String description;

    private String streetName;
    private String city;
    private String neededExperience;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateStart;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateEnd;

    private Long houseNumber;
    private Long ZIP;

    private int hourlyWage;

    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

    @Nullable
    @ManyToOne(fetch = FetchType.LAZY)
    private Worker acceptedWorker;

    private Boolean isArchived = false;


/*    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "assignment_job",
            joinColumns = @JoinColumn(name = "assignment_id"),
            inverseJoinColumns = @JoinColumn(name = "job_id"))
    private Set<Job> jobTitles = new HashSet<>();*/

    @ManyToOne(cascade = CascadeType.ALL)
    private Job job;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "assignment_requests",
            joinColumns = @JoinColumn(name = "assignment_id"),
            inverseJoinColumns = @JoinColumn(name = "worker_id"))
    private Set<Worker> assignmentRequests = new HashSet<>();



    public Assignment() {
    }

    public Assignment(long id, Boolean isArchived) {
        this.id = id;
        this.isArchived = isArchived;
    }

    public Assignment(String name, String description, String streetName, String city, String neededExperience, Date dateStart, Date dateEnd, Long houseNumber, Long ZIP, int hourlyWage, Job job, Set<Worker> assignmentRequests) {
        this.name = name;
        this.description = description;
        this.streetName = streetName;
        this.city = city;
        this.neededExperience = neededExperience;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.houseNumber = houseNumber;
        this.ZIP = ZIP;
        this.hourlyWage = hourlyWage;
        this.job = job;
        this.assignmentRequests = assignmentRequests;
    }

    public Assignment(String name, String description, String streetName, String city, String neededExperience, Date dateStart, Date dateEnd, Long houseNumber, Long ZIP, int hourlyWage, Company company, Job job, Set<Worker> assignmentRequests) {
        this.name = name;
        this.description = description;
        this.streetName = streetName;
        this.city = city;
        this.neededExperience = neededExperience;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.houseNumber = houseNumber;
        this.ZIP = ZIP;
        this.hourlyWage = hourlyWage;
        this.company = company;
        this.job = job;
        this.assignmentRequests = assignmentRequests;
    }

    @Nullable
    public Worker getAcceptedWorker() {
        return acceptedWorker;
    }

    public void setAcceptedWorker(@Nullable Worker acceptedWorker) {
        this.acceptedWorker = acceptedWorker;
    }

    public Boolean getArchived() {
        return isArchived;
    }

    public void setArchived(Boolean archived) {
        isArchived = archived;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getNeededExperience() {
        return neededExperience;
    }

    public void setNeededExperience(String neededExperience) {
        this.neededExperience = neededExperience;
    }

    public Long getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(Long houseNumber) {
        this.houseNumber = houseNumber;
    }

    public int getHourlyWage() {
        return hourlyWage;
    }

    public void setHourlyWage(int hourlyWage) {
        this.hourlyWage = hourlyWage;
    }

    /*public Set<Job> getJobTitles() {
        return jobTitles;
    }*/

    /*public void setJobTitles(Set<Job> jobTitles) {
        this.jobTitles = jobTitles;
    }*/

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Set<Worker> getAssignmentRequests() {
        return assignmentRequests;
    }

    public void setAssignmentRequests(Set<Worker> assignmentRequests) {
        this.assignmentRequests = assignmentRequests;
    }

    public Long getZIP() {
        return ZIP;
    }

    public void setZIP(Long ZIP) {
        this.ZIP = ZIP;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
