package com.vikar.work.controllers;

import com.vikar.work.models.Assignment;
import com.vikar.work.models.Worker;
import com.vikar.work.services.AssignmentService;
import com.vikar.work.services.FreelanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.Assign;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

@Controller
public class AssignmentController {

    public AssignmentController() {
    }

    @Autowired
    AssignmentService assignmentService;

    @Autowired
    FreelanceService freelanceService;

    Logger log = Logger.getLogger(CompanyController.class.getName());

    @GetMapping("/showAssignment/{id}")
    public String showAssignment(@PathVariable("id") long assignmentId, Model model){

        log.info("showAssignment called with id: " + assignmentId);
        //Assignment test = new Assignment();*/
        //test = assignmentService.findById(assignmentId).get().getArchived();
        log.info("showAssignment called with id: " + assignmentId);

        model.addAttribute("assignment", assignmentService.findById(assignmentId).get());
        model.addAttribute("workersOnAssignment", assignmentService.findById(assignmentId).get().getAssignmentRequests());

        return "showAssignment";

    }

    @RequestMapping(value = "/assignmentSetActive", method = RequestMethod.POST)
    public String assignmentSetActive(@RequestParam("id")long id, @RequestParam("arkiveret") int arkiveret) {

        log.info("Setactive called with id "+id);
        Assignment oldAssignment = assignmentService.findById(id).get();
        //hvis arkiveret er 0 sæt til false hvis arkiveret er 1 sæt true
        if(arkiveret == 0) {
            oldAssignment.setArchived(false);
        }
        else if(arkiveret == 1) {
            oldAssignment.setArchived(true);
        }

        log.info("Arkiveret? "+oldAssignment.getArchived());

        assignmentService.save(oldAssignment);

        return "redirect:/showAssignment/"+id;
    }

    @RequestMapping(value = "/applyForAssignment", method = RequestMethod.POST)
    public String applyForAssignment(@RequestParam("id")long id /*Her skal være et loggedin userid med*/) {
        log.info("applyforassignmed called assignmentid: "+id);

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

        return "redirect:/showAssignment/"+id;
    }

    @GetMapping("/activeAssignmentList")
    public String activeAssignmentList(Model model){
        log.info("Active assignment list called...");

        model.addAttribute("assignments", assignmentService.findAll());

        return "active_assignment_list";
    }
}
