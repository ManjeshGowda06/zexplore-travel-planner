package com.zexplore.travelplanner.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zexplore.travelplanner.model.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "`user`")  // Use backticks to escape the reserved keyword
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    @JsonIgnore
    private String password;
//    private String role; // USER, ADMIN, GUIDE


    @Enumerated(EnumType.STRING)
    private Role role;


    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Booking> bookings;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Memory> memories;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<ProgressLog> progressLogs;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<UserAchievement> userAchievements;


    @ElementCollection
    private Set<String> achievements;


    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(unique = true)
    private String referralCode;

}