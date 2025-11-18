package com.zexplore.travelplanner.trip.repository;

import com.zexplore.travelplanner.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long>, JpaSpecificationExecutor<Trip> {}
