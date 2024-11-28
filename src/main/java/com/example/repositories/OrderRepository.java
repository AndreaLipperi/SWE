package com.example.repositories;

import com.example.models.Order;
import com.example.models.Order_Details;
import com.example.models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Trova gli ordini per un dato utente

    List<Order> findByUser(User user);

    List<Order> findByUserAndStatus(User user, String status);
    @Transactional
    void delete(Order order);
    // Puoi aggiungere altre query personalizzate se necessario
}
