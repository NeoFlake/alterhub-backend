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
public class CardDTO {

    private UUID id;
    private String alteredId;
    private String reference;
    private String name;
    private String image;
    private FactionDTO faction;
    private TypeDTO type;
    private List<SubTypeDTO> subTypes;
    private RarityDTO rarity;
    private ElementDTO element;
    private Boolean isSuspended;
    private Boolean isBanned;
    private Boolean isErrated;
    private Integer deckCount;

}
