package com.alterhub.alterhubbackend.repository;

import com.alterhub.alterhubbackend.entity.Element;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ElementRepository extends JpaRepository<Element, UUID> {
}
