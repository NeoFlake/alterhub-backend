package com.alterhub.alterhubbackend.repository;

import com.alterhub.alterhubbackend.entity.Deck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeckRepository extends JpaRepository<Deck, UUID> {

    Optional<Deck> findByName(String name);

    List<Deck> findByNameContaining(String name);

}
