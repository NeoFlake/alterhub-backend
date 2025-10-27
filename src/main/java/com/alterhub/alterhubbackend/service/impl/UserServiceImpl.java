package com.alterhub.alterhubbackend.service.impl;

import com.alterhub.alterhubbackend.dto.UserDTO;
import com.alterhub.alterhubbackend.entity.User;
import com.alterhub.alterhubbackend.exception.NoResultByIdException;
import com.alterhub.alterhubbackend.mapper.UserMapper;
import com.alterhub.alterhubbackend.repository.UserRepository;
import com.alterhub.alterhubbackend.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public User getUserByIdInternalUsage(UUID id) {
        return userRepository.findById(id).orElseThrow(NoResultByIdException::new);
    }

    public UserDTO getUserById(UUID id) {
        User user = getUserByIdInternalUsage(id);
        return UserMapper.toDto(user);
    }

}
