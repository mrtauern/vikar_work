package com.vikar.work.controllers;

import com.vikar.work.models.Company;
import com.vikar.work.services.CompanyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;
import java.util.logging.Logger;

@Controller
public class CompanyController {

    public CompanyController(){

    }

    @Autowired
    CompanyServiceImpl companyService;

    Logger log = Logger.getLogger(CompanyController.class.getName());

    @GetMapping("/")
    public String index(){
        Optional<Company> company = companyService.findById(1);
        if(company.isPresent()){
            log.info("Fandt " + company.get().getCompanyName());
        } else {
            log.info("Fandt ingen firma");
        }
        return "index";
    }
}
