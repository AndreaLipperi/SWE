package com.example.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/login")
    public String loginPage() {
        return "account_pages/login_page"; // Restituisce login_page.html (Thymeleaf)
    }

    @GetMapping("/register")
    public String registerPage() {
        return "account_pages/registration_page"; // Restituisce registration_page.html (Thymeleaf)
    }
}
