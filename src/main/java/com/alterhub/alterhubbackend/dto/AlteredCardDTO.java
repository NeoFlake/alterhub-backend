package com.alterhub.alterhubbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlteredCardDTO {
    private String id;
    private String name;
    private String reference;
    private Map<String, String> elements;
    private AlteredTypeDTO cardType;
    private AlteredSetDTO cardSet;
    private AlteredRarityDTO rarity;
    private AlteredFactionDTO mainFaction;
    private List<AlteredSubTypeDTO> cardSubTypes;
    private Boolean isSuspended;
    private Boolean isErrated;
    private Boolean isBanned;
    private String imagePath;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AlteredTypeDTO {
        private String id;
        private String name;
        private String reference;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AlteredSubTypeDTO {
        private String id;
        private String name;
        private String reference;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AlteredSetDTO {
        private String id;
        private String name;
        private String reference;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AlteredRarityDTO {
        private String id;
        private String name;
        private String reference;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AlteredFactionDTO {
        private String id;
        private String name;
        private String reference;
        private String color;
    }
}