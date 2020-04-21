package com.vikar.work.controllers;

import com.vikar.work.models.Company;
import com.vikar.work.models.User;
import com.vikar.work.services.AdminServiceImpl;
import com.vikar.work.services.CompanyServiceImpl;
import com.vikar.work.services.FreelanceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;
import java.util.logging.Logger;

@Controller
public class HomeController {

    public HomeController(){

    }

    @Autowired
    FreelanceServiceImpl freelanceService;

    @Autowired
    CompanyServiceImpl companyService;

    @Autowired
    AdminServiceImpl adminService;

    Logger log = Logger.getLogger(HomeController.class.getName());

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

    @GetMapping("/createUser")
    public String createUser(Model model){
        log.info("create user called");

        model.addAttribute("pageTitle", "Opret bruger");

        return "create_user";
    }

    @PostMapping("/createUser")
    public String createUser(@ModelAttribute User user){

        return "redirect:/";
    }
}
