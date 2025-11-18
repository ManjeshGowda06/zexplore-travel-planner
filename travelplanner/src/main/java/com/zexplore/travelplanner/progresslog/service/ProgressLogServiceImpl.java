package com.zexplore.travelplanner.progresslog.service;

import com.zexplore.travelplanner.model.ProgressLog;
import com.zexplore.travelplanner.model.Trip;
import com.zexplore.travelplanner.model.User;
import com.zexplore.travelplanner.progresslog.dto.ProgressLogRequest;
import com.zexplore.travelplanner.progresslog.dto.ProgressLogResponse;
import com.zexplore.travelplanner.progresslog.mapper.ProgressLogMapper;
import com.zexplore.travelplanner.progresslog.repository.ProgressLogRepository;

import com.zexplore.travelplanner.reward.service.RewardService;
import com.zexplore.travelplanner.trip.repository.TripRepository;
import com.zexplore.travelplanner.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProgressLogServiceImpl implements ProgressLogService {

    private final ProgressLogRepository progressLogRepository;
    private final UserRepository userRepository;
    private final TripRepository tripRepository;
    private final ProgressLogMapper mapper;
    private final RewardService rewardService;

    @Override
    public ProgressLogResponse logProgress(ProgressLogRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Trip trip = tripRepository.findById(request.getTripId())
                .orElseThrow(() -> new IllegalArgumentException("Trip not found"));

        ProgressLog log = mapper.toEntity(request);
        log.setUser(user);
        log.setTrip(trip);


        ProgressLog savedLog = progressLogRepository.save(log);

        // ✅ Award points based on progress
        int points = calculatePoints(request.getDistanceKm(), request.getSteps(), request.getElevationGain());
        rewardService.addPoints(user.getId(), points);

        return mapper.toResponse(savedLog);

    }



    @Override
    @Transactional(readOnly = true)
    public List<ProgressLogResponse> getProgressByUserAndTrip(Long userId, Long tripId) {
        return progressLogRepository.findByUser_IdAndTrip_Id(userId, tripId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }


    // Example points calculation
    private int calculatePoints(double distanceKm, int steps, double elevationGain) {
        int distancePoints = (int) (distanceKm * 10); // 10 points per km
        int stepPoints = steps / 1000; // 1 point per 1000 steps
        int elevationPoints = (int) (elevationGain / 10); // 1 point per 10m elevation
        return distancePoints + stepPoints + elevationPoints;
    }
}
