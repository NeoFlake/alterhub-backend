package com.alterhub.alterhubbackend.repository;

import com.alterhub.alterhubbackend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {

    Boolean existsByUserIdAndRole(UUID userId, com.alterhub.alterhubbackend.enums.Role role);
}
