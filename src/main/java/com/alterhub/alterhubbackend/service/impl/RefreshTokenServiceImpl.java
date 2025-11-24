package com.alterhub.alterhubbackend.service.impl;

import com.alterhub.alterhubbackend.entity.RefreshToken;
import com.alterhub.alterhubbackend.exception.ExpiredRefreshTokenException;
import com.alterhub.alterhubbackend.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken createRefreshToken(UUID userId) {

        // Permet d'éviter le multiples refresh token sur un user unique
        refreshTokenRepository.deleteByUserId(userId);

        return refreshTokenRepository.save(RefreshToken.builder()
                .userId(userId)
                .token(UUID.randomUUID().toString())
                .expiresAt(Instant.now().plusSeconds(60 * 60 * 24 * 14)) // Token persistant sur 14 jours
                .revoked(false)
                .build()
        );

    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if(token.getExpiresAt().isBefore(Instant.now()) || token.getRevoked()){
            refreshTokenRepository.delete(token);
            throw new ExpiredRefreshTokenException();
        }
        return token;
    }

}
