package com.vikar.work.controllers;

import com.vikar.work.models.Assignment;
import com.vikar.work.services.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.Assign;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.Optional;
import java.util.logging.Logger;

@Controller
public class AssignmentController {

    public AssignmentController() {
    }

    @Autowired
    AssignmentService assignmentService;

    Logger log = Logger.getLogger(CompanyController.class.getName());

    @GetMapping("/showAssignment/{id}")
    public String showAssignment(@PathVariable("id") long assignmentId, Model model){

        log.info("showAssignment called with id: " + assignmentId);
        /*Assignment test = new Assignment();*/
        /*test = assignmentService.findById(assignmentId).get().getArchived();*/
        log.info("showAssignment called with id: " + assignmentId);

        model.addAttribute("assignment", assignmentService.findById(assignmentId).get());
        //model.addAttribute("test", "Dette er en test");

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

}
