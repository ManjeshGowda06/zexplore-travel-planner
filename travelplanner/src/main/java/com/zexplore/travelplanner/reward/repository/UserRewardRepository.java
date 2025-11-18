package com.zexplore.travelplanner.reward.repository;


import com.zexplore.travelplanner.model.UserReward;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRewardRepository extends JpaRepository<UserReward, Long> {
    List<UserReward> findByUser_Id(Long userId);
}
