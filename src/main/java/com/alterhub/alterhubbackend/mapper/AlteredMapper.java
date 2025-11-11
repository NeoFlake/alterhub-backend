package com.alterhub.alterhubbackend.mapper;

import com.alterhub.alterhubbackend.dto.AlteredCardDTO;
import com.alterhub.alterhubbackend.entity.*;
import com.alterhub.alterhubbackend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AlteredMapper {

    private final TypeRepository typeRepository;
    private final SubTypeRepository subTypeRepository;
    private final SetRepository setRepository;
    private final RarityRepository rarityRepository;
    private final FactionRepository factionRepository;

    public Card toCardEntity(AlteredCardDTO dto) {
        Card card = Card.builder()
                .alteredId(dto.getId())
                .name(dto.getName())
                .reference(dto.getReference())
                .isSuspended(dto.getIsSuspended())
                .isBanned(dto.getIsBanned())
                .isErrated(dto.getIsErrated())
                .build();

        // --- Type ---
        Type type = typeRepository.findByTypeId(dto.getCardType().getId())
                .orElseGet(() -> typeRepository.save(
                        Type.builder()
                                .typeId(dto.getCardType().getId())
                                .name(dto.getCardType().getName())
                                .reference(dto.getCardType().getReference())
                                .build()
                ));
        card.setType(type);

        // --- Rarity ---
        Rarity rarity = rarityRepository.findByRarityId(dto.getRarity().getId())
                .orElseGet(() -> rarityRepository.save(
                        Rarity.builder()
                                .rarityId(dto.getRarity().getId())
                                .name(dto.getRarity().getName())
                                .reference(dto.getRarity().getReference())
                                .build()
                ));
        card.setRarity(rarity);

        // --- Faction ---
        Faction faction = factionRepository.findByFactionId(dto.getMainFaction().getId())
                .orElseGet(() -> factionRepository.save(
                        Faction.builder()
                                .factionId(dto.getMainFaction().getId())
                                .name(dto.getMainFaction().getName())
                                .reference(dto.getMainFaction().getReference())
                                .color(dto.getMainFaction().getColor())
                                .build()
                ));
        card.setFaction(faction);

        // --- Set ---
        if(dto.getCardSet() != null) {
            Set set = setRepository.findBySetId(dto.getCardSet().getId())
                    .orElseGet(() -> setRepository.save(
                            Set.builder()
                                    .setId(dto.getCardSet().getId())
                                    .name(dto.getCardSet().getName())
                                    .reference(dto.getCardSet().getReference())
                                    .build()
                    ));
            List<Set> sets = new ArrayList<>();
            sets.add(set);
            card.setSets(sets);
        }

        // --- SubTypes ---
        if(dto.getCardSubTypes() != null && !dto.getCardSubTypes().isEmpty()) {
            List<SubType> subTypes = new ArrayList<>();
            for (AlteredCardDTO.AlteredSubTypeDTO subDto : dto.getCardSubTypes()) {
                SubType subType = subTypeRepository.findBySubTypeId(subDto.getId())
                        .orElseGet(() -> subTypeRepository.save(
                                SubType.builder()
                                        .subTypeId(subDto.getId())
                                        .name(subDto.getName())
                                        .reference(subDto.getReference())
                                        .build()
                        ));
                subTypes.add(subType);
            }
            card.setSubtypes(subTypes);
        }

        // --- Element ---
        Element element = Element.builder()
                .mainCost(dto.getElements().getOrDefault("MAIN_COST","0"))
                .recallCost(dto.getElements().getOrDefault("RECALL_COST","0"))
                .oceanPower(dto.getElements().getOrDefault("OCEAN_POWER","0"))
                .mountainPower(dto.getElements().getOrDefault("MOUNTAIN_POWER","0"))
                .forestPower(dto.getElements().getOrDefault("FOREST_POWER","0"))
                .mainEffect(dto.getElements().getOrDefault("MAIN_EFFECT",""))
                .echoEffect(dto.getElements().getOrDefault("RECALL_EFFECT",""))
                .build();
        card.setElement(element);

        // --- Image ---
        // La récupération et sauvegarde locale des images sera gérée dans le service
        card.setImage(dto.getImagePath());

        return card;
    }
}
