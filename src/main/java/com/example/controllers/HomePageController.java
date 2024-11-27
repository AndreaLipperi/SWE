package com.example.controllers;

import com.example.models.Store;
import com.example.models.Subcategory;
import com.example.models.User;
import com.example.services.StoreService;
import com.example.services.SubcategoryService;
import com.example.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import com.example.models.Category;
import com.example.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class HomePageController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SubcategoryService subcategoryService;

    @Autowired
    private StoreService storeService;

    // Metodo per caricare le categorie e redirigere alla pagina giusta
    @GetMapping("/homepage")
    public String getCategoriesAndStore(@RequestParam(value = "subcategoryId", required = false) Long subcategoryId,Model model, HttpSession session) {

        List<Category> categorie = categoryService.getAllCategorie();
        model.addAttribute("categorie", categorie);


        Boolean isProvider = (Boolean) session.getAttribute("isProvider");
        if (isProvider != null && isProvider) {
            User user = (User) session.getAttribute("user"); // Supponiamo che l'utente sia salvato nella sessione
            Map<Category, List<Store>> storesByCategoryForProvider = storeService.getStoresGroupedByCategoryForProv(subcategoryId, user);

            // Passa la mappa dei negozi raggruppati per categoria alla vista
            model.addAttribute("storesByCategory", storesByCategoryForProvider);
            return "provider/homepage_provider";
        } else {
            Map<Category, List<Store>> storesByCategory = storeService.getStoresGroupedByCategory(subcategoryId);

            // Passiamo la mappa alla vista
            model.addAttribute("storesByCategory", storesByCategory);

            return "client/homepage_client";
        }
    }

    // Metodo per ottenere le sottocategorie in base all'ID della categoria selezionata
    @GetMapping("/getSubcategories")
    @ResponseBody
    public List<Subcategory> getSubcategories(@RequestParam("categoryId") Long categoryId) {
        // Recupera le sottocategorie in base alla categoria
        List<Subcategory> subcategories = subcategoryService.findByCategoryId(categoryId);
        return subcategories; // Restituisce la lista di sottocategorie in formato JSON
    }

    @GetMapping("/getProductsBySubcategory")
    @ResponseBody
    public String getProductsBySubcategory(@RequestParam Long subcategoryId) {
        // Ottieni i negozi filtrati per sottocategoria
        Map<Category, List<Store>> stores = storeService.getStoresGroupedByCategory(subcategoryId);


        // Puoi usare Thymeleaf per rendere l'HTML, oppure farlo in modo manuale
        // Se preferisci renderizzare i dati dinamicamente, puoi creare un metodo template per questa parte

        StringBuilder htmlResponse = new StringBuilder();

        // Iterazione sui prodotti e categorie
        for (Map.Entry<Category, List<Store>> entry : stores.entrySet()) {
            // Aggiungi il nome della categoria
            htmlResponse.append("<h4>Categoria: ").append(entry.getKey().getName()).append("</h4>");

            // Inizio della griglia dei titoli
            htmlResponse.append("<div class='grid_titoli'>");

            // Aggiungi i titoli delle colonne
            htmlResponse.append("<div class='grid_item_titoli'>Sottocategoria</div>");
            htmlResponse.append("<div class='grid_item_titoli'>Fornitore</div>");
            htmlResponse.append("<div class='grid_item_titoli'>Prezzo Unitario</div>");
            htmlResponse.append("<div class='grid_item_titoli'>Descrizione</div>");
            htmlResponse.append("<div class='grid_item_titoli'>Quantità disponibile</div>");
            htmlResponse.append("<div class='grid_item_titoli'>Sconto</div>");
            htmlResponse.append("<div class='grid_item_titoli'></div>");

            htmlResponse.append("</div>"); // Fine della griglia dei titoli

            // Inizio della griglia dei dati
            htmlResponse.append("<div class='grid_dati'>");

            // Itera su tutti i prodotti per questa categoria
            for (Store store : entry.getValue()) {
                htmlResponse.append("<div class='grid_item_dati'>").append(store.getSubcategory().getName()).append("</div>");
                htmlResponse.append("<div class='grid_item_dati'>").append(store.getProvider().getUsername()).append("</div>");
                htmlResponse.append("<div class='grid_item_dati'>€ <span>").append(store.getPriceProduct()).append("</span></div>");
                htmlResponse.append("<div class='grid_item_dati'>").append(store.getDescProd()).append("</div>");
                htmlResponse.append("<div class='grid_item_dati'>").append(store.getAvailableQuantity())
                        .append(" ").append(store.getMeasureUnit().getAbbreviation()).append("</div>");
                htmlResponse.append("<div class='grid_item_dati'>").append(store.getDiscount()).append("</div>");
                htmlResponse.append("<div class='grid_item_dati'>")
                        .append("<a href='#'>Inserisci nel carrello</a>")
                        .append("</div>");
            }

            htmlResponse.append("</div>"); // Fine della griglia dei dati
        }


        return htmlResponse.toString();  // Ritorna l'HTML generato
    }

    @GetMapping("/getProductsBySubcategoryForProv")
    @ResponseBody
    public String getProductsBySubcategoryForProv(@RequestParam Long subcategoryId, HttpSession session) {
        // Ottieni i negozi filtrati per sottocategoria
        User user = (User) session.getAttribute("user");
        if (user == null) {
            // Gestisci il caso in cui l'utente non sia loggato
            return "redirect:/login"; // O altre azioni di errore
        }

        Map<Category, List<Store>> stores = storeService.getStoresGroupedByCategoryForProv(subcategoryId, user);

        // Puoi usare Thymeleaf per rendere l'HTML, oppure farlo in modo manuale
        // Se preferisci renderizzare i dati dinamicamente, puoi creare un metodo template per questa parte

        StringBuilder htmlResponse = new StringBuilder();

        // Iterazione sui prodotti e categorie
        for (Map.Entry<Category, List<Store>> entry : stores.entrySet()) {
            // Aggiungi il nome della categoria
            htmlResponse.append("<h4>Categoria: ").append(entry.getKey().getName()).append("</h4>");

            // Inizio della griglia dei titoli
            htmlResponse.append("<div class='grid_titoli'>");

            // Aggiungi i titoli delle colonne
            htmlResponse.append("<div class='grid_item_titoli'>Sottocategoria</div>");
            htmlResponse.append("<div class='grid_item_titoli'>Prezzo Unitario</div>");
            htmlResponse.append("<div class='grid_item_titoli'>Descrizione</div>");
            htmlResponse.append("<div class='grid_item_titoli'>Quantità disponibile</div>");
            htmlResponse.append("<div class='grid_item_titoli'>Sconto</div>");
            htmlResponse.append("<div class='grid_item_titoli'></div>");
            htmlResponse.append("<div class='grid_item_titoli'></div>");

            htmlResponse.append("</div>"); // Fine della griglia dei titoli

            // Inizio della griglia dei dati
            htmlResponse.append("<div class='grid_dati'>");

            // Itera su tutti i prodotti per questa categoria
            for (Store store : entry.getValue()) {
                htmlResponse.append("<div class='grid_item_dati'>").append(store.getSubcategory().getName()).append("</div>");
                htmlResponse.append("<div class='grid_item_dati'>€ <span>").append(store.getPriceProduct()).append("</span></div>");
                htmlResponse.append("<div class='grid_item_dati'>").append(store.getDescProd()).append("</div>");
                htmlResponse.append("<div class='grid_item_dati'>").append(store.getAvailableQuantity())
                        .append(" ").append(store.getMeasureUnit().getAbbreviation()).append("</div>");
                htmlResponse.append("<div class='grid_item_dati'>").append(store.getDiscount()).append("</div>");
                htmlResponse.append("<div class='grid_item_dati'>")
                        .append("<a href='#'>cancella prodotto</a>")
                        .append("</div>");
                htmlResponse.append("<div class='grid_item_dati'>")
                        .append("<a href='#'>modifica prodotto</a>")
                        .append("</div>");
            }

            htmlResponse.append("</div>"); // Fine della griglia dei dati
        }


        return htmlResponse.toString();  // Ritorna l'HTML generato
    }

}