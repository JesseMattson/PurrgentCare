package com.VetApp.PurrgentCare.controllers;


import com.VetApp.PurrgentCare.dtos.PetRequest;
import com.VetApp.PurrgentCare.dtos.PetResponse;
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
    private PetResponse getPet(@PathVariable("id") Integer id) {

        return petService.getPet(id);
    }

    @PostMapping(BASE_URL)
    @ResponseStatus(HttpStatus.CREATED)
    private PetResponse addPet(@RequestBody PetRequest petRequest) {
        return petService.addPet(petRequest);
    }

    @DeleteMapping(BASE_URL + "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void deletePet(@PathVariable("id") Integer petId) {
        petService.deletePet(petId);
    }


    @PutMapping(BASE_URL + "/{id}")
    @ResponseStatus(HttpStatus.OK)
    private PetResponse updatePet(@PathVariable("id") Integer petId, @RequestBody PetRequest petRequest) {
        return petService.updatePet(petRequest, petId);
    }

    @GetMapping(BASE_URL)
    @ResponseStatus(HttpStatus.OK)
    private List<PetResponse> getAllPets() {

        return petService.getAllPets();
    }
}