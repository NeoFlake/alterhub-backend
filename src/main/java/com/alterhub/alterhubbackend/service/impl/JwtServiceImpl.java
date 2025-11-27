package com.alterhub.alterhubbackend.service.impl;

import com.alterhub.alterhubbackend.service.interfaces.JwtService;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${security.jwt.secret}")
    private String secretKey;

    @Value("${security.jwt.expiration-seconds:900}")
    private long expirationSeconds;

    private JwtEncoder encoder;
    private JwtDecoder decoder;

    @jakarta.annotation.PostConstruct
    private void init() {
        byte[] keyBytes = secretKey.getBytes();
        SecretKey hmacKey = new SecretKeySpec(keyBytes, "HmacSHAS256");

        this.encoder = new NimbusJwtEncoder(new ImmutableSecret<>(hmacKey));
        this.decoder = NimbusJwtDecoder.withSecretKey(hmacKey).build();
    }

    public String generateToken(UUID userId, boolean accessGranted, Map<String, Object> extraClaims) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(expirationSeconds);

        JwtClaimsSet.Builder claimsBuilder = JwtClaimsSet.builder()
                .subject(userId.toString())
                .issuedAt(now)
                .expiresAt(exp)
                .claim("accessGranted", accessGranted);

        if (extraClaims != null) {
            extraClaims.forEach(claimsBuilder::claim);
        }

        return encoder.encode(JwtEncoderParameters.from(claimsBuilder.build())).getTokenValue();
    }

}
