package com.alterhub.alterhubbackend.repository;

import com.alterhub.alterhubbackend.entity.SubType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SubTypeRepository extends JpaRepository<SubType, UUID> {
}
