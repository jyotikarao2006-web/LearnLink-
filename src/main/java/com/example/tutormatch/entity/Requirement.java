package com.example.tutormatch.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "requirement")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Requirement {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long studentUserId;
    private String subject;
    private String level;
    private String timeslots;
    @Enumerated(EnumType.STRING)
    private Mode mode = Mode.ONLINE;
    private String location;
    private Double budget;
    private Instant createdAt = Instant.now();

    public enum Mode { ONLINE, OFFLINE }
}
