package com.alterhub.alterhubbackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "players")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Player {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)") // Amélioration de la performance vis-à-vis d'un varchar(36)
    private UUID id;

    @Column(nullable = false, unique = true, length = 128)
    private String name;

    @OneToOne(mappedBy = "player")
    private User user;

    @OneToMany(mappedBy = "player")
    private List<Deck> decks = new ArrayList<>();

    @OneToMany(mappedBy = "player")
    private List<Participant> participants = new ArrayList<>();

}
