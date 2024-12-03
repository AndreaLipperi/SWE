package com.example.controllers;


import ch.qos.logback.core.model.Model;
import com.example.models.Order;
import com.example.models.Order_Details;
import com.example.models.Store;
import com.example.repositories.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.models.User;
import com.example.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StoreRepository storeRepository;

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

    @GetMapping("/control_psw")
    public String controlPsw(@RequestParam("psw") String psw, RedirectAttributes redirectAttributes, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (!user.getPsw().equals(psw)) {
            redirectAttributes.addFlashAttribute("error", "Password sbagliata");
        } else {
            redirectAttributes.addFlashAttribute("username", user.getUsername());
            redirectAttributes.addFlashAttribute("email", user.getEmail());
            redirectAttributes.addFlashAttribute("formVisible", true); // Attributo per mostrare il form
        }
        return "redirect:/modify_account";
    }

    @GetMapping("/update_datas")
    public String updateDatas(@RequestParam("email") String email, @RequestParam("username") String username, RedirectAttributes redirectAttributes, HttpSession session) {
        User user = (User) session.getAttribute("user");

        // Aggiorna i dati dell'utente
        user.setEmail(email);
        user.setUsername(username);

        // Salva i dati aggiornati nel database
        boolean updateSuccess = userService.updateUser(user); // Metodo del servizio per aggiornare nel DB

        if (updateSuccess) {
            redirectAttributes.addFlashAttribute("message", "Dati aggiornati con successo.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Errore durante l'aggiornamento.");
        }

        return "redirect:/homepage";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("password") String password, RedirectAttributes redirectAttributes, HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user.getType().equals("C") && user.getPsw().equals(password)) {
            List<Order> orders = orderRepository.findByUserAndStatus(user, "S");
            if (!orders.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Non puoi eliminare il tuo account se ha ordini in sospeso, annullali poi eliminca l'account");
                return "redirect:/homepage";
            } else {
                List<Order> order = orderRepository.findByUser(user);
                for (Order o : order) {
                    List<Order_Details> order_details = orderDetailsRepository.findByOrderId(o.getId());
                    orderDetailsRepository.deleteAll(order_details);
                }
                orderRepository.deleteAll(order);
                cartRepository.deleteByUser(user);
                session.removeAttribute("user");
                userRepository.delete(user);
            }
        } else if(user.getType().equals("F") && user.getPsw().equals(password)) {
            int control = 0;
            List<Store> store = storeRepository.findByProvider(user);
            for (Store s : store) {
                List<Order_Details> order_details = orderDetailsRepository.findByStoreAndStatus(s, "S");
                if (!order_details.isEmpty()) {
                    control = 0;
                } else {
                    control = 1;
                }
            }
            if (control == 0) {
                for (Store s : store) {
                    List<Order_Details> order_details = orderDetailsRepository.findByStore(s);
                    orderDetailsRepository.deleteAll(order_details);
                }
                storeRepository.deleteAll(store);
                session.removeAttribute("user");
                userRepository.delete(user);
            } else {
                redirectAttributes.addFlashAttribute("error", "Non puoi eliminare il tuo account se ha ordini in sospeso, annullali poi eliminca l'account");
                return "redirect:/homepage";
            }
        }
        return "redirect:/";
    }
}
