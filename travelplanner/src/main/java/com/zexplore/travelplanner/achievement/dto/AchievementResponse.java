package com.zexplore.travelplanner.achievement.dto;


import lombok.Data;

@Data
public class AchievementResponse {
    private Long id;
    private String title;
    private String description;
    private String badgeIconUrl;
}