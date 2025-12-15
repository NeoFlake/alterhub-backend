package com.alterhub.alterhubbackend.service.interfaces;

import com.alterhub.alterhubbackend.dto.AuthResponseDTO;
import com.alterhub.alterhubbackend.dto.UserAuthenticationDTO;
import com.alterhub.alterhubbackend.dto.UserDTO;
import com.alterhub.alterhubbackend.dto.UserRequestDTO;
import com.alterhub.alterhubbackend.entity.User;

import java.util.UUID;

public interface UserService {

    User getUserByIdInternalUsage(UUID id);

    UserDTO getUserById(UUID id);

    UserDTO addUser(UserRequestDTO userRequestDTO);

    AuthResponseDTO authentication(UserAuthenticationDTO userAuthenticationDTO);

    UserDTO updateUserById(UUID id, UserRequestDTO userRequestDTO);

    Boolean accessGranted(UserDTO userDTO);

    void deleteUserById(UUID id);
}
