package com.alterhub.alterhubbackend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "cards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)") // Amélioration de la performance vis-à-vis d'un varchar(36)
    private UUID id;

    @Column(nullable = false, length = 256)
    private String name;

    @Column(nullable = false, length = 256)
    private String alteredId;

    @Column(nullable = false, length = 2048)
    private String image;

    @Column(nullable = false)
    private Boolean isSuspended;

    @Column(nullable = false)
    private Boolean isBanned;

    @Column(nullable = false, length = 256)
    private String reference;


}
