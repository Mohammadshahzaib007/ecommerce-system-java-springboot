package com.pm.userservice.controller;

import com.pm.userservice.dto.common.PagedResponse;
import com.pm.userservice.dto.UserRequestDTO;
import com.pm.userservice.dto.UserResponseDTO;
import com.pm.userservice.dto.UserUpdateRequestDTO;
import com.pm.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "End point for managing users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Create a new user")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO userResponseDTO = userService.createUser(userRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);
    }

    // Add pagination
    @GetMapping
    @Operation(summary = "Get all users")
    public ResponseEntity<PagedResponse<UserResponseDTO>> findAllUsers(@RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "10") int size) {
        Page<UserResponseDTO> users = userService.findAllUsers(page, size);
        return ResponseEntity.ok().body(new PagedResponse<>(users));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a user by id")
    public ResponseEntity<UserResponseDTO> findUserById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(userService.findUserById(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user by id")
    public ResponseEntity<Void> deleteUserById(@PathVariable UUID id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user by id")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable UUID id,
            @Valid @RequestBody UserUpdateRequestDTO userUpdateRequestDTO) {
        UserResponseDTO userResponseDTO = userService.updateUser(id, userUpdateRequestDTO);
        return ResponseEntity.ok(userResponseDTO);
    }
}
