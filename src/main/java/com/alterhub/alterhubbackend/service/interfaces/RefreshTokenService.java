package com.alterhub.alterhubbackend.service.interfaces;

import java.util.UUID;

public interface RefreshTokenService {

    String createRefreshToken(UUID userId);

    UUID validate(String token);

    void revokedByToken(String token);

    void deleteRefreshToken(UUID userId);

}
