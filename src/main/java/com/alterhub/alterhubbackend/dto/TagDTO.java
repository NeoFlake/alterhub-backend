package com.alterhub.alterhubbackend.dto;

import com.alterhub.alterhubbackend.entity.Deck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TagDTO {

    private UUID id;
    private String name;

}
