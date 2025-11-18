package com.zexplore.travelplanner.memory.repository;

import com.zexplore.travelplanner.model.Memory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoryRepository extends JpaRepository<Memory, Long> {
    Page<Memory> findByUser_Id(Long userId, Pageable pageable);
    Page<Memory> findByTrip_Id(Long tripId, Pageable pageable);

    boolean existsByIdAndUser_Id(Long id, Long userId);

}
