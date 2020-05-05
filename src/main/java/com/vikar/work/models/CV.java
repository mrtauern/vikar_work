package com.vikar.work.models;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Optional;

@Entity
public class CV {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    //@JoinTable(name = "cv_worker", joinColumns = @JoinColumn(name = "worker_id"), inverseJoinColumns = @JoinColumn(name = "cv_id"))
    private Worker worker;

    private String workplace;
    private String jobTitle;
    private String startDate;

    @Nullable
    private String endDate;

    public CV() {
    }

    public CV(Worker worker, String workplace, String jobTitle, String startDate, @Nullable String endDate) {
        this.worker = worker;
        this.workplace = workplace;
        this.jobTitle = jobTitle;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @Nullable
    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(@Nullable String endDate) {
        this.endDate = endDate;
    }
}
