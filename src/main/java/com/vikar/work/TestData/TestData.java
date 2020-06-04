package com.vikar.work.TestData;

import com.vikar.work.models.*;

import com.vikar.work.repositories.*;

import com.vikar.work.services.AssignmentService;
import com.vikar.work.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
// Hasan, Felix, Niklas og Gustav
@Component
public class TestData implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    @Qualifier("AssignmentRepo")
    private AssignmentRepo assignmentRepo;

    @Autowired
    @Qualifier("AssignmentService")
    private AssignmentService assignmentService;

    @Autowired
    @Qualifier("CompanyService")
    private CompanyService companyService;

    @Autowired
    @Qualifier("FreelanceRepo")
    private FreelanceRepo freelanceRepo;

    @Autowired
    @Qualifier("JobRepo")
    private JobRepo jobRepo;
  
    @Autowired
    @Qualifier("CVRepo")
    private CVRepo cvRepo;

    @Autowired
    @Qualifier("CompanyRepo")
    private CompanyRepo companyRepo;

    @Autowired
    @Qualifier("MessageRepo")
    private MessageRepo messageRepo;

    List<Worker> workers = new ArrayList<>();
    List<Job> jobs = new ArrayList<>();
    List<Message> messages = new ArrayList<>();

    List<CV> cvs = new ArrayList<>();

    List<Company> companies = new ArrayList<>();
  
    private List<Assignment> createAssignment(){
        List<Assignment> assignments = new ArrayList<>();

        Assignment assignment1 = new Assignment();
        Assignment assignment2 = new Assignment();

        // add a test assignment
        assignment1.setName("Opvasker");
        assignment1.setDescription("Du skal tage opvasken hjemme hos mig hver fredag");
        assignment1.setStreetName("Skoleholdervej");
        assignment1.setCity("odense");
        assignment1.setNeededExperience("Du skal være erfaren opvasker, og have opvasket mindst 10.000 tallerkner");
        assignment1.setHouseNumber((long) 21);
        assignment1.setHourlyWage(55);

        String date = "2020-06-24";
        assignment1.setDateStart(assignmentService.createDateFromString(date));
        date = "2020-06-25";
        assignment1.setDateEnd(assignmentService.createDateFromString(date));

        assignment2.setName("Pakke skal leveres");
        assignment2.setDescription("Lever en pakke");
        assignment2.setStreetName("Skoleholdervej");
        assignment2.setCity("København");
        assignment2.setNeededExperience("du skal kunne levere en pakke");
        assignment2.setHouseNumber((long) 2);
        assignment2.setHourlyWage(65);

        date = "2020-07-12";
        assignment2.setDateStart(assignmentService.createDateFromString(date));
        date = "2020-07-14";
        assignment2.setDateEnd(assignmentService.createDateFromString(date));


        //add a test job
        Job jobs1 = new Job();
        Job jobs2 = new Job();
        Job jobs3 = new Job();
        Job jobs4 = new Job();


        jobs1.setProfession("Proffesional opvasker");
        jobs2.setProfession("Ufaglært");
        jobs3.setProfession("Blikkenslager");
        jobs4.setProfession("Elektriker");


        jobs2.getAssignments().add(assignment1);
        assignment1.setJob(jobs2);
        assignment2.setJob(jobs3);


        Worker worker1 = new Worker();
        worker1.setCVRNumber(22311551);
        worker1.setBankNumber(22154321);
        worker1.setHouseNumber(33);
        worker1.setZip(2400);

        worker1.setFirstname("Arne");
        worker1.setLastname("Kallesen");
        worker1.setEmail("palle@gmail.com");
        worker1.setPassword("pass1234");
        worker1.setStreetName("Skoleholdervej");
        worker1.setCity("Copenhagen");
        worker1.setUsername("user1234");

        //testworker2
        Worker worker2 = new Worker();
        worker2.setCVRNumber(1122332);
        worker2.setBankNumber(22333111);
        worker2.setHouseNumber(22);
        worker2.setZip(2311);

        worker2.setFirstname("palle");
        worker2.setLastname("pallesen");
        worker2.setEmail("pallepallesen@gmail.com");
        worker2.setPassword("pass4321");
        worker2.setStreetName("Svendevej");
        worker2.setCity("Copenhagen");
        worker2.setUsername("user4321");

        //testworker3
        Worker worker3 = new Worker();
        worker3.setCVRNumber(1122332);
        worker3.setBankNumber(22333111);
        worker3.setHouseNumber(22);
        worker3.setZip(2311);

        worker3.setFirstname("arnold");
        worker3.setLastname("Dietersen");
        worker3.setEmail("Arnold@gmail.com");
        worker3.setPassword("999");
        worker3.setStreetName("Adolfvej");
        worker3.setCity("Tølløse");
        worker3.setUsername("user567");

        Company company1 = new Company();
        Company company2 = new Company();

        company1.setCVRNumber(12345678);
        company1.setBankNumber(987654321);
        company1.setHouseNumber(67);
        company1.setZip(3030);
        company1.setPhoneNumber(12345678);

        company1.setCompanyName("AudiBilService");
        company1.setUsername("Audi");
        company1.setPassword("1234");
        company1.setStreetName("Route66");
        company1.setCity("Ingolfstadt");
        company1.setEmail("hhaidari25@gmail.com");

        company2.setCVRNumber(12345679);
        company2.setBankNumber(987654322);
        company2.setHouseNumber(345);
        company2.setZip(3035);
        company2.setPhoneNumber(12345687);

        company2.setCompanyName("BMWservice");
        company2.setUsername("BMW");
        company2.setPassword("1234");
        company2.setStreetName("Autobahn");
        company2.setCity("Ausfart");


        CV cv1 = new CV();
        cv1.setWorker(worker1);
        cv1.setWorkplace("BMW");
        cv1.setJobTitle("CEO");
        cv1.setStartDate("03/11/2014");
        cv1.setEndDate("07/05/2019");

        worker1.getCvs().add(cv1);

        companies.add(company1);
        companies.add(company2);

        jobs.add(jobs1);
        jobs.add(jobs2);
        jobs.add(jobs3);
        jobs.add(jobs4);

        workers.add(worker1);
        workers.add(worker2);
        workers.add(worker3);
        assignments.add(assignment1);
        assignments.add(assignment2);


        return assignments;
    }

    private void combineAssignmentWithCompany(){
        Assignment assignment1 = assignmentService.findById(1).get();
        Assignment assignment2 = assignmentService.findById(2).get();
        Company company = companyService.findById(1).get();

        assignment1.setCompany(company);
        assignment2.setCompany(company);

        company.getAssignments().add(assignment1);
        company.getAssignments().add(assignment2);

        assignmentService.save(assignment1);
        assignmentService.save(assignment2);
        companyService.save(company);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        System.out.println("context refreshed TestData");



        assignmentRepo.saveAll(createAssignment());
        companyRepo.saveAll(companies);
        jobRepo.saveAll(jobs);
        freelanceRepo.saveAll(workers);

        Assignment assignment1 = assignmentService.findById(1).get();
        Assignment assignment2 = assignmentService.findById(2).get();
        Company company = companyService.findById(1).get();

        assignment1.setCompany(company);
        assignment2.setCompany(company);

        Set<Assignment> assignmentSet = new HashSet<>();

        assignmentSet.add(assignment1);
        assignmentSet.add(assignment2);

        company.setAssignments(assignmentSet);

        assignmentService.save(assignment1);
        assignmentService.save(assignment2);
        companyService.save(company);

    }
}
