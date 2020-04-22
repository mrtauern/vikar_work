package com.vikar.work.controllers;

import com.vikar.work.models.Worker;
import com.vikar.work.services.FreelanceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        log.info("edit Worker called med id: " + userId);

        model.addAttribute("worker", freelanceService.findById(userId));


        return "editWorker";
    }

    @PostMapping("/editWorker")
    public String editWorker (@ModelAttribute Worker worker, Model model) {
        log.info("editworker putmapping called...");
        String test = ""+worker.getCVRNumber();
        log.info("CVR test "+test);
        if (worker.getCVRNumber() > 0) {
            freelanceService.updateWorker(worker);
        }
        else {
            worker.setCVRNumber(0);
            freelanceService.updateWorker(worker);
        }


        //log.info(worker.lastname);

        return "redirect:/editWorker/1";
    }

    @PostMapping("/deleteWorker")
    public String deleteWorker(@ModelAttribute Worker worker) {
        log.info("delete worker called id: "+worker.getId());
        freelanceService.deleteWorker(worker.getId());

        return "index";
    }
}
