package com.example.controllers;

import com.example.models.*;
import com.example.ORM.OrderDetailsDAO;
import com.example.ORM.OrderDAO;
import com.example.ORM.StoreDAO;
import com.example.services.StoreService;
import com.example.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;


@Controller
public class OrderDetailsController {

    @Autowired
    private UserService userService;  // Aggiungi il tuo servizio per recuperare l'utente

    @Autowired
    private StoreService storeService; // Aggiungi il tuo servizio per recuperare il prodotto
    @Autowired
    private OrderDetailsDAO orderDetailsDAO;
    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private StoreDAO storeDAO;

    // Metodo per aggiungere un prodotto al carrello
    @GetMapping("/order_details")
    public String order_details(@RequestParam Long orderId,
                            HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        Order order = orderDAO.findById(orderId).orElse(null);
        session.setAttribute("order", order);
        List<Order_Details> orderDetails = orderDetailsDAO.findByOrderId(orderId);

        model.addAttribute("orderDetails", orderDetails);
        return "client/order_details_page_client";  // Dopo aver aggiunto il prodotto, reindirizza alla pagina del carrello
    }

    @GetMapping("/cancel_order_one_product")
    public String cancel_order_one_product(@RequestParam Long orderDetailId,
                                           HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        orderDetailsDAO.deleteById(orderDetailId);
        redirectAttributes.addFlashAttribute("message", "Prodotto rimosso con successo!");
        Order order = (Order) session.getAttribute("order");
        Long orderId = order.getId();
        List<Order_Details> orderDetails = orderDetailsDAO.findByOrderId(orderId);
        if(orderDetails.isEmpty()) {
            orderDAO.deleteById(orderId);
            return "redirect:/orders";
        }
        return "redirect:/order_details?orderId=" + orderId;
    }

    @GetMapping("/order_details_provider")
    public String order_details_provider(@RequestParam Long orderId,
                                HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        Order order = orderDAO.findById(orderId).orElse(null);
        User user = (User) session.getAttribute("user");
        List<Store> stores = storeDAO.findByProvider(user);
        session.setAttribute("order", order);
        List<Order_Details> allOrderDetails = new ArrayList<>();
        List<Order_Details> orderDetails = orderDetailsDAO.findByOrderId(orderId);
        for(Store store : stores) {
            // Filtra solo quelli che appartengono al negozio corrente
            List<Order_Details> filteredByStore = orderDetails.stream()
                    .filter(od -> od.getStore().equals(store))
                    .toList();
            allOrderDetails.addAll(filteredByStore);
        }
        model.addAttribute("orderDetails", allOrderDetails);
        return "/provider/orders_details_page_provider";  // Dopo aver aggiunto il prodotto, reindirizza alla pagina del carrello
    }

    @GetMapping("/accept_deny_single_product")
    public String accept_deny_single_product(@RequestParam String accept_or_deny, @RequestParam Long orderDetailId, HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        Order order = (Order) session.getAttribute("order");
        Order_Details orderDetail = orderDetailsDAO.findById(orderDetailId).orElse(null);
        if (accept_or_deny.equals("accept")) {
            orderDetail.setStatus("A");
            orderDetailsDAO.save(orderDetail);
        } else {
            orderDetail.setStatus("R");
            orderDetailsDAO.save(orderDetail);
        }
        List<Order_Details> orderDetails = orderDetailsDAO.findByOrderId(order.getId());
        List<Order_Details> filteredByStatus = orderDetails.stream()
                .filter(od -> od.getStatus().equals("S"))
                .toList();
        if (filteredByStatus.isEmpty()) {
            order.setStatus("C");
            orderDAO.save(order);
        }
        model.addAttribute("messagge", "Prodotto accettato con successo");
        return "redirect:/orders_for_provider";
    }

    @GetMapping("/accept_or_deny_all")
    public String accept_or_deny_all(@RequestParam("action_value") String accept_or_deny, HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        Order order = (Order) session.getAttribute("order");
        User user = (User) session.getAttribute("user");
        List<Store> stores = storeDAO.findByProvider(user);
        List<Order_Details> orderDetails = orderDetailsDAO.findByOrderId(order.getId());
        List<Order_Details> allOrderDetails = new ArrayList<>();
        for(Store store : stores) {
            // Filtra solo quelli che appartengono al negozio corrente
            List<Order_Details> filteredByStore = orderDetails.stream()
                    .filter(od -> od.getStore().equals(store))
                    .toList();
            allOrderDetails.addAll(filteredByStore);
            for (Order_Details od : allOrderDetails) {
                if (accept_or_deny.equals("accept") && od.getStatus().equals("S")) {
                    od.setStatus("A");
                    orderDetailsDAO.save(od);
                } else if (accept_or_deny.equals("deny") && od.getStatus().equals("S")) {
                    od.setStatus("R");
                    orderDetailsDAO.save(od);
                }
            }
        }
        List<Order_Details> ord_det = orderDetailsDAO.findByOrderId(order.getId());
        int hasS = 0;
        for(Order_Details od : ord_det) {
            if (od.getStatus().equals("S")) {
                hasS = 1;  // Se trovi almeno un prodotto con stato "S", setti hasS a 1
                break;
            }
        }
        if (hasS == 0) {
            order.setStatus("C");
            orderDAO.save(order);
        }
        model.addAttribute("messagge", "Ordine accettato con successo");
        return "redirect:/orders_for_provider";
    }

}
