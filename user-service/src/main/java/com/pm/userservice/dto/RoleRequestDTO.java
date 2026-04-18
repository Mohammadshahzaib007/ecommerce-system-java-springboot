package com.pm.userservice.dto;

import com.pm.userservice.model.enums.RoleType;
import jakarta.validation.constraints.NotNull;

public class RoleRequestDTO {
    @NotNull(message = "Role name is required")
    private RoleType role;

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }
}
