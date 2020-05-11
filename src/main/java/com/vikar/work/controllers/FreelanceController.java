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

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Qualifier("FreelanceController")
@Controller
@SessionAttributes("login")
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
    public String editWorker(@PathVariable("id") long userId, Model model, HttpSession session){
        log.info("edit Worker called med id: " + userId);



        if(session.getAttribute("login") != null){
            log.info(""+session.getAttribute("login"));
            /*String test = (String)session.getAttribute("login");*/
            String[] sessionId = freelanceService.checkSession((String)session.getAttribute("login"));

            if(sessionId[0].equals(""+userId) && sessionId[1].equals("w")) {
                model.addAttribute("worker", freelanceService.findById(userId));

                return "editWorker";
            } else if (sessionId[0].equals(""+userId) && sessionId[1].equals("c")){

                return "index";
            }
            else {
                return "redirect:/editWorker/"+sessionId[0];
            }

        } else {
            log.info("Not logged in!");
            return "login";
        }
    }

    @PostMapping("/editWorker")
    public String editWorker (@ModelAttribute Worker worker, Model model) {
        //skal der også session check her???
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

        return "redirect:/editWorker/"+worker.getId();
    }

    @PostMapping("/deleteWorker")
    public String deleteWorker(@ModelAttribute Worker worker, HttpSession session) {
        log.info("delete worker called id: "+worker.getId());
        if(session.getAttribute("login") != null){
            log.info(""+session.getAttribute("login"));
            /*String test = (String)session.getAttribute("login");*/
            String[] sessionId = freelanceService.checkSession((String)session.getAttribute("login"));

            if(sessionId[0].equals(""+worker.getId()) && sessionId[1].equals("w")) {
                freelanceService.deleteWorker(worker.getId());

                return "index";
            }
            else if (sessionId[0].equals(""+worker.getId()) && sessionId[1].equals("c")){

                return "index";
            }
            else {

                return "redirect:/editWorker/"+sessionId[0];
            }

        } else {
            log.info("Not logged in!");
            return "login";
        }
    }

    @GetMapping("/addToCv/{workerId}")
    public String addToCv(@PathVariable("workerId") long workerId, Model model, HttpSession session){
        log.info("Add to cv called (get)");


        if(session.getAttribute("login") != null){
            String[] sessionId = freelanceService.checkSession((String) session.getAttribute("login"));
            if(sessionId[0].equals(""+workerId) && sessionId[1].equals("w")) {
                model.addAttribute("pageTitle", "Tilføj til CV");
                model.addAttribute("workerId", workerId);
                return "add_to_cv";
            }
            else if (sessionId[0].equals(""+workerId) && sessionId[1].equals("c")){

                return "index";
            }

            else {
                //måske anden redirect her?
                log.info("not correct workerId");
                return "redirect:/addToCv/"+sessionId[0];
            }
        }

        else {
            //måske anden redirect her?
            return "login";
        }
    }

    @PostMapping("/addToCv")
    public String addToCv(@RequestParam("workerId")String workerId,
                          @RequestParam("workplace")String workplace,
                          @RequestParam("jobTitle")String jobTitle,
                          @RequestParam("startDate")String startDate,
                          @RequestParam("endDate")String endDate,
                          HttpSession session){

        if(session.getAttribute("login") != null){
            String[] sessionId = freelanceService.checkSession((String) session.getAttribute("login"));
            if(sessionId[0].equals(""+workerId) && sessionId[1].equals("w")) {
                Worker worker = freelanceService.findById(Long.parseLong(workerId)).get();

                CV cv = new CV(worker, workplace, jobTitle, startDate, endDate);

                cvService.save(cv);

                log.info("Add to cv called (post)");
                return "redirect:/showProfile/"+workerId;
            }
            else if (sessionId[0].equals(""+workerId) && sessionId[1].equals("c")){

                return "index";
            }
            else {
                //måske anden redirect her?
                log.info("not correct workerId");
                return "redirect:/showProfile/"+workerId;
            }
        }
        else {

            //måske anden redirect her?
            return "redirect:/showProfile/"+workerId;
        }
    }

    @GetMapping("/removeFromCv/{cvId}")
    public String removeFromCv(@PathVariable("cvId") String cvId, HttpSession session){
        log.info("Remove from cv called");

        CV cv = cvService.findById(Long.parseLong(cvId)).get();

        cvService.delete(cv);

        log.info("Cv with "+cvId+" is removed.");
        if(session.getAttribute("login") != null){
            String[] sessionId = freelanceService.checkSession((String) session.getAttribute("login"));
            if(sessionId[0].equals(""+cvService.findById(Long.parseLong(cvId)).get().getWorker().getId()) && sessionId[1].equals("w")) {
                cvService.delete(cv);
                return "redirect:/";
            }

            else if (sessionId[0].equals(""+cvService.findById(Long.parseLong(cvId)).get().getWorker().getId()) && sessionId[1].equals("c")){

                return "index";
            }
            else {
                //måske anden redirect her?
                log.info("not correct workerId");
                return "redirect:/";
            }
        }
        else {
            //måske anden redirect her?
            return "redirect:/";
        }

    }

    @PostMapping("/removeFromCv")
    public String removeFromCv(@RequestParam("cvId") long cvId,
                               @RequestParam("workerId")long workerId,
                               HttpSession session) {
        log.info("removeFromCv called with cvId: "+cvId+" and workerId: "+workerId);

        if(session.getAttribute("login") != null){
            log.info(""+session.getAttribute("login"));

            String[] sessionId = freelanceService.checkSession((String)session.getAttribute("login"));

            if(sessionId[0].equals(""+workerId) && sessionId[1].equals("w")) {
                CV cv = cvService.findById(cvId).get();
                cvService.delete(cv);

                return "redirect:/showProfile/"+workerId;

            }
            else if (sessionId[0].equals(""+workerId) && sessionId[1].equals("c")){

                return "index";
            }
            else {

                return "redirect:/editWorker/"+sessionId[0];
            }

        } else {
            log.info("Not logged in!");
            return "login";
        }
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
    public String showProfile(@PathVariable("id") long userId, Model model, HttpSession session) {
        log.info("showprofile is called with Id "+ userId);

        if(session.getAttribute("login") != null){
            log.info(""+session.getAttribute("login"));

            String[] sessionId = freelanceService.checkSession((String)session.getAttribute("login"));
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
        } else {
            log.info("Not logged in!");
            return "login";
        }
    }
}
