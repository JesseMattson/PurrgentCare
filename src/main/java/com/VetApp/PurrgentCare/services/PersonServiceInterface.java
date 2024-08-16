package com.VetApp.PurrgentCare.services;

import com.VetApp.PurrgentCare.dtos.PersonRequest;
import com.VetApp.PurrgentCare.dtos.PersonResponse;
import com.VetApp.PurrgentCare.models.Account;
import com.VetApp.PurrgentCare.models.Person;

import java.util.List;


public interface PersonServiceInterface {
    PersonResponse getPerson(Integer personId);

   // List<PersonResponse> getAllPersons();

    PersonResponse addPerson(PersonRequest personRequest);

    void deletePerson(Integer personId);

    Person updatePerson(Person person, Integer personId);


    PersonResponse updatePerson(PersonRequest personRequest, Integer personId);
}
