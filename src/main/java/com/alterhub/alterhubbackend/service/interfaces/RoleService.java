package com.alterhub.alterhubbackend.service.interfaces;

import com.alterhub.alterhubbackend.dto.RoleDTO;

import java.util.UUID;

public interface RoleService {

    RoleDTO addRole(RoleDTO roleDTO);

    RoleDTO updateRoleById(UUID id, RoleDTO roleDTO);

    void deleteRoleByUserId(UUID id);

    Boolean permissionGrantedForUser(UUID id);

}
