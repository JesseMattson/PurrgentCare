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
       personService.addPerson(person);
    }

    @DeleteMapping("/persons/DeletePerson/{id}")
    private void deletePerson(@PathVariable("id") Integer personId) {
        personService.deletePerson(personId);

    }


}