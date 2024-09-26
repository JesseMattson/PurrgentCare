package com.VetApp.PurrgentCare.services;

import com.VetApp.PurrgentCare.FakeDataGenerator;
import com.VetApp.PurrgentCare.dtos.PersonResponse;
import com.VetApp.PurrgentCare.models.Person;
import com.VetApp.PurrgentCare.repositories.PersonRepository;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PersonServiceImplementationTest {

    private final FakeDataGenerator fakeDataGenerator = new FakeDataGenerator();
    // Visibility (private) Type (PersonServiceImpl) Name(serviceUnderTest)
    private PersonServiceImplementation serviceUnderTest;
    @Mock
    private PersonRepository mockPersonRepository;
    @Mock
    private ModelMapper mockMapper;
    @Captor
    private ArgumentCaptor<Person> personCaptor;

    @BeforeEach
    // Allows serviceUnderTest to use new Instance (class) for each test.
    public void setup() {
        this.serviceUnderTest = new PersonServiceImplementation(mockPersonRepository, mockMapper);
    }

    @Test
    public void getPerson_whenExist_returnOnePerson() {
        // given


        final var fakePerson = fakeDataGenerator.generateFakePerson();
        final var fakePersonId = fakePerson.getId();
        final var fakePersonResponse = fakeDataGenerator.generateFakePersonResponse();
        given(mockPersonRepository.findById(fakePersonId)).willReturn(Optional.of(fakePerson));
        given(mockMapper.map(Optional.of(fakePerson), PersonResponse.class)).willReturn(fakePersonResponse);

        // when
        final var actual = serviceUnderTest.getPerson(fakePersonId);

        // then
        then(actual).isEqualTo(fakePersonResponse);
    }
// ToDo: getPerson_whenNotExist_returnEmptyPersonResponse TEST

    @Test

    public void getAllPersons_withValidInput_returnsAllPersons() {
        // given
        final var fakeNumberOfFakePets = fakeDataGenerator.generateRandomInteger();
        final var fakePersons = fakeDataGenerator.generateFakePersonList(fakeNumberOfFakePets);
        final List<PersonResponse> fakePersonResponses = fakeDataGenerator.generateFakePersonResponseList(fakeNumberOfFakePets);
        given(mockPersonRepository.findAll()).willReturn(fakePersons);
        for (var i = 0; i < fakeNumberOfFakePets; i++) {
            given(mockMapper.map(fakePersons.get(i), PersonResponse.class)).willReturn(fakePersonResponses.get(i));
        }
        // when
        final var actual = serviceUnderTest.getAllPersons();

        // then
        then(actual).isEqualTo(fakePersonResponses);
        then(actual).hasSize(fakePersonResponses.size());
    }

    @Test
    public void getAllPersons_whenNoPersons_returnsEmptyList() {
        // given

        final var fakePersons = new ArrayList<Person>();
        final List<PersonResponse> fakePersonResponses = new ArrayList<>();
        given(mockPersonRepository.findAll()).willReturn(fakePersons);

        // when
        final var actual = serviceUnderTest.getAllPersons();

        // then
        then(actual).isEqualTo(fakePersonResponses);
    }

    @Test
    public void addPerson_whenValidInput_returnsValidInput() {
        // given
        // any mocks we need to simulate a person/repo etc
        final var fakePersonRequest = fakeDataGenerator.generateFakePersonRequest();
        final var fakePerson = fakeDataGenerator.generateFakePerson();
        final var fakePersonResponse = fakeDataGenerator.generateFakePersonResponse();
        given(mockMapper.map(fakePersonRequest, Person.class)).willReturn(fakePerson);
        given(mockMapper.map(fakePerson, PersonResponse.class)).willReturn(fakePersonResponse);

        // when
        // Mocked Person added
        final var actual = serviceUnderTest.addPerson(fakePersonRequest);

        // then
        // check repo to see if person was added once
        then(actual).isEqualTo(fakePersonResponse);
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
        final var fakePersonRequest = fakeDataGenerator.generateFakePersonRequest();
        final var fakeOriginalPerson = fakeDataGenerator.generateFakePerson();
        final var fakeUpdatedPerson = fakeDataGenerator.generateFakePerson(fakeOriginalPerson);
        final var fakePersonResponse = fakeDataGenerator.generateFakePersonResponse();
        given(mockPersonRepository.findById(fakeOriginalPerson.getId())).willReturn(Optional.of(fakeOriginalPerson));
        given(mockPersonRepository.save(fakeOriginalPerson)).willReturn(fakeUpdatedPerson);
        given(mockMapper.map(fakePersonRequest, Person.class)).willReturn(fakeUpdatedPerson);
        given(mockMapper.map(fakeUpdatedPerson, PersonResponse.class)).willReturn(fakePersonResponse);

        // when
        final var actual = serviceUnderTest.updatePerson(fakePersonRequest, fakeOriginalPerson.getId());

        // then
        verify(mockPersonRepository).save(personCaptor.capture());
        assertThat(personCaptor.getValue()).usingRecursiveComparison().isEqualTo(fakeUpdatedPerson);
        assertThat(actual).usingRecursiveComparison().isEqualTo(fakePersonResponse);
    }

    @Test
    public void updatePerson_whenPersonNotExists_throwEntityNotFoundException() {
        // given
        final var fakePersonRequest = fakeDataGenerator.generateFakePersonRequest();
        final var fakePersonId = fakeDataGenerator.generateRandomInteger();
        given(mockPersonRepository.findById(fakePersonId)).willThrow(new EntityNotFoundException(String.valueOf(fakePersonId)));

        // when && then
        final var exception = assertThrows(EntityNotFoundException.class, () -> {
            serviceUnderTest.updatePerson(fakePersonRequest, fakePersonId);
        });
        then(exception.getMessage()).contains(String.valueOf(fakePersonId));
    }


}


