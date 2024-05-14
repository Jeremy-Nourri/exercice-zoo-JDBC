package org.example.entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data

public class Animal {
    int id_animal;
    String nom;
    String race;
    String description;
    int age;

    @Override
    public String toString() {
        return "Animal{" +
                ", nom='" + nom + '\'' +
                ", race='" + race + '\'' +
                ", description='" + description + '\'' +
                ", age=" + age +
                '}';
    }
}

