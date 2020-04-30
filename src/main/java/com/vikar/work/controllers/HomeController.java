package com.vikar.work.controllers;

import com.vikar.work.models.Company;
import com.vikar.work.models.User;
import com.vikar.work.models.Worker;
import com.vikar.work.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Controller
public class HomeController {

    public HomeController(){

    }

    @Autowired
    FreelanceService freelanceService;

    @Autowired
    CompanyService companyService;

    @Autowired
    AdminService adminService;

    Logger log = Logger.getLogger(HomeController.class.getName());

    @GetMapping("/")
    public String index(HttpSession session){
        Optional<Company> company = companyService.findById(1);
        if(company.isPresent()){
            log.info("Fandt " + company.get().getCompanyName());
        } else {
            log.info("Fandt ingen firma");
        }

        if(session.getAttribute("login") != null){
            log.info(""+session.getAttribute("login"));
        }

        return "index";
    }

    @GetMapping("/createUser")
    public String createUser(Model model){
        log.info("create user called (get)");

        model.addAttribute("pageTitle", "Opret bruger");

        return "create_user";
    }

    @PostMapping("/createUser")
    public String createUser(@ModelAttribute User user){
        log.info("create user called (post)");

        log.info(user.getType());

        if(!user.getPassword1().equals(user.getPassword2())){
            log.info("The passwords does not match!");
            return null;
        }

        if(user.getType().equals("company")){

            Company company = new Company();
            company.setCVRNumber(user.getCVRNumber());
            company.setBankNumber(user.getBankNumber());
            company.setHouseNumber(user.getHouseNumber());
            company.setZip(user.getZip());

            company.setCompanyName(user.getCompanyName());
            company.setUsername(user.getUsername());
            company.setPassword(user.getPassword1());
            company.setStreetName(user.getStreetName());
            company.setCity(user.getCity());

            companyService.save(company);
        }

        if(user.getType().equals("freelance")){

            Worker worker = new Worker();
            worker.setCVRNumber(user.getCVRNumber());
            worker.setBankNumber(user.getBankNumber());
            worker.setHouseNumber(user.getHouseNumber());
            worker.setZip(user.getZip());

            worker.setFirstname(user.getFirstname());
            worker.setLastname(user.getLastname());
            worker.setEmail(user.getEmail());
            worker.setUsername(user.getUsername());
            worker.setPassword(user.getPassword1());
            worker.setStreetName(user.getStreetName());
            worker.setCity(user.getCity());

            freelanceService.save(worker);
        }

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(Model model){
        log.info("login called (get)");

        model.addAttribute("pageTitle", "Log ind");

        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpSession session){
        log.info("login called (post)");

        boolean userFound = false;

        Iterable<Company> companies = companyService.findAll();
        for (Company c: companies) {
            if(c.getUsername().equals(user.getUsername())){
                userFound = true;
                if(c.getPassword().equals(user.getPassword1())){
                    setLogin(session, 'c', c.getId());
                } else {
                    log.info("Username or password is wrong!");
                }
            }
        }

        companies.forEach(c -> {log.info("Company: "+c.getCompanyName());});

        Iterable<Worker> workers = freelanceService.findAll();
        for (Worker w: workers) {
            if(w.getUsername().equals(user.getUsername())){
                userFound = true;
                if(w.getPassword().equals(user.getPassword1())){
                    setLogin(session, 'w', w.getId());
                } else {
                    log.info("Username or password is wrong!");
                }
            }
        }

        workers.forEach(w -> {log.info("Worker: "+w.getFirstname());});

        if(userFound == false){
            log.info("Username or password is wrong!");
        }

        return "redirect:/assignments";
    }

    private void setLogin(HttpSession session, char type, long id){
        String user = type+""+id;
        session.setAttribute("login", user);
        log.info("login success");
    }

    @PostMapping("/search/{searchInput}")
    public String search(@PathVariable("searchInput") String searchInput, @RequestParam("page") int page) {

        return "redirect:/searchResult/"+searchInput+"/"+page;
    }

    @GetMapping("loginLandingPage")
    public String loginLandingPag(){

        return "LoginLandingPage";
    }
}