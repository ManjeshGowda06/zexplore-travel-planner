package com.zexplore.travelplanner.referral.service;

import com.zexplore.travelplanner.model.Referral;
import com.zexplore.travelplanner.model.User;
import com.zexplore.travelplanner.referral.dto.ReferralResponse;
import com.zexplore.travelplanner.referral.repository.ReferralRepository;
import com.zexplore.travelplanner.reward.service.RewardService;
import com.zexplore.travelplanner.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReferralServiceImpl implements ReferralService {

    private final ReferralRepository referralRepository;
    private final UserRepository userRepository;
    private final RewardService rewardService; // For awarding points

    @Override
    public void processReferral(String referralCode, Long newUserId) {
        User referrer = userRepository.findByReferralCode(referralCode)
                .orElseThrow(() -> new IllegalArgumentException("Invalid referral code"));
        User newUser = userRepository.findById(newUserId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));


        if (referralRepository.existsByReferrer_IdAndReferred_Id(referrer.getId(), newUser.getId())) {
            // already processed; idempotent
            return;
        }

        Referral referral = new Referral();
        referral.setReferrer(referrer);
        referral.setReferred(newUser);
        referral.setReferralDate(LocalDate.now());
        referralRepository.save(referral);

        // Award points
        rewardService.addPoints(referrer.getId(), 100); // Referrer gets 100 points
        rewardService.addPoints(newUser.getId(), 50);   // New user gets 50 points
    }

    @Override
    public List<ReferralResponse> getReferrals(Long userId) {
        return referralRepository.findByReferrer_Id(userId)
                .stream()
                .map(r -> new ReferralResponse(r.getReferrer().getId(), r.getReferred().getId(), r.getReferralDate()))
                .toList();
    }
}
