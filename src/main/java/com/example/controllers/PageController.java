package com.example.controllers;

import org.springframework.ui.Model;
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

    @GetMapping("/forgot_password")
    public String forgot_password() {
        return "account_pages/forgot_password"; // Restituisce registration_page.html (Thymeleaf)
    }

    @GetMapping("/update_psw")
    public String update_psw() {
        return "account_pages/update_psw"; // Restituisce registration_page.html (Thymeleaf)
    }
    @GetMapping("/modify_account")
    public String modifyAccount(Model model) {
        if (!model.containsAttribute("formVisible")) {
            model.addAttribute("formVisible", false); // Form nascosto per default
        }
        return "account_pages/modify_data_account_page";
    }
    @GetMapping("/delete_account")
    public String delete_account(Model model) {
        return "account_pages/delete_account";
    }

}
