package com.pm.userservice.service;

import com.pm.userservice.dto.RoleRequestDTO;
import com.pm.userservice.dto.RoleResponseDTO;
import com.pm.userservice.exception.RoleAlreadyExistsException;
import com.pm.userservice.mapper.RoleMapper;
import com.pm.userservice.model.Role;
import com.pm.userservice.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;


    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;

    }

    public List<RoleResponseDTO> findAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(RoleMapper::toResponseDTO).toList();
    }

    public RoleResponseDTO createRole(RoleRequestDTO roleRequestDTO) {
        if (roleRepository.existsByRole(roleRequestDTO.getRole())) {
            throw new RoleAlreadyExistsException("Role " + roleRequestDTO.getRole() + " already exists");
        }

        Role role = RoleMapper.toEntity(roleRequestDTO);
        Role savedRole = roleRepository.save(role);
        return RoleMapper.toResponseDTO(savedRole);
    }
}
