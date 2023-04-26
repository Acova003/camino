package com.ameec.camino.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
    // show home method
    @RequestMapping("")
    public String home() {
        return "home";
    }
}
