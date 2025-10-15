package com.alterhub.alterhubbackend.repository;

import com.alterhub.alterhubbackend.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ParticipantRepository extends JpaRepository<Participant, UUID> {
}
