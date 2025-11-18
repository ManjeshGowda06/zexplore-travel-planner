package com.zexplore.travelplanner.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zexplore.travelplanner.model.enums.Difficulty;
import com.zexplore.travelplanner.model.enums.Season;
import com.zexplore.travelplanner.model.enums.TripType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Where(clause = "deleted_at IS NULL")
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "trips",
        indexes = {
                @Index(name = "idx_trip_location", columnList = "location"),
                @Index(name = "idx_trip_start_date", columnList = "start_date"),
                @Index(name = "idx_trip_difficulty", columnList = "difficulty")
        })
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 160)
    private String title;

    @Column(nullable = false, length = 120)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Difficulty difficulty;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Season season;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;


    @Enumerated(EnumType.STRING)
    @Column(name = "trip_type", length = 30)
    private TripType tripType;


    @Column(length = 500)
    private String highlights;

    private Boolean guideRequired;

    @Column(length = 100)
    private String terrainType;


    @Column(length = 100)
    private String vehicleType;


    // optional: capacity/pricing/status fields for booking consistency
    // private Integer maxCapacity;
    // private Integer price;

    @Version
    private Long version;

    @OneToMany(mappedBy = "trip", fetch = FetchType.LAZY) // no cascade to avoid accidental deletes
    @JsonIgnore
    @Builder.Default
    private List<Booking> bookings = new ArrayList<>();

    @OneToMany(mappedBy = "trip", fetch = FetchType.LAZY)
    @JsonIgnore
    @Builder.Default
    private List<Memory> memories = new ArrayList<>();

    @OneToMany(mappedBy = "trip", fetch = FetchType.LAZY)
    @JsonIgnore
    @Builder.Default
    private List<ProgressLog> progressLogs = new ArrayList<>();

    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;

    @Column(name = "deleted_by")
    private Long deletedBy;

}