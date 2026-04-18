package com.pm.userservice.dto;

import com.pm.userservice.model.enums.RoleType;

import java.util.UUID;

public class RoleResponseDTO {
    private UUID id;
    private RoleType role;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }
}
