package com.zexplore.travelplanner.trip.dto;

import com.zexplore.travelplanner.model.enums.Difficulty;
import com.zexplore.travelplanner.model.enums.Season;
import com.zexplore.travelplanner.model.enums.TripType;
import jakarta.validation.constraints.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;

/*
public record TripCreateRequest(
        @NotBlank @Size(max = 160) String title,
        @NotBlank @Size(max = 120) String location,
        @NotNull Difficulty difficulty,
        @NotNull Season season,
        @NotNull @FutureOrPresent LocalDate startDate,
        @NotNull LocalDate endDate
) {
    @AssertTrue(message = "endDate must be on or after startDate")
    public boolean isValidDateRange() {
        return endDate != null && startDate != null && !endDate.isBefore(startDate);
    }
}*/
public record TripCreateRequest(
        @NotBlank @Size(max = 160) String title,
        @NotBlank @Size(max = 120) String location,
        @NotNull Difficulty difficulty,
        @NotNull Season season,
        @NotNull TripType tripType,
        @Size(max = 500) String highlights,
        Boolean guideRequired,
        @Size(max = 100) String terrainType,
        @Size(max = 100) String vehicleType,
        @NotNull @FutureOrPresent LocalDate startDate,
        @NotNull LocalDate endDate
) {
    @AssertTrue(message = "endDate must be on or after startDate")
    public boolean isValidDateRange() {
        return endDate != null && startDate != null && !endDate.isBefore(startDate);
    }
}