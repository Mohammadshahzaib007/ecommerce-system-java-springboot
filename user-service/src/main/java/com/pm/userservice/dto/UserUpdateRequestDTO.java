package com.pm.userservice.dto;

import com.pm.userservice.model.enums.RoleType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class UserUpdateRequestDTO {
    @Size(min = 2, max = 50)
    private String name;

    @Email
    private String email;

    private RoleType role;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }
}
