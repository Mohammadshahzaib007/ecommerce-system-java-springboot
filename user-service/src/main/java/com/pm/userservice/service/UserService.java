package com.pm.userservice.service;

import com.pm.userservice.dto.UserRequestDTO;
import com.pm.userservice.dto.UserResponseDTO;
import com.pm.userservice.dto.UserUpdateRequestDTO;
import com.pm.userservice.exception.EmailAlreadyExistsException;
import com.pm.userservice.exception.RoleDoesNotExistException;
import com.pm.userservice.exception.UserWithThisIdDoesNotExistsException;
import com.pm.userservice.mapper.UserMapper;
import com.pm.userservice.model.Role;
import com.pm.userservice.model.User;
import com.pm.userservice.repository.RoleRepository;
import com.pm.userservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        log.info("Creating user with email: {}", userRequestDTO.getEmail());

        if (userRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException(
                    "User with email " + userRequestDTO.getEmail() + " already exists"
            );
        }

        Role role = roleRepository.findByRole(userRequestDTO.getRole())
                .orElseThrow(() -> new RoleDoesNotExistException(
                        "This role " + userRequestDTO.getRole() + " does not exist"
                ));

        String hashedPassword = passwordEncoder.encode(userRequestDTO.getPassword());
        User user = UserMapper.toEntity(userRequestDTO, role);
        user.setPassword(hashedPassword);

        User savedUser = userRepository.save(user);

        log.info("User created with id: {}", savedUser.getId());

        return UserMapper.toResponseDTO(savedUser);
    }

    public List<UserResponseDTO> findAllUsers() {
        log.info("Finding all users");
        List<User> users = userRepository.findAll();
        return users.stream().map(UserMapper::toResponseDTO).toList();
    }

    public UserResponseDTO findUserById(UUID id) {
        log.info("Fetching user by id");

        User user = userRepository.findById(id).orElseThrow(
                () -> new UserWithThisIdDoesNotExistsException("User with this id does not exist " + id));

        return UserMapper.toResponseDTO(user);
    }

    @Transactional
    public void deleteUserById(UUID id) {
        log.info("Deleting user by id");
        if (!userRepository.existsById(id)) {
            throw new UserWithThisIdDoesNotExistsException("User with this id does not exist " + id);
        }
        userRepository.deleteById(id);
    }

    @Transactional
    public UserResponseDTO updateUser(UUID id, UserUpdateRequestDTO userUpdateRequestDTO) {
        log.info("Updating user by id");
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserWithThisIdDoesNotExistsException("User not found"));

        if (userUpdateRequestDTO.getEmail() != null && userRepository.existsByEmailAndIdNot(userUpdateRequestDTO.getEmail(), id)) {
            throw new EmailAlreadyExistsException("Email already in use");
        }

        if (userUpdateRequestDTO.getName() != null) user.setName(userUpdateRequestDTO.getName());
        if (userUpdateRequestDTO.getEmail() != null) user.setEmail(userUpdateRequestDTO.getEmail());

        if (userUpdateRequestDTO.getRole() != null) {
            Role role = roleRepository.findByRole(userUpdateRequestDTO.getRole())
                    .orElseThrow(() -> new RoleDoesNotExistException("Role not found"));
            user.setRole(role);
        }

        return UserMapper.toResponseDTO(userRepository.save(user));
//        if (userRepository.existsByEmail(userRequestDTO.getEmail())) {
//            throw new EmailAlreadyExistsException(
//                    "User with email " + userRequestDTO.getEmail() + " already exists"
//            );
//        }
//
//        User user = userRepository.findById(id)
//                .orElseThrow(() -> new UserWithThisIdDoesNotExistsException(
//                        "User with this id does not exist " + id
//                ));
//
//        Role role = null;
//        if (userRequestDTO.getRole() != null) {
//            role = roleRepository.findByRole(userRequestDTO.getRole())
//                    .orElseThrow(() -> new RoleDoesNotExistException(
//                            "This role " + userRequestDTO.getRole() + " does not exist"
//                    ));
//        }
//
//
//        user.setName(userRequestDTO.getName());
//        user.setEmail(userRequestDTO.getEmail());
//        if (role != null) {
//            user.setRole(role);
//        }
//
//        return UserMapper.toResponseDTO(userRepository.save(user));
    }
}
