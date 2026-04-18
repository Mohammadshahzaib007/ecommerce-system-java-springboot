package com.pm.userservice.repository;

import com.pm.userservice.model.Role;
import com.pm.userservice.model.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    boolean existsByRole(RoleType roleType);
}
