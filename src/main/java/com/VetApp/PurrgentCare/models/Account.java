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
@Entity(name = "Account") // Specified that this is a DATABASE Entity
public class Account {


    @Id // Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto Generate Values
    private Integer id;
    @Setter
    private Boolean active;
    @Setter
    private Date dateCreated;

    @JsonManagedReference
    @Setter
    @OneToMany(mappedBy = "account")
    private List<Person> accountHolders;

    @JsonManagedReference
    @Setter
    @OneToMany(mappedBy = "account")
    private List<Pet> pets;

    @OneToMany(cascade = CascadeType.REMOVE,
            mappedBy = "post", orphanRemoval = true)
    @Override
    public String toString() {
        return "Account [id=" + id + ", active=" + active + " , dateCreated=" + dateCreated + "]";
    }
}



