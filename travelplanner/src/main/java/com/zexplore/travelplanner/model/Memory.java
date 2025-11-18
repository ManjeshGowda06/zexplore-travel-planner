package com.zexplore.travelplanner.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zexplore.travelplanner.model.enums.MediaType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "memories",
        indexes = {
                @Index(name = "idx_memory_user", columnList = "user_id"),
                @Index(name = "idx_memory_trip", columnList = "trip_id"),
                @Index(name = "idx_memory_date", columnList = "date"),
                @Index(name = "idx_memory_location", columnList = "location")
        })
public class Memory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "media_url", nullable = false, length = 512)
    private String mediaUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "media_type", nullable = false, length = 16)
    private MediaType mediaType; // IMAGE or VIDEO

    @Column(nullable = false)
    private LocalDate date;

    @Column(length = 120)
    private String location;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "trip_id", nullable = false)
    @JsonIgnore
    private Trip trip;

    @Version
    private Long version;

    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;

    @Column(name = "deleted_by")
    private Long deletedBy;
}