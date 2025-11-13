package com.alterhub.alterhubbackend.service.interfaces;

import com.alterhub.alterhubbackend.dto.RoleDTO;

import java.util.UUID;

public interface RoleService {

    void addRole(RoleDTO roleDTO);

    void deleteRoleByUserId(UUID id);

    Boolean permissionGrantedForUser(UUID id);

}
