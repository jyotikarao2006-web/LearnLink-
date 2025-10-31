package com.example.tutormatch.controller;

import com.example.tutormatch.dto.AuthDto;
import com.example.tutormatch.dto.LoginDto;
import com.example.tutormatch.entity.User;
import com.example.tutormatch.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000"})
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService) { this.authService = authService; }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody AuthDto dto) {
        User u = authService.signup(dto.getName(), dto.getEmail(), dto.getPassword());
        return ResponseEntity.ok(u);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto dto) {
        String token = authService.login(dto.getEmail(), dto.getPassword());
        return ResponseEntity.ok().body(Map.of("token", token));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader(name="X-Auth-Token", required = false) String token) {
        if (token != null) authService.logout(token);
        return ResponseEntity.ok(Map.of("ok", true));
    }

    @PostMapping("/select-role")
    public ResponseEntity<?> selectRole(@RequestHeader("X-Auth-Token") String token, @RequestParam String role) {
        User u = authService.getByToken(token);
        if (u == null) return ResponseEntity.status(401).body(Map.of("error","unauth"));
        try {
            User.Role r = User.Role.valueOf(role.toUpperCase());
            authService.setRole(u, r);
            return ResponseEntity.ok(u);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error","invalid role"));
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(@RequestHeader("X-Auth-Token") String token) {
        User u = authService.getByToken(token);
        if (u == null) return ResponseEntity.status(401).body(Map.of("error","unauth"));
        return ResponseEntity.ok(u);
    }
}
