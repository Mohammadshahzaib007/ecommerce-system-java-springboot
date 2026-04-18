package com.pm.userservice.mapper;

import com.pm.userservice.dto.RoleRequestDTO;
import com.pm.userservice.dto.RoleResponseDTO;
import com.pm.userservice.model.Role;

public class RoleMapper {

    public static Role toEntity(RoleRequestDTO roleRequestDTO) {
        Role role = new Role();
        role.setRole(roleRequestDTO.getRole());
        return role;
    }

    public static RoleResponseDTO toResponseDTO(Role role) {
        RoleResponseDTO roleResponseDTO = new RoleResponseDTO();
        roleResponseDTO.setRole(role.getRole());
        roleResponseDTO.setId(role.getId());
        return roleResponseDTO;
    }
}
