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
public class ElementDTO {

    private UUID id;
    private String mainCost;
    private String recallCost;
    private String forestPower;
    private String mountainPower;
    private String oceanPower;
    private String mainEffect;
    private String echoEffect;

}
