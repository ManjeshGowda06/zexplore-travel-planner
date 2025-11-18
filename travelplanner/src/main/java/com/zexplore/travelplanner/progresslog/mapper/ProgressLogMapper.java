package com.zexplore.travelplanner.progresslog.mapper;
/*

import com.zexplore.travelplanner.model.ProgressLog;
import com.zexplore.travelplanner.progresslog.dto.ProgressLogRequest;
import com.zexplore.travelplanner.progresslog.dto.ProgressLogResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ProgressLogMapper {

//    @Mapping(target = "user.id", source = "userId")
//    @Mapping(target = "trip.id", source = "tripId")
//    ProgressLog toEntity(ProgressLogRequest request);
//
//    @Mapping(source = "trip.id", target = "tripId")
//    ProgressLogResponse toResponse(ProgressLog log);
}*/

import com.zexplore.travelplanner.model.ProgressLog;
import com.zexplore.travelplanner.model.Trip;
import com.zexplore.travelplanner.model.User;
import com.zexplore.travelplanner.progresslog.dto.ProgressLogRequest;
import com.zexplore.travelplanner.progresslog.dto.ProgressLogResponse;
import org.springframework.stereotype.Component;

@Component
public class ProgressLogMapper {

    public ProgressLog toEntity(ProgressLogRequest request) {
        ProgressLog log = new ProgressLog();
        log.setSteps(request.getSteps());
        log.setDistanceKm(request.getDistanceKm());
        log.setElevationGain(request.getElevationGain());
        log.setDate(request.getDate());

        if (request.getUserId() != null) {
            User user = new User();
            user.setId(request.getUserId());
            log.setUser(user);
        }

        if (request.getTripId() != null) {
            Trip trip = new Trip();
            trip.setId(request.getTripId());
            log.setTrip(trip);
        }

        return log;
    }

    public ProgressLogResponse toResponse(ProgressLog log) {
        Long userId = (log.getUser() != null) ? log.getUser().getId() : null;
        Long tripId = (log.getTrip() != null) ? log.getTrip().getId() : null;

        return new ProgressLogResponse(
                log.getId(),
                log.getDate(),
                log.getDistanceKm(),
                log.getSteps(),
                log.getElevationGain(),
                userId,
                tripId
        );
    }
}

