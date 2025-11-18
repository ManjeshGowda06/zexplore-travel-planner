package com.zexplore.travelplanner.trip.service;

import com.zexplore.travelplanner.exception.TripNotFoundException;
import com.zexplore.travelplanner.trip.dto.*;
import com.zexplore.travelplanner.trip.mapper.TripMapper;
import com.zexplore.travelplanner.model.Trip;
import com.zexplore.travelplanner.trip.repository.TripRepository;
import com.zexplore.travelplanner.trip.repository.TripSpecifications;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class TripServiceImpl implements TripService {

    private final TripRepository tripRepository;
    private final TripMapper tripMapper;

    @Transactional
    @Override    //this is not required -> Since TripCreateRequest already has @AssertTrue for date range validation, you can safely remove this from the service:
    public TripResponse create(TripCreateRequest req) {
        Trip entity = tripMapper.toEntity(req);
        log.info("Mapped Trip: {}", entity);

                // Debug log to confirm mapping
        log.info("Creating trip: title={}, startDate={}, endDate={}",
                entity.getTitle(), entity.getStartDate(), entity.getEndDate());
        log.info("Mapped Trip: difficulty={}, startDate={}, endDate={}", entity.getDifficulty(), entity.getStartDate(), entity.getEndDate());

        Trip saved = tripRepository.save(entity);
        return tripMapper.toResponse(saved);
    }


    @Transactional(readOnly = true)
    @Override
    public TripResponse get(Long id) {
        Trip t = tripRepository.findById(id)
                .orElseThrow(() -> new TripNotFoundException(id));
        return tripMapper.toResponse(t);
    }


    @Override
    public Page<TripResponse> list(TripFilter filter, Pageable pageable) {
        // Defensive cap: avoid heavy/unbounded queries
        int size = Math.min(Math.max(pageable.getPageSize(), 1), 100);
        pageable = PageRequest.of(Math.max(pageable.getPageNumber(), 0), size, pageable.getSort());

        Specification<Trip> spec = Specification.unrestricted();
        Specification<Trip> byLocation   = TripSpecifications.locationContains(filter.location());
        Specification<Trip> byDifficulty = TripSpecifications.difficultyIs(filter.difficulty());
        Specification<Trip> bySeason     = TripSpecifications.seasonIs(filter.season());
        Specification<Trip> byStartDate  = TripSpecifications.startDateBetween(filter.startDateFrom(), filter.startDateTo());

        if (byLocation   != null) spec = spec.and(byLocation);
        if (byDifficulty != null) spec = spec.and(byDifficulty);
        if (bySeason     != null) spec = spec.and(bySeason);
        if (byStartDate  != null) spec = spec.and(byStartDate);

        return tripRepository.findAll(spec, pageable).map(tripMapper::toResponse);
    }

    @Transactional
    @Override
    public TripResponse update(Long id, TripUpdateRequest req) {
        Trip t = tripRepository.findById(id)
                .orElseThrow(() -> new TripNotFoundException(id));

        // Correct order: entity first, DTO second
        tripMapper.updateEntityFromDto(t, req);

        if (t.getStartDate() != null && t.getEndDate() != null &&
                t.getEndDate().isBefore(t.getStartDate())) {
            throw new IllegalArgumentException("endDate must be on or after startDate");
        }

        Trip saved = tripRepository.save(t);
        return tripMapper.toResponse(saved);
    }


    @Override
    public void delete(Long id) {
        Trip t = tripRepository.findById(id)
                .orElseThrow(() -> new TripNotFoundException(id));
        // Soft delete
        t.setDeletedAt(OffsetDateTime.now());
        // Optional: set deletedBy from SecurityContext if admin
        // t.setDeletedBy(currentAdminId());
        tripRepository.save(t);
    }

}