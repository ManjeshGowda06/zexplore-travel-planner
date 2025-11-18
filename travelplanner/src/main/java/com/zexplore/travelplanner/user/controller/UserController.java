package com.zexplore.travelplanner.user.controller;


import com.zexplore.travelplanner.exception.UserAlreadyExistsException;

import com.zexplore.travelplanner.model.enums.Role;
import com.zexplore.travelplanner.user.dto.SignupRequest;
import com.zexplore.travelplanner.user.dto.UserDTO;
import com.zexplore.travelplanner.model.User;
import com.zexplore.travelplanner.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public UserDTO signup(@RequestBody SignupRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(Role.USER); // Enum assignment
        try {
            User savedUser = userService.registerUser(user);
            return new UserDTO(savedUser.getName(), savedUser.getEmail(), savedUser.getRole().name(),savedUser.getReferralCode());
        } catch (UserAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }


    // Admin-only endpoint for creating ADMIN/GUIDE
    @PostMapping("/admin/create")
    @PreAuthorize("hasRole('ADMIN')")
    public UserDTO createAdminOrGuide(@RequestBody SignupRequest request) {
        if (request.getRole() == null || request.getRole() == Role.USER) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role must be ADMIN or GUIDE");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());

        try {
            User savedUser = userService.registerUser(user);
            return new UserDTO(savedUser.getName(), savedUser.getEmail(), savedUser.getRole().name(), savedUser.getReferralCode());
        } catch (UserAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }


    // ✅ Fetch user by ID (Admin or self)
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public UserDTO getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return new UserDTO(user.getName(), user.getEmail(), user.getRole().name(), user.getReferralCode());
    }


    // ✅ Update user details (self or Admin)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public UserDTO updateUser(@PathVariable Long id, @RequestBody SignupRequest request) {
        User updatedUser = userService.updateUser(id, request);
        return new UserDTO(updatedUser.getName(), updatedUser.getEmail(), updatedUser.getRole().name(), updatedUser.getReferralCode());
    }


    // ✅ Delete user (Admin only)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}

