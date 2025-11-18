package com.zexplore.travelplanner.progresslog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ProgressLogResponse {
    private Long id;
    private LocalDate date;
    private double distanceKm;
    private int steps;
    private double elevationGain;
    private Long userId;
    private Long tripId;
}
