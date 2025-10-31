package com.example.tutormatch.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "acceptance")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Acceptance {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long studentUserId;
    private Long teacherId;
    private Instant acceptedAt = Instant.now();
}
