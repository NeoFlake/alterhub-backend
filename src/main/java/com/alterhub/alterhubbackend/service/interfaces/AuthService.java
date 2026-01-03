package com.alterhub.alterhubbackend.service.interfaces;

import com.alterhub.alterhubbackend.dto.AuthResponseDTO;
import com.alterhub.alterhubbackend.dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;

public interface AuthService {

    String getCookieStatut(String refreshToken);

    AuthResponseDTO createAuthResponse(UserDTO userDTO, Boolean accessGranted);

    AuthResponseDTO refreshToken(HttpServletRequest request);

    ResponseCookie logout(String refreshToken);

}
