package com.alterhub.alterhubbackend.service.interfaces;

import com.alterhub.alterhubbackend.dto.UserDTO;
import com.alterhub.alterhubbackend.dto.UserRequestDTO;
import com.alterhub.alterhubbackend.entity.User;

import java.util.UUID;

public interface UserService {

    User getUserByIdInternalUsage(UUID id);

    UserDTO getUserById(UUID id);

    void verifyUserIntegrity(UserDTO userDTO);

    void verifyUserRequestIntegrity(UserRequestDTO userRequestDTO);

}
