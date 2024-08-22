package com.VetApp.PurrgentCare.services;

import com.VetApp.PurrgentCare.dtos.PetRequest;
import com.VetApp.PurrgentCare.dtos.PetResponse;

import java.util.List;

public interface PetServiceInterface {


    PetResponse getPet(Integer petId);

    PetResponse addPet(PetRequest pet);

    void deletePet(Integer petId);

    List<PetResponse> getAllPets();

    PetResponse updatePet(PetRequest pet, Integer petId);
}
