package com.alterhub.alterhubbackend.service.impl;

import com.alterhub.alterhubbackend.dto.RoleDTO;
import com.alterhub.alterhubbackend.entity.Role;
import com.alterhub.alterhubbackend.exception.IdNotMatchException;
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

    public RoleDTO addRole(RoleDTO roleDTO) {
        return RoleMapper.toDto(roleRepository.save(RoleMapper.toEntity(roleDTO)));
    }

    public RoleDTO updateRoleById(UUID id, RoleDTO roleDTO) {
        if(roleDTO.getId().equals(id)) {
            Role roleToUpdate = roleRepository.findById(id).orElseThrow(NoResultByIdException::new);
            Role roleUpdated = RoleMapper.toEntity(roleDTO);

            roleToUpdate.setUserId(roleUpdated.getUserId());
            roleToUpdate.setRole(roleUpdated.getRole());
            return RoleMapper.toDto(roleRepository.save(roleToUpdate));

        } else {
            throw new IdNotMatchException();
        }
    }

    public void deleteRoleByUserId(UUID id) {
        if(!roleRepository.existsByUserId(id)) {
            throw new NoResultByIdException();
        }
        roleRepository.deleteByUserId(id);
    }

}
