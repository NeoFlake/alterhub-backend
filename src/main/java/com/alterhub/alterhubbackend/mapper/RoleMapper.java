package com.alterhub.alterhubbackend.mapper;

import com.alterhub.alterhubbackend.dto.RoleDTO;
import com.alterhub.alterhubbackend.entity.Role;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RoleMapper {

    public RoleDTO toDto(Role role){
        if(role == null) return null;

        return RoleDTO.builder()
                .id(role.getId())
                .userId(role.getUserId())
                .role(role.getRole())
                .build();
    }

    public Role toEntity(RoleDTO roleDTO){
        if(roleDTO == null) return null;

        return Role.builder()
                .id(roleDTO.getId())
                .userId(roleDTO.getUserId())
                .role(roleDTO.getRole())
                .build();
    }

}
