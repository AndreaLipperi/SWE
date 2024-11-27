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
public class UpdatePswController {

    @Autowired
    private UserService userService;

    @PostMapping("/update_psw")
    public String updatePassword(
            @RequestParam("psw") String newPassword,
            HttpSession session,
            Model model) {

        // Recupera l'utente dalla sessione
        User user = (User) session.getAttribute("user");

        // Verifica che l'utente sia presente nella sessione
        if (user == null) {
            model.addAttribute("error", "Utente non trovato nella sessione");
            return "account_pages/login_page"; // Reindirizza alla pagina di login se l'utente non è nella sessione
        }

        // Imposta la nuova password
        user.setPsw(newPassword);

        // Salva l'utente aggiornato
        userService.saveUser(user);

        // Aggiungi un messaggio di successo
        model.addAttribute("message", "Password aggiornata con successo");

        // (Opzionale) Rimuovi l'utente dalla sessione dopo l'update (se vuoi)
        session.removeAttribute("user");

        // Ritorna alla pagina di login o alla home dell'utente
        return "account_pages/login_page"; // O redirect a un'altra pagina se necessario
    }
}
