package com.zexplore.travelplanner.analytics.service;

import com.zexplore.travelplanner.analytics.dto.AnalyticsSummaryResponse;

public interface AnalyticsService {
    AnalyticsSummaryResponse getUserAnalytics(Long userId);
}
