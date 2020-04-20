package com.vikar.work.controllers;

import com.vikar.work.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.logging.Logger;

@Controller
public class AdminController {

    public AdminController(){

    }

    @Autowired
    AdminService adminService;

    Logger log = Logger.getLogger(AdminController.class.getName());
}
