package com.alterhub.alterhubbackend.repository;

import com.alterhub.alterhubbackend.entity.Hero;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HeroRepository extends JpaRepository<Hero, UUID> {
}
