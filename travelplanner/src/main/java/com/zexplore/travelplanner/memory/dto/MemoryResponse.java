package com.zexplore.travelplanner.memory.dto;

import com.zexplore.travelplanner.model.enums.MediaType;


import java.time.LocalDate;


public record MemoryResponse(
        Long id,
        Long userId,
        Long tripId,
        String mediaUrl,
        MediaType mediaType,
        LocalDate date,
        String location
) {}
