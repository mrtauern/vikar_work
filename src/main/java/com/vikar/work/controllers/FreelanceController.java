package com.vikar.work.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vikar.work.models.CV;
import com.vikar.work.models.MapMarker;
import com.vikar.work.models.Worker;
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

    @GetMapping("/googleMap")
    public String googleMap(Model model) {
        log.info("googleMap called");

        Gson gsonBuilder = new GsonBuilder().create();

        ArrayList<MapMarker> markerList = new ArrayList<>();
        MapMarker markerTest = new MapMarker();

        markerTest.setId(0);
        markerTest.setLatitude(55.716346);
        markerTest.setLongitude(12.531859);
        markerTest.setTitle("Test");

        MapMarker markerTest2 = new MapMarker();

        markerTest.setId(1);
        markerTest2.setLatitude(55.716092);
        markerTest2.setLongitude(12.530518);
        markerTest2.setTitle("Test2");

        markerList.add(markerTest);
        markerList.add(markerTest2);

        String jsonFromJavaArrayList = gsonBuilder.toJson(markerList);
        log.info(jsonFromJavaArrayList);
        model.addAttribute("marker", markerTest);
        model.addAttribute("json", jsonFromJavaArrayList);

        return "googleMap";
    }
}
