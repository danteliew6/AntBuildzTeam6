package com.antbuildz.team6.controllers;

import com.antbuildz.team6.models.Request;
import com.antbuildz.team6.repositories.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;

@Controller
public class DashboardController {
    @Autowired
    RequestRepository requestRepository;

    @GetMapping("/index")
    public String loginPage(Model model){
        //return the webpage for login
//        ArrayList<Request> requestList = (ArrayList<Request>) requestRepository.findAll();
//        model.addAttribute("requestList", requestList);
        return "login";
    }



    @GetMapping("/signup")
    public String signUpPage(Model model) {
        return "signup";
    }


}