package com.VetApp.PurrgentCare.dtos;


import com.VetApp.PurrgentCare.models.Person;
import com.VetApp.PurrgentCare.models.Pet;
import lombok.*;

import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {
    private Integer id;
    private Boolean active;
    private Date dateCreated;
    // TODO: Replace list of persons with personResponse Object
    private List<Person> accountHolders;
    // TODO: Replace list of pet with petResponse Object
    private List<Pet> pets;
}

