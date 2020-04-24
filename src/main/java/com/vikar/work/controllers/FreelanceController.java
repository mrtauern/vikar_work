package com.vikar.work.controllers;

import com.vikar.work.models.CV;
import com.vikar.work.models.Worker;
import com.vikar.work.services.CVService;
import com.vikar.work.services.FreelanceService;
import com.vikar.work.services.FreelanceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@Qualifier("FreelanceController")
@Controller
public class FreelanceController {

    public FreelanceController(){

    }

    @Qualifier("FreelanceService")
    @Autowired
    FreelanceService freelanceService;

    @Qualifier("CVService")
    @Autowired
    CVService cvService;

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

    @GetMapping("/addToCv")
    public String addToCv(Model model){
        log.info("Add to cv called (get)");

        model.addAttribute("pageTitle", "Tilføj til CV");
        model.addAttribute("workerId", 2);

        return "add_to_cv";
    }

    @PostMapping("/addToCv")
    public String addToCv(@RequestParam("workerId")String workerId,
                          @RequestParam("workplace")String workplace,
                          @RequestParam("jobTitle")String jobTitle,
                          @RequestParam("startDate")String startDate,
                          @RequestParam("endDate")String endDate){

        Worker worker = freelanceService.findById(Long.parseLong(workerId)).get();

        CV cv = new CV(worker, workplace, jobTitle, startDate, endDate);

        cvService.save(cv);

        log.info("Add to cv called (post)");

        return "redirect:/";
    }

    @GetMapping("/removeFromCv/{cvId}")
    public String removeFromCv(@PathVariable("cvId") String cvId){
        log.info("Remove from cv called");

        CV cv = cvService.findById(Long.parseLong(cvId)).get();

        cvService.delete(cv);

        log.info("Cv with "+cvId+" is removed.");

        return "redirect:/";
    }
}
