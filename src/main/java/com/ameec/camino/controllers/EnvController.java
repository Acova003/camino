package com.ameec.camino.controllers;

import org.springframework.web.bind.annotation.GetMapping;

@
public class EnvController {
    @GetMapping("/api/env")
    public String getEnv() {
        String apiKey = System.getenv(GOOGLE_MAPS_API_KEY);
        return apiKey;
    }
}
