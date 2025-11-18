package com.zexplore.travelplanner.memory.dto;

import com.zexplore.travelplanner.model.enums.MediaType;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record MemoryCreateRequest(
        @NotNull Long userId,
        @NotNull Long tripId,
        @NotBlank @Size(max = 512) String mediaUrl,
        @NotNull MediaType mediaType,
        @NotNull LocalDate date,
        @Size(max = 120) String location
) {}
