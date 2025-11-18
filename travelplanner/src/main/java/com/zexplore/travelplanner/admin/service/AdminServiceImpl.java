package com.zexplore.travelplanner.admin.service;



import com.zexplore.travelplanner.memory.repository.MemoryRepository;
import com.zexplore.travelplanner.model.Trip;
import com.zexplore.travelplanner.trip.dto.TripResponse;
import com.zexplore.travelplanner.trip.dto.TripUpdateRequest;
import com.zexplore.travelplanner.trip.repository.TripRepository;
import com.zexplore.travelplanner.trip.service.TripService;
import com.zexplore.travelplanner.booking.service.BookingService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminServiceImpl implements AdminService {

    private final TripService tripService;
    private final BookingService bookingService;
    private final MemoryRepository memoryRepository;
    private final TripRepository tripRepository;

    @Transactional
    @Override
    public TripResponse updateTrip(Long tripId, TripUpdateRequest req) {
        return tripService.update(tripId, req); // reuse your trip service + mapper + validation
    }

    @Transactional
    @Override
    public void deleteTrip(Long tripId) {
        // Hard delete right now; see note below for soft delete
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new EntityNotFoundException("Trip not found"));
        trip.setDeletedAt(OffsetDateTime.now());
        trip.setDeletedBy(0L); // admin ID placeholder
        tripRepository.save(trip);
    }

    @Transactional
    @Override
    public void forceCancelBooking(Long bookingId) {
        // Acting user not needed with override=true
        bookingService.cancel(bookingId, 0L, true);
    }

    @Transactional
    @Override
    public void deleteMemory(Long memoryId) {
        if (!memoryRepository.existsById(memoryId)) {
            throw new EntityNotFoundException("Memory %d not found".formatted(memoryId));
        }
        memoryRepository.deleteById(memoryId);
    }
}
