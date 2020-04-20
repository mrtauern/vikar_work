package com.vikar.work.controllers;

import com.vikar.work.services.CompanyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.logging.Logger;

@Controller
public class CompanyController {

    public CompanyController(){

    }

    @Autowired
    CompanyServiceImpl companyService;

    Logger log = Logger.getLogger(CompanyController.class.getName());
}
