package com.example.services;

import com.example.models.Subcategory;
import com.example.ORM.SubcategoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubcategoryService {

    @Autowired
    private SubcategoryDAO subcategoryDAO;

    // Trova le sottocategorie per una categoria data
    public List<Subcategory> findByCategoryId(Long categoryId) {
        return subcategoryDAO.findByCategoryId(categoryId);
    }
}
