package com.zexplore.travelplanner.trip.service;

import com.zexplore.travelplanner.trip.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TripService {
    TripResponse create(TripCreateRequest req);
    TripResponse get(Long id);
    Page<TripResponse> list(TripFilter filter, Pageable pageable);
    TripResponse update(Long id, TripUpdateRequest req);
    void delete(Long id);
}
