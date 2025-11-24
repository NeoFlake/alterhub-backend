package com.alterhub.alterhubbackend.service.interfaces;

import com.alterhub.alterhubbackend.entity.RefreshToken;

import java.util.UUID;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(UUID userId);

    RefreshToken verifyExpiration(RefreshToken token);


}
