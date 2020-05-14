package com.vikar.work.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vikar.work.models.*;
import com.vikar.work.services.AssignmentService;
import com.vikar.work.services.CompanyService;
import com.vikar.work.services.FreelanceService;
import com.vikar.work.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.Assign;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;


import javax.servlet.http.HttpSession;
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

    int refreshCount = 1000;

    @GetMapping("/showAssignment/{id}")
    public String showAssignment(@PathVariable("id") long assignmentId, Model model, HttpSession session) {

        long id = 0;
        String type = "";

        log.info("showAssignment called with id: " + assignmentId);
        //Assignment test = new Assignment();*/
        //test = assignmentService.findById(assignmentId).get().getArchived();
        //log.info("showAssignment called with id: " + assignmentId);

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

        Worker worker = freelanceService.findById(id).get();
        Assignment assignment = assignmentService.findById(assignmentId).get();
        String[] sessionId = companyService.checkSession((String)session.getAttribute("login"));
        log.info("Assignment worker: "+assignment.getAssignmentRequests().contains(worker));

        model.addAttribute("userId", id);
        model.addAttribute("type", type);
        model.addAttribute("loginType", sessionId[1]);

        model.addAttribute("requested", assignment.getAssignmentRequests().contains(worker));
        model.addAttribute("assignment", assignment);
        model.addAttribute("companyId", assignment.getCompany().getId());
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

            //Create dummy user and assign to assignment
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
// NOTE kan pt ikke checkes om det er virksomheden der er ejer af opgaven da der ikke er et company id sat til assignment.

            if(sessionId[1].equals("c") && Long.parseLong(sessionId[0]) == tempAssignment.getCompany().getId()) {
                Assignment tempAssignment = assignmentService.findById(id).get();
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
// NOTE kan pt ikke checkes om det er virksomheden der er ejer af opgaven da der ikke er et company id sat til assignment.
            if(sessionId[1].equals("c")) {
                /*Job tempJob = new Job();
                Job oldJob = new Job();
                Assignment oldAssignment = new Assignment();*/
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
        //model.addAttribute("numNotifications", 2);


            return "active_assignment_list";

        } else {
            log.info("Not logged in!");
            return "redirect:/notLoggedIn";
        }
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

        /*List<Assignment> assignmentList = (ArrayList<Assignment>) assignmentService.findAll();

        model.addAttribute("numNotifications", assignmentList.size());*/
        model.addAttribute("numNotifications", refreshCount++);
        if(refreshCount > 20){ refreshCount = 1;}

        return "fragments/notification :: notificationElement";
    }

    @GetMapping("/archiveAssignment/{id}")
    public String archiveAssignment(@PathVariable("id") long id, HttpSession session){
        log.info("Archive assignment called...");

        if(session.getAttribute("login") != null){
            log.info(""+session.getAttribute("login"));

            String[] sessionId = companyService.checkSession((String)session.getAttribute("login"));
// kan ikke checke på om det er den korrekte virksomhed som har assignment pt.
            if(sessionId[1].equals("c")) {
                Assignment assignment = assignmentService.findById(id).get();
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

    @GetMapping("/assignments")
    public String assignments(Model model, HttpSession session){
        log.info("Landing page Assignments called list called...");
        String[] sessionId = companyService.checkSession((String)session.getAttribute("login"));
        if(session.getAttribute("login") != null){

            List<Assignment> assignmentList = (ArrayList<Assignment>) assignmentService.findAll();
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

            model.addAttribute("json", jsonFromJavaArrayList);
            model.addAttribute("loginType", sessionId[1]);
            model.addAttribute("assignments", assignments);
            model.addAttribute("pageTitle", "Landing Page");

            return "assignments";

        } else {
            log.info("Not logged in!");
            return "redirect:/notLoggedIn";
        }

    }
}
