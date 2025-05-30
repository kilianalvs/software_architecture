package com.esgi.notfound.application.service;

import com.esgi.notfound.domain.entity.User;
import com.esgi.notfound.domain.repositories.;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Récupère tous les utilisateurs
     */
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Récupère un utilisateur par ID
     */
    @Transactional(readOnly = true)
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Récupère un utilisateur par email
     */
    @Transactional(readOnly = true)
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Vérifie si un email existe déjà
     */
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Crée un nouvel utilisateur
     */
    public User createUser(String name, String email, String phone) {
        // Vérification que l'email n'existe pas déjà
        if (existsByEmail(email)) {
            throw new IllegalArgumentException("Un utilisateur avec cet email existe déjà");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);
        
        return userRepository.save(user);
    }

    /**
     * Met à jour un utilisateur
     */
    public Optional<User> updateUser(Long id, String name, String email, String phone) {
        return userRepository.findById(id)
                .map(user -> {
                    // Vérifier que l'email n'est pas utilisé par un autre utilisateur
                    if (!user.getEmail().equals(email) && existsByEmail(email)) {
                        throw new IllegalArgumentException("Cet email est déjà utilisé par un autre utilisateur");
                    }
                    
                    user.setName(name);
                    user.setEmail(email);
                    user.setPhone(phone);
                    return userRepository.save(user);
                });
    }

    /**
     * Supprime un utilisateur
     */
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Compte le nombre total d'utilisateurs
     */
    @Transactional(readOnly = true)
    public long countUsers() {
        return userRepository.count();
    }
}