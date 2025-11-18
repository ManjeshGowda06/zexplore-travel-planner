package com.zexplore.travelplanner.location.service;

import com.zexplore.travelplanner.location.repository.LocationRepository;
import com.zexplore.travelplanner.location.repository.UserTripCompletionRepository;
import com.zexplore.travelplanner.location.util.GeoUtils;
import com.zexplore.travelplanner.model.Location;
import com.zexplore.travelplanner.model.Trip;
import com.zexplore.travelplanner.model.UserTripCompletion;
import com.zexplore.travelplanner.model.enums.TripType;
import com.zexplore.travelplanner.reward.service.RewardService;
import com.zexplore.travelplanner.trip.repository.TripRepository;
import com.zexplore.travelplanner.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

import static com.zexplore.travelplanner.model.enums.TripType.*;

@Service
@RequiredArgsConstructor
public class TrekCompletionService {

    private final LocationRepository locationRepository;
    private final TripRepository tripRepository;
    private final UserRepository userRepository;
    private final UserTripCompletionRepository completionRepository;
    private final RewardService rewardService;

    private static final double COMPLETION_THRESHOLD_METERS = 100.0;

   /* public boolean checkAndCompleteTrek(Long userId, Long tripId) {


        if (completionRepository.existsByUser_IdAndTrip_Id(userId, tripId)) {
            return true; // already completed; idempotent
        }

        Location latestLocation = locationRepository.findTopByUser_IdAndTrip_IdOrderByTimestampDesc(userId, tripId)
                .orElse(null);

        if (latestLocation == null) return false;

        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new IllegalArgumentException("Trip not found"));

        // Assuming trip.getLocation() is a string like "12.9716,77.5946"
        String[] destCoords = trip.getLocation().split(",");
        double destLat = Double.parseDouble(destCoords[0].trim());
        double destLon = Double.parseDouble(destCoords[1].trim());

        double distanceKm = GeoUtils.haversine(
                latestLocation.getLatitude(),
                latestLocation.getLongitude(),
                destLat,
                destLon
        );

        double distanceMeters = distanceKm * 1000;

        if (distanceMeters <= COMPLETION_THRESHOLD_METERS) {
            // Award reward points
            rewardService.addCompletionPoints(userId, tripId, 100); // You can customize points
            return true;
        }

        return false;
    }*/

    public boolean checkAndCompleteTrek(Long userId, Long tripId) {
        if (completionRepository.existsByUser_IdAndTrip_Id(userId, tripId)) {
            return true; // already completed; idempotent
        }

        Location latestLocation = locationRepository
                .findTopByUser_IdAndTrip_IdOrderByTimestampDesc(userId, tripId)
                .orElse(null);

        if (latestLocation == null) return false;

        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new IllegalArgumentException("Trip not found"));

        double[] dest = parseLatLon(trip.getLocation());
        double distanceKm = GeoUtils.haversine(
                latestLocation.getLatitude(), latestLocation.getLongitude(), dest[0], dest[1]
        );

        double distanceMeters = distanceKm * 1000.0;

        if (distanceMeters <= COMPLETION_THRESHOLD_METERS) {
            // award points and mark completion
            rewardService.addPoints(userId, 100);
            completionRepository.save(UserTripCompletion.builder()
                    .user(latestLocation.getUser())
                    .trip(trip)
                    .completedAt(OffsetDateTime.now())
                    .build());
            return true;
        }
        return false;
    }

    public boolean checkCompletion(Long userId, Long tripId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new IllegalArgumentException("Trip not found"));
        TripType type = trip.getTripType();

        return switch (type) {
            case TREKKING -> checkAndCompleteTrek(userId, tripId);
            case HISTORICAL -> checkHistoricalVisit(userId, trip);
            case ADVENTURE -> checkAdventureCompletion(userId, trip);
            case RELIGIOUS -> checkReligiousVisit(userId, trip);
            case CULTURAL -> checkCulturalParticipation(userId, trip);
            case MIXED -> checkMixedCompletion(userId, trip);
            default -> false;
        };
    }


    private boolean checkHistoricalVisit(Long userId, Trip trip) {
        Location latestLocation = locationRepository.findTopByUser_IdAndTrip_IdOrderByTimestampDesc(userId, trip.getId())
                .orElse(null);
        if (latestLocation == null) return false;

        // Parse destination coordinates from trip.location (e.g., "20.5537,75.7033")
        String[] destCoords = trip.getLocation().split(",");
        double destLat = Double.parseDouble(destCoords[0].trim());
        double destLon = Double.parseDouble(destCoords[1].trim());

        double distanceKm = GeoUtils.haversine(latestLocation.getLatitude(), latestLocation.getLongitude(), destLat, destLon);
        double distanceMeters = distanceKm * 1000;

        // Historical visit logic: within 100m + optional time spent check
        if (distanceMeters <= COMPLETION_THRESHOLD_METERS) {
            // Optional: Check time spent (e.g., at least 30 minutes)
            // For now, assume check-in is enough
            rewardService.addCompletionPoints(userId, trip.getId(), 50); // Award 50 points for historical trips
            return true;
        }
        return false;
    }


    private boolean checkAdventureCompletion(Long userId, Trip trip) {
        // Adventure logic: confirm booking status (pseudo-code)
        // Booking booking = bookingRepository.findByUserAndTrip(userId, trip.getId());
        // if (booking != null && booking.getStatus() == BookingStatus.COMPLETED) {
        rewardService.addCompletionPoints(userId, trip.getId(), 150);
        return true;
        // }
        // return false;
    }


    private boolean checkReligiousVisit(Long userId, Trip trip) {
        Location latestLocation = locationRepository.findTopByUser_IdAndTrip_IdOrderByTimestampDesc(userId, trip.getId())
                .orElse(null);
        if (latestLocation == null) return false;

        String[] destCoords = trip.getLocation().split(",");
        double destLat = Double.parseDouble(destCoords[0].trim());
        double destLon = Double.parseDouble(destCoords[1].trim());

        double distanceKm = GeoUtils.haversine(latestLocation.getLatitude(), latestLocation.getLongitude(), destLat, destLon);
        double distanceMeters = distanceKm * 1000;

        if (distanceMeters <= COMPLETION_THRESHOLD_METERS) {
            rewardService.addCompletionPoints(userId, trip.getId(), 30);
            return true;
        }
        return false;
    }

    private boolean checkCulturalParticipation(Long userId, Trip trip) {
        // Cultural logic: check-in or QR validation (simplified)
        rewardService.addCompletionPoints(userId, trip.getId(), 40);
        return true;
    }

    private boolean checkMixedCompletion(Long userId, Trip trip) {
        // Mixed logic: combine trekking + cultural
        rewardService.addCompletionPoints(userId, trip.getId(), 200);
        return true;
    }

    //newly addded
    private double[] parseLatLon(String location) {
        if (location == null || !location.contains(",")) {
            throw new IllegalArgumentException("Trip location is invalid");
        }
        String[] parts = location.split(",");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Trip location format must be 'lat,lon'");
        }
        try {
            return new double[] {
                    Double.parseDouble(parts[0].trim()),
                    Double.parseDouble(parts[1].trim())
            };
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Trip location coordinates are not numeric");
        }
    }


}
