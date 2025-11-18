package com.zexplore.travelplanner.booking.mapper;

import com.zexplore.travelplanner.booking.dto.BookingResponse;
import com.zexplore.travelplanner.model.Booking;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {

    public BookingResponse toResponse(Booking booking) {
        Long userId = (booking.getUser() != null) ? booking.getUser().getId() : null;
        Long tripId = (booking.getTrip() != null) ? booking.getTrip().getId() : null;

        return new BookingResponse(
                booking.getId(),
                userId,
                tripId,
                booking.getBookingDate(),
                booking.getStatus()
        );
    }
}