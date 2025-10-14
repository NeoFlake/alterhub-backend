package com.alterhub.alterhubbackend.mapper;

import com.alterhub.alterhubbackend.dto.ElementDTO;
import com.alterhub.alterhubbackend.entity.Element;
import lombok.experimental.UtilityClass;
import org.springframework.stereotype.Component;

@UtilityClass
public class ElementMapper {

    public ElementDTO toDTO(Element element) {
        if (element == null) return null;

        return ElementDTO.builder()
                .id(element.getId())
                .mainCost(element.getMainCost())
                .recallCost(element.getRecallCost())
                .forestPower(element.getForestPower())
                .mountainPower(element.getMountainPower())
                .oceanPower(element.getOceanPower())
                .mainEffect(element.getMainEffect())
                .echoEffect(element.getEchoEffect())
                .build();
    }

    public Element toEntity(ElementDTO elementDTO) {
        if (elementDTO == null) return null;

        return Element.builder()
                .id(elementDTO.getId())
                .mainCost(elementDTO.getMainCost())
                .recallCost(elementDTO.getRecallCost())
                .forestPower(elementDTO.getForestPower())
                .mountainPower(elementDTO.getMountainPower())
                .oceanPower(elementDTO.getOceanPower())
                .mainEffect(elementDTO.getMainEffect())
                .echoEffect(elementDTO.getEchoEffect())
                .build();
    }

}
