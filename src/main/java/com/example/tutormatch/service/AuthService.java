package com.example.tutormatch.service;

import com.example.tutormatch.entity.User;
import com.example.tutormatch.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthService {

    private final UserRepository userRepo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final Map<String, Long> tokenStore = new ConcurrentHashMap<>();

    public AuthService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    // ✅ SIGNUP: save new user if not exists
    public User signup(String name, String email, String password) {
        Optional<User> existing = userRepo.findByEmail(email);
        if (existing.isPresent()) {
            throw new RuntimeException("User already exists");
        }

        User u = new User();
        u.setName(name);
        u.setEmail(email);
        u.setPassword(encoder.encode(password));
        u.setRole(User.Role.NONE);
        u.setCreatedAt(java.time.Instant.now());

        return userRepo.save(u); // ✅ ensure user is persisted
    }

    // ✅ LOGIN: verify user + generate token
    public String login(String email, String password) {
        Optional<User> ou = userRepo.findByEmail(email);
        if (ou.isEmpty()) throw new RuntimeException("User not found");
        User u = ou.get();

        if (!encoder.matches(password, u.getPassword()))
            throw new RuntimeException("Invalid password");

        String token = UUID.randomUUID().toString();
        tokenStore.put(token, u.getId());
        return token;
    }

    // ✅ LOGOUT
    public void logout(String token) {
        tokenStore.remove(token);
    }

    // ✅ Fetch user by token
    public User getByToken(String token) {
        if (token == null) return null;
        Long userId = tokenStore.get(token);
        if (userId == null) return null;
        return userRepo.findById(userId).orElse(null);
    }

    // ✅ Set role after login
    public void setRole(User u, User.Role role) {
        u.setRole(role);
        userRepo.save(u);
    }
}
