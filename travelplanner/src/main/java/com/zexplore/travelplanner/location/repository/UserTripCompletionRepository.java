package com.zexplore.travelplanner.location.repository;

import com.zexplore.travelplanner.model.UserTripCompletion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserTripCompletionRepository extends JpaRepository<UserTripCompletion, Long> {
    boolean existsByUser_IdAndTrip_Id(Long userId, Long tripId);
    Optional<UserTripCompletion> findByUser_IdAndTrip_Id(Long userId, Long tripId);
}
