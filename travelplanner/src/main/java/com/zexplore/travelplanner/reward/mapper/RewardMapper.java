package com.zexplore.travelplanner.reward.mapper;

import com.zexplore.travelplanner.model.Reward;
import com.zexplore.travelplanner.model.UserReward;
import com.zexplore.travelplanner.reward.dto.RewardRequest;
import com.zexplore.travelplanner.reward.dto.RewardResponse;
import com.zexplore.travelplanner.reward.dto.UserRewardResponse;
import org.springframework.stereotype.Component;

/*@Mapper(componentModel = "spring")
public interface RewardMapper {
    Reward toEntity(RewardRequest request);
    RewardResponse toResponse(Reward reward);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "reward.id", target = "rewardId")
    UserRewardResponse toUserRewardResponse(UserReward userReward);
} */

@Component
public class RewardMapper {

    public Reward toEntity(RewardRequest request) {
        Reward reward = new Reward();
        reward.setTitle(request.getTitle());
        reward.setDescription(request.getDescription());
        reward.setPointsRequired(request.getPointsRequired());
        reward.setType(request.getType());
        reward.setExpiryDate(request.getExpiryDate());
        return reward;
    }

    public RewardResponse toResponse(Reward reward) {
        RewardResponse response = new RewardResponse();
        response.setId(reward.getId());
        response.setTitle(reward.getTitle());
        response.setDescription(reward.getDescription());
        response.setPointsRequired(reward.getPointsRequired());
        response.setType(reward.getType());
        response.setExpiryDate(reward.getExpiryDate());
        return response;
    }

    public UserRewardResponse toUserRewardResponse(UserReward userReward) {
        UserRewardResponse response = new UserRewardResponse();
        response.setId(userReward.getId());
        response.setUserId(userReward.getUser() != null ? userReward.getUser().getId() : null);
        response.setRewardId(userReward.getReward() != null ? userReward.getReward().getId() : null);
        response.setRedeemedAt(userReward.getRedeemedAt());
        return response;
    }
}




