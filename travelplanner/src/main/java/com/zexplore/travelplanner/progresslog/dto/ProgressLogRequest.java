package com.zexplore.travelplanner.progresslog.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProgressLogRequest {
    @NotNull
    private Long userId;

    @NotNull
    private Long tripId;

    @NotNull
    private LocalDate date;

    @PositiveOrZero
    private double distanceKm;

    @PositiveOrZero
    private int steps;

    @PositiveOrZero
    private double elevationGain;
}
