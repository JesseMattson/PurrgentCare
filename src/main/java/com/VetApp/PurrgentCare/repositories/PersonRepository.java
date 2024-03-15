package com.VetApp.PurrgentCare.repositories;

import com.VetApp.PurrgentCare.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PersonRepository
        extends JpaRepository<Person, Integer> { // Extend the functionality from CrudRepository

    public Person findById(int id);

    void addPerson(String name);
}