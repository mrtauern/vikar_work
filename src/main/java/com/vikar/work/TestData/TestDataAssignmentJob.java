package com.vikar.work.TestData;


import com.vikar.work.models.Assignment;
import com.vikar.work.models.Job;
import com.vikar.work.models.Worker;
import com.vikar.work.repositories.AssignmentRepo;
import com.vikar.work.repositories.FreelanceRepo;
import com.vikar.work.services.AssignmentService;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Component
public class TestDataAssignmentJob /*implements ApplicationListener<ContextRefreshedEvent>*/ {

    @Autowired
    @Qualifier("AssignmentRepo")
    private AssignmentRepo assignmentRepo;


    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    @Qualifier("FreelanceRepo")
    private FreelanceRepo freelanceRepo;

    @Autowired
    private TestDataWorker testDataWorker;

    Logger log = Logger.getLogger(TestDataAssignmentJob.class.getName());

    List<Worker> workers;
/*    List<Worker> workers = testDataWorker.createWorker();*/

    private List<Assignment> createAssignment(){
        List<Assignment> assignments = new ArrayList<>();
        workers = testDataWorker.createWorker();

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

        //add date mm-dd-yyyy
        assignment1.setDateStart(assignmentService.createDateFromString("10/08/2020"));
        assignment1.setDateEnd(assignmentService.createDateFromString("12/08/2020"));

        //add assignment requests to workers
/*        workers.get(1).getRequestedAssignments().add(assignment1);
        assignment1.getAssignmentRequests().add(workers.get(1));*/

        assignments.add(assignment1);
        return assignments;
    }



/*    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        System.out.println("context refreshed");

        freelanceRepo.saveAll(workers);
        assignmentRepo.saveAll(createAssignment());


    }*/
}
