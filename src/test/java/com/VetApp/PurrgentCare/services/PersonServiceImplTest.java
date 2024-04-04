package com.VetApp.PurrgentCare.services;

import com.VetApp.PurrgentCare.models.Person;
import com.VetApp.PurrgentCare.repositories.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class PersonServiceImplTest {
    @Mock
    private PersonRepository mockPersonRepository;

    private PersonServiceImpl serviceUnderTest;

    @BeforeEach
    public void setUp() {
        serviceUnderTest = new PersonServiceImpl(mockPersonRepository);
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
