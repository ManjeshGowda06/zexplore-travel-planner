package com.zexplore.travelplanner.analytics.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AnalyticsFilterRequest {
    private Long userId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String tripType; // optional
}
