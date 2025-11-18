package com.zexplore.travelplanner.admin.controller;

import com.zexplore.travelplanner.admin.service.AdminService;
import com.zexplore.travelplanner.trip.dto.TripResponse;
import com.zexplore.travelplanner.trip.dto.TripUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // ------- Trips -------
    @PutMapping("/trips/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TripResponse> updateTrip(@PathVariable("id") Long id,
                                                   @Valid @RequestBody TripUpdateRequest req) {
        return ResponseEntity.ok(adminService.updateTrip(id, req));
    }

    @DeleteMapping("/trips/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTrip(@PathVariable("id") Long id) {
        adminService.deleteTrip(id);
        return ResponseEntity.noContent().build();
    }

    // ------- Bookings -------
    @PutMapping("/bookings/{id}/cancel")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> forceCancelBooking(@PathVariable("id") Long id) {
        adminService.forceCancelBooking(id);
        return ResponseEntity.noContent().build();
    }

    // ------- Memories -------
    @DeleteMapping("/memories/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteMemory(@PathVariable("id") Long id) {
        adminService.deleteMemory(id);
        return ResponseEntity.noContent().build();
    }
}