package com.zexplore.travelplanner.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnalyticsSummary {
    private double totalDistance;
    private int totalSteps;
    private double averagePace;
    private int treksCompleted;
    private double totalElevation;
    private double totalExpenses;
}
