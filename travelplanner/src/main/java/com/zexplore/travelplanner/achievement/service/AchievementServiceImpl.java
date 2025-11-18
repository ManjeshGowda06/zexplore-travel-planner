package com.zexplore.travelplanner.achievement.service;

import com.zexplore.travelplanner.achievement.dto.AchievementResponse;
import com.zexplore.travelplanner.achievement.dto.UserAchievementResponse;
import com.zexplore.travelplanner.achievement.mapper.AchievementMapper;

import com.zexplore.travelplanner.achievement.repository.AchievementRepository;
import com.zexplore.travelplanner.achievement.repository.UserAchievementRepository;
import com.zexplore.travelplanner.model.Achievement;
import com.zexplore.travelplanner.model.User;
import com.zexplore.travelplanner.model.UserAchievement;
import com.zexplore.travelplanner.reward.service.RewardService;
import com.zexplore.travelplanner.reward.service.RewardServiceImpl;
import com.zexplore.travelplanner.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AchievementServiceImpl implements AchievementService {

    private final AchievementRepository achievementRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final UserRepository userRepository;
    private final AchievementMapper mapper;
    private final RewardService rewardService;

    @Override
    public List<AchievementResponse> getAllAchievements() {
        return achievementRepository.findAll()
                .stream()
                .map(mapper::toAchievementResponse)
                .toList();
    }

    @Override
    public List<UserAchievementResponse> getUserAchievements(Long userId) {
        return userAchievementRepository.findByUser_Id(userId)
                .stream()
                .map(mapper::toUserAchievementResponse)
                .toList();
    }

    @Override
    public UserAchievementResponse awardAchievement(Long userId, Long achievementId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Achievement achievement = achievementRepository.findById(achievementId)
                .orElseThrow(() -> new IllegalArgumentException("Achievement not found"));


// Prevent duplicate awards
        boolean alreadyAwarded = userAchievementRepository.findByUser_Id(userId)
                .stream()
                .anyMatch(ua -> ua.getAchievement().getId().equals(achievementId));
        if (alreadyAwarded) {
            throw new IllegalArgumentException("Achievement already awarded to this user");
        }


        UserAchievement userAchievement = new UserAchievement();
        userAchievement.setUser(user);
        userAchievement.setAchievement(achievement);
        userAchievement.setAwardedAt(LocalDate.now().atStartOfDay());




        // Award bonus points

        rewardService.addPoints(userId, 200);





        return mapper.toUserAchievementResponse(userAchievementRepository.save(userAchievement));
    }
}
