package com.zexplore.travelplanner.reward.service;

import com.zexplore.travelplanner.model.*;
import com.zexplore.travelplanner.reward.dto.RewardRequest;
import com.zexplore.travelplanner.reward.dto.RewardResponse;
import com.zexplore.travelplanner.reward.dto.UserRewardResponse;
import com.zexplore.travelplanner.reward.mapper.RewardMapper;
import com.zexplore.travelplanner.reward.repository.RewardRepository;
import com.zexplore.travelplanner.reward.repository.UserPointsRepository;
import com.zexplore.travelplanner.reward.repository.UserRewardRepository;
import com.zexplore.travelplanner.trip.repository.TripRepository;
import com.zexplore.travelplanner.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RewardServiceImpl implements RewardService {

    private final RewardRepository rewardRepository;
    private final UserRewardRepository userRewardRepository;
    private final UserRepository userRepository;
    private final RewardMapper mapper;
    private final UserPointsRepository userPointsRepository;
    private final TripRepository tripRepository;




    private static final int REFERRAL_BONUS = 100;
    private static final int NEW_USER_BONUS = 50;
    private static final double SEASONAL_MULTIPLIER = 2.0; // Double points during seasonal events


    @Override
    public RewardResponse createReward(RewardRequest request) {
        Reward reward = mapper.toEntity(request);
        return mapper.toResponse(rewardRepository.save(reward));
    }

    @Override
    public List<RewardResponse> getAllRewards() {
        return rewardRepository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public UserRewardResponse redeemReward(Long userId, Long rewardId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Reward reward = rewardRepository.findById(rewardId)
                .orElseThrow(() -> new IllegalArgumentException("Reward not found"));


        UserPoints userPoints = userPointsRepository.findByUser_Id(userId)
                .orElseThrow(() -> new IllegalArgumentException("User points record not found"));

        if (userPoints.getTotalPoints() < reward.getPointsRequired()) {
            throw new IllegalArgumentException("Insufficient points to redeem this reward");
        }


// Deduct points
        userPoints.setTotalPoints(userPoints.getTotalPoints() - reward.getPointsRequired());
        userPointsRepository.save(userPoints);

        // Check points logic here
        // Deduct points from UserPoints entity

        UserReward userReward = new UserReward();
        userReward.setUser(user);
        userReward.setReward(reward);
        userReward.setRedeemedAt(LocalDate.now());

        return mapper.toUserRewardResponse(userRewardRepository.save(userReward));
    }

    @Override
    public int getUserPoints(Long userId) {
        // Fetch from UserPoints table

        return userPointsRepository.findByUser_Id(userId)
                .map(UserPoints::getTotalPoints)
                .orElse(0);

    }

    @Override
    public void addPoints(Long userId, int points) {
        UserPoints userPoints = userPointsRepository.findByUser_Id(userId)
                .orElseThrow(() -> new IllegalArgumentException("User points record not found"));
        userPoints.setTotalPoints(userPoints.getTotalPoints() + points);
        userPointsRepository.save(userPoints);
    }

    // Seasonal bonus logic
    public void addSeasonalPoints(Long userId, int basePoints, boolean isSeasonalEvent) {
        int finalPoints = isSeasonalEvent ? (int) (basePoints * SEASONAL_MULTIPLIER) : basePoints;
        addPoints(userId, finalPoints);
    }

    // Eco-reward: Convert points to tree planting
    public String redeemEcoReward(Long userId, int pointsToRedeem) {
        UserPoints userPoints = userPointsRepository.findByUser_Id(userId)
                .orElseThrow(() -> new IllegalArgumentException("User points record not found"));

        if (userPoints.getTotalPoints() < pointsToRedeem) {
            throw new IllegalArgumentException("Insufficient points for eco-reward");
        }

        userPoints.setTotalPoints(userPoints.getTotalPoints() - pointsToRedeem);
        userPointsRepository.save(userPoints);

        return "Eco-reward redeemed: " + pointsToRedeem + " points converted to tree planting!";
    }

    public void addCompletionPoints(Long userId, Long tripId, int points) {
        boolean alreadyRewarded = rewardRepository.existsByUser_IdAndTrip_Id(userId, tripId);
        if (alreadyRewarded) return;

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new IllegalArgumentException("Trip not found"));

        Reward reward = new Reward();
        reward.setUser(user);
        reward.setTrip(trip);
        reward.setTitle("Completed: " + trip.getTitle());
        reward.setDescription("Reward for completing " + trip.getTripType() + " trip");
        reward.setPointsRequired(points);
        reward.setType("COMPLETION");
        reward.setExpiryDate(LocalDate.now().plusMonths(6)); // Optional expiry

        rewardRepository.save(reward);
    }

    // Replace the existing addCompletionPoints with:
/*    @Override
    public void addCompletionPoints(Long userId, Long tripId, int points) {
        // Now handled via TrekCompletionService completion ledger to ensure idempotency.
        // Keep this method to add points directly (if called elsewhere).
        addPoints(userId, points);
    }*/
}