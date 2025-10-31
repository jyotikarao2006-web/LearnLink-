package com.example.tutormatch.dto;

import lombok.Data;

@Data
public class RequirementDto {
    private String subject;
    private String level;
    private String timeslots;
    private String mode;
    private String location;
    private Double budget;
}
