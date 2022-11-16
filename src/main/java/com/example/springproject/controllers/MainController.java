package com.example.springproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelExtensionsKt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.xml.transform.sax.SAXResult;

@Controller
    public class MainController {

        @GetMapping("/main")
        public String home(Model model) {
            model.addAttribute("title", "Main page");
            return "home";
        }
        @GetMapping("/manager")
        public String managerMenu(Model model){
            model.addAttribute("Name","Manager");
                return "manager";
        }





    }

