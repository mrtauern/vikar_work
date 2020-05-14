package com.vikar.work.controllers;

import com.vikar.work.models.*;
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

    @Autowired
    MessageService messageService;

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
            if(c.getUsername().toLowerCase().equals(user.getUsername().toLowerCase())){
                log.info("Username already exist!");
                redirAttr.addFlashAttribute("userExist", true);
                redirAttr.addFlashAttribute("user", user);
                return "redirect:/createUser";
            }
        }

        Iterable<Worker> workers = freelanceService.findAll();
        for (Worker w: workers) {
            if(w.getUsername().toLowerCase().equals(user.getUsername().toLowerCase())){
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
            if(c.getUsername().toLowerCase().equals(user.getUsername().toLowerCase())){
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
            if(w.getUsername().toLowerCase().equals(user.getUsername().toLowerCase())){
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


    @GetMapping("/sendMessage")
    public String sendMessage(HttpSession session, @ModelAttribute Message message, Model model) {
        log.info("sendMessage called...");
        String[] sessionId = freelanceService.checkSession((String)session.getAttribute("login"));
        log.info("SenderId: "+sessionId[0]+" SenderType: "+sessionId[1]);

        if(session.getAttribute("login") != null){
            model.addAttribute("pageTitle", "Send Besked");
            model.addAttribute("senderId", sessionId[0]);
            model.addAttribute("senderType", sessionId[1]);
            model.addAttribute("reply",false);
            model.addAttribute("loginType", sessionId[1]);

            return "sendMessage";
        }
        else {
            return "redirect:/notLoggedIn";
        }
    }

    @GetMapping("/sendMessage/{recipientUser}/{messageId}")
    public String sendMessage(HttpSession session, @PathVariable("recipientUser") String recipientUser, @PathVariable("messageId") long messageId, Model model) {
        log.info("sendMessage called from reply with recipientUser: "+recipientUser+" and messageId: "+messageId);
        String[] sessionId = freelanceService.checkSession((String)session.getAttribute("login"));

        if(session.getAttribute("login") != null){
            Message prevMessage = messageService.findById(messageId).get();
            model.addAttribute("prevMessage", prevMessage);
            model.addAttribute("recipient",recipientUser);
            model.addAttribute("reply", true);
            model.addAttribute("pageTitle", "Send Besked");
            model.addAttribute("senderId", sessionId[0]);
            model.addAttribute("senderType", sessionId[1]);
            model.addAttribute("loginType", sessionId[1]);

            return "sendMessage";
        }
        else {
            return "redirect:/notLoggedIn";
        }
    }

    @PostMapping("/sendMessage")
    public String sendMessage(@ModelAttribute Message message,
                                   @RequestParam("senderId") Long senderId,
                                   @RequestParam("senderType") String senderType,
                                   @RequestParam("username") String recipientName) {
        log.info("sendMessage called POST called...");
        Worker wSender = new Worker();
        Company cSender = new Company();

        if(senderType.equals("w")) {
            wSender = freelanceService.findById(senderId).get();
        } else if(senderType.equals("c")) {
            cSender = companyService.findById(senderId).get();
        } else {
            log.info("something went wrong1");
        }

        ArrayList<Company> companies = (ArrayList<Company>) companyService.findAll();
        ArrayList<Worker> workers = (ArrayList<Worker>) freelanceService.findAll();
        Company sendToCompany = new Company();
        Worker sendToWorker = new Worker();

        log.info("RecipientName: "+recipientName);

        for (Company c: companies) {
            if(c.getUsername().equals(recipientName)) {
                sendToCompany = c;
                log.info("sending to company: "+c.getUsername());
            }
        }

        for (Worker w: workers) {
            if(w.getUsername().equals(recipientName)) {
                sendToWorker = w;
                log.info("sending to worker: "+w.getUsername());
            }
        }

        if(sendToCompany.getUsername() != null) {
            Message sendMessage = new Message();
            sendMessage.setContent(message.getContent());

            if(senderType.equals("w")) {
                sendMessage.setSenderWorker(wSender);
            }else {
                sendMessage.setSenderCompany(cSender);
            }
            sendMessage.setHeadline(message.getHeadline());
            sendMessage.setRecipientCompany(sendToCompany);
            messageService.save(sendMessage);

        } else if (sendToWorker.getUsername() != null) {
            log.info("time to send the message to worker: "+sendToWorker.getUsername());
            Message sendMessage = new Message();
            sendMessage.setContent(message.getContent());

            if(senderType.equals("w")) {
                sendMessage.setSenderWorker(wSender);
            }else {
                sendMessage.setSenderCompany(cSender);
            }
            sendMessage.setHeadline(message.getHeadline());
            sendMessage.setRecipientWorker(sendToWorker);
            messageService.save(sendMessage);

        } else {
            //error
            log.info("something went wrong2");
        }

        //skal nok laves til indboks eller sendte beskeder
        return "redirect:/inbox";
    }

    @GetMapping("/inbox")
    public String inbox(HttpSession session, Model model) {
        log.info("inbox called");
        if(session.getAttribute("login") != null){
            String[] sessionId = freelanceService.checkSession((String)session.getAttribute("login"));
            model.addAttribute("pageTitle", "Inbox");
            log.info("added page title and session info variables");

            ArrayList<Message> messages = messageService.findMessages(sessionId[0],sessionId[1]);
            log.info("Found messages");

            if(messages.size() == 0) {
                log.info("message list size 0... adding empty message");
                Message emptyMessage = new Message();
                messages.add(emptyMessage);
                model.addAttribute("messages",messages);
            }
            else {
                log.info("message list size >0");
                model.addAttribute("messages", messages);
            }
            model.addAttribute("loginType", sessionId[1]);
            return "inbox";
        }
        else {
            return "redirect:/notLoggedIn";
        }
    }

    @GetMapping("/showMessage/{id}")
    public String showMessage(HttpSession session, @PathVariable("id") long messageId, Model model) {
        log.info("showMessage called with id: "+messageId);
        if(session.getAttribute("login") != null){
            String[] sessionId = freelanceService.checkSession((String)session.getAttribute("login"));
            Message message = messageService.findById(messageId).get();
            Boolean isCompany = false;
            model.addAttribute("pageTitle", "Vis besked");


            if(sessionId[1].equals("c")) {
                Company company = companyService.findById(Integer.valueOf(sessionId[0])).get();
                if(company.getId() == message.getRecipientCompany().getId()) {
                    model.addAttribute("message", message);
                    log.info("adding message");
                    if(message.getSenderWorker() !=null) {
                        model.addAttribute("sender",message.getSenderWorker());
                        log.info("setting sender worker");
                    }else if(message.getSenderCompany()!=null) {
                        model.addAttribute("sender",message.getSenderCompany());
                        isCompany = true;
                        log.info("setting sender company "+message.getSenderCompany().getCompanyName());
                    }
                }
                model.addAttribute("replier",company);
            } else if(sessionId[1].equals("w")) {
                Worker worker = freelanceService.findById(Integer.valueOf(sessionId[0])).get();
                if(worker.getId() == message.getRecipientWorker().getId()) {
                    model.addAttribute("message", message);
                    log.info("adding message");
                    if(message.getSenderWorker() !=null) {
                        model.addAttribute("sender",message.getSenderWorker());
                        log.info("setting sender worker");
                    }else if(message.getSenderCompany()!=null) {
                        model.addAttribute("sender",message.getSenderCompany());
                        isCompany = true;
                        log.info("setting sender company");
                    }
                }
                model.addAttribute("replier",worker);
                model.addAttribute("isCompany",isCompany);
                model.addAttribute("loginType", sessionId[1]);
            }
            else {
                log.info("Error sessiontype is neither c or w");
            }

            return "showMessage";
        }
        else {
            return "redirect:/notLoggedIn";
        }
    }


    @GetMapping("/omOs")
    public String omOs(Model model){
        model.addAttribute("pageTitle", "Om os");

        return "om_os";
    }
}