package com.alterhub.alterhubbackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "heros")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hero {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)") // Amélioration de la performance vis-à-vis d'un varchar(36)
    private UUID id;

    @Column(nullable = false, length = 64)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "factionId", nullable = false)
    private Faction faction;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "heros_sets",
            joinColumns = @JoinColumn(name = "herosId"),
            inverseJoinColumns = @JoinColumn(name = "setsId")
    )
    @Column(nullable = false)
    @Builder.Default
    private List<Set> sets = new ArrayList<>();

    private Short reserveSlot;

    private Short landmarkSlot;

    @Column(nullable = false, length = 2048)
    private String effect;

}
