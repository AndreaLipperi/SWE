package com.example.repositories;

import com.example.models.Order_Details;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailsRepository extends JpaRepository<Order_Details, Long> {
    // Trova i dettagli dell'ordine per un dato ordine
    List<Order_Details> findByOrderId(Long orderId);

    // Puoi aggiungere altre query personalizzate se necessario
}
