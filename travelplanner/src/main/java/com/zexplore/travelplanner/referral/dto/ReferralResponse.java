package com.zexplore.travelplanner.referral.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ReferralResponse {
    private Long referrerId;
    private Long referredId;
    private LocalDate referralDate;
}
