package com.zexplore.travelplanner.location.dto;

import lombok.Data;

@Data
public class LocationDTO {
    private Long userId;
    private Long tripId;
    private double latitude;
    private double longitude;
}
