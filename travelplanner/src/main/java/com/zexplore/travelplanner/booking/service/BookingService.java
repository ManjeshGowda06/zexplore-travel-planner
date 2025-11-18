package com.zexplore.travelplanner.booking.service;

import com.zexplore.travelplanner.booking.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookingService {
    BookingResponse create(BookingCreateRequest req);
    BookingResponse get(Long id);
    Page<BookingResponse> listByUser(Long userId, Pageable pageable);
    void cancel(Long bookingId, Long actingUserId, boolean adminOverride);
}