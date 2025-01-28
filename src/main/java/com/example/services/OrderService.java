package com.example.services;

import com.example.models.Order;
import com.example.models.User;
import com.example.ORM.OrderDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderDAO orderDAO;

    public void save(Order order) {
        orderDAO.save(order);
    }

    public List<Order> findOrdersByUser(User user, String status) {
        List<Order> orders;

        if (status != null && !status.isEmpty()) {
            // Filtra per utente e stato
            orders = orderDAO.findByUserAndStatus(user, status);
        } else {
            // Se lo stato non è specificato, filtra solo per utente
            orders = orderDAO.findByUser(user);
        }
        orders.sort(Comparator.comparing(Order::getDate).reversed());

        return orders;
    }


}

