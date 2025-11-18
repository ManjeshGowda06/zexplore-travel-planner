package com.zexplore.travelplanner.reward.repository;



import com.zexplore.travelplanner.model.UserPoints;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPointsRepository extends JpaRepository<UserPoints, Long> {
    Optional<UserPoints> findByUser_Id(Long userId);
}

