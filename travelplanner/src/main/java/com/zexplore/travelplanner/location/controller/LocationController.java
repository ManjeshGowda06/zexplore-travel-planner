package com.zexplore.travelplanner.location.controller;

import com.zexplore.travelplanner.location.dto.LocationDTO;

import com.zexplore.travelplanner.location.service.LocationService;
import com.zexplore.travelplanner.location.service.TrekCompletionService;
import com.zexplore.travelplanner.model.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/location")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;
    private TrekCompletionService trekCompletionService;

    @PostMapping
    public ResponseEntity<Location> saveLocation(@RequestBody LocationDTO dto) {
        Location saved = locationService.saveLocation(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/latest")
    public ResponseEntity<Location> getLatestLocation(@RequestParam Long userId, @RequestParam Long tripId) {
        Location latest = locationService.getLatestLocation(userId, tripId);
        return latest != null ? ResponseEntity.ok(latest) : ResponseEntity.notFound().build();
    }

    @GetMapping("/check-completion")
    public ResponseEntity<String> checkCompletion(@RequestParam Long userId, @RequestParam Long tripId) {
        boolean completed = trekCompletionService.checkCompletion(userId, tripId);
        if (completed) {
            return ResponseEntity.ok("Trip completed! Reward points awarded.");
        } else {
            return ResponseEntity.ok("Trip not completed yet.");
        }
    }
}
