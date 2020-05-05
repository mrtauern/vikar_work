package com.vikar.work.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vikar.work.models.Assignment;
import com.vikar.work.models.CV;
import com.vikar.work.models.MapMarker;
import com.vikar.work.models.Worker;
import com.vikar.work.services.AssignmentService;
import com.vikar.work.services.CVService;
import com.vikar.work.services.FreelanceService;
import com.vikar.work.services.FreelanceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
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

    @Qualifier("AssignmentService")
    @Autowired
    AssignmentService assignmentService;

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

    @GetMapping("/addToCv/{workerId}")
    public String addToCv(@PathVariable("workerId") long workerId, Model model){
        log.info("Add to cv called (get)");

        model.addAttribute("pageTitle", "Tilføj til CV");
        model.addAttribute("workerId", workerId);

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

    @PostMapping("/removeFromCv")
    public String removeFromCv(@RequestParam("cvId") long cvId,
                               @RequestParam("workerId")long workerId ) {
        log.info("removeFromCv called with cvId: "+cvId+" and workerId: "+workerId);

        CV cv = cvService.findById(cvId).get();
        cvService.delete(cv);

        return "redirect:/showProfile/"+workerId;
    }

    @GetMapping("/googleMap")
    public String googleMap(Model model) {
        log.info("googleMap called");

        Gson gsonBuilder = new GsonBuilder().create();

        ArrayList<MapMarker> markerList = new ArrayList<>();
        Iterable<Assignment> assignments = assignmentService.findAll();

        for (Assignment assignment: assignments) {
            markerList.add(new MapMarker(assignment.getStreetName(), assignment.getHouseNumber()));
        }
        log.info("street: "+markerList.get(0).getStreetname());

        String jsonFromJavaArrayList = gsonBuilder.toJson(markerList);

        log.info(jsonFromJavaArrayList);
        model.addAttribute("json", jsonFromJavaArrayList);
        model.addAttribute("pageTitle", "Map Overview");

        return "googleMap";
    }

    @GetMapping("/showProfile/{id}")
    public String showProfile(@PathVariable("id") long userId, Model model) {

        log.info("showprofile is called with Id "+ userId);

        Worker worker = freelanceService.findById(userId).get();

        Iterable<CV> cvList = cvService.findAll();
        ArrayList<CV> workerCV = new ArrayList<>();

        for (CV cv: cvList) {
            if(cv.getWorker().getId() == userId){
                workerCV.add(cv);
            }

        }

        model.addAttribute("Worker", worker);
        model.addAttribute("pageTitle", "Vis profil");
        model.addAttribute("workerCV", workerCV);

        return "showProfile";
    }
}
