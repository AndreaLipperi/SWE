package com.example.repositories;

import com.example.models.Order_Details;
import com.example.models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailsRepository extends JpaRepository<Order_Details, Long> {
    // Trova i dettagli dell'ordine per un dato ordine
    List<Order_Details> findByOrderId(Long orderId);
    @Transactional
    void delete(Order_Details orderDetail);
}
