package com.alterhub.alterhubbackend.repository.projection;

import java.util.UUID;

public interface CardDeckCountProjection {
    UUID getCardId();
    Integer getDeckCount();
}
