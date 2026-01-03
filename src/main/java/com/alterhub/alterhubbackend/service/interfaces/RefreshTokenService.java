package com.alterhub.alterhubbackend.service.interfaces;

import com.alterhub.alterhubbackend.entity.RefreshToken;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenService {

    String createRefreshToken(UUID userId);

    UUID validate(String token);

    void revokedByToken(String token);

    void deleteRefreshToken(UUID userId);

    Optional<RefreshToken> findByToken(String token);

}
