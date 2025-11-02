package com.alterhub.alterhubbackend.service.impl;

import com.alterhub.alterhubbackend.dto.TagDTO;
import com.alterhub.alterhubbackend.entity.*;
import com.alterhub.alterhubbackend.exception.IdNotMatchException;
import com.alterhub.alterhubbackend.exception.NoResultByIdException;
import com.alterhub.alterhubbackend.mapper.TagMapper;
import com.alterhub.alterhubbackend.repository.TagRepository;
import com.alterhub.alterhubbackend.service.interfaces.TagService;
import com.alterhub.alterhubbackend.validation.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    private final ValidationService validationService;

    public List<TagDTO> getAllTags() {
        return tagRepository.findAll()
                .stream()
                .map(TagMapper::toDTO)
                .toList();
    }

    public TagDTO getTagById(UUID id) {
        Tag tag = tagRepository.findById(id).orElseThrow(NoResultByIdException::new);
        return TagMapper.toDTO(tag);
    }

    public TagDTO getTagByName(String name) {
        Tag tag = tagRepository.findByName(name).orElseThrow(NoResultByIdException::new);
        return TagMapper.toDTO(tag);
    }

    public TagDTO createTag(TagDTO tagDTO) {
        validationService.verifyTagIntegrity(tagDTO);
        Tag tag = TagMapper.toEntity(tagDTO);
        return TagMapper.toDTO(tagRepository.save(tag));
    }

    public TagDTO updateTagById(UUID id, TagDTO tagDTO) {
        if (!tagDTO.getId().equals(id)){
            throw new IdNotMatchException();
        }
        validationService.verifyTagIntegrity(tagDTO);

        Tag tagUpdated = TagMapper.toEntity(tagDTO);
        Tag tagToUpdate = tagRepository.findById(tagDTO.getId()).orElseThrow(NoResultByIdException::new);

        tagToUpdate.setId(tagUpdated.getId());
        tagToUpdate.setName(tagUpdated.getName());

        Tag tag = tagRepository.save(tagToUpdate);

        return TagMapper.toDTO(tag);

    }

    public void deleteTagById(UUID id) {
        if(!tagRepository.existsById(id)){
            throw new NoResultByIdException();
        }
        tagRepository.deleteById(id);
    }

}
