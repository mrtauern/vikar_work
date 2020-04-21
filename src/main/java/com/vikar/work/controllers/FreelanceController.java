package com.vikar.work.controllers;

import com.vikar.work.models.Worker;
import com.vikar.work.services.FreelanceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.logging.Logger;

@Controller
public class FreelanceController {

    public FreelanceController(){

    }

    @Autowired
    FreelanceServiceImpl freelanceService;

    Logger log = Logger.getLogger(FreelanceController.class.getName());

    @GetMapping("/editWorker/{id}")
    public String editWorker(@PathVariable("id") long userId, Model model){
        log.info("edit Worker called");

        //model.addAttribute("worker", freelanceService.findById(userId));

        return "editWorker";
    }

    @PutMapping("/editWorker")
    public String editWorker (@ModelAttribute Worker worker, Model model) {
        log.info("editProducts putmapping called...");

        //freelanceService.updateWorker(worker);

        return "redirect:/showWorker";
    }

}
