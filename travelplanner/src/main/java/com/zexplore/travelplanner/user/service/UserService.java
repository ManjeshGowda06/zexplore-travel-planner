package com.zexplore.travelplanner.user.service;

import com.zexplore.travelplanner.model.User;
import com.zexplore.travelplanner.model.enums.Role;
import com.zexplore.travelplanner.user.dto.SignupRequest;
import com.zexplore.travelplanner.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.zexplore.travelplanner.exception.UserAlreadyExistsException;
import org.apache.commons.lang3.RandomStringUtils;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists: " + user.getEmail());
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setReferralCode(generateReferralCode());
        return userRepository.save(user);
    }

    private String generateReferralCode() {
        String code;
        do {
            code = "ZEXP-" + RandomStringUtils.randomAlphanumeric(6).toUpperCase();
        } while (userRepository.findByReferralCode(code).isPresent());
        return code;
    }


    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    public User updateUser(Long id, SignupRequest request) {
        User user = getUserById(id);
        if (request.getName() != null) user.setName(request.getName());
        if (request.getEmail() != null) user.setEmail(request.getEmail());
        if (request.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        if (request.getRole() != null && user.getRole() != Role.USER) {
            user.setRole(request.getRole());
        }
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }

}