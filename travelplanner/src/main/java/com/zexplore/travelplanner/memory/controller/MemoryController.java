package com.zexplore.travelplanner.memory.controller;

import com.zexplore.travelplanner.memory.dto.MemoryCreateRequest;
import com.zexplore.travelplanner.memory.dto.MemoryResponse;
import com.zexplore.travelplanner.memory.dto.MemoryUpdateRequest;
import com.zexplore.travelplanner.memory.service.MemoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/memories")
@RequiredArgsConstructor
public class MemoryController {

    private final MemoryService memoryService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<MemoryResponse> create(@Valid @RequestBody MemoryCreateRequest req,
                                                 Authentication auth) {
        Long actorId = ((com.zexplore.travelplanner.security.CustomUserDetails) auth.getPrincipal()).getId();
        MemoryResponse created = memoryService.createForUser(actorId, req);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemoryResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(memoryService.get(id));
    }

    @GetMapping("/user/{userId}")
    public Page<MemoryResponse> listByUser(
            @PathVariable Long userId,
            @PageableDefault(size = 20, sort = "date", direction = Sort.Direction.DESC) Pageable pageable) {
        return memoryService.listByUser(userId, pageable);
    }

    @GetMapping("/trip/{tripId}")
    public Page<MemoryResponse> listByTrip(
            @PathVariable Long tripId,
            @PageableDefault(size = 20, sort = "date", direction = Sort.Direction.DESC) Pageable pageable) {
        return memoryService.listByTrip(tripId, pageable);
    }


    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.id")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<MemoryResponse> update(@PathVariable Long id,
                                                 @Valid @RequestBody MemoryUpdateRequest req,
                                                 Authentication auth) {
        Long actorId = ((com.zexplore.travelplanner.security.CustomUserDetails) auth.getPrincipal()).getId();
        MemoryResponse updated = memoryService.updateOwned(id, req, actorId);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.id")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id, Authentication auth) {
        Long userId = ((com.zexplore.travelplanner.security.CustomUserDetails) auth.getPrincipal()).getId();
        memoryService.softDeleteOwned(id, userId);
        return ResponseEntity.noContent().build();
    }
}
