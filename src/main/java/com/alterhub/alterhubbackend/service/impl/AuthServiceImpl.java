package com.alterhub.alterhubbackend.service.impl;

import com.alterhub.alterhubbackend.constant.ReturnMessages;
import com.alterhub.alterhubbackend.dto.AuthPayloadDTO;
import com.alterhub.alterhubbackend.dto.AuthResponseDTO;
import com.alterhub.alterhubbackend.dto.UserDTO;
import com.alterhub.alterhubbackend.entity.User;
import com.alterhub.alterhubbackend.exception.NoResultByIdException;
import com.alterhub.alterhubbackend.mapper.UserMapper;
import com.alterhub.alterhubbackend.repository.RoleRepository;
import com.alterhub.alterhubbackend.repository.UserRepository;
import com.alterhub.alterhubbackend.service.interfaces.AuthService;
import com.alterhub.alterhubbackend.service.interfaces.JwtService;
import com.alterhub.alterhubbackend.service.interfaces.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public AuthResponseDTO createAuthResponse(UserDTO userDTO, Boolean accessGranted){
        Map<String, Object> extraClaims = new HashMap<>();

        String accessToken = jwtService.generateToken(userDTO.getId(), accessGranted, extraClaims);

        String refreshToken = refreshTokenService.createRefreshToken(userDTO.getId());

        ResponseCookie cookie = ResponseCookie.from(ReturnMessages.REFRESH_TOKEN_COOKIE_NAME, refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofDays(14))
                .build();

        AuthPayloadDTO payloadDTO = AuthPayloadDTO.builder()
                .accessToken(accessToken)
                .userDTO(userDTO)
                .build();

        return AuthResponseDTO.builder()
                .authentificationInfos(payloadDTO)
                .refreshTokenCookie(cookie)
                .build();

    }

    public AuthResponseDTO refreshToken(String refreshToken) {
        UUID userIdFromValidToken = refreshTokenService.validate(refreshToken);

        User user = userRepository.findById(userIdFromValidToken).orElseThrow(NoResultByIdException::new);

        refreshTokenService.revokedByToken(refreshToken);

        String newRefreshToken = refreshTokenService.createRefreshToken(user.getId());

        ResponseCookie cookie = ResponseCookie.from(ReturnMessages.REFRESH_TOKEN_COOKIE_NAME, newRefreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofDays(14))
                .build();

        Boolean accessGranted = roleRepository.existsByUserIdAndRole(user.getId(), com.alterhub.alterhubbackend.enums.Role.ADMIN);

        String accessToken = jwtService.generateToken(user.getId(), accessGranted, null);

        AuthPayloadDTO payloadDTO = AuthPayloadDTO.builder()
                .accessToken(accessToken)
                .userDTO(UserMapper.toDto(user))
                .build();

        return AuthResponseDTO.builder()
                .authentificationInfos(payloadDTO)
                .refreshTokenCookie(cookie)
                .build();

    }

    public ResponseCookie logout(String refreshToken) {
        UUID userId = refreshTokenService.validate(refreshToken);

        refreshTokenService.deleteRefreshToken(userId);
        return ResponseCookie.from(ReturnMessages.REFRESH_TOKEN_COOKIE_NAME, "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .build();
    }

}
