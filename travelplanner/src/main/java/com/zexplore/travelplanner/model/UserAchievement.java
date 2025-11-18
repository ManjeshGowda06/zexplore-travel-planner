package com.zexplore.travelplanner.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
public class UserAchievement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    private Long userId;
//    private Long achievementId;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "achievement_id")
    private Achievement achievement;


    @Column(name = "awarded_at")
    private LocalDateTime awardedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Achievement getAchievement() {
        return achievement;
    }

    public void setAchievement(Achievement achievement) {
        this.achievement = achievement;
    }

    public LocalDateTime getAwardedAt() {
        return awardedAt;
    }

    public void setAwardedAt(LocalDateTime awardedAt) {
        this.awardedAt = awardedAt;
    }
}
