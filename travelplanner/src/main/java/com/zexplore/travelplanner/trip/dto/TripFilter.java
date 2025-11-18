package com.zexplore.travelplanner.trip.dto;


import com.zexplore.travelplanner.model.enums.Difficulty;
import com.zexplore.travelplanner.model.enums.Season;

import java.time.LocalDate;

/**
 * Filter DTO for listing/searching trips.
 * All fields are optional; combine them as needed.
 */
public record TripFilter(
        String location,
        Difficulty difficulty,
        Season season,
        LocalDate startDateFrom,
        LocalDate startDateTo
) {}
