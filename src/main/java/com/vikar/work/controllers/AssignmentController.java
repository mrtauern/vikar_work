package com.vikar.work.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vikar.work.models.*;
import com.vikar.work.services.AssignmentService;
import com.vikar.work.services.CompanyService;
import com.vikar.work.services.FreelanceService;
import com.vikar.work.services.JobService;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;


import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Logger;

@Controller
public class AssignmentController {

    public AssignmentController() {
    }

    @Autowired
    AssignmentService assignmentService;

    @Autowired
    FreelanceService freelanceService;

    @Autowired
    JobService jobService;

    @Autowired
    CompanyService companyService;

    Logger log = Logger.getLogger(CompanyController.class.getName());



    @GetMapping("/showAssignment/{id}")
    public String showAssignment(@PathVariable("id") long assignmentId, Model model, HttpSession session) {

        long id = 0;
        String type = "";

        log.info("showAssignment called with id: " + assignmentId);

        if(session.getAttribute("login") != null){
            String userId = (String) session.getAttribute("login");
            log.info("User full id: " + userId);

            id = Long.valueOf(userId.substring(1));
            type = userId.substring(0, 1);

            log.info("User id: " + id);
            log.info("Type: " + type);
        } else {
            return "redirect:/notLoggedIn";
        }

        Assignment assignment = assignmentService.findById(assignmentId).get();

        if(type.equals("w")) {
            Worker worker = freelanceService.findById(id).get();
            log.info("Assignment worker: " + assignment.getAssignmentRequests().contains(worker));
            model.addAttribute("requested", assignment.getAssignmentRequests().contains(worker));
        }

        Boolean accepted;
        long acceptedId = 0;

        if(assignment.getAcceptedWorker() != null){
            accepted = true;
            acceptedId = assignment.getAcceptedWorker().getId();
        } else {
            accepted = false;
        }

        String[] sessionId = companyService.checkSession((String)session.getAttribute("login"));
        long companyId = 0;

        if (assignment.getCompany() != null) {
            companyId = assignment.getCompany().getId();
        }

        model.addAttribute("userId", id);
        model.addAttribute("type", type);
        model.addAttribute("loginType", sessionId[1]);
        model.addAttribute("accepted", accepted);
        model.addAttribute("acceptedId", acceptedId);
        model.addAttribute("assignment", assignment);
        model.addAttribute("companyId", companyId);
        model.addAttribute("workersOnAssignment", assignment.getAssignmentRequests());
        model.addAttribute("WOA", assignment.getAssignmentRequests().size());

        return "showAssignment";
    }

    @RequestMapping(value = "/assignmentSetActive", method = RequestMethod.POST)
    public String assignmentSetActive(@RequestParam("id") long id, @RequestParam("arkiveret") int arkiveret, HttpSession session) {

        log.info("Setactive called with id " + id);

        if(session.getAttribute("login") != null){
            log.info(""+session.getAttribute("login"));

            String[] sessionId = companyService.checkSession((String)session.getAttribute("login"));

            if(sessionId[0].equals(""+id) && sessionId[1].equals("c")) {
                Assignment oldAssignment = assignmentService.findById(id).get();
                //hvis arkiveret er 0 sæt til false hvis arkiveret er 1 sæt true
                if (arkiveret == 0) {
                    oldAssignment.setArchived(false);
                } else if (arkiveret == 1) {
                    oldAssignment.setArchived(true);
                }

                log.info("Arkiveret? " + oldAssignment.getArchived());

                assignmentService.save(oldAssignment);

                return "redirect:/showAssignment/" + id;

            }
            else if (sessionId[0].equals(""+id) && sessionId[1].equals("w")){

                return "redirect:/showAssignment/" + id;
            }

            else {

                return "redirect:/showAssignment/" + id;
            }

        } else {
            log.info("Not logged in!");
            return "redirect:/notLoggedIn";
        }


    }

    @RequestMapping(value = "/applyForAssignment", method = RequestMethod.POST)
    public String applyForAssignment(@RequestParam("assignmentId") long assignmentId, @RequestParam("userId") long userId, HttpSession session) {
        log.info("applyforassignmed called assignmentid: " + assignmentId + " userId: " + userId);

        if(session.getAttribute("login") != null){
            log.info(""+session.getAttribute("login"));

            String[] sessionId = companyService.checkSession((String)session.getAttribute("login"));

            if(sessionId[1].equals("w")) {
                Assignment assignment = assignmentService.findById(assignmentId).get();
                Worker worker = freelanceService.findById(userId).get();

                assignment.getAssignmentRequests().add(worker);
                worker.getRequestedAssignments().add(assignment);

                if(assignment.getJob().getId() == 1){
                    assignment.setAcceptedWorker(worker);
                    worker.getAcceptedWorker().add(assignment);
                }

                //save data in database
                assignmentService.save(assignment);
                freelanceService.save(worker);

                return "redirect:/showAssignment/" + assignmentId;

            }
            else if (sessionId[1].equals("c")){

                return "redirect:/showAssignment/" + assignmentId;
            }

            else {

                return "redirect:/showAssignment/" + assignmentId;
            }

        } else {
            log.info("Not logged in!");
            return "redirect:/notLoggedIn";
        }
    }

