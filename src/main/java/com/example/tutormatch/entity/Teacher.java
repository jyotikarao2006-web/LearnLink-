package com.example.tutormatch.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "teacher")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    // CSV strings for simplicity
    private String subjects;
    private String levels;
    private Double feePerHour;
    private String timeslots;
    @Enumerated(EnumType.STRING)
    private Mode mode = Mode.ONLINE;
    private String location;

    public enum Mode { ONLINE, OFFLINE, BOTH }
}
