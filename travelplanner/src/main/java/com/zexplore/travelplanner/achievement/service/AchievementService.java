package com.zexplore.travelplanner.achievement.service;



import com.zexplore.travelplanner.achievement.dto.AchievementResponse;
import com.zexplore.travelplanner.achievement.dto.UserAchievementResponse;

import java.util.List;

public interface AchievementService {
    List<AchievementResponse> getAllAchievements();
    List<UserAchievementResponse> getUserAchievements(Long userId);
    UserAchievementResponse awardAchievement(Long userId, Long achievementId);
}
