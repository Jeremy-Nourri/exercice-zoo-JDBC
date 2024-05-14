package org.example.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data

public class Repas {
    private int id_repas;
    private LocalDateTime dateHeure;
    private String nourriture;
    private Animal animal;

}
