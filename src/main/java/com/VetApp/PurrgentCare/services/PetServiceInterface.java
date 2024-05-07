package com.VetApp.PurrgentCare.services;

import com.VetApp.PurrgentCare.models.Pet;

public interface PetServiceInterface {


    Pet getPet(Integer petId);

    void addPet(Pet pet);

    void deletePet (Integer petId);
}
