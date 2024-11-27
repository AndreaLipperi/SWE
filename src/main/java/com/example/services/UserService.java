package com.example.services;

import com.example.models.User;
import com.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
