package com.alterhub.alterhubbackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "elements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Element {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)") // Amélioration de la performance vis-à-vis d'un varchar(36)
    private UUID id;

    @Column(nullable = false, length = 8)
    private String mainCost;

    @Column(nullable = false, length = 8)
    private String recallCost;

    @Column(nullable = false, length = 8)
    private String oceanPower;

    @Column(nullable = false, length = 8)
    private String mountainPower;

    @Column(nullable = false, length = 8)
    private String forestPower;

    @Column(nullable = false, length = 4096)
    private String mainEffect;

    @Column(nullable = false, length = 4096)
    private String echoEffect;

}
