package com.alterhub.alterhubbackend.repository;

import com.alterhub.alterhubbackend.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TypeRepository extends JpaRepository<Type, UUID> {

    Optional<Type> findByTypeId(String typeId);

}