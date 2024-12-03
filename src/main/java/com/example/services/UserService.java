package com.example.services;

import com.example.models.User;
import com.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Metodo per salvare un nuovo utente nel database
    public void saveUser(User user) {
        userRepository.save(user); // Salva l'utente nel database
    }
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public List<User> findByType(String userType) {
        return userRepository.findByType(userType);  // Trova tutti gli utenti per tipo
    }
    public boolean updateUser(User user) {
        try {
            // Recupera l'utente esistente dal database tramite l'ID
            Optional<User> existingUser = userRepository.findById(user.getId());

            if (existingUser.isPresent()) {
                User userToUpdate = existingUser.get();

                // Aggiorna i campi con i nuovi valori
                userToUpdate.setEmail(user.getEmail());
                userToUpdate.setUsername(user.getUsername());

                // Salva l'utente aggiornato
                userRepository.save(userToUpdate);
                return true;
            } else {
                System.out.println("Utente non trovato con ID: " + user.getId());
                return false; // Nessun utente trovato con l'ID fornito
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log dell'errore per il debug
            return false;
        }
    }

}
