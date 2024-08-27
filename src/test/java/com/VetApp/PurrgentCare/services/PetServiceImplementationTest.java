package com.VetApp.PurrgentCare.services;


import com.VetApp.PurrgentCare.FakeDataGenerator;
import com.VetApp.PurrgentCare.dtos.PetResponse;
import com.VetApp.PurrgentCare.models.Pet;
import com.VetApp.PurrgentCare.repositories.PetRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PetServiceImplementationTest {

    private final FakeDataGenerator fakeDataGenerator = new FakeDataGenerator();
    private PetServiceImplementation serviceUnderTest;
    @Mock
    private PetRepository mockPetRepository;
    @Mock
    private ModelMapper mockMapper;
    @Captor
    private ArgumentCaptor<Pet> petCaptor;

    @BeforeEach
    public void setup() {
        this.serviceUnderTest = new PetServiceImplementation(mockPetRepository, mockMapper);
    }

    @Test
    public void getPet_whenExist_returnOnePet() {
        // given
        final var fakePetId = fakeDataGenerator.generateRandomInteger();
        final var fakePet = fakeDataGenerator.generateFakePet();
        final var fakePetResponse = fakeDataGenerator.generateFakePetResponse();
        given(mockPetRepository.findById(fakePetId)).willReturn(Optional.of(fakePet));
        given(mockMapper.map(Optional.of(fakePet), PetResponse.class)).willReturn(fakePetResponse);

        // when
        final var actual = serviceUnderTest.getPet(fakePetId);

        // then
        then(actual).isEqualTo(fakePetResponse);
    }

    @Test
    public void getPet_whenNotExist_returnEmptyPetResponse() {
        // given
        final var fakePetId = fakeDataGenerator.generateRandomInteger();
        final var fakePetResponse = new PetResponse();
        given(mockPetRepository.findById(fakePetId)).willReturn(Optional.empty());

        // when
        final var actual = serviceUnderTest.getPet(fakePetId);

        // then
        then(actual).isInstanceOf(PetResponse.class);
        then(actual).usingRecursiveComparison().isEqualTo(fakePetResponse);
    }

    @Test
    public void addPet_whenValidInput_returnsValidInput() {
        // given
        final var fakePetRequest = fakeDataGenerator.generateFakePetRequest();
        final var fakePet = fakeDataGenerator.generateFakePet();
        final var fakePetResponse = fakeDataGenerator.generateFakePetResponse();
        given(mockMapper.map(fakePetRequest, Pet.class)).willReturn(fakePet);
        given(mockMapper.map(fakePet, PetResponse.class)).willReturn(fakePetResponse);

        // when
        final var actual = serviceUnderTest.addPet(fakePetRequest);

        // then
        then(actual).isEqualTo(fakePetResponse);
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
        final var numberOfPets = 3;
        final var fakePets = fakeDataGenerator.generateFakePetList(numberOfPets);
        final List<PetResponse> fakePetResponses = fakeDataGenerator.generateFakePetResponses(numberOfPets);

        given(mockPetRepository.findAll()).willReturn(fakePets);
        for (var i = 0; i < numberOfPets; i++) {
            given(mockMapper.map(fakePets.get(i), PetResponse.class))
                    .willReturn(fakePetResponses.get(i));
        }

        // when
        final var actual = serviceUnderTest.getAllPets();

        // then
        then(actual).isEqualTo(fakePetResponses);
        then(actual).hasSize(fakePetResponses.size());
    }

    @Test
    public void getAllPets_whenNoPets_returnsEmptyList() {
        // given
        final var fakePets = new ArrayList<Pet>();
        final List<PetResponse> fakePetResponses = new ArrayList<>();
        given(mockPetRepository.findAll()).willReturn(fakePets);

        // when
        final var actual = serviceUnderTest.getAllPets();
        verify(mockMapper, never()).map(any(), eq(PetResponse.class));

        // then
        then(actual).isEqualTo(fakePetResponses);
    }


    @Test
    public void updatePet_whenPetExists_returnsUpdatedPet() {
        // given
        final var fakePetRequest = fakeDataGenerator.generateFakePetRequest();
        final var fakePetId = fakeDataGenerator.generateRandomInteger();
        final var fakeOriginalPet = fakeDataGenerator.generateFakePet();
        final var fakeUpdatedPet = fakeOriginalPet.toBuilder()
                .name(fakePetRequest.getName())
                .type(fakePetRequest.getType())
                .age(fakePetRequest.getAge())
                .gender(fakePetRequest.getGender())
                .build();
        final var fakePetResponse = fakeDataGenerator.generateFakePetResponse();
        given(mockMapper.map(fakePetRequest, Pet.class)).willReturn(fakeUpdatedPet);
        given(mockPetRepository.findById(fakePetId))
                .willReturn(Optional.of(fakeOriginalPet));
        given(mockPetRepository.save(fakeOriginalPet))
                .willReturn(fakeUpdatedPet);
        given(mockMapper.map(fakeUpdatedPet, PetResponse.class)).willReturn(fakePetResponse);

        // when
        final var actual = serviceUnderTest.updatePet(fakePetRequest, fakePetId);

        // then
        verify(mockPetRepository).save(petCaptor.capture());
        assertThat(petCaptor.getValue()).usingRecursiveComparison().isEqualTo(fakeUpdatedPet);
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(fakePetResponse);
    }

    @Test
    public void updatePet_whenPetNotExists_throwEntityNotFoundException() {
        // given
        final var fakePetRequest = fakeDataGenerator.generateFakePetRequest();
        final var fakePetId = fakeDataGenerator.generateRandomInteger();
        given(mockPetRepository.findById(fakePetId))
                .willThrow(new EntityNotFoundException(String.valueOf(fakePetId)));

        // when && then
        final var exception = assertThrows(EntityNotFoundException.class, () -> {
            serviceUnderTest.updatePet(fakePetRequest, fakePetId);
        });
        then(exception.getMessage()).contains(String.valueOf(fakePetId));
    }

}