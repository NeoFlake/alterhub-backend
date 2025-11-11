package com.alterhub.alterhubbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class CardListWrapper {

    @JsonProperty("hydra:totalItems")
    private int totalItems; // nombre total d'éléments pour cette requête

    @JsonProperty("hydra:member")
    private List<AlteredCardDTO> hydraMember; // la liste des cartes de cette page
}