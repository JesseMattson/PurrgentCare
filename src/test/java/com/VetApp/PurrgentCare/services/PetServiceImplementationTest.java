package com.VetApp.PurrgentCare.services;


import com.VetApp.PurrgentCare.FakeDataGenerator;
import com.VetApp.PurrgentCare.models.Account;
import com.VetApp.PurrgentCare.models.Pet;
import com.VetApp.PurrgentCare.repositories.PetRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
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

    private final FakeDataGenerator fakeDataGenerator = new FakeDataGenerator();


    @BeforeEach
    public void setup() {
        this.serviceUnderTest = new PetServiceImplementation(mockPetRepository);
    }

    @Test
    public void getPet_whenExist_returnOnePet() {
        // given
        final var fakePetId = fakeDataGenerator.generateRandomInteger();
                final var fakePet = fakeDataGenerator.generatePet(fakePetId);
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
        final var fakePetId = fakeDataGenerator.generateRandomInteger();
        final var fakePet = fakeDataGenerator.generatePet(fakePetId);

        // when
        serviceUnderTest.addPet(fakePet);

        // then
        verify(mockPetRepository, times(1)).save(fakePet);
    }

    @Test
    public void deletePet_whenValidInput() {
        // given
        final var fakePetId = fakeDataGenerator.generateRandomInteger();

        // when
        serviceUnderTest.deletePet(fakePetId);

        // then
        verify(mockPetRepository, times(1)).deleteById(fakePetId);

    }


    @Test
    public void getAllPets_withValidInput_returnsAllPets() {
        // given
        final var countOfPets = new Random().nextInt(1000);
        final var expected = buildPetList(countOfPets);
        given(mockPetRepository.findAll())
                .willReturn(expected);

        // when
        final var actual = serviceUnderTest.getAllPets();

        // then
        then(actual).isEqualTo(expected);
        then(actual).hasSize(countOfPets);
    }

    @Test
    public void getAllPets_whenNoPets_returnsEmptyList() {
        // given
        final List<Pet> expected = buildPetList(0);
        given(mockPetRepository.findAll())
                .willReturn(expected);

        // when
        final var actual = serviceUnderTest.getAllPets();

        // then
        then(actual).isEqualTo(expected);
    }

    private List<Pet> buildPetList(Integer countOfPets) {
        List<Pet> petList = new ArrayList<>(List.of());
        var i = 1;
        while (i <= countOfPets) {
            var pet = mock(Pet.class);
            petList.add(pet);
            i++;
        }
        return petList;
    }


    @Test
    public void updatePet_whenPetExists_returnsUpdatedPet() {
        // given
        final var fakePetId = new Random().nextInt(1000);
        final var fakeAccount = new Account();
        final var originalPet = new Pet(fakePetId, "Tiger", "Cat", 2, "Male", fakeAccount);
        final var updatedPet = new Pet(fakePetId, "Maggie", "Dog", 3, "Female", fakeAccount);
        given(mockPetRepository.findById(fakePetId))
                .willReturn(Optional.of(originalPet));
        when(mockPetRepository.save(any(Pet.class)))
                .thenReturn(updatedPet);

        // when
        final var actual = serviceUnderTest.updatePet(updatedPet, fakePetId);

        // then
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(originalPet);
    }

    @Test
    public void updatePet_whenPetNotExists_throwEntityNotFoundException() {
        // given
        final var fakePetId = new Random().nextInt(1000);
        final var fakeAccount = new Account();
        final var updatedPet = new Pet(fakePetId, "Tiger", "Cat", 2, "Male", fakeAccount);
        given(mockPetRepository.findById(fakePetId))
                .willThrow(new EntityNotFoundException(String.valueOf(fakePetId)));

        // when && then
        final var exception = assertThrows(EntityNotFoundException.class, () -> {
            serviceUnderTest.updatePet(updatedPet, fakePetId);
        });
        then(exception.getMessage()).contains(String.valueOf(fakePetId));
    }

}