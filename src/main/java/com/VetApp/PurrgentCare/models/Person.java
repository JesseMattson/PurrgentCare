package com.VetApp.PurrgentCare.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity(name = "Person") // Specified that this is a DATABASE Entity
public class Person {

    @Id // Primary Key
    @GeneratedValue(strategy = GenerationType.AUTO) // Auto Generate Values
    private int id;
    private String name;


    public Person() {
        this.id = 0;
        this.name = "";
    }

    public Person(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    // Required for Serializing
    @Override
    public String toString() {
        return "Person [id=" + id + ", name=" + name + "]";
    }


}