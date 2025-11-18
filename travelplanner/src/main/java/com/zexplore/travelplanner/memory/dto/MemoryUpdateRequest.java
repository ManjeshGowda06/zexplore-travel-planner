package com.zexplore.travelplanner.memory.dto;

import com.zexplore.travelplanner.model.enums.MediaType;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record MemoryUpdateRequest(
        @Size(max = 512) String mediaUrl,
        MediaType mediaType,
        LocalDate date,
        @Size(max = 120) String location
) {}
