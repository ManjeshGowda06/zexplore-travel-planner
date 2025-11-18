package com.zexplore.travelplanner.referral.service;

import com.zexplore.travelplanner.referral.dto.ReferralResponse;

import java.util.List;

public interface ReferralService {
    void processReferral(String referralCode, Long newUserId);
    List<ReferralResponse> getReferrals(Long userId);
}
