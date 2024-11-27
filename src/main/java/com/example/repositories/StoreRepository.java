package com.example.repositories;

import com.example.models.Store;
import com.example.models.Subcategory;
import com.example.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    List<Store> findByProvider(User provider);
    List<Store> findBySubcategoryId(Long subcategoryId);
}
