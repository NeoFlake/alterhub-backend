package com.alterhub.alterhubbackend.repository;

import com.alterhub.alterhubbackend.entity.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TournamentRepository extends JpaRepository<Tournament, UUID> {
}
