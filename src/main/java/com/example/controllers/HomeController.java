package com.example.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        // Percorso relativo alla configurazione di spring.thymeleaf.prefix
        return "account_pages/index";
    }
}
