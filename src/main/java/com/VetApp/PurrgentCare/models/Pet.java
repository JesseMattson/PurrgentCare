package com.VetApp.PurrgentCare.models;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Statement;

@Builder
@Getter
@Entity(name = "Pet")
@NoArgsConstructor

@AllArgsConstructor


public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;

    @Setter
    private  String name;

    @Setter
    private  String type;//TODO: Add Breed

    @Setter
    private Integer age;

    @Setter
    private  String gender;


    @ManyToOne
    private Account account;

    @Override
    public String toString() {
        return "Pet [id=" + id + ", name=" + name + ", type=" + type + ", age=" + age + ", gender=" + gender
               + "]";
    }
}
