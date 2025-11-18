package com.zexplore.travelplanner.referral.repository;

import com.zexplore.travelplanner.model.Referral;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReferralRepository extends JpaRepository<Referral, Long> {
    List<Referral> findByReferrer_Id(Long referrerId);


    boolean existsByReferrer_IdAndReferred_Id(Long referrerId, Long referredId);

}
