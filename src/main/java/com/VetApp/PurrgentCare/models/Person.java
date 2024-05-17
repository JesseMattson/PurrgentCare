package com.VetApp.PurrgentCare.models;


import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity(name = "Person") // Specified that this is a DATABASE Entity
public class Person {

    @Id // Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto Generate Values
    private Integer id;
    @Setter
    private String name;

    @ManyToOne
    private Account account;





    // Required for Serializing
    @Override
    public String toString() {
        return "Person [id=" + id + ", name=" + name + "]";
    }


}