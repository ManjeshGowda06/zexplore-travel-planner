package com.zexplore.travelplanner.trip.controller;

import com.zexplore.travelplanner.model.enums.Difficulty;
import com.zexplore.travelplanner.model.enums.Season;
import com.zexplore.travelplanner.trip.dto.*;
import com.zexplore.travelplanner.trip.service.TripService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("/api/v1/trips")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    @GetMapping
    public Page<TripResponse> list(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Difficulty difficulty,
            @RequestParam(required = false) Season season,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDateFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDateTo,
            @PageableDefault(size = 20, sort = "startDate", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        TripFilter filter = new TripFilter(location, difficulty, season, startDateFrom, startDateTo);
        // cap size to avoid heavy queries
        int cappedSize = Math.min(pageable.getPageSize(), 100);
        pageable = PageRequest.of(pageable.getPageNumber(), cappedSize, pageable.getSort());
        return tripService.list(filter, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TripResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(tripService.get(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TripResponse> create(@Valid @RequestBody TripCreateRequest req,
                                               UriComponentsBuilder uriBuilder) {

        log.info(req.difficulty().toString());
        TripResponse created = tripService.create(req);
        log.info(created.difficulty().toString());
        return ResponseEntity.created(
                uriBuilder.path("/api/v1/trips/{id}").buildAndExpand(created.id()).toUri()
        ).body(created);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TripResponse> update(@PathVariable Long id,
                                               @Valid @RequestBody TripUpdateRequest req) {
        return ResponseEntity.ok(tripService.update(id, req));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tripService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

