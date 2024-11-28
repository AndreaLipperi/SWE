package com.example.controllers;

import com.example.models.*;
import com.example.repositories.OrderDetailsRepository;
import com.example.repositories.OrderRepository;
import com.example.services.CartService;
import com.example.services.StoreService;
import com.example.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Controller
public class OrderDetailsController {

    @Autowired
    private UserService userService;  // Aggiungi il tuo servizio per recuperare l'utente

    @Autowired
    private StoreService storeService; // Aggiungi il tuo servizio per recuperare il prodotto
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;
    @Autowired
    private OrderRepository orderRepository;

    // Metodo per aggiungere un prodotto al carrello
    @GetMapping("/order_details")
    public String order_details(@RequestParam Long orderId,
                            HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        Order order = orderRepository.findById(orderId).orElse(null);
        session.setAttribute("order", order);
        List<Order_Details> orderDetails = orderDetailsRepository.findByOrderId(orderId);

        model.addAttribute("orderDetails", orderDetails);
        return "client/order_details_page_client";  // Dopo aver aggiunto il prodotto, reindirizza alla pagina del carrello
    }

    @GetMapping("/cancel_order_one_product")
    public String cancel_order_one_product(@RequestParam Long orderDetailId,
                                           HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        orderDetailsRepository.deleteById(orderDetailId);
        redirectAttributes.addFlashAttribute("message", "Prodotto rimosso con successo!");
        Order order = (Order) session.getAttribute("order");
        Long orderId = order.getId();
        List<Order_Details> orderDetails = orderDetailsRepository.findByOrderId(orderId);
        if(orderDetails.isEmpty()) {
            orderRepository.deleteById(orderId);
            return "redirect:/orders";
        }
        return "redirect:/order_details?orderId=" + orderId;
    }
}
