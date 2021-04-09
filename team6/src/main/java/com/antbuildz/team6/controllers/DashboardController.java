package com.antbuildz.team6.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DashboardController {
    @GetMapping("/index")
    public String loginPage(Model model){
        //return the webpage for login
        model.addAttribute("name", "your mother");
        return "login";
    }



    @GetMapping("/signup")
    public String signUpPage(Model model) {
        return "signup";
    }


}