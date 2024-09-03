package com.VetApp.PurrgentCare.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Builder(toBuilder = true)
@Getter
@Setter
@Entity(name = "Pet")
@NoArgsConstructor
@AllArgsConstructor
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String type;//TODO: Add Breed

    private Integer age;

    private String gender;

    @JsonBackReference
    @ManyToOne
    private Account account;

    @Override
    public String toString() {
        return "Pet [id=" + id + ", name=" + name + ", type=" + type + ", age=" + age + ", gender=" + gender
                + "]";
    }
}
