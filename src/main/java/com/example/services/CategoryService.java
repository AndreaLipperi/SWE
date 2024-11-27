package com.example.services;

import com.example.models.Category;
import com.example.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoriaRepository;

    public List<Category> getAllCategorie() {
        return categoriaRepository.findAll();
    }
}
