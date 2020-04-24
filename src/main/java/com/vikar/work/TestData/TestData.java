package com.vikar.work.TestData;

import com.vikar.work.models.Assignment;

import com.vikar.work.models.CV;
import com.vikar.work.models.Job;
import com.vikar.work.models.Worker;
import com.vikar.work.repositories.AssignmentRepo;
import com.vikar.work.repositories.CVRepo;

import com.vikar.work.repositories.FreelanceRepo;
import com.vikar.work.services.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TestData implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    @Qualifier("AssignmentRepo")
    private AssignmentRepo assignmentRepo;

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    @Qualifier("FreelanceRepo")
    private FreelanceRepo freelanceRepo;

    @Autowired
    @Qualifier("CVRepo")
    private CVRepo cvRepo;

    List<Worker> workers = new ArrayList<>();

    List<CV> cvs = new ArrayList<>();
    List<Worker> workers = new ArrayList<>();
  
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

        //assignment1.setZIP((long) 2222);

        //add a test job
        Job jobs1 = new Job();
        jobs1.setProfession("Proffesional opvasker");

        jobs1.getAssignments().add(assignment1);
        assignment1.getJobTitles().add(jobs1);

        //add date mm-dd-yyyy

        /*assignment1.setDateStart(assignmentService.createDateFromString("10/08/2020"));
        assignment1.setDateEnd(assignmentService.createDateFromString("12/08/2020"));*/

        Worker worker1 = new Worker();
        worker1.setCVRNumber(22311551);
        worker1.setBankNumber(22154321);
        worker1.setHouseNumber(33);
        worker1.setZip(2400);

        worker1.setFirstname("Kalle");
        worker1.setLastname("Kallesen");
        worker1.setEmail("KalleKallesen@gmail.com");
        worker1.setPassword("SmukkeKalle21");
        worker1.setStreetName("Helgevej");
        worker1.setCity("Copenhagen");
        worker1.setUsername("user123");

        //testworker2
        Worker worker2 = new Worker();
        worker2.setCVRNumber(1122332);
        worker2.setBankNumber(22333111);
        worker2.setHouseNumber(22);
        worker2.setZip(2311);

        worker2.setFirstname("palle");
        worker2.setLastname("pallesen");
        worker2.setEmail("pallepallesen@gmail.com");
        worker2.setPassword("Smukkepalle21");
        worker2.setStreetName("Svendevej");
        worker2.setCity("Copenhagen");
        worker2.setUsername("user321");


        //add assignment requests to workers
        /*worker1.getRequestedAssignments().add(assignment1);
        worker2.getRequestedAssignments().add(assignment1);
        assignment1.getAssignmentRequests().add(worker1);
        assignment1.getAssignmentRequests().add(worker2);*/

        CV cv1 = new CV();
        cv1.setWorker(worker1);
        cv1.setWorkplace("BMW");
        cv1.setJobTitle("CEO");
        cv1.setStartDate("03/11/2014");
        cv1.setEndDate("07/05/2019");

        //cvs.add(cv1);

        worker1.getCvs().add(cv1);

        workers.add(worker1);
        workers.add(worker2);
        assignments.add(assignment1);
        return assignments;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        System.out.println("context refreshed TestData");

        assignmentRepo.saveAll(createAssignment());
        freelanceRepo.saveAll(workers);

        //cvRepo.saveAll(cvs);

    }
}
