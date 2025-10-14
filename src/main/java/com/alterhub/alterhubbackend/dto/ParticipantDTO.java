package com.alterhub.alterhubbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantDTO {

    private UUID id;
    private String playerName;
    private UUID tournamentId;
    private String score;
    private Short classement;
    private DeckDTO deck;

}
