package com.VetApp.PurrgentCare.services;

import com.VetApp.PurrgentCare.models.Person;
import com.VetApp.PurrgentCare.repositories.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImplementation implements PersonServiceInterface {
    private final PersonRepository personRepository;

    public PersonServiceImplementation(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
 @Override
    public Person getPerson(Integer personId) {

        final var person = personRepository.findById(personId);
        if (person.isPresent()) {
            return person.get();
        }
        //TODO Add ErrorHandler Class
        // throw new RuntimeException("PersonId: %d Not Found".formatted(personId));
        return buildDefaultPerson();
    }

    @Override
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @Override
    public void addPerson(Person person) {
        personRepository.save(person);
    }

    @Override
    public void deletePerson (Integer personId) {
        personRepository.deleteById(personId);
    }

    @Override
    public Person updatePerson(Person newPerson, Integer personId) {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(personId)));
        person.setName(newPerson.getName());
        return personRepository.save(person);
    }


    private static Person buildDefaultPerson() {
        final var defaultPerson = new Person();
        defaultPerson.setName("default");
        return defaultPerson;
    }
}
