package com.VetApp.PurrgentCare.services;

import com.VetApp.PurrgentCare.models.Pet;

import java.util.List;

public interface PetServiceInterface {


    Pet getPet(Integer petId);

    void addPet(Pet pet);

    List<Pet> getAllPets();

    Pet updatePet (Pet pet, Integer petId);
}
