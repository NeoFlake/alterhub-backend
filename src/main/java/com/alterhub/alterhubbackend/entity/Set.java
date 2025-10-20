package com.alterhub.alterhubbackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "sets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Set {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)") // Amélioration de la performance vis-à-vis d'un varchar(36)
    private UUID id;

    @Column(nullable = false, length = 128)
    private String setId;

    @Column(nullable = false, length = 64)
    private String name;

    @Column(nullable = false, length = 128)
    private String reference;

}
