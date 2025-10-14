package com.alterhub.alterhubbackend.dto;

import com.alterhub.alterhubbackend.entity.Faction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HeroDTO {

    private UUID id;
    private String name;
    private Faction faction;
    private Short reserveSlot;
    private Short landmarkSlot;
    private String effect;

}
