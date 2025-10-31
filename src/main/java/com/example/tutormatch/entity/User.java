package com.example.tutormatch.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "`user`")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(unique = true)
    private String email;
    private String password; // BCrypt hashed
    @Enumerated(EnumType.STRING)
    private Role role = Role.NONE;
    private Instant createdAt = Instant.now();

    public enum Role { NONE, STUDENT, TEACHER }
}

