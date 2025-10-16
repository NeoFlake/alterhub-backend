package com.alterhub.alterhubbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDTO {

    private UUID id;
    private UUID userId;
    private String name;
    private List<DeckDTO> decks;
    private List<ParticipantDTO> participants;

}
