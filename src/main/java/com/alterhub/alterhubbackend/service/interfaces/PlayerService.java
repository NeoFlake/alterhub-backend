package com.alterhub.alterhubbackend.service.interfaces;

import com.alterhub.alterhubbackend.dto.PlayerDTO;
import com.alterhub.alterhubbackend.entity.Player;
import com.alterhub.alterhubbackend.entity.User;

import java.util.List;
import java.util.UUID;

public interface PlayerService {

    List<PlayerDTO> getAllPlayers();

    PlayerDTO getPlayerById(UUID id);

    PlayerDTO getPlayerByUserId(UUID id);

    PlayerDTO getPlayerByName(String name);

    PlayerDTO addPlayer(PlayerDTO playerDTO);

    PlayerDTO setLinkBetweenUserAndPlayer(User user, UUID playerId);

    PlayerDTO unsetUserFromPlayer(UUID userId);

    void verifyPlayerIntegrity(PlayerDTO playerDTO);

    Boolean existsByName(String name);

    void validatePlayer(PlayerDTO playerDTO);

    PlayerDTO mapPlayerDTOWithSubObject(Player player);

    List<PlayerDTO> mapPlayersDTOWithSubObject(List<Player> players);

    Player mapPlayerWithSubObject(PlayerDTO playerDTO);

    List<Player> mapPlayersWithSubObject(List<PlayerDTO> playersDTO);

}
