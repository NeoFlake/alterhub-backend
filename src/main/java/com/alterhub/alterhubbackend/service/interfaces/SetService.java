package com.alterhub.alterhubbackend.service.interfaces;

import com.alterhub.alterhubbackend.dto.SetDTO;

import java.util.List;
import java.util.UUID;

public interface SetService {

    List<SetDTO> getAllSets();

    SetDTO getSetById(UUID id);

    SetDTO createSet(SetDTO setDTO);

    SetDTO updateSetById(UUID id, SetDTO setDTO);

    void deleteSetById(UUID id);

}
