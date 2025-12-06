package com.alterhub.alterhubbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthPayloadDTO {

    // Le jeton jwt correctement formaté
    private String accessToken;
    // On inclus le userDTO historiquement présent dans notre retour
    private UserDTO user;

}
