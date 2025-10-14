package com.alterhub.alterhubbackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "decks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Deck {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)") // Amélioration de la performance vis-à-vis d'un varchar(36)
    private UUID id;

    @Column(nullable = false, length = 128)
    private String name;

    @Column(nullable = false, length = 1024)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "playerId", nullable = false)
    private Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "factionId", nullable = false)
    private Faction faction;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "heroId", nullable = false)
    private Hero hero;

    @Column(nullable = false, columnDefinition = "DATE")
    private LocalDate dateOfCreation;

    @Column(nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime lastModification;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "deck_card",
            joinColumns = @JoinColumn(name = "deckId"),
            inverseJoinColumns = @JoinColumn(name = "cardId")
    )
    @Column(nullable = false)
    private List<Card> cards = new ArrayList<>();

}
