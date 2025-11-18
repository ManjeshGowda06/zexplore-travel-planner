package com.zexplore.travelplanner.analytics.controller;

import com.zexplore.travelplanner.analytics.dto.AnalyticsSummaryResponse;
import com.zexplore.travelplanner.analytics.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/summary")
    public AnalyticsSummaryResponse getUserAnalytics(@RequestParam Long userId) {
        return analyticsService.getUserAnalytics(userId);
    }
}
