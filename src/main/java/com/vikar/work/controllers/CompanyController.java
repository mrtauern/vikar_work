package com.vikar.work.controllers;

import com.vikar.work.models.Assignment;
import com.vikar.work.models.Company;
import com.vikar.work.services.AssignmentService;
import com.vikar.work.services.CompanyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.logging.Logger;

@Controller
public class CompanyController {

    public CompanyController(){

    }

    @Autowired
    CompanyServiceImpl companyService;

    @Autowired
    AssignmentService assignmentService;

    Logger log = Logger.getLogger(CompanyController.class.getName());


    @GetMapping("/editCompany/{id}")
    public String editWorker(@PathVariable("id") long companyId, Model model, HttpSession session){
        log.info("edit Company called med id: " + companyId);

        if(session.getAttribute("login") != null){
            log.info(""+session.getAttribute("login"));

            String[] sessionId = companyService.checkSession((String)session.getAttribute("login"));

            if(sessionId[0].equals(""+companyId) && sessionId[1].equals("c")) {
                model.addAttribute("company", companyService.findById(companyId));
                model.addAttribute("loginType", sessionId[1]);

                return "editCompany";

            }
            else if (sessionId[0].equals(""+companyId) && sessionId[1].equals("w")){

                return "index";
            }

            else {

                return "redirect:/editCompany/"+sessionId[0];
            }

        } else {
            log.info("Not logged in!");
            return "login";
        }
    }

    @PostMapping("/editCompany")
    public String editWorker (@ModelAttribute Company company, Model model, HttpSession session) {
        log.info("editCompany putmapping called...");

        if(session.getAttribute("login") != null){
            log.info(""+session.getAttribute("login"));

            String[] sessionId = companyService.checkSession((String)session.getAttribute("login"));

            if(sessionId[0].equals(""+company.Id) && sessionId[1].equals("c")) {
                String test = ""+company.getCVRNumber();

                companyService.updateCompany(company);

                return "redirect:/editCompany/"+company.Id;

            }
            else if (sessionId[0].equals(""+company.Id) && sessionId[1].equals("w")){

                return "index";
            }
            else {

                return "redirect:/editCompany/"+sessionId[0];
            }

        } else {
            log.info("Not logged in!");
            return "login";
        }
    }

    @PostMapping("/deleteCompany")
    public String deleteCompany(@ModelAttribute Company company, HttpSession session) {
        log.info("delete worker called id: "+company.getId());

        if(session.getAttribute("login") != null){
            log.info(""+session.getAttribute("login"));

            String[] sessionId = companyService.checkSession((String)session.getAttribute("login"));
            ArrayList<Assignment> assignments = (ArrayList<Assignment>) assignmentService.findAll();

            if(sessionId[0].equals(""+company.Id) && sessionId[1].equals("c")) {
                for (Assignment a: assignments) {
                    if(a.getCompany().getId() == company.getId()) {
                        a.setCompany(null);
                        a.setArchived(true);
                        assignmentService.save(a);
                    }
                }
                companyService.deleteCompany(company.getId());

                return "redirect:/log_out";

            }
            else if (sessionId[0].equals(""+company.Id) && sessionId[1].equals("w")){

                return "redirect:/landingPage";
            }
            else {

                return "redirect:/landingPage";
            }

        } else {
            log.info("Not logged in!");
            return "login";
        }
    }
}
