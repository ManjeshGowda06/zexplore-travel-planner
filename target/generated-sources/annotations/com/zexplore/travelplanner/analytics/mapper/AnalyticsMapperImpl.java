package com.zexplore.travelplanner.analytics.mapper;

import com.zexplore.travelplanner.analytics.dto.AnalyticsSummaryResponse;
import com.zexplore.travelplanner.model.AnalyticsSummary;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-17T12:50:17+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class AnalyticsMapperImpl implements AnalyticsMapper {

    @Override
    public AnalyticsSummaryResponse toDto(AnalyticsSummary summary) {
        if ( summary == null ) {
            return null;
        }

        AnalyticsSummaryResponse analyticsSummaryResponse = new AnalyticsSummaryResponse();

        return analyticsSummaryResponse;
    }
}
