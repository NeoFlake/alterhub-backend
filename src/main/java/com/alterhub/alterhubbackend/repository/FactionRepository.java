package com.alterhub.alterhubbackend.repository;

import com.alterhub.alterhubbackend.entity.Faction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FactionRepository extends JpaRepository<Faction, UUID> {
}
