package com.alterhub.alterhubbackend.mapper;

import com.alterhub.alterhubbackend.dto.FactionDTO;
import com.alterhub.alterhubbackend.dto.TypeDTO;
import com.alterhub.alterhubbackend.entity.Faction;
import com.alterhub.alterhubbackend.entity.Type;
import lombok.experimental.UtilityClass;
import org.springframework.stereotype.Component;

@UtilityClass
public class TypeMapper {

    public TypeDTO toDTO(Type type) {
        if (type == null) return null;

        return TypeDTO.builder()
                .id(type.getId())
                .typeId(type.getTypeId())
                .reference(type.getReference())
                .name(type.getName())
                .build();
    }

    public Type toEntity(TypeDTO typeDTO) {
        if (typeDTO == null) return null;

        return Type.builder()
                .id(typeDTO.getId())
                .typeId(typeDTO.getTypeId())
                .reference(typeDTO.getReference())
                .name(typeDTO.getName())
                .build();

    }

}
