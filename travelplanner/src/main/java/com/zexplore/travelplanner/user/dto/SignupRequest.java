package com.zexplore.travelplanner.user.dto;

import com.zexplore.travelplanner.model.enums.Role;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class  SignupRequest {
    private String name;
    private String email;

    @Size(min = 8, message = "Password must be at least 8 characters")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).+$", message = "Password must contain at least one uppercase letter and one number")
    private String password;


    // Optional role field (used only in admin endpoint)
    private Role role;


}
