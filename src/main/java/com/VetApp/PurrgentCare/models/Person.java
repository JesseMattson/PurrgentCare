package com.VetApp.PurrgentCare.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter


@Entity(name = "Person") // Specified that this is a DATABASE Entity
public class Person {
    @Id // Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto Generate Values
    private Integer id;
    private String name;

    @JsonBackReference
    @ManyToOne
    private Account account;


    // Required for Serializing
    @Override
    public String toString() {
        return "Person [id=" + id + ", name=" + name + "]";
    }

}