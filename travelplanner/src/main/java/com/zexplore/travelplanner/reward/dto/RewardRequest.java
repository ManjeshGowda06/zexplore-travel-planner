package com.zexplore.travelplanner.reward.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RewardRequest {
    @NotBlank private String title;
    @NotBlank
    private String description;
    @Positive
    private int pointsRequired;
    @NotBlank private String type;
    private LocalDate expiryDate;
}
