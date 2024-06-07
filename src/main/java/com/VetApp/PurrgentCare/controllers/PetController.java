package com.VetApp.PurrgentCare.controllers;


import com.VetApp.PurrgentCare.models.Pet;
import com.VetApp.PurrgentCare.services.PetServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:3000") // This enables CORS for this controller
public class PetController {

    private static final String BASE_URL = "/pets";

    @Autowired
    PetServiceInterface petService;

    @GetMapping(BASE_URL + "/{id}") // PathVariable is used to get ID from the URL
    @ResponseStatus(HttpStatus.OK)
    private Pet getPet(@PathVariable("id") Integer id) {

        return petService.getPet(id);
    }

    @PostMapping(BASE_URL)
    @ResponseStatus(HttpStatus.CREATED)
    private Pet addPet(@RequestBody Pet pet) {
        return petService.addPet(pet);
    }

    @DeleteMapping(BASE_URL + "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void deletePet(@PathVariable("id") Integer petId) {
        petService.deletePet(petId);
    }


    @PutMapping(BASE_URL + "/{id}")
    @ResponseStatus(HttpStatus.OK)
    private Pet updatePet(@PathVariable("id") Integer petId, @RequestBody Pet pet) {
        return petService.updatePet(pet, petId);
    }

    @GetMapping(BASE_URL)
    @ResponseStatus(HttpStatus.OK)
    private List<Pet> getAllPets() {

        return petService.getAllPets();
    }
}