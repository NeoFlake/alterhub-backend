package com.alterhub.alterhubbackend.repository;

import com.alterhub.alterhubbackend.entity.Rarity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RarityRepository extends JpaRepository<Rarity, UUID> {
}
