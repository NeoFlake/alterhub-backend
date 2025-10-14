package com.alterhub.alterhubbackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)") // Amélioration de la performance vis-à-vis d'un varchar(36)
    private UUID id;

    @Column(nullable = false, length = 128)
    private String lastname;

    @Column(nullable = false, length = 128)
    private String firstname;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "playerId", nullable = false)
    private Player player;

    @Column(nullable = false, length = 128)
    private String password;

    @Column(nullable = false, columnDefinition = "DATE")
    private LocalDate dateOfCreation;

    @Column(nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime lastModification;

}
