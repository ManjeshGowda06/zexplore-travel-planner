package com.zexplore.travelplanner.trip.mapper;

import com.zexplore.travelplanner.model.Trip;
import com.zexplore.travelplanner.trip.dto.TripCreateRequest;
import com.zexplore.travelplanner.trip.dto.TripResponse;
import com.zexplore.travelplanner.trip.dto.TripUpdateRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TripMapper {

    // Convert create request to entity
    Trip toEntity(TripCreateRequest req);

    // Convert entity to response DTO
    TripResponse toResponse(Trip entity);

    // Update entity from update request (ignore nulls)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(@MappingTarget Trip entity, TripUpdateRequest req);
}