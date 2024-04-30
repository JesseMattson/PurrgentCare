package com.VetApp.PurrgentCare.repositories;


import com.VetApp.PurrgentCare.models.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository
 extends JpaRepository<Pet, Integer>{

    // Extend the functionality from CrudRepository
}
