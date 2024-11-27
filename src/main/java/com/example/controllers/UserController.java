package com.example.controllers;

import ch.qos.logback.core.model.Model;
import com.example.models.User;
import com.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    // Gestisce la registrazione dell'utente
    @PostMapping("/register")
    public String registerUser(
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("psw") String psw,
            @RequestParam("user_type") String type) {

        // Crea un nuovo oggetto User con i dati ricevuti
        User user = new User(type, email, psw, username);

        // Salva l'utente nel database tramite il servizio
        userService.saveUser(user);

        // Ritorna la vista di successo
        return "redirect:/"; // Assicurati che questa pagina esista
    }

}
