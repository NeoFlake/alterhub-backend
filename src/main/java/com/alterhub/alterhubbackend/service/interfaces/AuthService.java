package com.alterhub.alterhubbackend.service.interfaces;

import com.alterhub.alterhubbackend.dto.AuthResponseDTO;
import com.alterhub.alterhubbackend.dto.UserDTO;
import org.springframework.http.ResponseCookie;

public interface AuthService {

    AuthResponseDTO createAuthResponse(UserDTO userDTO, Boolean accessGranted);

    AuthResponseDTO refreshToken(String refreshToken);

    ResponseCookie logout(String refreshToken);

}
