package com.alterhub.alterhubbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

    private UUID id;
    private String lastName;
    private String firstName;
    private String playerName;
    private String email;
    private String password;
    private String newPassword;
    private LocalDate dateOfCreation;
    private LocalDateTime lastModification;

}
