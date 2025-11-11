package com.alterhub.alterhubbackend.repository;

import com.alterhub.alterhubbackend.entity.Faction;
import com.alterhub.alterhubbackend.entity.Rarity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface FactionRepository extends JpaRepository<Faction, UUID> {

    Optional<Faction> findByFactionId(String factionId);

}
