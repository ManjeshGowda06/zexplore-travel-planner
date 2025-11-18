package com.zexplore.travelplanner.analytics.mapper;

import com.zexplore.travelplanner.analytics.dto.AnalyticsSummaryResponse;
import com.zexplore.travelplanner.model.AnalyticsSummary;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface AnalyticsMapper {
    AnalyticsSummaryResponse toDto(AnalyticsSummary summary);
}

