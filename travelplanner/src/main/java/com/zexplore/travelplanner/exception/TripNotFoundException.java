package com.zexplore.travelplanner.exception;

public class TripNotFoundException extends RuntimeException {
    public TripNotFoundException(Long id) {
        super("Trip with ID " + id + " not found.");
    }
}

