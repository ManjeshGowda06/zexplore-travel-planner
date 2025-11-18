package com.zexplore.travelplanner.booking.service;

import com.zexplore.travelplanner.booking.dto.*;
import com.zexplore.travelplanner.booking.mapper.BookingMapper;
import com.zexplore.travelplanner.booking.repository.BookingRepository;
import com.zexplore.travelplanner.model.Booking;
import com.zexplore.travelplanner.model.Trip;
import com.zexplore.travelplanner.model.User;
import com.zexplore.travelplanner.model.enums.BookingStatus;
import com.zexplore.travelplanner.model.enums.Season;
import com.zexplore.travelplanner.reward.service.RewardService;
import com.zexplore.travelplanner.trip.repository.TripRepository;
import com.zexplore.travelplanner.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final TripRepository tripRepository;
    private final BookingMapper bookingMapper;
    private final RewardService rewardService;


    @Transactional
    @Override
    public BookingResponse create(BookingCreateRequest req) {
        User user = userRepository.findById(req.userId())
                .orElseThrow(() -> new EntityNotFoundException("User %d not found".formatted(req.userId())));
        Trip trip = tripRepository.findById(req.tripId())
                .orElseThrow(() -> new EntityNotFoundException("Trip %d not found".formatted(req.tripId())));

        // Optional: capacity check via trip.getMaxCapacity() & countByTrip_IdAndStatus(...)
        // long confirmed = bookingRepository.countByTrip_IdAndStatus(trip.getId(), BookingStatus.CONFIRMED);
        // if (trip.getMaxCapacity() != null && confirmed >= trip.getMaxCapacity()) {
        //     throw new IllegalStateException("Trip is fully booked");
        // }


        // Duplicate booking prevention
        if (bookingRepository.existsByUser_IdAndTrip_Id(user.getId(), trip.getId())) {
            throw new IllegalArgumentException("You have already booked this trip");
        }

        // Capacity check if you have capacity (uncomment if you add maxCapacity)
        // long confirmed = bookingRepository.countByTrip_IdAndStatus(trip.getId(), BookingStatus.CONFIRMED);
        // if (trip.getMaxCapacity() != null && confirmed >= trip.getMaxCapacity()) {
        //     throw new IllegalStateException("Trip is fully booked");
        // }

        Booking b = Booking.builder()
                .user(user)
                .trip(trip)
                .bookingDate(LocalDate.now())
                .status(BookingStatus.CONFIRMED)
                .build();

        Booking savedBooking = bookingRepository.save(b);

        // ✅ Award points for booking
        /*boolean isSeasonalEvent = checkSeasonalOffer(trip.getSeason()); // Implement this logic
        rewardService.addSeasonalPoints(user.getId(), 50, isSeasonalEvent);
*/
        // Award points (seasonal logic omitted for simplicity)
        rewardService.addPoints(user.getId(), 50);
        return bookingMapper.toResponse(savedBooking);

    }


    // Example seasonal check
    private boolean checkSeasonalOffer(Season season) {
        return season == Season.WINTER; // Example: double points in winter
    }


    @Override
    public BookingResponse get(Long id) {
        Booking b = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking %d not found".formatted(id)));
        return bookingMapper.toResponse(b);
    }

    @Override
    public Page<BookingResponse> listByUser(Long userId, Pageable pageable) {
        int size = Math.min(Math.max(pageable.getPageSize(), 1), 100);
        Pageable capped = PageRequest.of(Math.max(pageable.getPageNumber(), 0), size, pageable.getSort());
        return bookingRepository.findByUser_Id(userId, capped).map(bookingMapper::toResponse);
    }

    @Transactional
    @Override
    public void cancel(Long bookingId, Long actingUserId, boolean adminOverride) {
        Booking b = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking %d not found".formatted(bookingId)));

        if (b.getStatus() == BookingStatus.CANCELLED) {
            return; // idempotent
        }
        if (!adminOverride && !b.getUser().getId().equals(actingUserId)) {
            throw new AccessDeniedException("You cannot cancel another user's booking");
        }

        b.setStatus(BookingStatus.CANCELLED);
        b.setCancelledAt(OffsetDateTime.now());
        b.setCancelledBy(actingUserId);
        b.setCancelledReason(adminOverride ? "ADMIN_OVERRIDE" : "USER_REQUEST");
        bookingRepository.save(b);
    }
}
