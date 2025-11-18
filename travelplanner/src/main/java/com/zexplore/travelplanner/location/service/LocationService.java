package com.zexplore.travelplanner.location.service;

import com.zexplore.travelplanner.location.dto.LocationDTO;
import com.zexplore.travelplanner.location.repository.LocationRepository;
import com.zexplore.travelplanner.model.Location;
import com.zexplore.travelplanner.model.Trip;
import com.zexplore.travelplanner.model.User;
import com.zexplore.travelplanner.trip.repository.TripRepository;
import com.zexplore.travelplanner.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;
    private final UserRepository userRepository;
    private final TripRepository tripRepository;

    public Location saveLocation(LocationDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Trip trip = tripRepository.findById(dto.getTripId())
                .orElseThrow(() -> new IllegalArgumentException("Trip not found"));

        Location location = new Location();
        location.setUser(user);
        location.setTrip(trip);
        location.setLatitude(dto.getLatitude());
        location.setLongitude(dto.getLongitude());
        location.setTimestamp(OffsetDateTime.now());

        return locationRepository.save(location);
    }

    public Location getLatestLocation(Long userId, Long tripId) {
        return locationRepository.findTopByUser_IdAndTrip_IdOrderByTimestampDesc(userId, tripId)
                .orElse(null);
    }
}