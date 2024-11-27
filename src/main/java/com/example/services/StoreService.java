package com.example.services;

import com.example.models.Category;
import com.example.models.Store;
import com.example.models.User;
import com.example.repositories.CategoryRepository;
import com.example.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class StoreService {
    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Map<Category, List<Store>> getStoresGroupedByCategory(Long subcategoryId) {
        List<Store> stores;
        if (subcategoryId != null) {
            // Se è stato fornito un ID di sottocategoria, filtra per sottocategoria
            stores = storeRepository.findBySubcategoryId(subcategoryId);
        } else {
            // Altrimenti prendi tutti i negozi
            stores = storeRepository.findAll();
        }

        // Creiamo una mappa per raggruppare i prodotti per categoria
        Map<Category, List<Store>> storesByCategory = new HashMap<>();

        // Raggruppiamo per categoria
        for (Store store : stores) {
            Category category = store.getSubcategory().getCategory();
            storesByCategory
                    .computeIfAbsent(category, k -> new ArrayList<>())
                    .add(store);
        }

        return storesByCategory;
    }
    public Map<Category, List<Store>> getStoresGroupedByCategoryForProv(Long subcategoryId, User user) {
        // Ottieni i negozi per il provider corrente
        // Recupera tutti i negozi per il fornitore
        List<Store> stores = storeRepository.findByProvider(user);

        if (subcategoryId != null) {
            // Se è stato fornito un ID di sottocategoria, filtra i negozi per sottocategoria
            stores = stores.stream()
                    .filter(store -> store.getSubcategory().getId().equals(subcategoryId))
                    .toList();
        }


        // Creiamo una mappa per raggruppare i prodotti per categoria
        Map<Category, List<Store>> storesByCategory = new HashMap<>();

        // Raggruppiamo per categoria
        for (Store store : stores) {
            Category category = store.getSubcategory().getCategory();
            storesByCategory
                    .computeIfAbsent(category, k -> new ArrayList<>())
                    .add(store);
        }

        return storesByCategory;
    }

}
