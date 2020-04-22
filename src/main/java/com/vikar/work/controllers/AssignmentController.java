package com.vikar.work.controllers;

import com.vikar.work.models.Assignment;
import com.vikar.work.services.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

        model.addAttribute("assignment", assignmentService.findById(assignmentId).get());
        //model.addAttribute("test", "Dette er en test");

        return "showAssignment";

    }

}
