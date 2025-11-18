package com.zexplore.travelplanner.booking.controller;

import com.zexplore.travelplanner.booking.dto.*;
import com.zexplore.travelplanner.booking.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

  /*  @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BookingResponse> create(@Valid @RequestBody BookingCreateRequest req) {
        BookingResponse created = bookingService.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }*/

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BookingResponse> create(@Valid @RequestBody BookingCreateRequest req, Authentication auth) {
        Long actorId = ((com.zexplore.travelplanner.security.CustomUserDetails) auth.getPrincipal()).getId();
        BookingResponse created = bookingService.create(new BookingCreateRequest(actorId, req.tripId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.get(id));
    }

    @GetMapping("/user/{userId}")
    public Page<BookingResponse> getUserBookings(@PathVariable Long userId,
                                                 @PageableDefault(size = 20, sort = "bookingDate", direction = Sort.Direction.DESC)
                                                 Pageable pageable) {
        return bookingService.listByUser(userId, pageable);
    }

    @PutMapping("/{bookingId}/cancel")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> cancel(@PathVariable Long bookingId,
                                       @RequestParam(defaultValue = "false") boolean adminOverride,
                                       Authentication auth) {
        Long actorId = ((com.zexplore.travelplanner.security.CustomUserDetails) auth.getPrincipal()).getId();
        bookingService.cancel(bookingId, actorId, adminOverride);
        return ResponseEntity.noContent().build();
    }
}
