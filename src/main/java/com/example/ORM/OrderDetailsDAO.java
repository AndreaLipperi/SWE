package com.example.ORM;

import com.example.models.Order;
import com.example.models.Order_Details;
import com.example.models.Store;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailsDAO extends JpaRepository<Order_Details, Long> {
    // Trova i dettagli dell'ordine per un dato ordine
    List<Order_Details> findByOrderId(Long orderId);
    List<Order_Details> findByStoreAndStatus(Store store, String status);
    List<Order_Details> findByStore(Store store);
    List<Order_Details> findByOrderAndStore(Order order, Store store);
    @Transactional
    void delete(Order_Details orderDetail);
}
