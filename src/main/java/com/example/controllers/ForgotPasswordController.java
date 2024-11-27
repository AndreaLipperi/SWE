package com.example.controllers;

import com.example.models.User;
import com.example.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ForgotPasswordController {

    @Autowired
    private UserService userService;

    @PostMapping("/forgot_password")
    public String login(
            @RequestParam("username") String username,
            @RequestParam("user_type") String userType,
            Model model,
            HttpSession session) {

        // Controlla se l'utente esiste
        List<User> users = userService.findByType(userType);

        // Filtra ulteriormente per username se ci sono utenti per quel tipo
        User user = users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);

        // Se l'utente non esiste o la password non corrisponde
        if (user == null) {
            model.addAttribute("error", "Credenziali errate");
            return "account_pages/login_page"; // Ritorna alla pagina di login con un messaggio di errore
        }

            // Se è un fornitore
        session.setAttribute("user", user);
        return "redirect:/update_psw"; // Reindirizza alla home del fornitore

    }
}
