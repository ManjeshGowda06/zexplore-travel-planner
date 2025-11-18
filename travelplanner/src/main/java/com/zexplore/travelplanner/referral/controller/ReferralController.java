package com.zexplore.travelplanner.referral.controller;

import com.zexplore.travelplanner.referral.dto.ReferralResponse;
import com.zexplore.travelplanner.referral.service.ReferralService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/referrals")
@RequiredArgsConstructor
public class ReferralController {

    private final ReferralService referralService;

    @PostMapping
    public ResponseEntity<String> processReferral(@RequestParam String referralCode, @RequestParam Long newUserId) {
        referralService.processReferral(referralCode, newUserId);
        return ResponseEntity.ok("Referral processed successfully");
    }

    @GetMapping("/{userId}")
    public List<ReferralResponse> getReferrals(@PathVariable Long userId) {
        return referralService.getReferrals(userId);
    }
}
