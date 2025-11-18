package com.zexplore.travelplanner.analytics.service;

import com.zexplore.travelplanner.analytics.dto.AnalyticsSummaryResponse;
import com.zexplore.travelplanner.analytics.mapper.AnalyticsMapper;
import com.zexplore.travelplanner.model.AnalyticsSummary;
import com.zexplore.travelplanner.model.ProgressLog;
import com.zexplore.travelplanner.progresslog.repository.ProgressLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    private final ProgressLogRepository progressLogRepository;
    private final AnalyticsMapper analyticsMapper;

    @Override
    public AnalyticsSummaryResponse getUserAnalytics(Long userId) {
        List<ProgressLog> logs = progressLogRepository.findByUser_Id(userId);

        double totalDistance = 0;
        int totalSteps = 0;
        double totalElevation = 0;
        int treksCompleted = 0;

        for (ProgressLog log : logs) {
            totalDistance += log.getDistanceKm();
            totalSteps += log.getSteps();
            totalElevation += log.getElevationGain();
        }

        double averagePace = logs.size() > 0 ? totalDistance / logs.size() : 0;

        AnalyticsSummary summary = new AnalyticsSummary();
        summary.setTotalDistance(totalDistance);
        summary.setTotalSteps(totalSteps);
        summary.setAveragePace(averagePace);
        summary.setTreksCompleted(treksCompleted); // Enhance later
        summary.setTotalElevation(totalElevation);
        summary.setTotalExpenses(0); // Add expense tracking later

        return analyticsMapper.toDto(summary);
    }
}
