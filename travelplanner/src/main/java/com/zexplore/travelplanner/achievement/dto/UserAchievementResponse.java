package com.zexplore.travelplanner.achievement.dto;



import lombok.Data;

@Data
public class UserAchievementResponse {
    private Long id;
    private Long userId;
    private Long achievementId;
    private String awardedAt; // Optional: if you track timestamps
}