    @RequestMapping(value = "/unapplyForAssignment", method = RequestMethod.POST)
    public String unapplyForAssignment(@RequestParam("assignmentId") long assignmentId, @RequestParam("userId") long userId, HttpSession session) {
        log.info("unapplyforassignmed called assignmentid: " + assignmentId + " userId: " + userId);

        if(session.getAttribute("login") != null){
            log.info(""+session.getAttribute("login"));

            String[] sessionId = companyService.checkSession((String)session.getAttribute("login"));

            Assignment assignment = assignmentService.findById(assignmentId).get();
            Worker worker = freelanceService.findById(userId).get();

            assignment.getAssignmentRequests().remove(worker);
            worker.getRequestedAssignments().remove(assignment);

            //save data in database
            assignmentService.save(assignment);
            freelanceService.save(worker);

            return "redirect:/showAssignment/" + assignmentId;

        } else {
            log.info("Not logged in!");
            return "login";
        }
    }

    @GetMapping("/createAssignment")
    public String createAssignment(Model model, HttpSession session) {

        log.info("create Assignment called");

        if(session.getAttribute("login") != null){
            log.info(""+session.getAttribute("login"));

            String[] sessionId = companyService.checkSession((String)session.getAttribute("login"));

            if(sessionId[1].equals("c")) {
                model.addAttribute("pageTitle", "Opret opgave");
                model.addAttribute("jobList", jobService.findAll());
                model.addAttribute("loginType", sessionId[1]);

                return "createAssignment";

            }
            else if (sessionId[1].equals("w")){

                return "redirect:/landingPage";
            }

            else {

                return "redirect:/notLoggedIn";
            }

        } else {
            log.info("Not logged in!");
            return "redirect:/notLoggedIn";
        }
    }

    @PostMapping("/createAssignment")
    public String createAssignment(@ModelAttribute Assignment assignment, @RequestParam("profession") Long jobId, HttpSession session) {

        log.info("Create Assignment POST called");
        log.info("create assignment jobid " + jobId);
        log.info("Start date + " + assignment.getDateStart());

        if(session.getAttribute("login") != null){
            log.info(""+session.getAttribute("login"));

            String[] sessionId = companyService.checkSession((String)session.getAttribute("login"));

            if(sessionId[1].equals("c")) {
                Job tempJob = new Job();
                tempJob = jobService.findById(jobId).get();
                Company company = companyService.findById(Long.parseLong(sessionId[0])).get();

                tempJob.getAssignments().add(assignment);
                assignment.setJob(tempJob);
                assignment.setCompany(company);

                company.getAssignments().add(assignment);

                jobService.save(tempJob);
                assignmentService.save(assignment);
                companyService.save(company);

                return "redirect:/landingPage";

            }
            else if (sessionId[1].equals("w")){

                return "redirect:/assignments";
            }

            else {

                return "redirect:/landingPage";
            }

        } else {
            log.info("Not logged in!");
            return "redirect:/notLoggedIn";
        }

    }

    @GetMapping("/editAssignment/{id}")
    public String editAssignment(@PathVariable("id") long id, Model model, HttpSession session){

        log.info("edit Assignment called med id" + id);


        if(session.getAttribute("login") != null){
            log.info(""+session.getAttribute("login"));

            String[] sessionId = companyService.checkSession((String)session.getAttribute("login"));
            Assignment tempAssignment = assignmentService.findById(id).get();

            if(sessionId[1].equals("c") && Long.parseLong(sessionId[0]) == tempAssignment.getCompany().getId()) {
                if(tempAssignment.getDateStart() != null) {
                    String dateStartString = tempAssignment.getDateStart().toString();
                    String[] splitDateStart = dateStartString.split(" ");
                    model.addAttribute("dateStart", splitDateStart[0]);
                } else {
                    model.addAttribute("dateStart", "0");
                }

                if(tempAssignment.getDateEnd() != null) {
                    String dateEndString = tempAssignment.getDateEnd().toString();
                    String[] splitDateEnd = dateEndString.split(" ");
                    model.addAttribute("dateEnd", splitDateEnd[0]);
                } else {
                    model.addAttribute("dateEnd", "0");
                }

                Job tempJob = tempAssignment.getJob();
                log.info(tempJob.getId()+"tempjob id");

                model.addAttribute("assigmentJobId", tempAssignment.getJob().getId());
                model.addAttribute("jobId", tempJob.getId());
                model.addAttribute("pageTitle", "Edit Assignment");


                model.addAttribute("assignment", assignmentService.findById(id));
                model.addAttribute("jobList", jobService.findAll());
                model.addAttribute("loginType", sessionId[1]);

                return "editAssignment";

            }
            else if (sessionId[1].equals("w")){

                return "redirect:/showAssignment/" + id;
            }

            else {

                return "redirect:/showAssignment/" + id;
            }

        } else {
            log.info("Not logged in!");
            return "redirect:/notLoggedIn";
        }

    }

