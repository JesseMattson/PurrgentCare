package com.VetApp.PurrgentCare.services;

import com.VetApp.PurrgentCare.models.Person;

import java.util.List;


public interface PersonServiceInterface {
    Person getPerson(Integer personId);

    List<Person> getAllPersons();

    Person addPerson(Person person);

}
