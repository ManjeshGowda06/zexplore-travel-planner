package com.zexplore.travelplanner.suggestion.mapper;


import com.zexplore.travelplanner.model.Suggestion;
import com.zexplore.travelplanner.model.User;
import com.zexplore.travelplanner.suggestion.dto.SuggestionRequest;
import com.zexplore.travelplanner.suggestion.dto.SuggestionResponse;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Component
public class SuggestionMapper {

    public Suggestion toEntity(SuggestionRequest request) {
        Suggestion suggestion = new Suggestion();
        suggestion.setType(request.getType());
        suggestion.setMessage(request.getMessage());
        if (request.getUserId() != null) {
            User user = new User();
            user.setId(request.getUserId());
            suggestion.setUser(user);
        }
        return suggestion;
    }

    public SuggestionResponse toResponse(Suggestion suggestion) {
        Long userId = (suggestion.getUser() != null) ? suggestion.getUser().getId() : null;
        return new SuggestionResponse(suggestion.getType(), suggestion.getMessage(), userId);
    }
}