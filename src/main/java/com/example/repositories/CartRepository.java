package com.example.repositories;

import com.example.models.Cart;
import com.example.models.User;
import com.example.models.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUserAndProduct(User user, Store product);
    List<Cart> findByUser(User user);
    Optional<Cart> findById(Long cartId);

}
