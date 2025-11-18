package com.zexplore.travelplanner.memory.mapper;

import com.zexplore.travelplanner.memory.dto.MemoryResponse;
import com.zexplore.travelplanner.model.Memory;
import org.springframework.stereotype.Component;

@Component
public class MemoryMapper {

    public MemoryResponse toResponse(Memory memory) {
        Long userId = (memory.getUser() != null) ? memory.getUser().getId() : null;
        Long tripId = (memory.getTrip() != null) ? memory.getTrip().getId() : null;

        return new MemoryResponse(
                memory.getId(),
                userId,
                tripId,
                memory.getMediaUrl(),
                memory.getMediaType(),
                memory.getDate(),
                memory.getLocation()
        );
    }
}











//@Mapper(componentModel = "spring")
//public interface MemoryMapper {
//
//    @Mapping(target = "userId", source = "user.id")
//    @Mapping(target = "tripId", source = "trip.id")
//    MemoryResponse toResponse(Memory memory);
//}