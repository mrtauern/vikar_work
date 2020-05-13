package com.vikar.work.controllers;

import com.vikar.work.models.Company;
import com.vikar.work.models.User;
import com.vikar.work.models.Worker;
import com.vikar.work.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String index(HttpSession session, Model model){
        Optional<Company> company = companyService.findById(1);
        if(company.isPresent()){
            log.info("Fandt " + company.get().getCompanyName());
        } else {
            log.info("Fandt ingen firma");
        }

        if(session.getAttribute("login") != null){
            log.info(""+session.getAttribute("login"));
        } else {
            log.info("Not logged in!");
        }

        model.addAttribute("pageTitle", "Forside");

        return "index";
    }

    @GetMapping("/createUser")
    public String createUser(Model model){
        log.info("create user called (get)");

        model.addAttribute("pageTitle", "Opret bruger");

        return "create_user";
    }

    @PostMapping("/createUser")
    public String createUser(@ModelAttribute User user, RedirectAttributes redirAttr){
        log.info("create user called (post)");

        log.info(user.getType());

        if(!user.getPassword1().equals(user.getPassword2())){
            log.info("The passwords does not match!");
            return "redirect:/createUser";
        }

        Iterable<Company> companies = companyService.findAll();
        for (Company c: companies) {
            if(c.getUsername().equals(user.getUsername())){
                log.info("Username already exist!");
                redirAttr.addFlashAttribute("userExist", true);
                redirAttr.addFlashAttribute("user", user);
                return "redirect:/createUser";
            }
        }

        Iterable<Worker> workers = freelanceService.findAll();
        for (Worker w: workers) {
            if(w.getUsername().equals(user.getUsername())){
                log.info("Username already exist!");
                redirAttr.addFlashAttribute("userExist", true);
                redirAttr.addFlashAttribute("user", user);
                return "redirect:/createUser";
            }
        }

        if(user.getType().equals("company")){

            Company company = new Company();
            company.setCVRNumber(0);
            company.setBankNumber(0);
            company.setHouseNumber(0);
            company.setZip(0);

            company.setCompanyName("");
            company.setUsername(user.getUsername());
            company.setPassword(user.getPassword1());
            company.setStreetName("");
            company.setCity("");
            company.setPhoneNumber(user.getPhoneNumber());

            companyService.save(company);
        }

        if(user.getType().equals("freelance")){

            Worker worker = new Worker();
            worker.setCVRNumber(0);
            worker.setBankNumber(0);
            worker.setHouseNumber(0);
            worker.setZip(0);

            worker.setFirstname(user.getFirstname());
            worker.setLastname(user.getLastname());
            worker.setEmail(user.getEmail());
            worker.setUsername(user.getUsername());
            worker.setPassword(user.getPassword1());
            worker.setStreetName("");
            worker.setPhoneNumber(user.getPhoneNumber());
            worker.setCity("");

            log.info("telefon nummer" + user.getPhoneNumber());
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

        return "redirect:/landingPage";
    }

    private void setLogin(HttpSession session, char type, long id){
        String user = type+""+id;
        session.setAttribute("login", user);
        log.info("login success");
    }

    @GetMapping("/log_out")
    public String logout(HttpSession session){
        log.info("Log out called...");

        session.removeAttribute("login");

        return "redirect:/";
    }

    @PostMapping("/search/{searchInput}")
    public String search(@PathVariable("searchInput") String searchInput, @RequestParam("page") int page) {

        return "redirect:/searchResult/"+searchInput+"/"+page;
    }

    @GetMapping("loginLandingPage")
    public String loginLandingPag(){

        return "LoginLandingPage";
    }

    @GetMapping("/notLoggedIn")
    public String notLoggedIn(){
        log.info("User not logged in");

        return "not_logged_in";
    }

    @GetMapping("/settings")
    public String settings(HttpSession session){
        long id = 0;
        String type = "";

        if(session.getAttribute("login") != null){
            String userId = (String) session.getAttribute("login");
            log.info("User full id: " + userId);

            id = Long.valueOf(userId.substring(1));
            type = userId.substring(0, 1);

            log.info("User id: " + id);
            log.info("Type: " + type);

            if(type.equals("w")){
                return "redirect:/editWorker/" + id;
            } else if (type.equals("c")) {
                return "redirect:/editCompany/" + id;
            } else {
                return "redirect:/notLoggedIn";
            }
        } else {
            return "redirect:/notLoggedIn";
        }
    }

    @GetMapping("/landingPage")
    public String landingPage(HttpSession session){
        long id = 0;
        String type = "";

        if(session.getAttribute("login") != null){
            String userId = (String) session.getAttribute("login");
            log.info("User full id: " + userId);

            id = Long.valueOf(userId.substring(1));
            type = userId.substring(0, 1);

            log.info("User id: " + id);
            log.info("Type: " + type);

            if(type.equals("w")){
                return "redirect:/assignments";
            } else if (type.equals("c")) {
                return "redirect:/activeAssignmentList";
            } else {
                return "redirect:/notLoggedIn";
            }
        } else {
            return "redirect:/notLoggedIn";
        }
    }

    @GetMapping("/omOs")
    public String omOs(Model model){
        model.addAttribute("pageTitle", "Om os");

        return "om_os";
    }
}