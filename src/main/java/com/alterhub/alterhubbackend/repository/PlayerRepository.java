package com.alterhub.alterhubbackend.repository;

import com.alterhub.alterhubbackend.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PlayerRepository extends JpaRepository<Player, UUID> {

    Optional<Player> findByName(String name);

    Optional<Player> findByUser_Id(UUID userId);

    Boolean existsByName(String name);

}
