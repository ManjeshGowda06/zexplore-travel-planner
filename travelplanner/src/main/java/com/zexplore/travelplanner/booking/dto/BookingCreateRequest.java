package com.zexplore.travelplanner.booking.dto;

import jakarta.validation.constraints.NotNull;

public record BookingCreateRequest(
        @NotNull Long userId,   // if you have JWT auth, infer userId from token instead
        @NotNull Long tripId
) {}
