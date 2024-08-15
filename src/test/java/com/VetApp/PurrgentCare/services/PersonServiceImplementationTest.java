package com.VetApp.PurrgentCare.services;

import com.VetApp.PurrgentCare.FakeDataGenerator;
import com.VetApp.PurrgentCare.dtos.PersonResponse;
import com.VetApp.PurrgentCare.models.Person;
import com.VetApp.PurrgentCare.repositories.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PersonServiceImplementationTest {

    // Visibility (private) Type (PersonServiceImpl) Name(serviceUnderTest)
    private PersonServiceImplementation serviceUnderTest;

    @Mock
    private PersonRepository mockPersonRepository;

    @Mock
    private ModelMapper mockMapper;



    private final FakeDataGenerator fakeDataGenerator = new FakeDataGenerator();

    @BeforeEach
    // Allows serviceUnderTest to use new Instance (class) for each test.
    public void setup() {
        this.serviceUnderTest = new PersonServiceImplementation(mockPersonRepository, mockMapper);
    }

    @Test
    public void getPerson_whenExist_returnOnePerson() {
        // given

        final var fakePersonId = fakeDataGenerator.generateRandomInteger();
        final var fakePerson = fakeDataGenerator.generateFakePerson();
        final var fakePersonResponse = fakeDataGenerator.generateFakePersonResponse();
        given(mockPersonRepository.findById(fakePersonId))
                .willReturn(Optional.of(fakePerson));
        given(mockMapper.map(Optional.of(fakePerson), PersonResponse.class)).willReturn(fakePersonResponse);

        // when
        final var actual = serviceUnderTest.getPerson(fakePersonId);

        // then
        then(actual).isEqualTo(fakePersonResponse);
    }

    @Test
    public void getAllPersons_withValidInput_returnsAllPersons() {
        // given
        final var fakeCountOfFakePerson = fakeDataGenerator.generateRandomInteger();
        final var expected = fakeDataGenerator.generatePersonList(fakeCountOfFakePerson);
        given(mockPersonRepository.findAll())
                .willReturn(expected);

        // when
        final var actual = serviceUnderTest.getAllPersons();

        // then
        then(actual).isEqualTo(expected);
        then(actual).hasSize(fakeCountOfFakePerson);
    }

    @Test
    public void getAllPersons_whenNoPersons_returnsEmptyList() {
        // given
        final var fakeCountOfFakePersons = 0;
        final var fakePersonList = fakeDataGenerator.generatePersonList(fakeCountOfFakePersons);
        final var expected = fakePersonList;
        given(mockPersonRepository.findAll())
                .willReturn(expected);

        // when
        final var actual = serviceUnderTest.getAllPersons();

        // then
        then(actual).isEqualTo(expected);
    }

    @Test
    public void addPerson_whenValidInput_returnsValidInput() {
        // given
        // any mocks we need to simulate a person/repo etc

        final var fakePersonId = fakeDataGenerator.generateRandomInteger();
        final var fakePerson = fakeDataGenerator.generateFakePerson();

        // when
        // Mocked Person added
        serviceUnderTest.addPerson(fakePerson);

        // then
        // check repo to see if person was added once
        verify(mockPersonRepository, times(1)).save(fakePerson);
    }

    @Test
    public void deletePerson_whenValidInput() {
        // given
        final var fakePersonId = fakeDataGenerator.generateRandomInteger();

        // when
        serviceUnderTest.deletePerson(fakePersonId);

        // then
        verify(mockPersonRepository, times(1)).deleteById(fakePersonId);

    }

    @Test
    public void updatePerson_whenPersonExists_returnsUpdatedPerson() {
        // given
        final var fakePersonId = fakeDataGenerator.generateRandomInteger();
        final var fakeAccountId = fakeDataGenerator.generateRandomInteger();
        final var fakeActive = fakeDataGenerator.generateRandomBoolean();
        final var fakeDateCreated = fakeDataGenerator.generateRandomDate();
        final var fakeCountOfFakePets = fakeDataGenerator.generateRandomInteger();
        final var fakeCountOfFakePersons = fakeDataGenerator.generateRandomInteger();
        final var fakeListPersons = fakeDataGenerator.generatePersonList(fakeCountOfFakePersons);
        final var fakeListPets = fakeDataGenerator.generatePetList(fakeCountOfFakePets);
        final var fakeAccount = fakeDataGenerator.generateAccount(fakeAccountId, fakeActive, fakeDateCreated, fakeListPets, fakeListPersons);
        final var fakeOriginalPerson = new Person(fakePersonId, "John notTest", fakeAccount);
        final var fakeUpdatedPerson = new Person(fakePersonId, "Gerald Test", fakeAccount);
        given(mockPersonRepository.findById(fakePersonId))
                .willReturn(Optional.of(fakeOriginalPerson));
        when(mockPersonRepository.save(any(Person.class)))
                .thenReturn(fakeUpdatedPerson);

        // when
        final var actual = serviceUnderTest.updatePerson(fakeUpdatedPerson, fakePersonId);

        // then
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(fakeOriginalPerson);
    }

    @Test
    public void updatePerson_whenPersonNotExists_throwEntityNotFoundException() {
        // given
        final var fakePersonId = fakeDataGenerator.generateRandomInteger();
        final var fakeAccountId = fakeDataGenerator.generateRandomInteger();
        final var fakeActive = fakeDataGenerator.generateRandomBoolean();
        final var fakeDateCreated = fakeDataGenerator.generateRandomDate();
        final var fakeCountOfFakePersons = fakeDataGenerator.generateRandomInteger();
        final var fakeCountOfFakePets = fakeDataGenerator.generateRandomInteger();
        final var fakePersonList = fakeDataGenerator.generatePersonList(fakeCountOfFakePersons);
        final var fakePetList = fakeDataGenerator.generatePetList(fakeCountOfFakePets);
        final var fakeAccount = fakeDataGenerator.generateAccount(fakeAccountId, fakeActive, fakeDateCreated, fakePetList, fakePersonList);
        final var updatedPerson = new Person(fakePersonId, "Gerald Test", fakeAccount);
        given(mockPersonRepository.findById(fakePersonId))
                .willThrow(new EntityNotFoundException(String.valueOf(fakePersonId)));

        // when && then
        final var exception = assertThrows(EntityNotFoundException.class, () -> {
            serviceUnderTest.updatePerson(updatedPerson, fakePersonId);
        });
        then(exception.getMessage()).contains(String.valueOf(fakePersonId));
    }


}


