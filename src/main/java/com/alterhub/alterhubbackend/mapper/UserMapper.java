package com.alterhub.alterhubbackend.mapper;

import com.alterhub.alterhubbackend.dto.UserDTO;
import com.alterhub.alterhubbackend.dto.UserRequestDTO;
import com.alterhub.alterhubbackend.entity.Player;
import com.alterhub.alterhubbackend.entity.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {

    public UserDTO toDto(User user){
        if (user == null) return null;

        return UserDTO.builder()
                .id(user.getId())
                .lastName(user.getLastname())
                .firstName(user.getFirstname())
                .playerName(user.getPlayer().getName())
                .email(user.getEmail())
                .dateOfCreation(user.getDateOfCreation())
                .lastModification(user.getLastModification())
                .build();
    }

    public User toEntity(UserRequestDTO userRequestDTO, Player player){
        if (userRequestDTO == null) return null;

        return User.builder()
                .id(userRequestDTO.getId())
                .lastname(userRequestDTO.getLastName())
                .firstname(userRequestDTO.getFirstName())
                .player(player)
                .email(userRequestDTO.getEmail())
                .password(userRequestDTO.getPassword())
                .dateOfCreation(userRequestDTO.getDateOfCreation())
                .lastModification(userRequestDTO.getLastModification())
                .build();
    }

}
