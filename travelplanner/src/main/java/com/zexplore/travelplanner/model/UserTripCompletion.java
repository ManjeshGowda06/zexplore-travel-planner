package com.zexplore.travelplanner.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Table(name = "user_trip_completion",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id","trip_id"}))
public class UserTripCompletion {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "trip_id")
    private Trip trip;

    @Column(name = "completed_at", nullable = false)
    private OffsetDateTime completedAt;
}
