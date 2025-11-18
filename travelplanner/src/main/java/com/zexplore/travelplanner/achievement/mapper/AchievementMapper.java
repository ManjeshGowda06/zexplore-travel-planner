package com.zexplore.travelplanner.achievement.mapper;

import com.zexplore.travelplanner.achievement.dto.AchievementResponse;
import com.zexplore.travelplanner.achievement.dto.UserAchievementResponse;
import com.zexplore.travelplanner.model.Achievement;
import com.zexplore.travelplanner.model.UserAchievement;
import org.springframework.stereotype.Component;


@Component
public class AchievementMapper {

    public AchievementResponse toAchievementResponse(Achievement achievement) {
        AchievementResponse response = new AchievementResponse();
        response.setId(achievement.getId());
        response.setTitle(achievement.getTitle());
        response.setDescription(achievement.getDescription());
        response.setBadgeIconUrl(achievement.getBadgeIconUrl());
        return response;
    }

    public UserAchievementResponse toUserAchievementResponse(UserAchievement userAchievement) {
        UserAchievementResponse response = new UserAchievementResponse();
        response.setId(userAchievement.getId());
        response.setUserId(userAchievement.getUser() != null ? userAchievement.getUser().getId() : null);
        response.setAchievementId(userAchievement.getAchievement() != null ? userAchievement.getAchievement().getId() : null);
        response.setAwardedAt(userAchievement.getAwardedAt() != null ? userAchievement.getAwardedAt().toString() : null);
        return response;
    }
}















































//---------------------@Mapper(componentModel = "spring")
////public interface AchievementMapper {
////
////    AchievementResponse toAchievementResponse(Achievement achievement);
////
////    @Mapping(source = "user.id", target = "userId")
////    @Mapping(source = "achievement.id", target = "achievementId")
////    UserAchievementResponse toUserAchievementResponse(UserAchievement userAchievement);
////}---------------------//
