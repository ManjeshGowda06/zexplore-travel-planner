package com.zexplore.travelplanner.trip.mapper;

import com.zexplore.travelplanner.model.Trip;
import com.zexplore.travelplanner.model.enums.Difficulty;
import com.zexplore.travelplanner.model.enums.Season;
import com.zexplore.travelplanner.model.enums.TripType;
import com.zexplore.travelplanner.trip.dto.TripCreateRequest;
import com.zexplore.travelplanner.trip.dto.TripResponse;
import com.zexplore.travelplanner.trip.dto.TripUpdateRequest;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-17T12:50:17+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class TripMapperImpl implements TripMapper {

    @Override
    public Trip toEntity(TripCreateRequest req) {
        if ( req == null ) {
            return null;
        }

        Trip trip = new Trip();

        return trip;
    }

    @Override
    public TripResponse toResponse(Trip entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        String title = null;
        String location = null;
        Difficulty difficulty = null;
        Season season = null;
        TripType tripType = null;
        String highlights = null;
        Boolean guideRequired = null;
        String terrainType = null;
        String vehicleType = null;
        LocalDate startDate = null;
        LocalDate endDate = null;

        TripResponse tripResponse = new TripResponse( id, title, location, difficulty, season, tripType, highlights, guideRequired, terrainType, vehicleType, startDate, endDate );

        return tripResponse;
    }

    @Override
    public void updateEntityFromDto(Trip entity, TripUpdateRequest req) {
        if ( req == null ) {
            return;
        }
    }
}
