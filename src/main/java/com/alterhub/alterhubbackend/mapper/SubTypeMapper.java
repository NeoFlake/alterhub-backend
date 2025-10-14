package com.alterhub.alterhubbackend.mapper;

import com.alterhub.alterhubbackend.dto.SubTypeDTO;
import com.alterhub.alterhubbackend.entity.SubType;
import lombok.experimental.UtilityClass;
import org.springframework.stereotype.Component;

@UtilityClass
public class SubTypeMapper {

    public SubTypeDTO toDTO(SubType subType) {
        if (subType == null) return null;

        return SubTypeDTO.builder()
                .id(subType.getId())
                .subTypeId(subType.getSubTypeId())
                .name(subType.getName())
                .reference(subType.getReference())
                .build();
    }

    public SubType toEntity(SubTypeDTO subTypeDTO) {
        if (subTypeDTO == null) return null;

        return SubType.builder()
                .id(subTypeDTO.getId())
                .subTypeId(subTypeDTO.getSubTypeId())
                .name(subTypeDTO.getName())
                .reference(subTypeDTO.getReference())
                .build();
    }

}
