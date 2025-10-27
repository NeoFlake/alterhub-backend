package com.alterhub.alterhubbackend.service.interfaces;

import com.alterhub.alterhubbackend.dto.PlayerDTO;
import com.alterhub.alterhubbackend.entity.Player;

import java.util.List;
import java.util.UUID;

public interface PlayerService {

    PlayerDTO getPlayerById(UUID id);

    PlayerDTO mapPlayerDTOWithSubObject(Player player);

    List<PlayerDTO> mapPlayersDTOWithSubObject(List<Player> players);

    Player mapPlayerWithSubObject(PlayerDTO playerDTO);

    List<Player> mapPlayersWithSubObject(List<PlayerDTO> playersDTO);

}
