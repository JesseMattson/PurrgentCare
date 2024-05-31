package com.VetApp.PurrgentCare.controllers;


import com.VetApp.PurrgentCare.models.Person;
import com.VetApp.PurrgentCare.services.PersonServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController // Specifies that this is a Rest API
@CrossOrigin(origins = "http://localhost:3000") // This enables CORS for this controller
public class PersonController {

    private static final String BASE_URL = "/persons";

    @Autowired // You don't need to initialize the object.
    PersonServiceInterface personService;

    @GetMapping(BASE_URL + "/{id}") // PathVariable is used to get ID from the URL
    private Person getPerson(@PathVariable("id") Integer id) {

        return personService.getPerson(id);
    }

    @GetMapping(BASE_URL )
    private List<Person> getAllPersons() {

        return personService.getAllPersons();
    }

    @PostMapping(BASE_URL)
    private void addPerson(@RequestBody Person person) {
       personService.addPerson(person);
    }

    @DeleteMapping(BASE_URL + "/{id}")
    private void deletePerson(@PathVariable("id") Integer personId) {
        personService.deletePerson(personId);

    }

    @PutMapping(BASE_URL + "/{id}")
    private Person updatePerson(@PathVariable("id")  Integer personId, @RequestBody Person person) {
        return personService.updatePerson(person, personId);

    }


}