package com.alterhub.alterhubbackend.repository;

import com.alterhub.alterhubbackend.entity.Hero;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HeroRepository extends JpaRepository<Hero, UUID> {
    Optional<Hero> findByName(String name);

    Optional<List<Hero>> findByFactionId(UUID factionId);
}
