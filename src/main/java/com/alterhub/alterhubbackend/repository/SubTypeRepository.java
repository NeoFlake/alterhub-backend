package com.alterhub.alterhubbackend.repository;

import com.alterhub.alterhubbackend.entity.Rarity;
import com.alterhub.alterhubbackend.entity.SubType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SubTypeRepository extends JpaRepository<SubType, UUID> {

    Optional<SubType> findBySubTypeId(String subTypeId);

}
