package com.zexplore.travelplanner.progresslog.controller;

import com.zexplore.travelplanner.progresslog.dto.ProgressLogRequest;
import com.zexplore.travelplanner.progresslog.dto.ProgressLogResponse;
import com.zexplore.travelplanner.progresslog.service.ProgressLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/progress")
@RequiredArgsConstructor
public class ProgressLogController {

    private final ProgressLogService progressLogService;


    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ProgressLogResponse logProgress(@RequestBody @Validated ProgressLogRequest request) {
        return progressLogService.logProgress(request);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public List<ProgressLogResponse> getProgress(@RequestParam Long userId, @RequestParam Long tripId) {
        return progressLogService.getProgressByUserAndTrip(userId, tripId);
    }
}

