package com.zexplore.travelplanner.achievement.controller;

import com.zexplore.travelplanner.achievement.dto.AchievementResponse;
import com.zexplore.travelplanner.achievement.dto.UserAchievementResponse;
import com.zexplore.travelplanner.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/achievements")
@RequiredArgsConstructor
public class AchievementController {

    private final AchievementService achievementService;

    @GetMapping
    public List<AchievementResponse> getAllAchievements() {
        return achievementService.getAllAchievements();
    }

    @GetMapping("/user/{userId}")
    public List<UserAchievementResponse> getUserAchievements(@PathVariable Long userId) {
        return achievementService.getUserAchievements(userId);
    }

    @PostMapping("/award")
    public UserAchievementResponse awardAchievement(@RequestParam Long userId, @RequestParam Long achievementId) {
        return achievementService.awardAchievement(userId, achievementId);
    }
}
