package com.zexplore.travelplanner.booking.repository;


import com.zexplore.travelplanner.model.Booking;
import com.zexplore.travelplanner.model.enums.BookingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Note: use relation traversal for derived queries
    Page<Booking> findByUser_Id(Long userId, Pageable pageable);

    Page<Booking> findByTrip_Id(Long tripId, Pageable pageable);

    long countByTrip_IdAndStatus(Long tripId, BookingStatus status);

    boolean existsByIdAndUser_Id(Long bookingId, Long userId);


    boolean existsByUser_IdAndTrip_Id(Long id, Long id1);
}

