package com.VetApp.PurrgentCare.services;

import com.VetApp.PurrgentCare.models.Pet;
import com.VetApp.PurrgentCare.repositories.PetRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

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
    @Override
    public void addPet(Pet pet) {
        petRepository.save(pet);
    }

    @Override
    public void deletePet (Integer petId) {petRepository.deleteById(petId);}

    @Override
    public List<Pet> getAllPets() {return petRepository.findAll();}


    @Override
    public Pet updatePet(Pet newPet, Integer petId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(petId)));
        pet.setName(newPet.getName());
        pet.setType(newPet.getType());
        pet.setAge(newPet.getAge());
        pet.setGender(newPet.getGender());
        return petRepository.save(pet);
    }


}
