package com.VetApp.PurrgentCare.services;

import com.VetApp.PurrgentCare.dtos.PersonRequest;
import com.VetApp.PurrgentCare.dtos.PersonResponse;
import com.VetApp.PurrgentCare.models.Person;
import com.VetApp.PurrgentCare.repositories.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PersonServiceImplementation implements PersonServiceInterface {

    private final PersonRepository personRepository;
    @Autowired
    private final ModelMapper mapper;


    public PersonServiceImplementation(PersonRepository personRepository, ModelMapper mapper) {
        this.personRepository = personRepository;
        this.mapper = mapper;
    }

    @Override
    public PersonResponse getPerson(Integer personId) {

        final var person = personRepository.findById(personId);
        if (person.isPresent()) {
            PersonResponse personResponse = mapper.map(person, PersonResponse.class);
            return personResponse;
        }
        return new PersonResponse();
    }

    @Override
    public PersonResponse addPerson(PersonRequest personRequest) {
        final Person person = mapper.map(personRequest, Person.class);
        personRepository.save(person);
        return mapper.map(person, PersonResponse.class);
    }

//    @Override
//    public List<PersonResponse> getAllPersons() {
//        final List<Person> personList = personRepository.findAll();
//        final List<PersonResponse> personResponse1 = new ArrayList<>();
//        for (Person person : personList){
//    PersonResponse personResponse = mapper.map(person, PersonResponse.class);
//    personResponse1.add(personResponse);
//        }
//        return personResponse1;
//    }


    @Override
    public void deletePerson(Integer personId) {
        personRepository.deleteById(personId);
    }


    @Override
    public PersonResponse updatePerson(PersonRequest personRequest, Integer personId) {
        final Person newPerson = mapper.map(personRequest, Person.class);
        Person person = personRepository.findById(personId).orElseThrow(() -> new EntityNotFoundException(String.valueOf(personId)));
        person.setName(newPerson.getName());
        person = personRepository.save(person);
        return mapper.map(person, PersonResponse.class);
    }


    private static Person buildDefaultPerson() {
        final var defaultPerson = new Person();
        defaultPerson.setName("default");
        return defaultPerson;
    }
}
