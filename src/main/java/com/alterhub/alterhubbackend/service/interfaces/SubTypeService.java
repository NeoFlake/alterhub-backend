package com.alterhub.alterhubbackend.service.interfaces;

import com.alterhub.alterhubbackend.dto.SubTypeDTO;

import java.util.List;
import java.util.UUID;

public interface SubTypeService {

    List<SubTypeDTO> getAllSubTypes();

    SubTypeDTO getSubType(UUID id);

    SubTypeDTO updateSubTypeById(UUID id, SubTypeDTO subTypeDTO);

    void verifySubTypeIntegrity(SubTypeDTO subTypeDTO);

}
