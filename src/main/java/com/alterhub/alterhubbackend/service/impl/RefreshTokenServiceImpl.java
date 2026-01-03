package com.alterhub.alterhubbackend.service.impl;

import com.alterhub.alterhubbackend.constant.ErrorMessages;
import com.alterhub.alterhubbackend.entity.RefreshToken;
import com.alterhub.alterhubbackend.exception.ExpiredRefreshTokenException;
import com.alterhub.alterhubbackend.repository.RefreshTokenRepository;
import com.alterhub.alterhubbackend.service.interfaces.RefreshTokenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    private final SecureRandom secureRandom = new SecureRandom();

    @Value("${security.refresh-token.duration-seconds:1209600}")
    private Long refreshTokenDurationSeconds;

    private String generateSecureToken() {
        byte[] bytes = new byte[48];
        secureRandom.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    @Transactional
    public String createRefreshToken(UUID userId) {
        // Supprime l'ancien jeton (si existant) ce qui garanti un jeton unique par utilisateur
        refreshTokenRepository.deleteByUserId(userId);

        String token;
        int attempts = 0;
        // Génération du jeton avec vérification de duplication de l'identifiant (improbable mais permet
        // de bloquer toutes les failles possible
        do {
            token = generateSecureToken();
            attempts++;

            // L'improbabilité c'est produite, alors on projette une erreur et on bloque tout
            if(attempts > 5){
                throw new IllegalStateException(ErrorMessages.CREATE_AUTH_TOKEN_TRY_OVERFLOW);
            }
        } while(refreshTokenRepository.findByToken(token).isPresent());

        refreshTokenRepository.save(RefreshToken.builder()
                .userId(userId)
                .token(token)
                .expiresAt(Instant.now().plusSeconds(refreshTokenDurationSeconds))
                .revoked(false)
                .build());

        return token;
    }

    @Transactional
    public UUID validate(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token).orElseThrow(ExpiredRefreshTokenException::new);
        verifyTokenRevocationStatus(refreshToken);

        return refreshToken.getUserId();
    }

    @Transactional
    public void revokedByToken(String token) {
        refreshTokenRepository.findByToken(token).ifPresent((RefreshToken refreshToken) -> {
           refreshToken.setRevoked(true);
           refreshTokenRepository.save(refreshToken);
        });
    }

    @Transactional
    public void deleteRefreshToken(UUID userId){
        refreshTokenRepository.deleteByUserId(userId);
    }

    @Transactional
    public String rotateRefreshToken(String oldToken){
        RefreshToken existing = refreshTokenRepository.findByToken(oldToken).orElseThrow(ExpiredRefreshTokenException::new);

        verifyTokenRevocationStatus(existing);

        UUID userId = existing.getUserId();
        refreshTokenRepository.delete(existing);
        return createRefreshToken(userId);

    }

    private void verifyTokenRevocationStatus(RefreshToken token){
        if(token.getRevoked() || token.getExpiresAt().isBefore(Instant.now())){
            refreshTokenRepository.delete(token);
            throw new ExpiredRefreshTokenException();
        }
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

}
