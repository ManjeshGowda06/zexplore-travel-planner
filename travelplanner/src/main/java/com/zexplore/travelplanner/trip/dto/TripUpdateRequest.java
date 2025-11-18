package com.zexplore.travelplanner.trip.dto;




import com.zexplore.travelplanner.model.enums.Difficulty;
import com.zexplore.travelplanner.model.enums.Season;
import com.zexplore.travelplanner.model.enums.TripType;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

/**
 * DTO for updating a Trip (partial updates allowed; nulls are ignored).
 */
/*public record TripUpdateRequest(
        @Size(max = 160) String title,
        @Size(max = 120) String location,
        Difficulty difficulty,
        Season season,
        LocalDate startDate,
        LocalDate endDate
) {
    @AssertTrue(message = "endDate must be on or after startDate (when both provided)")
    public boolean isValidDateRange() {
        if (startDate == null || endDate == null) return true;
        return !endDate.isBefore(startDate);
    }
}*/


public record TripUpdateRequest(
        @Size(max = 160) String title,
        @Size(max = 120) String location,
        Difficulty difficulty,
        Season season,
        TripType tripType,
        @Size(max = 500) String highlights,
        Boolean guideRequired,
        @Size(max = 100) String terrainType,
        @Size(max = 100) String vehicleType,
        LocalDate startDate,
        LocalDate endDate
) {
    @AssertTrue(message = "endDate must be on or after startDate")
    public boolean isValidDateRange() {
        if (startDate == null || endDate == null) return true;
        return !endDate.isBefore(startDate);
    }
}