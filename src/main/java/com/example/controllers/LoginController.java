package com.example.controllers;

import com.example.models.User;
import com.example.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(
            @RequestParam("username") String username,
            @RequestParam("psw") String password,
            @RequestParam("user_type") String userType,
            Model model,
            HttpSession session) {

        // Controlla se l'utente esiste
        User user = userService.findByUsername(username);

        // Se l'utente non esiste o la password non corrisponde
        if (user == null || !user.getPsw().equals(password)) {
            model.addAttribute("error", "Credenziali errate");
            return "account_pages/login_page"; // Ritorna alla pagina di login con un messaggio di errore
        }

        // Verifica il tipo dell'utente
        if (userType.equals("F") && user.getType().equals("F")) {
            // Se è un fornitore
            session.setAttribute("user", user);
            session.setAttribute("isProvider", true);
            return "redirect:/homepage"; // Reindirizza alla home del fornitore
        } else if (userType.equals("C") && user.getType().equals("C")) {
            // Se è un cliente
            session.setAttribute("user", user);
            session.setAttribute("isProvider", false);
            return "redirect:/homepage";// Reindirizza alla home del cliente
        } else {
            model.addAttribute("error", "Tipo di utente non corrispondente");
            return "account_pages/login_page"; // Ritorna alla pagina di login con un messaggio di errore
        }
    }
}
