package com.alterhub.alterhubbackend.repository;

import com.alterhub.alterhubbackend.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ParticipantRepository extends JpaRepository<Participant, UUID> {

    List<Participant> findByPlayer_Id(UUID id);

    void deleteByPlayer_Id(UUID id);

}
