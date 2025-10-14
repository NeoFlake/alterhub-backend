package com.alterhub.alterhubbackend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "factions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Faction {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)") // Amélioration de la performance vis-à-vis d'un varchar(36)
    private UUID id;

    @Column(nullable = false, length = 128)
    private String factionId;

    @Column(nullable = false, length = 32)
    private String name;

    @Column(nullable = false, length = 128)
    private String reference;

    @Column(nullable = false, length = 32)
    private String color;

}
