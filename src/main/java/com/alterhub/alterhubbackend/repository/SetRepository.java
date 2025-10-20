package com.alterhub.alterhubbackend.repository;

import com.alterhub.alterhubbackend.entity.Set;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SetRepository  extends JpaRepository<Set, UUID>  {
}
