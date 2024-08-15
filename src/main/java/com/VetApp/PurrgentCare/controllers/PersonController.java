package com.VetApp.PurrgentCare.controllers;


import com.VetApp.PurrgentCare.dtos.PersonResponse;
import com.VetApp.PurrgentCare.models.Person;
import com.VetApp.PurrgentCare.services.PersonServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController // Specifies that this is a Rest API
@CrossOrigin(origins = "http://localhost:3000") // This enables CORS for this controller
public class PersonController {

    private static final String BASE_URL = "/persons";

    @Autowired // You don't need to initialize the object.
    PersonServiceInterface personService;

    @GetMapping(BASE_URL + "/{id}") // PathVariable is used to get ID from the URL
    @ResponseStatus(HttpStatus.OK)
    private PersonResponse getPerson(@PathVariable("id") Integer id) {

        return personService.getPerson(id);
    }

    @GetMapping(BASE_URL)
    @ResponseStatus(HttpStatus.OK)
    private List<Person> getAllPersons() {

        return personService.getAllPersons();
    }

    @PostMapping(BASE_URL)
    @ResponseStatus(HttpStatus.CREATED)
    private Person addPerson(@RequestBody Person person) {
       return personService.addPerson(person);
    }

    @DeleteMapping(BASE_URL + "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void deletePerson(@PathVariable("id") Integer personId) {
        personService.deletePerson(personId);

    }

    @PutMapping(BASE_URL + "/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    private Person updatePerson(@PathVariable("id") Integer personId, @RequestBody Person person) {
        return personService.updatePerson(person, personId);

    }
}