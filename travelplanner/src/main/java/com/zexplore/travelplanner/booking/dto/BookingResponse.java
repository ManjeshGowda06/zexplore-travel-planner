package com.zexplore.travelplanner.booking.dto;

import com.zexplore.travelplanner.model.enums.BookingStatus;


import java.time.LocalDate;


public record BookingResponse(
        Long id,
        Long userId,
        Long tripId,
        LocalDate bookingDate,
        BookingStatus status
) {}