    @PostMapping("/editAssignment")
    public String editAssignment(@ModelAttribute Assignment assignment,
                                 @RequestParam("profession") Long jobId,
                                 HttpSession session){

        log.info("edit Assignment postmaping called");
        log.info("job id: "+jobId);
        log.info("Assignment ID "+assignment.getId());



        if(session.getAttribute("login") != null){
            log.info(""+session.getAttribute("login"));

            String[] sessionId = companyService.checkSession((String)session.getAttribute("login"));
            if(sessionId[1].equals("c")) {
                Assignment oldAssignment = assignmentService.findById(assignment.getId()).get();
                Job oldJob = jobService.findById(oldAssignment.getJob().getId()).get();
                Job tempJob = jobService.findById(jobId).get();


                assignment.setJob(tempJob);
                oldJob.getAssignments().remove(oldAssignment);

                tempJob.getAssignments().remove(assignment);
                tempJob.getAssignments().add(assignment);

                assignment.setCompany(oldAssignment.getCompany());

                log.info("presave");
                jobService.save(tempJob);
                assignmentService.save(assignment);
                log.info("postsave");

                return "redirect:/landingPage";

            }
            else if (sessionId[1].equals("w")){

                return "redirect:/landingPage";
            }

            else {

                return "redirect:/notLoggedIn";
            }

        } else {
            log.info("Not logged in!");
            return "redirect:/notLoggedIn";
        }

    }

    @GetMapping("/activeAssignmentList")
    public String activeAssignmentList(Model model, HttpSession session){
        log.info("Active assignment list called...");

        String[] sessionId = freelanceService.checkSession((String)session.getAttribute("login"));
        if(session.getAttribute("login") != null){
            log.info(""+session.getAttribute("login"));

            List<Assignment> assignmentList = (ArrayList<Assignment>) assignmentService.findAll();
            List<Assignment> assignments = new ArrayList<>();

            for (Assignment a: assignmentList) {
                if(a.getArchived() == false && a.getCompany().getId() == Long.parseLong(sessionId[0])){
                    assignments.add(a);
                }
            }

        model.addAttribute("assignments", assignments);
        model.addAttribute("pageTitle", "Aktive opgaver");
        model.addAttribute("loginType", sessionId[1]);

            return "active_assignment_list";

        } else {
            log.info("Not logged in!");
            return "redirect:/notLoggedIn";
        }
    }

    @GetMapping("/archiveAssignment/{id}")
    public String archiveAssignment(@PathVariable("id") long id, HttpSession session){
        log.info("Archive assignment called...");

        if(session.getAttribute("login") != null){
            log.info(""+session.getAttribute("login"));

            String[] sessionId = companyService.checkSession((String)session.getAttribute("login"));
            Assignment assignment = assignmentService.findById(id).get();
            if(sessionId[1].equals("c") && Long.parseLong(sessionId[0]) == assignment.getCompany().getId()) {

                assignment.setArchived(true);
                assignmentService.save(assignment);

                log.info("Assignment (id: "+id+") is archived");

                return "redirect:/activeAssignmentList";

            }
            else if (sessionId[1].equals("w")){

                return "redirect:/activeAssignmentList";
            }

            else {

                return "redirect:/activeAssignmentList";
            }

        } else {
            log.info("Not logged in!");
            return "redirect:/notLoggedIn";
        }
    }

    @GetMapping("/archivedAssignmentList")
    public String archivedAssignmentList(Model model, HttpSession session){
        log.info("Archived assignment list called...");

        if(session.getAttribute("login") != null){

            String[] sessionId = companyService.checkSession((String)session.getAttribute("login"));
            List<Assignment> assignmentList = (ArrayList<Assignment>) assignmentService.findAll();
            List<Assignment> assignments = new ArrayList<>();

            for (Assignment a: assignmentList) {
                if(a.getArchived() == true && a.getCompany().getId() == Long.parseLong(sessionId[0])){
                    assignments.add(a);
                }
            }

            model.addAttribute("assignments", assignments);
            model.addAttribute("pageTitle", "Arkiveret opgaver");

            return "archived_assignment_list";

        } else {
            log.info("Not logged in!");
            return "redirect:/notLoggedIn";
        }
    }

