package com.VetApp.PurrgentCare.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity(name = "Pet")
@NoArgsConstructor
@RequiredArgsConstructor
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private  int id;

    @Getter
    @Setter
    private  String name;

    @Getter
    @Setter
    private  String type;//TODO: Add Breed

    @Getter
    @Setter
    private int age;

    @Getter
    @Setter
    private  String gender;


}
