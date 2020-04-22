package com.vikar.work.controllers;

import com.vikar.work.models.Company;
import com.vikar.work.models.Worker;
import com.vikar.work.services.CompanyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;
import java.util.logging.Logger;

@Controller
public class CompanyController {

    public CompanyController(){

    }

    @Autowired
    CompanyServiceImpl companyService;

    Logger log = Logger.getLogger(CompanyController.class.getName());


    @GetMapping("/editCompany/{id}")
    public String editWorker(@PathVariable("id") long companyId, Model model){
        log.info("edit Company called med id: " + companyId);

        model.addAttribute("company", companyService.findById(companyId));


        return "editCompany";
    }

    @PostMapping("/editCompany")
    public String editWorker (@ModelAttribute Company company, Model model) {
        log.info("editCompany putmapping called...");
        String test = ""+company.getCVRNumber();

            companyService.updateCompany(company);

        return "redirect:/editCompany/1";
    }

    @PostMapping("/deleteCompany")
    public String deleteCompany(@ModelAttribute Company company) {
        log.info("delete worker called id: "+company.getId());
        companyService.deleteCompany(company.getId());

        return "index";
    }
}
