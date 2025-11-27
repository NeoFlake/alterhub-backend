package com.alterhub.alterhubbackend.service.interfaces;

import java.util.Map;
import java.util.UUID;

public interface JwtService {

    String generateToken(UUID userId, boolean accessGranted, Map<String, Object> extraClaims);

}
