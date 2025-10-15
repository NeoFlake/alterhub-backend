package com.alterhub.alterhubbackend.service.impl;

import com.alterhub.alterhubbackend.dto.TypeDTO;
import com.alterhub.alterhubbackend.entity.Type;
import com.alterhub.alterhubbackend.exception.IdNotMatchException;
import com.alterhub.alterhubbackend.exception.NoResultByIdException;
import com.alterhub.alterhubbackend.mapper.TypeMapper;
import com.alterhub.alterhubbackend.repository.TypeRepository;
import com.alterhub.alterhubbackend.service.interfaces.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TypeServiceImpl implements TypeService {

    private final TypeRepository typeRepository;

    /**
     * Permet de récupérer l'ensemble des types disponibles
     * @return L'ensemble des types de cartes disponibles
     * @author SCHMIDT Jonathan
     * @since 15/10/2025
     */
    @Override
    public List<TypeDTO> getAllTypes() {
        return typeRepository.findAll()
                .stream()
                .map(TypeMapper::toDTO)
                .toList();
    }

    @Override
    public TypeDTO getTypeById(UUID id){
        Type type = typeRepository.findById(id)
                .orElseThrow(NoResultByIdException::new);

        return TypeMapper.toDTO(type);
    }

    @Override
    public TypeDTO createType(TypeDTO typeDTO){
        Type type = TypeMapper.toEntity(typeDTO);
        return TypeMapper.toDTO(typeRepository.save(type));
    }

    public TypeDTO updateTypeById(UUID id, TypeDTO typeDTO){
        if(typeDTO.getId().equals(id)){
            Type typeToUpdate = typeRepository.findById(id)
                    .orElseThrow(NoResultByIdException::new);

            return typeDTO;

        } else {
            throw new IdNotMatchException();
        }
    }

}
