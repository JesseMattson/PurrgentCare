package com.VetApp.PurrgentCare.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity(name = "Account") // Specified that this is a DATABASE Entity
public class Account {


    @Id // Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto Generate Values
    private Integer id;
    @Setter
    private Boolean active;
    @Setter
    private Date dateCreated;

    @OneToMany(mappedBy = "account")

    private List<Person> accountHolders;

    @OneToMany(mappedBy = "account")
    private List<Pet> pets;


    @Override
    public String toString() {
        return "Account [id=" + id + ", active=" + active + " , dateCreated=" + dateCreated + "]";
    }
}



