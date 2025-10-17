package com.alterhub.alterhubbackend.service.interfaces;

import com.alterhub.alterhubbackend.dto.ElementDTO;

import java.util.List;
import java.util.UUID;

public interface ElementService {

    List<ElementDTO> getAllElements();

    ElementDTO getElementById(UUID id);

    ElementDTO createElement(ElementDTO elementDTO);

    ElementDTO updateElementById(UUID id, ElementDTO elementDTO);

    void  deleteElementById(UUID id);

    void verifyElementIntegrity(ElementDTO elementDTO);

}
