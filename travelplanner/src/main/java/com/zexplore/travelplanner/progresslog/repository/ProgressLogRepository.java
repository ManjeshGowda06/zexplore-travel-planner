package com.zexplore.travelplanner.progresslog.repository;

import com.zexplore.travelplanner.model.ProgressLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgressLogRepository extends JpaRepository<ProgressLog, Long> {
    List<ProgressLog> findByUser_IdAndTrip_Id(Long userId, Long tripId);
    List<ProgressLog> findByUser_Id(Long userId);
}
