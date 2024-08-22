package com.VetApp.PurrgentCare.models;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "Account") // Specified that this is a DATABASE Entity
public class Account {


    @Id // Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto Generate Values
    private Integer id;
    private Boolean active;
    private Date dateCreated;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.REMOVE,
            mappedBy = "account", orphanRemoval = true)
    private List<Person> accountHolders;
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.REMOVE,
            mappedBy = "account", orphanRemoval = true)
    private List<Pet> pets;

    public void addAccountHolder(Person accountHolder) {
        accountHolder.setAccount(this);
        getAccountHolders().add(accountHolder);
    }

    public void addAccountHolders(List<Person> accountHolders) {
        for (Person accountHolder : accountHolders) {
            if (!getAccountHolders().contains(accountHolder)) {
                addAccountHolder(accountHolder);
            }
        }
    }

    @Override
    public String toString() {
        return "Account [id=" + id + ", active=" + active + " , dateCreated=" + dateCreated + "]";
    }
}


