package com.alterhub.alterhubbackend.service.impl;

import com.alterhub.alterhubbackend.dto.RoleDTO;
import com.alterhub.alterhubbackend.exception.NoResultByIdException;
import com.alterhub.alterhubbackend.mapper.RoleMapper;
import com.alterhub.alterhubbackend.repository.RoleRepository;
import com.alterhub.alterhubbackend.service.interfaces.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public Boolean permissionGrantedForUser(UUID id) {
        return roleRepository.existsByUserIdAndRole(id, com.alterhub.alterhubbackend.enums.Role.ADMIN);
    }

    public void addRole(RoleDTO roleDTO) {
        RoleMapper.toDto(roleRepository.save(RoleMapper.toEntity(roleDTO)));
    }

    public void deleteRoleByUserId(UUID id) {
        if(!roleRepository.existsByUserId(id)) {
            throw new NoResultByIdException();
        }
        roleRepository.deleteByUserId(id);
    }

}
