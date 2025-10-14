package com.alterhub.alterhubbackend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "participants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Participant {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)") // Amélioration de la performance vis-à-vis d'un varchar(36)
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "playerId", nullable = true)
    private Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tournamentId", nullable = false)
    private Tournament tournament;

    @Column(nullable = false, length = 16)
    private String score;

    private Short classement;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "deckId", nullable = false)
    private Deck deck;

}
