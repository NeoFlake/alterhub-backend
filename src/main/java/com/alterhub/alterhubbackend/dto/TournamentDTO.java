package com.alterhub.alterhubbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TournamentDTO {

    private UUID id;
    private String name;
    private Integer numberOfPlayers;
    private String location;
    private LocalDate date;
    private List<ParticipantDTO> participants;

}
