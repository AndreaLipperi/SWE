package com.example.ORM;

import com.example.models.Store;
import com.example.models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreDAO extends JpaRepository<Store, Long> {
    List<Store> findByProvider(User provider);
    List<Store> findBySubcategoryId(Long subcategoryId);
    Store findStoreById(Long id);
    @Transactional
    void delete(Store store);
}
