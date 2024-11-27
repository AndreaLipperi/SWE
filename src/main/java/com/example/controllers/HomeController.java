package com.example.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(HttpSession session) {
        session.removeAttribute("user");
        // Percorso relativo alla configurazione di spring.thymeleaf.prefix
        return "account_pages/index";
    }
}
