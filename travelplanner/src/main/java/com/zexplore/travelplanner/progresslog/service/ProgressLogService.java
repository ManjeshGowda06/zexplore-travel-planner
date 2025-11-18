package com.zexplore.travelplanner.progresslog.service;

import com.zexplore.travelplanner.progresslog.dto.ProgressLogRequest;
import com.zexplore.travelplanner.progresslog.dto.ProgressLogResponse;

import java.util.List;

public interface ProgressLogService {
    ProgressLogResponse logProgress(ProgressLogRequest request);
    List<ProgressLogResponse> getProgressByUserAndTrip(Long userId, Long tripId);
}

