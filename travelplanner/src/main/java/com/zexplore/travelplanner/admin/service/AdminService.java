package com.zexplore.travelplanner.admin.service;

import com.zexplore.travelplanner.trip.dto.TripResponse;
import com.zexplore.travelplanner.trip.dto.TripUpdateRequest;

public interface AdminService {
    TripResponse updateTrip(Long tripId, TripUpdateRequest req);
    void deleteTrip(Long tripId);

    void forceCancelBooking(Long bookingId);    // admin override

    void deleteMemory(Long memoryId);           // moderation remove
}
