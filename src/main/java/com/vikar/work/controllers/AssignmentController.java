package com.vikar.work.controllers;

import com.vikar.work.models.Assignment;
import com.vikar.work.models.Job;
import com.vikar.work.models.Worker;
import com.vikar.work.services.AssignmentService;
import com.vikar.work.services.FreelanceService;
import com.vikar.work.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.Assign;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;


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

    Logger log = Logger.getLogger(CompanyController.class.getName());

    @GetMapping("/showAssignment/{id}")
    public String showAssignment(@PathVariable("id") long assignmentId, Model model) {

        log.info("showAssignment called with id: " + assignmentId);
        //Assignment test = new Assignment();*/
        //test = assignmentService.findById(assignmentId).get().getArchived();
        log.info("showAssignment called with id: " + assignmentId);

        model.addAttribute("assignment", assignmentService.findById(assignmentId).get());
        model.addAttribute("workersOnAssignment", assignmentService.findById(assignmentId).get().getAssignmentRequests());

        return "showAssignment";

    }

    @RequestMapping(value = "/assignmentSetActive", method = RequestMethod.POST)
    public String assignmentSetActive(@RequestParam("id") long id, @RequestParam("arkiveret") int arkiveret) {

        log.info("Setactive called with id " + id);
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

    @RequestMapping(value = "/applyForAssignment", method = RequestMethod.POST)
    public String applyForAssignment(@RequestParam("id") long id /*Her skal være et loggedin userid med*/) {
        log.info("applyforassignmed called assignmentid: " + id);

        //Create dummy user and assign to assignment
        Assignment oldAssignment = assignmentService.findById(id).get();
        Worker tempworker = new Worker();
        tempworker.setCVRNumber(51234234);
        tempworker.setBankNumber(234235551);
        tempworker.setHouseNumber(44);
        tempworker.setZip(223311);

        tempworker.setFirstname("Smalle");
        tempworker.setLastname("Smallesen");
        tempworker.setEmail("SmalleSmallesen@gmail.com");
        tempworker.setPassword("SmukkeSmalle21");
        tempworker.setStreetName("Andersvej");
        tempworker.setCity("Copenhagen");
        tempworker.setUsername("user222");

        //add dummy user to assignment
        tempworker.getRequestedAssignments().add(oldAssignment);
        oldAssignment.getAssignmentRequests().add(tempworker);

        //save data in database
        assignmentService.save(oldAssignment);
        freelanceService.save(tempworker);

        return "redirect:/showAssignment/" + id;
    }

    @GetMapping("/createAssignment")
    public String createAssignment(Model model) {

        log.info("create Assignment called");

        model.addAttribute("pageTitle", "Opret opgave");
        model.addAttribute("jobList", jobService.findAll());


        return "createAssignment";
    }

    @PostMapping("/createAssignment")
    public String createAssignment(@ModelAttribute Assignment assignment, @RequestParam("profession") Long jobId) {

        log.info("Create Assignment POST called");
        log.info("create assignment jobid " + jobId);
        log.info("Start date + " + assignment.getDateStart());

        Job tempJob = new Job();
        tempJob = jobService.findById(jobId).get();

        /*assignment.getJobTitles().add(tempJob);*/
        tempJob.getAssignments().add(assignment);
        assignment.setJob(tempJob);

        jobService.save(tempJob);
        assignmentService.save(assignment);

        return "createAssignment";
    }

    @GetMapping("/editAssignment/{id}")
    public String editAssignment(@PathVariable("id") long id, Model model){

        log.info("edit Assignment called med id" + id);
        Assignment tempAssignment = assignmentService.findById(id).get();
        String dateStartString = tempAssignment.getDateStart().toString();
        String dateEndString = tempAssignment.getDateEnd().toString();
        String[] splitDateStart = dateStartString.split(" ");
        String[] splitDateEnd = dateEndString.split(" ");

        Job tempJob = tempAssignment.getJob();
        log.info(tempJob.getId()+"tempjob id");

        model.addAttribute("assigmentJobId", tempAssignment.getJob().getId());
        model.addAttribute("jobId", tempJob.getId());
        model.addAttribute("pageTitle", "Edit Assignment");
        model.addAttribute("dateStart", splitDateStart[0]);
        model.addAttribute("dateEnd", splitDateEnd[0]);
        model.addAttribute("assignment", assignmentService.findById(id));
        model.addAttribute("jobList", jobService.findAll());

        return "editAssignment";
    }

    @PostMapping("/editAssignment")
    public String editAssignment(@ModelAttribute Assignment assignment,
                                 @RequestParam("profession") Long jobId){

        log.info("edit Assignment postmaping called");
        log.info("job id: "+jobId);
        log.info("Assignment ID "+assignment.getId());


        Job tempJob = new Job();
        Job oldJob = new Job();
        Assignment oldAssignment = new Assignment();
        oldAssignment = assignmentService.findById(assignment.getId()).get();
        oldJob = jobService.findById(oldAssignment.getJob().getId()).get();
        tempJob = jobService.findById(jobId).get();


        assignment.setJob(tempJob);
        oldJob.getAssignments().remove(oldAssignment);

        tempJob.getAssignments().remove(assignment);
        tempJob.getAssignments().add(assignment);

        log.info("presave");
        jobService.save(tempJob);
        assignmentService.save(assignment);
        log.info("postsave");

        return "redirect:/editAssignment/" + assignment.getId();
    }

    @GetMapping("/activeAssignmentList")
    public String activeAssignmentList(Model model){
        log.info("Active assignment list called...");

        List<Assignment> assignmentList = (ArrayList<Assignment>) assignmentService.findAll();
        List<Assignment> assignments = new ArrayList<>();

        for (Assignment a: assignmentList) {
            if(a.getArchived() == false){
                assignments.add(a);
            }
        }

        model.addAttribute("assignments", assignments);
        model.addAttribute("pageTitle", "Aktive opgaver");
        model.addAttribute("numNotifications", 2);

        return "active_assignment_list";
    }

    /*@RequestMapping(value="/notification-count", method=RequestMethod.GET)
    public String getEventCount(ModelMap map, Model model) {
        // TODO: retrieve the new value here so you can add it to model map
        map.addAttribute("numNotifications", ""+3);
        //model.addAttribute("numNotifications", 2);

        // change "myview" to the name of your view
        return "active_assignment_list :: #notificationCount";
    }*/

    @GetMapping("/notification")
    public String notification(Model model) {

        model.addAttribute("numNotifications", 2);

        return "fragments/notification :: countNotification";
    }

    @GetMapping("/archiveAssignment/{id}")
    public String archiveAssignment(@PathVariable("id") long id){
        log.info("Archive assignment called...");

        Assignment assignment = assignmentService.findById(id).get();
        assignment.setArchived(true);
        assignmentService.save(assignment);

        log.info("Assignment (id: "+id+") is archived");

        return "redirect:/activeAssignmentList";
    }

    @GetMapping("/archivedAssignmentList")
    public String archivedAssignmentList(Model model){
        log.info("Archived assignment list called...");

        List<Assignment> assignmentList = (ArrayList<Assignment>) assignmentService.findAll();
        List<Assignment> assignments = new ArrayList<>();

        for (Assignment a: assignmentList) {
            if(a.getArchived() == true){
                assignments.add(a);
            }
        }

        model.addAttribute("assignments", assignments);
        model.addAttribute("pageTitle", "Arkiveret opgaver");

        return "archived_assignment_list";
    }
}
