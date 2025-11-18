package com.zexplore.travelplanner.analytics.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnalyticsSummaryResponse {
    private double totalDistance;
    private int totalSteps;
    private double averagePace;
    private int treksCompleted;
    private double totalElevation;
    private double totalExpenses;
}
