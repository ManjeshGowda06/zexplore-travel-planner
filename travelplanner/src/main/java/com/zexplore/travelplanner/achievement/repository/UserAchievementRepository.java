package com.zexplore.travelplanner.achievement.repository;

import com.zexplore.travelplanner.model.UserAchievement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAchievementRepository extends JpaRepository<UserAchievement, Long> {
    List<UserAchievement> findByUser_Id(Long userId);
}
