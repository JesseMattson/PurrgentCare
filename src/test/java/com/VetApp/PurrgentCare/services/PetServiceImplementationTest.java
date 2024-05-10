package com.VetApp.PurrgentCare.services;


import com.VetApp.PurrgentCare.models.Person;
import com.VetApp.PurrgentCare.models.Pet;
import com.VetApp.PurrgentCare.repositories.PetRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PetServiceImplementationTest {

    private PetServiceImplementation serviceUnderTest;

    @Mock
    private PetRepository mockPetRepository;


    @BeforeEach
    public void setup() {
        this.serviceUnderTest = new PetServiceImplementation(mockPetRepository);
    }

    @Test
    public void getPet_whenExist_returnOnePet() {
        // given
        final var fakePet = mock(Pet.class);
        final var fakePetId = fakePet.getId();
        given(mockPetRepository.findById(fakePetId)).willReturn(Optional.of(fakePet));

        // when
        final var actual = serviceUnderTest.getPet(fakePetId);

        // then
        then(actual).isEqualTo(fakePet);
    }


//        //*TODO @Test public void getPet_whenNotExist_returnDefaultPet() {
//            // given
//            final var fakePet = mock(Pet.class);
//            final var fakePetId = fakePet.getId();
//            given(mockPetRepository.findById(fakePetId))
//                    .
//
//            // when
//          serviceUnderTest.getPet(fake);
//
//            // then
//            then(actual).isEqualTo(fakePet);
//  }

    @Test
    public void addPet_whenValidInput_returnsValidInput() {
        // given
        final var fakePet = mock(Pet.class);

        // when
        serviceUnderTest.addPet(fakePet);

        // then
        verify(mockPetRepository, times(1)).save(fakePet);
    }

    @Test
    public void updatePet_whenPetExists_returnsUpdatedPet () {
        // given
        final var fakePetId = new Random().nextInt(1000);
        final var originalPet = new Pet(fakePetId, "Tiger", "Cat", 2, "Male");
        final var updatedPet = new Pet(fakePetId, "Maggie", "Dog", 3, "Female");
        given(mockPetRepository.findById(fakePetId))
                .willReturn(Optional.of(originalPet));
        when(mockPetRepository.save(any(Pet.class)))
                .thenReturn(updatedPet);

        // when
        final var actual = serviceUnderTest.updatePet(updatedPet, fakePetId);

        // then
//        then(actual).isEqualTo(originalPet);
        assertThat(actual)
                .isEqualToComparingFieldByFieldRecursively(originalPet);
    }

    @Test
    public void updatePet_whenPetNotExists_throwEntityNotFoundException () {
        // given
        final var fakePetId = new Random().nextInt(1000);
        final var updatedPet = new Pet(fakePetId, "Tiger", "Cat", 2, "Male");
        given(mockPetRepository.findById(fakePetId))
                .willThrow(new EntityNotFoundException(String.valueOf(fakePetId)));

        // when && then
        final var exception = assertThrows(EntityNotFoundException.class,() -> {
            serviceUnderTest.updatePet(updatedPet, fakePetId);
        });
        then(exception.getMessage()).contains(String.valueOf(fakePetId));
    }

}