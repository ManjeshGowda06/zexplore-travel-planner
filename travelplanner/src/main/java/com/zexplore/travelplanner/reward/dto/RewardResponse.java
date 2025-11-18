package com.zexplore.travelplanner.reward.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RewardResponse {
    private Long id;
    private String title;
    private String description;
    private int pointsRequired;
    private String type;
    private LocalDate expiryDate;
}
