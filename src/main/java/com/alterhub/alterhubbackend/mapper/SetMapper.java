package com.alterhub.alterhubbackend.mapper;

import com.alterhub.alterhubbackend.dto.SetDTO;
import com.alterhub.alterhubbackend.dto.SubTypeDTO;
import com.alterhub.alterhubbackend.entity.Set;
import com.alterhub.alterhubbackend.entity.SubType;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SetMapper {

    public SetDTO toDTO(Set set) {
        if (set == null) return null;

        return SetDTO.builder()
                .id(set.getId())
                .setId(set.getSetId())
                .name(set.getName())
                .reference(set.getReference())
                .build();
    }

    public Set toEntity(SetDTO setDTO) {
        if (setDTO == null) return null;

        return Set.builder()
                .id(setDTO.getId())
                .setId(setDTO.getSetId())
                .name(setDTO.getName())
                .reference(setDTO.getReference())
                .build();
    }

}
