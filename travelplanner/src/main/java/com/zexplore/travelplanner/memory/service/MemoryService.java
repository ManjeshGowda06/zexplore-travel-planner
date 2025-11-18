package com.zexplore.travelplanner.memory.service;

import com.zexplore.travelplanner.memory.dto.MemoryCreateRequest;
import com.zexplore.travelplanner.memory.dto.MemoryResponse;
import com.zexplore.travelplanner.memory.dto.MemoryUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface MemoryService {
//    MemoryResponse create(MemoryCreateRequest req);
    MemoryResponse get(Long id);
    Page<MemoryResponse> listByUser(Long userId, Pageable pageable);
    Page<MemoryResponse> listByTrip(Long tripId, Pageable pageable);

//    @Transactional
//    MemoryResponse update(Long id, MemoryUpdateRequest req);
//    void softDelete(Long id, Long userId);





    MemoryResponse createForUser(Long userId, MemoryCreateRequest req);


    @Transactional
    MemoryResponse updateOwned(Long id, MemoryUpdateRequest req, Long actorUserId);

    @Transactional
    void softDeleteOwned(Long id, Long actorUserId);


}
