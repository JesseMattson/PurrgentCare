package com.VetApp.PurrgentCare.services;

import com.VetApp.PurrgentCare.models.Person;
import com.VetApp.PurrgentCare.repositories.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PersonServiceImplTest {
    private PersonServiceImpl serviceUnderTest;

    @Mock
    private PersonRepository mockPersonRepository;

    @BeforeEach
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

}

