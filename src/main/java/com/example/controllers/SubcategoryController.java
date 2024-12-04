package com.example.controllers;

import com.example.models.Subcategory;
import com.example.services.SubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SubcategoryController {

    @Autowired
    private SubcategoryService subcategoryService;

    @GetMapping("/getSubcategories")
    @ResponseBody
    public List<Subcategory> getSubcategories(@RequestParam("categoryId") Long categoryId) {
        // Recupera le sottocategorie in base alla categoria
        return subcategoryService.findByCategoryId(categoryId); // Restituisce la lista di sottocategorie in formato JSON
    }
}
