package com.zexplore.travelplanner.suggestion.service;

import com.zexplore.travelplanner.model.Suggestion;
import com.zexplore.travelplanner.model.User;

import com.zexplore.travelplanner.suggestion.dto.SuggestionRequest;
import com.zexplore.travelplanner.suggestion.dto.SuggestionResponse;
import com.zexplore.travelplanner.suggestion.mapper.SuggestionMapper;
import com.zexplore.travelplanner.suggestion.repository.SuggestionRepository;
import com.zexplore.travelplanner.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SuggestionServiceImpl implements SuggestionService {

    private final SuggestionRepository suggestionRepository;
    private final UserRepository userRepository;
    private final SuggestionMapper suggestionMapper;

    @Override
    @Transactional
    public SuggestionResponse saveSuggestion(SuggestionRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Suggestion suggestion = suggestionMapper.toEntity(request);
        suggestion.setUser(user);

        Suggestion saved = suggestionRepository.save(suggestion);
        return suggestionMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SuggestionResponse> getWeatherAlerts(String location) {
        // Example: You could fetch from DB or generate dynamically
        Suggestion suggestion = new Suggestion();
        suggestion.setType("WEATHER_ALERT");
        suggestion.setMessage("Rain expected in " + location + ". Carry waterproof gear.");
        return List.of(suggestionMapper.toResponse(suggestion));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SuggestionResponse> getPackingList(String difficulty) {
        String message = "easy".equalsIgnoreCase(difficulty)
                ? "Carry light snacks, water bottle, and sun protection."
                : "Include trekking shoes, energy bars, raincoat, and first aid kit.";

        Suggestion suggestion = new Suggestion();
        suggestion.setType("PACKING_LIST");
        suggestion.setMessage(message);
        return List.of(suggestionMapper.toResponse(suggestion));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SuggestionResponse> getNearbyPlaces(String currentLocation) {
        Suggestion suggestion = new Suggestion();
        suggestion.setType("NEARBY_PLACE");
        suggestion.setMessage("Explore nearby trail: Sunset Peak near " + currentLocation);
        return List.of(suggestionMapper.toResponse(suggestion));
    }
}