package com.alterhub.alterhubbackend.repository;

import com.alterhub.alterhubbackend.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PlayerRepository extends JpaRepository<Player, UUID> {
}
