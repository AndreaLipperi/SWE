package com.example.controllers;

import com.example.models.*;
import com.example.repositories.OrderDetailsRepository;
import com.example.repositories.OrderRepository;
import com.example.repositories.StoreRepository;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import com.example.repositories.CartRepository;
import com.example.services.CartService;
import com.example.services.OrderDetailsService;
import com.example.services.OrderService;
import com.example.services.StoreService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class OrdersController {

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private OrderDetailsService orderDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(OrdersController.class);
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private StoreRepository storeRepository;

    @GetMapping("/do_order")
    public String doOrder(@RequestParam Map<String, String> quantities, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            model.addAttribute("error", "Utente non autenticato.");
            return "redirect:/login";
        }

        Order order = new Order();
        order.setUser(user);
        order.setDate(new Date());
        order.setStatus("S");
        orderService.save(order);

        for (String cartId : quantities.keySet()) {
            try {
                logger.debug("Processing cartId: {}", cartId); // Debug chiave cartId
                logger.debug("Value: {}", quantities.get(cartId)); // Debug valore

                // Rimuovi "quantity[" e "]"
                Long cartIdLong = Long.parseLong(cartId.replace("quantity[", "").replace("]", ""));
                int quantity = Integer.parseInt(quantities.get(cartId));

                if (quantity > 0) {
                    Cart cart = cartRepository.findById(cartIdLong)
                            .orElseThrow(() -> new IllegalArgumentException("Carrello non trovato"));

                    // Crea i dettagli dell'ordine
                    Order_Details orderDetail = new Order_Details();
                    orderDetail.setOrder(order);
                    orderDetail.setStore(cart.getProduct());
                    orderDetail.setQuantity(quantity);
                    orderDetail.setStatus("S");
                    orderDetailsService.save(orderDetail);
                }

            } catch (NumberFormatException e) {
                logger.error("Errore di formato per cartId: {} o quantità: {}", cartId, quantities.get(cartId), e);
                model.addAttribute("error", "Formato quantità errato.");
                return "redirect:/cart";  // Cambia la redirezione alla pagina di errore adeguata
            } catch (IllegalArgumentException e) {
                logger.error("Carrello non trovato o altri errori", e);
                model.addAttribute("error", "Errore con il carrello.");
                return "redirect:/cart";
            }
        }

        cartService.removeAllFromCart(user);

        model.addAttribute("message", "Ordine effettuato con successo!");
        return "redirect:/cart"; // Modifica con pagina di conferma adeguata
    }

    @GetMapping("/orders")
    public String getOrders(@RequestParam(value = "status", required = false) String status, Model model, HttpSession session) {
        // Recupera l'utente dalla sessione
        User user = (User) session.getAttribute("user");

        // Recupera la lista dei carrelli associati all'utente
        List<Order> orders = orderService.findOrdersByUser(user, status);

        // Se ci sono carrelli, aggiungili al modello
        model.addAttribute("orders", orders);
        return "client/orders_page_client";  // Ritorna alla pagina del carrello con i dati
    }
    @GetMapping("/getOrdersByStatus")
    @ResponseBody
    public String getOrdersByStatus(@RequestParam String status, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<Order> orders = orderService.findOrdersByUser(user, status);

        StringBuilder htmlResponse = new StringBuilder();

        // Inizio della struttura dati
        htmlResponse.append("<div id='datas'>");

        // Griglia dei titoli
        htmlResponse.append("<div class='grid_titoli'>");
        htmlResponse.append("<div class='grid_item_titoli'>Status</div>");
        htmlResponse.append("<div class='grid_item_titoli'>Data Ordine</div>");
        htmlResponse.append("<div class='grid_item_titoli'></div>");
        htmlResponse.append("</div>"); // Fine griglia dei titoli

        // Griglia dei dati
        htmlResponse.append("<div class='grid_dati'>");

        for (Order order : orders) {
            htmlResponse.append("<div class='grid_item_dati'>").append(order.getStatus()).append("</div>");
            htmlResponse.append("<div class='grid_item_dati'>").append(order.getDate()).append("</div>");
            htmlResponse.append("<div class='grid_item_dati'>")
                    .append("<a href='/order_details?orderId=" + order.getId() + "'>Visualizza dettagli ordine</a>")
                    .append("</div>");
        }

        htmlResponse.append("</div>"); // Fine griglia dei dati

        htmlResponse.append("</div>"); // Fine struttura dati

        return htmlResponse.toString();
    }

    @GetMapping("/cancel_order")
    public String cancelOrder(HttpSession session, Model model,
                              RedirectAttributes redirectAttributes) {
        Order order = (Order) session.getAttribute("order");
        List<Order_Details> order_details = orderDetailsRepository.findByOrderId(order.getId());

        for (Order_Details order_detail : order_details) {
            if (order_detail.getStatus().equals("S")) {
                orderDetailsRepository.delete(order_detail);
            }
        }
        List<Order_Details> order_details_canceled = orderDetailsRepository.findByOrderId(order.getId());
        if (order_details_canceled.isEmpty()) {
            orderRepository.delete(order);
        } else {
            order.setStatus("C");
            orderRepository.save(order);
        }
        session.removeAttribute("order");
        redirectAttributes.addFlashAttribute("message", "Prodotto rimosso con successo!");
        return "redirect:/homepage";
    }

    @GetMapping("/orders_for_provider")
    public String ordersForProvider(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");
        List<Store> stores = storeRepository.findByProvider(user);
        List<Order> orders = new ArrayList<>();

        Set<Long> processedOrders = new HashSet<>(); // Per evitare duplicati

        for (Store store : stores) {
            List<Order_Details> orderDetails = orderDetailsRepository.findByStore(store);

            for (Order_Details orderDetail : orderDetails) {
                Order order = orderDetail.getOrder();

                if (processedOrders.contains(order.getId())) {
                    continue;
                }

                orders.add(order);
                processedOrders.add(order.getId());
            }
        }
        for (Order order : orders) {
            List<Order_Details> orderDetails = orderDetailsRepository.findByOrderId(order.getId());
            int hasS = 0;  // Assumiamo che l'ordine sia completato (C) fin dall'inizio

            for (Order_Details orderDetail : orderDetails) {
                // Verifica se il prodotto ha stato "S" e se appartiene al fornitore corretto
                if (orderDetail.getStatus().equals("S") && orderDetail.getStore().getProvider().getId().equals(user.getId()))  {
                    hasS = 1;  // Se trovi almeno un prodotto con stato "S", setti hasS a 1
                    break;  // Puoi uscire dal ciclo appena trovi uno stato "S", non è necessario continuare a controllare gli altri dettagli
                }
            }

            // Se haS è 1, significa che almeno un prodotto ha stato "S", quindi l'ordine è "S", altrimenti è "C"
            if (hasS == 0) {
                order.setVirtualStatus("C");
            } else {
                order.setVirtualStatus("S");
            }
        }


        // Ordina gli ordini per data in ordine decrescente
        orders.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));

        // Aggiungi gli ordini al model
        model.addAttribute("orders", orders);
        return "provider/orders_page_provider";
    }


}