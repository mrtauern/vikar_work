package com.vikar.work.controllers;

import com.vikar.work.services.FreelanceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.logging.Logger;

@Controller
public class FreelanceController {

    public FreelanceController(){

    }

    @Autowired
    FreelanceServiceImpl freelanceService;

    Logger log = Logger.getLogger(FreelanceController.class.getName());

    @GetMapping("/")
    public String index(Model model){
        log.info("index called");

        return "index";
    }

}
