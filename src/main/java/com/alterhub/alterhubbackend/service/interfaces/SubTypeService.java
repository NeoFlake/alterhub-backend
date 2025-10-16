package com.alterhub.alterhubbackend.service.interfaces;

import com.alterhub.alterhubbackend.dto.SubTypeDTO;

import java.util.List;
import java.util.UUID;

public interface SubTypeService {

    List<SubTypeDTO> getAllSubTypes();

    SubTypeDTO getSubTypeById(UUID id);

    SubTypeDTO createSubType(SubTypeDTO subTypeDTO);

    SubTypeDTO updateSubTypeById(UUID id, SubTypeDTO subTypeDTO);

    void deleteSubTypeById(UUID id);

    void verifySubTypeIntegrity(SubTypeDTO subTypeDTO);

}
