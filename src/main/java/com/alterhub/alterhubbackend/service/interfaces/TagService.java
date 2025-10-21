package com.alterhub.alterhubbackend.service.interfaces;

import com.alterhub.alterhubbackend.dto.TagDTO;

import java.util.List;
import java.util.UUID;

public interface TagService {

    List<TagDTO> getAllTags();

    TagDTO getTagById(UUID id);

    TagDTO getTagByName(String name);

    TagDTO addTag(TagDTO tagDTO);

    TagDTO updateTagById(UUID id, TagDTO tagDTO);

    void deleteTagById(UUID id);

    void verifyTagIntegrity(TagDTO tagDTO);

    void validateTag(TagDTO tagDTO);

}
