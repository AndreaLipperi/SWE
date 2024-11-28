package com.example.services;

import com.example.models.Cart;
import com.example.models.User;
import com.example.models.Store;
import com.example.repositories.CartRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    public void save(Cart cart) {
        cartRepository.save(cart);
    }
    public Cart findCartByUserAndProduct(User user, Store product) {
        List<Cart> carts = cartRepository.findByUserAndProduct(user, product);
        if (carts != null && !carts.isEmpty()) {
            return carts.get(0); // Restituisce il primo carrello trovato (assumendo che ci sia solo uno)
        }
        return null;
    }
    public List<Cart> findCartByUser(User user) {
        return cartRepository.findByUser(user);
    }

    public boolean removeFromCart(Long cartId) {
        Optional<Cart> cartOptional = cartRepository.findById(cartId); // Cerca il carrello con l'ID specificato

        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
            cartRepository.delete(cart); // Rimuove il carrello dal database
            return true; // Restituisce true se è stato rimosso correttamente
        }
        return false; // Se non è stato trovato, restituisce false
    }
    @Transactional
    public void removeAllFromCart(User user) {
        // Elimina tutti gli articoli dal carrello dell'utente
        cartRepository.deleteByUser(user);
    }
}
