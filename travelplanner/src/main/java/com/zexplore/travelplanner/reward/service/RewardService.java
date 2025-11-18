package com.zexplore.travelplanner.reward.service;

import com.zexplore.travelplanner.reward.dto.RewardRequest;
import com.zexplore.travelplanner.reward.dto.RewardResponse;
import com.zexplore.travelplanner.reward.dto.UserRewardResponse;

import java.util.List;

public interface RewardService {
    RewardResponse createReward(RewardRequest request);
    List<RewardResponse> getAllRewards();
    UserRewardResponse redeemReward(Long userId, Long rewardId);
    int getUserPoints(Long userId);
    void addPoints(Long userId, int points);

    // ✅ Add this
    void addSeasonalPoints(Long userId, int basePoints, boolean isSeasonalEvent);

    // Optional eco-reward method
    String redeemEcoReward(Long userId, int pointsToRedeem);
    void addCompletionPoints(Long userId, Long tripId, int points);

}
