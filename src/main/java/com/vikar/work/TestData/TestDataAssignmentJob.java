package com.vikar.work.TestData;


import com.vikar.work.models.Assignment;
import com.vikar.work.models.Job;
import com.vikar.work.repositories.AssignmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Component
public class TestDataAssignmentJob implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    @Qualifier("AssignmentRepo")
    private AssignmentRepo assignmentRepo;

    Logger log = Logger.getLogger(TestDataAssignmentJob.class.getName());


    private List<Assignment> createAssignment(){
        List<Assignment> assignments = new ArrayList<>();

        Assignment assignment1 = new Assignment();


        // add a test assignment
        assignment1.setName("Opvasker");
        assignment1.setDescription("Du skal tage opvasken hjemme hos mig hver fredag");
        assignment1.setStreetName("ArbejdVej");
        assignment1.setCity("odense");
        assignment1.setNeededExperience("Du skal være erfaren opvasker, og have opvasket mindst 10.000 tallerkner");
        assignment1.setHouseNumber((long) 35);
        assignment1.setHourlyWage(55);

        //add a test job
        Job jobs1 = new Job();
        jobs1.setProfession("Proffesional opvasker");
        jobs1.getAssignments().add(assignment1);
        assignment1.getJobTitles().add(jobs1);

        assignments.add(assignment1);
        log.info("hey");
        return assignments;
    }



    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        System.out.println("context refreshed");
        log.info("context???");

        assignmentRepo.saveAll(createAssignment());
    }
}
