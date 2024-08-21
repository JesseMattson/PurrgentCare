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

    // Visibility (private) Type (PersonServiceImpl) Name(serviceUnderTest)
    private PersonServiceImplementation serviceUnderTest;

    @Mock
    private PersonRepository mockPersonRepository;

    @Mock
    private ModelMapper mockMapper;

    @Captor
    private ArgumentCaptor<Person> personCaptor;


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
        given(mockPersonRepository.findById(fakePersonId)).willReturn(Optional.of(fakePerson));
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
        final var fakePersonId = fakeDataGenerator.generateRandomInteger();
        final var fakePersonList = fakeDataGenerator.generatePersonList(fakeCountOfFakePerson);
        final var fakePersonResponse = fakeDataGenerator.generateFakePersonResponse();
        final var expected = fakeDataGenerator.generatePersonResponseList(fakeCountOfFakePerson);
        given(mockPersonRepository.findAll())
                .willReturn(fakePersonList);
        for (int i = 0; i < fakePersonList.size(); i++) {
            given(mockMapper.map(fakePersonList.get(i), PersonResponse.class))
                    .willReturn((expected.get(i)));
        }
        // when
        final var actual = serviceUnderTest.getAllPersons();

        // then
        then(actual).isEqualTo(expected);
        then(actual).hasSize(fakePersonList.size());
    }

//    @Test
//    public void getAllPersons_whenNoPersons_returnsEmptyList() {
//        // given
//        final var fakeCountOfFakePersons = 0;
//        final var fakePersonList = fakeDataGenerator.generatePersonList(fakeCountOfFakePersons);
//        final var expected = fakePersonList;
//        given(mockPersonRepository.findAll())
//                .willReturn(expected);
//
//        // when
//        final var actual = serviceUnderTest.getAllPersons();
//
//        // then
//        then(actual).isEqualTo(expected);
//    }

    @Test
    public void addPerson_whenValidInput_returnsValidInput() {
        // given
        // any mocks we need to simulate a person/repo etc
        final var fakePersonRequest = fakeDataGenerator.generateFakePersonRequest();
        final var fakePersonId = fakeDataGenerator.generateRandomInteger();
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
        //verify(mockMapper, times(1)).save(fakePerson);
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
        final var fakeOriginalPerson = fakeDataGenerator.generateFakePerson();
        final var fakeUpdatedPerson = fakeDataGenerator.generateFakePerson();
        fakeUpdatedPerson.setId(fakeOriginalPerson.getId());
        final var fakePersonResponse = fakeDataGenerator.generateFakePersonResponse();
        final var fakePersonRequest = fakeDataGenerator.generateFakePersonRequest();
        given(mockPersonRepository.findById(fakePersonId)).willReturn(Optional.of(fakeOriginalPerson));
        given(mockPersonRepository.save(fakeOriginalPerson)).willReturn(fakeUpdatedPerson);
        given(mockMapper.map(fakePersonRequest, Person.class)).willReturn(fakeUpdatedPerson);
        given(mockMapper.map(fakeUpdatedPerson, PersonResponse.class)).willReturn(fakePersonResponse);

        // when
        final var actual = serviceUnderTest.updatePerson(fakePersonRequest, fakePersonId);
        verify(mockPersonRepository).save(personCaptor.capture());
        Person capturedPerson = personCaptor.getValue();

        // then
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


