package com.VetApp.PurrgentCare.services;

import com.VetApp.PurrgentCare.models.Person;
import com.VetApp.PurrgentCare.repositories.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PersonServiceImplTest {

    // Visibility (private) Type (PersonServiceImpl) Name(serviceUnderTest)
    private PersonServiceImpl serviceUnderTest;

    @Mock
    private PersonRepository mockPersonRepository;

    @BeforeEach
    // Allows serviceUnderTest to use new Instance (class) for each test.
    public void setup() {
        this.serviceUnderTest = new PersonServiceImpl(mockPersonRepository);
    }

    @Test
    public void getPerson_whenExist_returnOnePerson() {
        // given
        final var fakePerson = mock(Person.class);
        final var fakePersonId = fakePerson.getId();
        given(mockPersonRepository.findById(fakePersonId))
                .willReturn(Optional.of(fakePerson));

        // when
        final var actual = serviceUnderTest.getPerson(fakePersonId);

        // then
        then(actual).isEqualTo(fakePerson);
    }

    @Test
    public void getAllPersons_withValidInput_returnsAllPersons() {
        // given
        final var countOfPersons = new Random().nextInt(1000);
        final var expected = buildPersonList(countOfPersons);
        given(mockPersonRepository.findAll())
                .willReturn(expected);

        // when
        final var actual = serviceUnderTest.getAllPersons();

        // then
        then(actual).isEqualTo(expected);
        then(actual).hasSize(countOfPersons);
    }

    @Test
    public void getAllPersons_whenNoPersons_returnsEmptyList() {
        // given
        final List<Person> expected = buildPersonList(0);
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
        final var fakePerson = mock(Person.class);

        // when
        // Mocked Person added
        serviceUnderTest.addPerson(fakePerson);

        // then
        // check repo to see if person was added once
        verify(mockPersonRepository,times(1)).save(fakePerson);
    }



    private List<Person> buildPersonList(Integer countOfPersons) {
        List<Person> personList = new ArrayList<>(List.of());
        var i = 1;
        while (i <= countOfPersons) {
            var person = mock(Person.class);
            personList.add(person);
            i++;
        }
        return personList;
    }
}

