package com.ameec.camino.controllers;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ameec.camino.entities.Trip;
import com.ameec.camino.services.TripService;

@RestController
@RequestMapping("api/v1/trip")
public class TripController {
    @Autowired
    private TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping("/{userId}")
    public Optional<Trip> getTripByUserId(@PathVariable Long userId) {
        return tripService.getTripByUserId(userId);
    }

    @GetMapping("/locations")
    public String locations() {
        try {
            String content = new String(Files.readAllBytes(Paths.get("src/main/resources/locations.json")));
            return content;
        } catch (Exception e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
        return "File not found";
    }

}
