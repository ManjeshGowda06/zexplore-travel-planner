package com.zexplore.travelplanner.suggestion.service;

import com.zexplore.travelplanner.suggestion.dto.SuggestionRequest;
import com.zexplore.travelplanner.suggestion.dto.SuggestionResponse;

import java.util.List;

public interface SuggestionService {
    List<SuggestionResponse> getWeatherAlerts(String location);
    List<SuggestionResponse> getPackingList(String difficulty);
    List<SuggestionResponse> getNearbyPlaces(String currentLocation);
    SuggestionResponse saveSuggestion(SuggestionRequest request);
}

