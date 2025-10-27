package com.alterhub.alterhubbackend.service.impl;

import com.alterhub.alterhubbackend.dto.UserDTO;
import com.alterhub.alterhubbackend.dto.UserRequestDTO;
import com.alterhub.alterhubbackend.entity.User;
import com.alterhub.alterhubbackend.exception.BadRequestException;
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

    public void verifyUserIntegrity(UserDTO userDTO) {
        if (userDTO.getLastName() == null || userDTO.getLastName().isEmpty()
        || userDTO.getFirstName() == null || userDTO.getFirstName().isEmpty()
        || userDTO.getEmail() == null || userDTO.getEmail().isEmpty()
        || userDTO.getPlayerName() == null || userDTO.getPlayerName().isEmpty()
        || userDTO.getDateOfCreation() == null || userDTO.getLastModification() == null ) {
            throw new BadRequestException();
        }
    }

    public void verifyUserRequestIntegrity(UserRequestDTO userRequestDTO) {
        if(userRequestDTO.getLastName() == null || userRequestDTO.getLastName().isEmpty()
        || userRequestDTO.getFirstName() == null || userRequestDTO.getFirstName().isEmpty()
        || userRequestDTO.getEmail() == null || userRequestDTO.getEmail().isEmpty()
        || userRequestDTO.getPlayerName() == null || userRequestDTO.getPlayerName().isEmpty()
        || userRequestDTO.getDateOfCreation() == null || userRequestDTO.getLastModification() == null
        || userRequestDTO.getPassword() == null || userRequestDTO.getPassword().isEmpty()
        || (userRequestDTO.getNewPassword() != null && userRequestDTO.getNewPassword().isEmpty())){
            throw new BadRequestException();
        }
    }

}
