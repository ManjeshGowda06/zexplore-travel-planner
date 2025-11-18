package com.zexplore.travelplanner.reward.controller;

import com.zexplore.travelplanner.reward.dto.RewardRequest;
import com.zexplore.travelplanner.reward.dto.RewardResponse;
import com.zexplore.travelplanner.reward.dto.UserRewardResponse;
import com.zexplore.travelplanner.reward.service.RewardService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rewards")
@RequiredArgsConstructor
public class RewardController {

    private final RewardService rewardService;

    @PostMapping
    public RewardResponse createReward(@RequestBody @Validated RewardRequest request) {
        return rewardService.createReward(request);
    }

    @GetMapping
    public List<RewardResponse> getAllRewards() {
        return rewardService.getAllRewards();
    }

    @PostMapping("/redeem")
    public UserRewardResponse redeemReward(@RequestParam Long userId, @RequestParam Long rewardId) {
        return rewardService.redeemReward(userId, rewardId);
    }

    @GetMapping("/points/{userId}")
    public int getUserPoints(@PathVariable Long userId) {
        return rewardService.getUserPoints(userId);
    }
}
