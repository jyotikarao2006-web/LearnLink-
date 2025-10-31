package com.example.tutormatch.dto;

import lombok.Data;

@Data
public class TeacherProfileDto {
    private String subjects;
    private String levels;
    private Double feePerHour;
    private String timeslots;
    private String mode;
    private String location;
}
