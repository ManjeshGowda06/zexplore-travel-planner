package com.zexplore.travelplanner.suggestion.repository;

import com.zexplore.travelplanner.model.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {

    List<Suggestion> findByUser_Id(Long userId);

    List<Suggestion> findByType(String type);
}
