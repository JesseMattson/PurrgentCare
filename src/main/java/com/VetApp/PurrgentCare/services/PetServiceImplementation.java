package com.VetApp.PurrgentCare.services;

import com.VetApp.PurrgentCare.models.Pet;
import com.VetApp.PurrgentCare.repositories.PetRepository;
import org.springframework.stereotype.Service;

@Service
public class PetServiceImplementation implements PetServiceInterface {

    private final PetRepository petRepository;

    public PetServiceImplementation(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public Pet getPet(Integer petId) {
        final var pet = petRepository.findById(petId);
        if (pet.isPresent()) {
            return pet.get();
        }
        return new Pet();

    }
}
