package com.zexplore.travelplanner.user.repository;

import com.zexplore.travelplanner.model.User;
import com.zexplore.travelplanner.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<User> findByReferralCode(String referralCode);
    boolean existsByRole(Role role);
}
