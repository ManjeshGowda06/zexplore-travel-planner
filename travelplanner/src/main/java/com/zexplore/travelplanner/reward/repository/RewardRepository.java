package com.zexplore.travelplanner.reward.repository;

import com.zexplore.travelplanner.model.Reward;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RewardRepository extends JpaRepository<Reward, Long> {
    boolean existsByUser_IdAndTrip_Id(Long userId, Long tripId);
}
