package com.zexplore.travelplanner.location.repository;


import com.zexplore.travelplanner.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {

    Optional<Location> findTopByUser_IdAndTrip_IdOrderByTimestampDesc(Long userId, Long tripId);
}

