package com.zexplore.travelplanner.suggestion.controller;

import com.zexplore.travelplanner.suggestion.dto.SuggestionRequest;
import com.zexplore.travelplanner.suggestion.dto.SuggestionResponse;
import com.zexplore.travelplanner.suggestion.service.SuggestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/suggestions")
@RequiredArgsConstructor
public class SuggestionController {

    private final SuggestionService suggestionService;

    @PostMapping
    public SuggestionResponse saveSuggestion(@RequestBody @Validated SuggestionRequest request) {
        return suggestionService.saveSuggestion(request);
    }

    @GetMapping("/weather-alerts")
    public List<SuggestionResponse> getWeatherAlerts(@RequestParam String location) {
        return suggestionService.getWeatherAlerts(location);
    }

    @GetMapping("/packing-list")
    public List<SuggestionResponse> getPackingList(@RequestParam String difficulty) {
        return suggestionService.getPackingList(difficulty);
    }

    @GetMapping("/nearby")
    public List<SuggestionResponse> getNearbyPlaces(@RequestParam String currentLocation) {
        return suggestionService.getNearbyPlaces(currentLocation);
    }
}
