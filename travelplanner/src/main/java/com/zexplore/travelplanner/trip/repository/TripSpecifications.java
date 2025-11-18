package com.zexplore.travelplanner.trip.repository;

import com.zexplore.travelplanner.model.Trip;
import com.zexplore.travelplanner.model.enums.Difficulty;
import com.zexplore.travelplanner.model.enums.Season;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class TripSpecifications {

    public static Specification<Trip> locationContains(String q) {
        return (root, cq, cb) -> q == null ? null :
                cb.like(cb.lower(root.get("location")), "%" + q.toLowerCase() + "%");
    }

    public static Specification<Trip> difficultyIs(Difficulty d) {
        return (root, cq, cb) -> d == null ? null : cb.equal(root.get("difficulty"), d);
    }

    public static Specification<Trip> seasonIs(Season s) {
        return (root, cq, cb) -> s == null ? null : cb.equal(root.get("season"), s);
    }

    public static Specification<Trip> startDateBetween(LocalDate from, LocalDate to) {
        return (root, cq, cb) -> {
            if (from == null && to == null) return null;
            if (from != null && to != null) return cb.between(root.get("startDate"), from, to);
            return from != null ? cb.greaterThanOrEqualTo(root.get("startDate"), from)
                    : cb.lessThanOrEqualTo(root.get("startDate"), to);
        };
    }
}

