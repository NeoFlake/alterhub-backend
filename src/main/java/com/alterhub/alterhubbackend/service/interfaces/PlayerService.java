package com.alterhub.alterhubbackend.service.interfaces;

import com.alterhub.alterhubbackend.dto.PlayerDTO;
import com.alterhub.alterhubbackend.entity.Player;
import com.alterhub.alterhubbackend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface PlayerService {

    Page<PlayerDTO> getAllPlayers(Pageable pageable);

    PlayerDTO getPlayerById(UUID id);

    PlayerDTO getPlayerByUserId(UUID id);

    Player getPlayerByNameInternalUsage(String name);

    PlayerDTO getPlayerByName(String name);

    PlayerDTO addPlayer(PlayerDTO playerDTO);

    PlayerDTO setLinkBetweenUserAndPlayer(User user, UUID playerId);

    PlayerDTO unsetUserFromPlayer(UUID userId);

    void verifyPlayerIntegrity(PlayerDTO playerDTO);

    Boolean existsByName(String name);

}
