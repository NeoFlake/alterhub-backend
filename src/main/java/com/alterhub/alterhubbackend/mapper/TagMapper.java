package com.alterhub.alterhubbackend.mapper;

import com.alterhub.alterhubbackend.dto.TagDTO;
import com.alterhub.alterhubbackend.entity.Tag;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TagMapper {

    public TagDTO toDTO(Tag tag) {
        if(tag == null)
            return null;

        return TagDTO.builder()
                .id(tag.getId())
                .name(tag.getName())
                .build();
    }

    public Tag toEntity(TagDTO tagDTO) {
        if(tagDTO == null)
            return null;

        return Tag.builder()
                .id(tagDTO.getId())
                .name(tagDTO.getName())
                .build();
    }

}
