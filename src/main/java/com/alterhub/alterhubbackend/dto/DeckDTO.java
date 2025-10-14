package com.alterhub.alterhubbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeckDTO {

    private UUID id;
    private String name;
    private String description;
    private FactionDTO faction;
    private HeroDTO hero;
    private LocalDate dateOfCreation;
    private LocalDateTime lastModification;
    private UUID playerId;
    private List<CardDTO> cards;

}
