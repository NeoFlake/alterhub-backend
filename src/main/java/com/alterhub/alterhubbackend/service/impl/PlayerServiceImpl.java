package com.alterhub.alterhubbackend.service.impl;

import com.alterhub.alterhubbackend.dto.PlayerDTO;
import com.alterhub.alterhubbackend.entity.Player;
import com.alterhub.alterhubbackend.entity.User;
import com.alterhub.alterhubbackend.exception.BadRequestException;
import com.alterhub.alterhubbackend.exception.NoResultByIdException;
import com.alterhub.alterhubbackend.mapper.*;
import com.alterhub.alterhubbackend.mapping.MappingService;
import com.alterhub.alterhubbackend.repository.PlayerRepository;
import com.alterhub.alterhubbackend.repository.UserRepository;
import com.alterhub.alterhubbackend.service.interfaces.*;
import lombok.RequiredArgsConstructor;
import org.owasp.encoder.Encode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final UserRepository userRepository;
    private final MappingService mappingService;

    public Page<PlayerDTO> getAllPlayers(Pageable pageable) {
        Page<Player> playerPage = playerRepository.findAll(pageable);
        List<PlayerDTO> playersDTO = mappingService.mapPlayersDTOWithSubObject(playerPage.getContent());
        return new PageImpl<>(playersDTO, pageable, playerPage.getTotalElements());
    }

    public PlayerDTO getPlayerById(UUID id) {
        return mappingService.mapPlayerDTOWithSubObject(playerRepository.findById(id).orElseThrow(NoResultByIdException::new));
    }

    public Player getPlayerByNameInternalUsage(String name) {
        if(name == null || name.isEmpty()){
            throw new BadRequestException();
        }
        name = Encode.forHtml(name);

        return playerRepository.findByName(name).orElseThrow(NoResultByIdException::new);
    }

    public PlayerDTO getPlayerByName(String name) {
        return mappingService.mapPlayerDTOWithSubObject(getPlayerByNameInternalUsage(name));
    }

    public PlayerDTO getPlayerByUserId(UUID id) {
        return mappingService.mapPlayerDTOWithSubObject(playerRepository.findByUser_Id(id).orElseThrow(NoResultByIdException::new));
    }

    public PlayerDTO addPlayer(PlayerDTO playerDTO) {
        verifyPlayerIntegrity(playerDTO);

        User user = playerDTO.getUserId() != null
                ? userRepository.findById(playerDTO.getUserId()).orElseThrow()
                : null;
        // N'oublions pas que l'on créé un nouveau Player, il n'a aucune participation ni aucun deck en l'état...
        return mappingService.mapPlayerDTOWithSubObject(playerRepository.save(PlayerMapper.toEntity(playerDTO, new ArrayList<>(), new ArrayList<>(), user)));
    }

    public PlayerDTO setLinkBetweenUserAndPlayer(User user, UUID playerId) {
        Player playerToLink = playerRepository.findById(playerId).orElseThrow(NoResultByIdException::new);
        playerToLink.setUser(user);
        return mappingService.mapPlayerDTOWithSubObject(playerRepository.save(playerToLink));
    }

    public PlayerDTO unsetUserFromPlayer(UUID userId) {
        Player playerToUnlink = playerRepository.findByUser_Id(userId).orElseThrow(NoResultByIdException::new);
        playerToUnlink.setUser(null);
        return mappingService.mapPlayerDTOWithSubObject(playerRepository.save(playerToUnlink));
    }

    public void verifyPlayerIntegrity(PlayerDTO playerDTO){
        if(playerDTO.getName() == null || playerDTO.getName().isEmpty()){
            throw new BadRequestException();
        }

    }

    public Boolean existsByName(String name) {
        return playerRepository.existsByName(name);
    }

}
