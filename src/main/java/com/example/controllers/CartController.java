package com.example.controllers;

import com.example.models.Cart;
import com.example.models.Store;
import com.example.models.User;
import com.example.ORM.StoreDAO;
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


@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;  // Aggiungi il tuo servizio per recuperare l'utente

    @Autowired
    private StoreService storeService; // Aggiungi il tuo servizio per recuperare il prodotto
    @Autowired
    private StoreDAO storeDAO;

    // Metodo per aggiungere un prodotto al carrello
    @GetMapping("/cart/add")
    public String addToCart(@RequestParam Long productId,
                            HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        // Recupera l'utente dalla sessione
        User user = (User) session.getAttribute("user");// Assume che l'ID utente sia memorizzato come "userId" nella sessione

        Store store = storeDAO.findStoreById(productId);
        Cart existingCart = cartService.findCartByUserAndProduct(user, store);
        if (existingCart != null) {
            redirectAttributes.addFlashAttribute("error", "Prodotto già nel carrello!");
        } else {
            Cart cart = new Cart();
            cart.setUser(user);
            cart.setProduct(store);
            cartService.save(cart);
            redirectAttributes.addFlashAttribute("message", "Prodotto aggiunto al carrello con successo!");
        }
        return "redirect:/homepage";  // Dopo aver aggiunto il prodotto, reindirizza alla pagina del carrello
    }

    // Metodo per caricare le categorie e redirigere alla pagina giusta
    @GetMapping("/cart")
    public String getCart(Model model, HttpSession session) {
        // Recupera l'utente dalla sessione
        User user = (User) session.getAttribute("user");

        // Recupera la lista dei carrelli associati all'utente
        List<Cart> carts = cartService.findCartByUser(user);

        // Se ci sono carrelli, aggiungili al modello
        model.addAttribute("carts", carts);
        return "client/cart_page";  // Ritorna alla pagina del carrello con i dati
    }
    @GetMapping("/cart/remove")
    public String removeFromCart(@RequestParam Long cartId, HttpSession session) {
        // Rimuove il carrello utilizzando l'ID
        cartService.removeFromCart(cartId);

        // Redirigi alla pagina del carrello per fare il "reload" della vista
        return "redirect:/cart"; // Renderizza la pagina del carrello
    }
}