    //Skal testes på Niklas eller Felix maskine
    @GetMapping("/assignments")
    public String assignments(Model model, HttpSession session){
        log.info("Landing page Assignments called list called...");
        String[] sessionId = companyService.checkSession((String)session.getAttribute("login"));
        if(session.getAttribute("login") != null){

            List<Assignment> assignmentList = (ArrayList<Assignment>) assignmentService.findAll();
            List<Job> jobs = (ArrayList<Job>) jobService.findAll();
            List<Assignment> assignments = new ArrayList<>();
            ArrayList<MapMarker> markerList = new ArrayList<>();
            Gson gsonBuilder = new GsonBuilder().create();

            for (Assignment a: assignmentList) {
                if(a.getArchived() == false){
                    assignments.add(a);
                    markerList.add(new MapMarker(a.getStreetName(), a.getHouseNumber()));
                }
            }

            String jsonFromJavaArrayList = gsonBuilder.toJson(markerList);

            Date today = new Date();

            String todayString = new SimpleDateFormat("yyyy-MM-dd").format(today);

            model.addAttribute("json", jsonFromJavaArrayList);
            model.addAttribute("loginType", sessionId[1]);
            model.addAttribute("assignments", assignments);
            model.addAttribute("pageTitle", "Landing Page");
            model.addAttribute("jobs", jobs);
            model.addAttribute("today", todayString);
            model.addAttribute("jobId", 0);
            model.addAttribute("wage", 0);

            return "assignments";

        } else {
            log.info("Not logged in!");
            return "redirect:/notLoggedIn";
        }

    }

    @PostMapping("/filterForFreelance")
    public String filterForFreelance(@RequestParam("job") String job, @RequestParam("hourlyWage") String hourlyWage, @RequestParam("startDate") String startDate, Model model, HttpSession session){
        log.info("filterForFreelance called...");
        long jobId = Long.parseLong(job);
        long wage = Long.parseLong(hourlyWage);
        Date date = assignmentService.createDateFromString(startDate);
        log.info("Job id: " + jobId);
        log.info("Hourly wage: " + hourlyWage);
        log.info("Start date: " + date);
        String[] sessionId = companyService.checkSession((String)session.getAttribute("login"));
        if(session.getAttribute("login") != null){

            List<Assignment> assignmentList = (ArrayList<Assignment>) assignmentService.findAll();
            List<Job> jobs = (ArrayList<Job>) jobService.findAll();
            List<Assignment> assignments = new ArrayList<>();
            ArrayList<MapMarker> markerList = new ArrayList<>();
            Gson gsonBuilder = new GsonBuilder().create();

            for (Assignment a: assignmentList) {
                if(a.getArchived() == false){
                    assignments.add(a);
                    markerList.add(new MapMarker(a.getStreetName(), a.getHouseNumber()));
                }
            }

            if (jobId != 0) {
                for(int i=0; i < assignments.size(); ){
                    if(assignments.get(i).getJob().getId() != jobId){
                        assignments.remove(i);
                        i=0;
                    } else {
                        i++;
                    }
                }
            }

            if (wage > 0) {
                for(int i=0; i < assignments.size(); ){
                    if(assignments.get(i).getHourlyWage() < wage){
                        assignments.remove(i);
                        i=0;
                    } else {
                        i++;
                    }
                }
            }

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();

            Date today = new Date();

            if(date.after(today)){
                //log.info("assignments size: " + assignments.size());
                for(int i=0; i < assignments.size();){
                    //log.info("Date start: "+assignments.get(i).getDateStart());
                    if(assignments.get(i).getDateStart().before(date)){
                        assignments.remove(i);
                        i=0;
                    } else {
                        i++;
                    }
                }
            }

            String jsonFromJavaArrayList = gsonBuilder.toJson(markerList);

            String dateString = new SimpleDateFormat("yyyy-MM-dd").format(date);

            model.addAttribute("json", jsonFromJavaArrayList);
            model.addAttribute("loginType", sessionId[1]);
            model.addAttribute("assignments", assignments);
            model.addAttribute("pageTitle", "Landing Page");
            model.addAttribute("jobs", jobs);
            model.addAttribute("today", dateString);
            model.addAttribute("jobId", jobId);
            model.addAttribute("wage", wage);

            return "assignments";

        } else {
            log.info("Not logged in!");
            return "redirect:/notLoggedIn";
        }
    }
}
