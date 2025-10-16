package com.alterhub.alterhubbackend.service.interfaces;

import com.alterhub.alterhubbackend.dto.TypeDTO;

import java.util.List;
import java.util.UUID;

public interface TypeService {

    List<TypeDTO> getAllTypes();

    TypeDTO getTypeById(UUID id);

    TypeDTO createType(TypeDTO typeDTO);

    TypeDTO updateTypeById(UUID id, TypeDTO typeDTO);

    void deleteTypeById(UUID id);

    void verifyTypeIntegrity(TypeDTO typeDTO);

}
