package com.example.services;

import com.example.models.Order;
import com.example.models.Store;
import com.example.models.User;
import com.example.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public void save(Order order) {
        orderRepository.save(order);
    }

    public List<Order> findOrdersByUser(User user, String status) {
        List<Order> orders;

        if (status != null && !status.isEmpty()) {
            // Filtra per utente e stato
            orders = orderRepository.findByUserAndStatus(user, status);
        } else {
            // Se lo stato non è specificato, filtra solo per utente
            orders = orderRepository.findByUser(user);
        }

        return orders;
    }

}

