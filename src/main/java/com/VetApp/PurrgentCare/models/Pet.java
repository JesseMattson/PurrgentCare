package com.VetApp.PurrgentCare.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.sql.Statement;

@Getter
@Entity(name = "Pet")
@NoArgsConstructor

@AllArgsConstructor


public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  int id;

    @Setter
    private  String name;

    @Setter
    private  String type;//TODO: Add Breed

    @Setter
    private int age;

    @Setter
    private  String gender;



    @Override
    public String toString() {
        return "Pet [id=" + id + ", name=" + name + ", type=" + type + ", age=" + age + ", gender=" + gender
               + "]";
    }
}
