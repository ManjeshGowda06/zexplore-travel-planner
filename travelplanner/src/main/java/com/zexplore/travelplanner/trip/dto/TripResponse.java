package com.zexplore.travelplanner.trip.dto;


import com.zexplore.travelplanner.model.enums.Difficulty;
import com.zexplore.travelplanner.model.enums.Season;
import com.zexplore.travelplanner.model.enums.TripType;

import java.time.LocalDate;

/**
 * DTO returned to clients when fetching Trip data.
 */
/*
public record TripResponse(
        Long id,
        String title,
        String location,
        Difficulty difficulty,
        Season season,
        LocalDate startDate,
        LocalDate endDate
) {}*/

public record TripResponse(
        Long id,
        String title,
        String location,
        Difficulty difficulty,
        Season season,
        TripType tripType,
        String highlights,
        Boolean guideRequired,
        String terrainType,
        String vehicleType,
        LocalDate startDate,
        LocalDate endDate
) {}
