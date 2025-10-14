package com.alterhub.alterhubbackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
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
    private String alteredId;

    @Column(nullable = false, length = 256)
    private String reference;

    @Column(nullable = false, length = 256)
    private String name;

    @Column(nullable = false, length = 2048)
    private String image;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "factionId", nullable = false)
    private Faction faction;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "typeId", nullable = false)
    private Type type;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "card_subtypes",
            joinColumns = @JoinColumn(name = "cardId"),
            inverseJoinColumns = @JoinColumn(name = "subTypesId")
    )
    @Column(nullable = false)
    private List<SubType> subtypes = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rarityId", nullable = false)
    private Rarity rarity;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Element element;

    @Column(nullable = false)
    private Boolean isSuspended;

    @Column(nullable = false)
    private Boolean isBanned;

    @Column(nullable = false)
    private Boolean isErrated;

}
