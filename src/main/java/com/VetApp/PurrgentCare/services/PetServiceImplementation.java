package com.VetApp.PurrgentCare.services;

import com.VetApp.PurrgentCare.dtos.PetRequest;
import com.VetApp.PurrgentCare.dtos.PetResponse;
import com.VetApp.PurrgentCare.models.Pet;
import com.VetApp.PurrgentCare.repositories.PetRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetServiceImplementation implements PetServiceInterface {

    private final PetRepository petRepository;
    @Autowired
    private final ModelMapper mapper;

    public PetServiceImplementation(PetRepository petRepository, ModelMapper mapper) {
        this.petRepository = petRepository;
        this.mapper = mapper;
    }

    @Override
    public PetResponse getPet(Integer petId) {
        final var pet = petRepository.findById(petId);
        if (pet.isPresent()) {
            return mapper.map(pet, PetResponse.class);
        }
        return new PetResponse();
    }

    @Override
    public PetResponse addPet(PetRequest petRequest) {
        final Pet pet = mapper.map(petRequest, Pet.class);
        petRepository.save(pet);
        return mapper.map(pet, PetResponse.class);

    }

    @Override
    public void deletePet(Integer petId) {
        petRepository.deleteById(petId);
    }

    @Override
    public List<PetResponse> getAllPets() {
        final List<Pet> pets = petRepository.findAll();
        final List<PetResponse> petResponses = pets.stream()
                .map(pet -> mapper.map(pet, PetResponse.class))
                .collect(Collectors.toList());
        return petResponses;
    }


    @Override
    public PetResponse updatePet(PetRequest petRequest, Integer petId) {
        final Pet newPet = mapper.map(petRequest, Pet.class);
        Pet pet = petRepository.findById(petId).orElseThrow(() -> new EntityNotFoundException(String.valueOf(petId)));
        pet.setName(newPet.getName());
        pet.setType(newPet.getType());
        pet.setAge(newPet.getAge());
        pet.setGender(newPet.getGender());
        petRepository.save(pet);
        return mapper.map(pet, PetResponse.class);
    }


}
