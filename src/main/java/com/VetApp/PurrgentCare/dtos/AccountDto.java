package com.VetApp.PurrgentCare.dtos;

import com.VetApp.PurrgentCare.models.Person;
import com.VetApp.PurrgentCare.models.Pet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AccountDto {
    private Boolean active;
    private Date dateCreated;
    private List<Person> accountHolders;
    private List<Pet> pets;
}
