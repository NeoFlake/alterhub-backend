package com.alterhub.alterhubbackend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tournaments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tournament {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)") // Amélioration de la performance vis-à-vis d'un varchar(36)
    private UUID id;

    @Column(nullable = false, unique = true, length = 256)
    private String name;

    @Column(nullable = false)
    private Integer numberOfPlayers;

    @Column(nullable = false, length = 256)
    private String location;

    @OneToMany(mappedBy = "tournament")
    private List<Participant> participants = new ArrayList<>();

}
