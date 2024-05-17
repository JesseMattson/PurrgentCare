package com.VetApp.PurrgentCare.models;


import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity(name = "Account") // Specified that this is a DATABASE Entity
public class Account {


    @Id // Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto Generate Values
    private Integer id;
    private Boolean active;

    private Date dateCreated;

    @OneToMany(mappedBy = "account")

    private List<Person> accountHolders;

    @OneToMany(mappedBy = "account")
    private List<Pet> pets;



}