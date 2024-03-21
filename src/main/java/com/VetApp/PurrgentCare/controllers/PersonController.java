package com.VetApp.PurrgentCare.controllers;


import com.VetApp.PurrgentCare.models.Person;
import com.VetApp.PurrgentCare.services.PersonServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController // Specifies that this is a Rest API
public class PersonController {

    @Autowired // You don't need to initialize the object.
    PersonServiceInterface personService;
//TODO REPLACE WITH INTERFACE IN PersonServiceImpl
//    @GetMapping("/persons") // URL end point
//    public List<Person> getPersonsList() {
//        return personRepository.findAll(); // JPA functions
//    }

    @GetMapping("/persons/{id}") // PathVariable is used to get ID from the URL
    private Person getPerson(@PathVariable("id") Integer id) {

        return personService.getPerson(id);
    }

    @GetMapping("/persons/all")
    private List<Person> getAllPersons() {

        return personService.getAllPersons();
    }

    @PostMapping("/persons/AddPerson")
    private void addPerson(@RequestBody Person person) {
        //payloadService?
        //pass payload into a mapper
        //then after mapping use add person service to add to repository
        personService.addPerson(person);
    }
}