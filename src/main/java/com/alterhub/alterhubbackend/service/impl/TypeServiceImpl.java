package com.alterhub.alterhubbackend.service.impl;

import com.alterhub.alterhubbackend.dto.TypeDTO;
import com.alterhub.alterhubbackend.entity.Type;
import com.alterhub.alterhubbackend.exception.BadRequestException;
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
     *
     * @return L'ensemble des types de cartes disponibles
     * @author SCHMIDT Jonathan
     * @since 15/10/2025
     */
    public List<TypeDTO> getAllTypes() {
        return typeRepository.findAll()
                .stream()
                .map(TypeMapper::toDTO)
                .toList();
    }

    /**
     * Permet de récupérer un type grâce à son identifiant
     *
     * @param id L'identifiant du type que l'on souhaite récupérer
     * @return Le type de carte récupéré grâce à l'id passé en paramètre
     * @author SCHMIDT Jonathan
     * @since 16/10/2025
     */
    public TypeDTO getTypeById(UUID id) {
        Type type = typeRepository.findById(id)
                .orElseThrow(NoResultByIdException::new);

        return TypeMapper.toDTO(type);
    }

    public TypeDTO createType(TypeDTO typeDTO) {
        verifyTypeIntegrity(typeDTO);
        Type type = TypeMapper.toEntity(typeDTO);
        return TypeMapper.toDTO(typeRepository.save(type));
    }

    public TypeDTO updateTypeById(UUID id, TypeDTO typeDTO) {
        if (typeDTO.getId().equals(id)) {
            verifyTypeIntegrity(typeDTO);
            Type typeToUpdate = typeRepository.findById(id)
                    .orElseThrow(NoResultByIdException::new);
            Type typeUpdated = TypeMapper.toEntity(typeDTO);

            typeToUpdate.setId(typeUpdated.getId());
            typeToUpdate.setTypeId(typeUpdated.getTypeId());
            typeToUpdate.setName(typeUpdated.getName());
            typeToUpdate.setReference(typeUpdated.getReference());

            return TypeMapper.toDTO(typeRepository.save(typeToUpdate));

        } else {
            throw new IdNotMatchException();
        }
    }

    public void deleteTypeById(UUID id) {
        if (!typeRepository.existsById(id)) {
            throw new NoResultByIdException();
        }
        typeRepository.deleteById(id);
    }

    public void verifyTypeIntegrity(TypeDTO typeDTO) {
        if (typeDTO.getTypeId() == null || typeDTO.getTypeId().isEmpty()
                || typeDTO.getName() == null || typeDTO.getName().isEmpty()
                || typeDTO.getReference() == null || typeDTO.getReference().isEmpty()) {
            throw new BadRequestException();
        }
    }

    public void validateType(TypeDTO typeDTO) {
        Type typeReceived = TypeMapper.toEntity(typeDTO);
        Type typeOnBase = typeRepository.findById(typeReceived.getId()).orElseThrow(NoResultByIdException::new);
        if(!typeOnBase.getTypeId().equals(typeReceived.getTypeId())
        || !typeOnBase.getName().equals(typeReceived.getName())
        || !typeOnBase.getReference().equals(typeReceived.getReference())) {
            throw new BadRequestException();
        }
    }

}
