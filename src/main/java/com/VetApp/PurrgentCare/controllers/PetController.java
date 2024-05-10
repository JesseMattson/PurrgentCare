package com.VetApp.PurrgentCare.controllers;



import com.VetApp.PurrgentCare.models.Pet;
import com.VetApp.PurrgentCare.services.PetServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class PetController {

    @Autowired
    PetServiceInterface petService;

    @GetMapping("/pet/{id}") // PathVariable is used to get ID from the URL
    private Pet getPet(@PathVariable("id") Integer id) {

        return petService.getPet(id);
    }
    @PostMapping("/pet/AddPet")
    private void addPet(@RequestBody Pet pet) {
        petService.addPet(pet);
    }

    @DeleteMapping("/pet/DeletePet/{id}")
    private void deletePet(@PathVariable("id") Integer petId) {
        petService.deletePet(petId);
    }


    @PutMapping("/pet/UpdatePet/{id}")
    private Pet updatePet(@PathVariable("id")  Integer petId, @RequestBody Pet pet) {
        return petService.updatePet(pet, petId);
    }

    @GetMapping("/pet/all")
    private List<Pet> getAllPets() {

        return petService.getAllPets();
    }
}