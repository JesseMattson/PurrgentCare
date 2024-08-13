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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        given(mockMapper.map(fakePet, PetResponse.class)).willReturn(fakePetResponse);

        // when
        final var actual = serviceUnderTest.getPet(fakePetId);

        // then
        then(actual).isEqualTo(fakePetResponse);
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
        final var fakePets = fakeDataGenerator.generatePetList();
        final List<PetResponse> fakePetResponses = fakeDataGenerator.generateFakePetResponses();
        given(mockPetRepository.findAll()).willReturn(fakePets);
        when(mockMapper.map(any(Pet.class), eq(PetResponse.class))).thenAnswer(invocationOnMock -> {
            Pet fakePet = invocationOnMock.getArgument(0);
            int index = Arrays.asList(fakePets).indexOf(fakePet);
            return fakePetResponses.get(index);
        });

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
        final List<PetResponse> fakePetResponses = fakeDataGenerator.generateFakePetResponses();
        given(mockPetRepository.findAll()).willReturn(fakePets);
        given(fakePets.stream().map(element -> mockMapper.map(element, PetResponse.class)).collect(Collectors.toList())).willReturn(fakePetResponses);

        // when
        final var actual = serviceUnderTest.getAllPets();

        // then
        then(actual).isEqualTo(fakePetResponses);
    }


    @Test
    public void updatePet_whenPetExists_returnsUpdatedPet() {
        // given
        final var fakePetRequest = fakeDataGenerator.generateFakePetRequest();
        final var fakePetId = fakeDataGenerator.generateRandomInteger();
        final var fakeOriginalPet = fakeDataGenerator.generateFakePet();
        final var fakeUpdatedPet = Pet.builder().name("Maggie").type("Dog").age(3).gender("Female").build();
        final var fakePetResponse = fakeDataGenerator.generateFakePetResponse();
        given(mockMapper.map(fakePetRequest, Pet.class)).willReturn(fakeUpdatedPet);
        given(mockPetRepository.findById(fakePetId))
                .willReturn(Optional.of(fakeOriginalPet));
        when(mockPetRepository.save(fakeOriginalPet))
                .thenReturn(fakeUpdatedPet);
        given(mockMapper.map(fakeUpdatedPet, PetResponse.class)).willReturn(fakePetResponse);

        // when
        final var actual = serviceUnderTest.updatePet(fakePetRequest, fakePetId);
        verify(mockPetRepository).save(petCaptor.capture());
        Pet capturedPet = petCaptor.getValue();

        // then
        assertThat(capturedPet).usingRecursiveComparison().isEqualTo(fakeUpdatedPet);
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