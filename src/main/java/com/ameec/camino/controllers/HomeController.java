package com.ameec.camino.controllers;

import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
     @GetMapping("")
     public String home() {
         return "home";
     }

    @GetMapping("/locations")
    public String locations() {
        try {
            String content = new String(Files.readAllBytes(Paths.get("src/main/resources/locations.json")));
            System.out.println(content);
            return content;
        } catch (Exception e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
        return "File not found";
    }
}
