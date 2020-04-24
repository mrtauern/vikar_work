package com.vikar.work.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Job {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    String Profession;


/*    @ManyToMany(mappedBy = "jobTitles")
    private Set<Assignment> assignments = new HashSet<>();*/

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Assignment> assignments = new HashSet<>();



    public Job() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProfession() {
        return Profession;
    }

    public void setProfession(String profession) {
        Profession = profession;
    }

    public Set<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(Set<Assignment> assignments) {
        this.assignments = assignments;
    }
}
