package com.zexplore.travelplanner.memory.service;

import com.zexplore.travelplanner.memory.dto.MemoryCreateRequest;
import com.zexplore.travelplanner.memory.dto.MemoryResponse;
import com.zexplore.travelplanner.memory.dto.MemoryUpdateRequest;
import com.zexplore.travelplanner.memory.mapper.MemoryMapper;
import com.zexplore.travelplanner.memory.repository.MemoryRepository;
import com.zexplore.travelplanner.model.Memory;
import com.zexplore.travelplanner.model.Trip;
import com.zexplore.travelplanner.model.User;
import com.zexplore.travelplanner.trip.repository.TripRepository;
import com.zexplore.travelplanner.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemoryServiceImpl implements MemoryService {

    private final MemoryRepository memoryRepository;
    private final UserRepository userRepository;
    private final TripRepository tripRepository;
    private final MemoryMapper memoryMapper;

/*    @Transactional
    @Override
    public MemoryResponse create(MemoryCreateRequest req) {
        User user = userRepository.findById(req.userId())
                .orElseThrow(() -> new EntityNotFoundException("User %d not found".formatted(req.userId())));
        Trip trip = tripRepository.findById(req.tripId())
                .orElseThrow(() -> new EntityNotFoundException("Trip %d not found".formatted(req.tripId())));

        Memory memory = Memory.builder()
                .user(user)
                .trip(trip)
                .mediaUrl(req.mediaUrl())
                .mediaType(req.mediaType())
                .date(req.date())
                .location(req.location())
                .build();

        return memoryMapper.toResponse(memoryRepository.save(memory));
    }*/

    @Transactional
    @Override
    public MemoryResponse createForUser(Long userId, MemoryCreateRequest req) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User %d not found".formatted(userId)));
        Trip trip = tripRepository.findById(req.tripId())
                .orElseThrow(() -> new EntityNotFoundException("Trip %d not found".formatted(req.tripId())));

        Memory memory = Memory.builder()
                .user(user)
                .trip(trip)
                .mediaUrl(req.mediaUrl())
                .mediaType(req.mediaType())
                .date(req.date())
                .location(req.location())
                .build();

        return memoryMapper.toResponse(memoryRepository.save(memory));
    }

    @Override
    public MemoryResponse get(Long id) {
        Memory m = memoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Memory %d not found".formatted(id)));
        return memoryMapper.toResponse(m);
    }

    @Override
    public Page<MemoryResponse> listByUser(Long userId, Pageable pageable) {
        int size = Math.min(Math.max(pageable.getPageSize(), 1), 100);
        Pageable capped = PageRequest.of(Math.max(pageable.getPageNumber(), 0), size, pageable.getSort());
        return memoryRepository.findByUser_Id(userId, capped).map(memoryMapper::toResponse);
    }

    @Override
    public Page<MemoryResponse> listByTrip(Long tripId, Pageable pageable) {
        int size = Math.min(Math.max(pageable.getPageSize(), 1), 100);
        Pageable capped = PageRequest.of(Math.max(pageable.getPageNumber(), 0), size, pageable.getSort());
        return memoryRepository.findByTrip_Id(tripId, capped).map(memoryMapper::toResponse);
    }

/*    @Transactional
    public void softDelete(Long id, Long userId) {
        Memory memory = memoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Memory not found"));
        memory.setDeletedAt(OffsetDateTime.now());
        memory.setDeletedBy(userId);
        memoryRepository.save(memory);
    }*/

/*    @Transactional
    @Override
    public MemoryResponse update(Long id, MemoryUpdateRequest req) {
        Memory memory = memoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Memory %d not found".formatted(id)));

        // Apply updates only if fields are non-null
        if (req.mediaUrl() != null) memory.setMediaUrl(req.mediaUrl());
        if (req.mediaType() != null) memory.setMediaType(req.mediaType());
        if (req.date() != null) memory.setDate(req.date());
        if (req.location() != null) memory.setLocation(req.location());

        Memory updated = memoryRepository.save(memory);
        return memoryMapper.toResponse(updated);
    }*/

    @Transactional
    @Override
    public MemoryResponse updateOwned(Long id, MemoryUpdateRequest req, Long actorUserId) {
        Memory memory = memoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Memory %d not found".formatted(id)));

        if (!memory.getUser().getId().equals(actorUserId)) {
            throw new AccessDeniedException("Not allowed to update this memory");
        }

        if (req.mediaUrl() != null) memory.setMediaUrl(req.mediaUrl());
        if (req.mediaType() != null) memory.setMediaType(req.mediaType());
        if (req.date() != null) memory.setDate(req.date());
        if (req.location() != null) memory.setLocation(req.location());

        Memory updated = memoryRepository.save(memory);
        return memoryMapper.toResponse(updated);
    }

    @Transactional
    @Override
    public void softDeleteOwned(Long id, Long actorUserId) {
        Memory memory = memoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Memory not found"));
        if (!memory.getUser().getId().equals(actorUserId)) {
            throw new AccessDeniedException("Not allowed to delete this memory");
        }
        if (memory.getDeletedAt() != null) return; // idempotent

        memory.setDeletedAt(OffsetDateTime.now());
        memory.setDeletedBy(actorUserId);
        memoryRepository.save(memory);
    }


}
