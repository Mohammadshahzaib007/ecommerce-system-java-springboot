package com.pm.userservice.controller;

import com.pm.userservice.dto.RoleRequestDTO;
import com.pm.userservice.dto.RoleResponseDTO;
import com.pm.userservice.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/roles")
@Tag(name = "Role Management", description = "End point for managing user roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    @Operation(summary = "Get all roles", description = "Retrieves a list of all roles")
    public ResponseEntity<List<RoleResponseDTO>> findAll() {
        List<RoleResponseDTO> roles = roleService.findAllRoles();
        return ResponseEntity.ok().body(roles);
    }

    @PostMapping
    @Operation(summary = "Create a new role", description = "Creates a new role with the specified type")
    public ResponseEntity<RoleResponseDTO> saveRole(@Valid @RequestBody RoleRequestDTO roleRequestDTO) {
        RoleResponseDTO roleResponseDTO = roleService.createRole(roleRequestDTO);
        return ResponseEntity.ok().body(roleResponseDTO);
    }
}
