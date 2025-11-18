package com.zexplore.travelplanner.reward.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserRewardResponse {
    private Long id;
    private Long userId;
    private Long rewardId;
    private LocalDate redeemedAt;
}
