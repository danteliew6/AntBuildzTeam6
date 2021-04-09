package com.antbuildz.team6.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    @GetMapping("/next")
    public String loginPage(Model model){
        //return the webpage for login
        model.addAttribute("name", "your mother");
        return "test";
    }
}