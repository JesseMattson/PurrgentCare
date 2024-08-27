package com.VetApp.PurrgentCare;

import com.VetApp.PurrgentCare.dtos.*;
import com.VetApp.PurrgentCare.models.Account;
import com.VetApp.PurrgentCare.models.Person;
import com.VetApp.PurrgentCare.models.Pet;
import org.instancio.Instancio;
import org.instancio.Model;
import org.instancio.generators.Generators;

import java.util.Date;
import java.util.List;

import static org.instancio.Select.field;


public class FakeDataGenerator {
    /////
    //Instancio Models
    /////
    Model<Pet> petModel = Instancio.of(Pet.class)
            .generate(field(Pet::getId), Generators::ints)
            .generate(field(Pet::getName), Generators::string)
            .generate(field(Pet::getType), Generators::string)
            .generate(field(Pet::getAge), Generators::ints)
            .generate(field(Pet::getGender), Generators::string)
            .toModel();
//    Model<Pet> petModel = Instancio.of(Pet.class)
//            .generate(field(Pet::getId), Generators::ints)
//            .generate(field(Pet::getName), Generators::string)
//            .generate(field(Pet::getType), Generators::string)
//            .generate(field(Pet::getAge), Generators::ints)
//            .generate(field(Pet::getGender), Generators::string)
//            .toModel();

    /////
    //General Types
    /////
    public Boolean generateRandomBoolean() {
        return Instancio.gen().booleans().get();
    }

    public Integer generateRandomInteger() {
        return Instancio.gen().ints().get();
    }

    public String generateRandomString() {
        return Instancio.gen().string().get();
    }

    public Date generateRandomDate() {
        return Instancio.gen().temporal().date().get();
    }

    /////
    //Pet Types
    /////
    public Pet generateFakePet() {
        return Instancio.of(Pet.class).create();
    }

    public List<Pet> generateFakePetList(int count) {
        return Instancio.ofList(Pet.class).size(count).create();
    }

    public List<Pet> generateDefaultPetList() {
        return Instancio.ofList(Pet.class).create();
    }

    public PetRequest generateFakePetRequest() {
        return Instancio.of(PetRequest.class).create();
    }

    public PetResponse generateFakePetResponse() {
        return Instancio.of(PetResponse.class).create();
    }

    public List<PetResponse> generateFakePetResponses(int count) {
        return Instancio.ofList(PetResponse.class).size(count).create();
    }

    public List<PetResponse> generateDefaultFakePetResponses() {
        return Instancio.ofList(PetResponse.class).create();
    }

    /////
    //Person Types
    /////

    public Person generateFakePerson() {
        return Instancio.of(Person.class).create();
    }

    public List<Person> generateFakePersonList(Integer countOfFakePerson) {
        return Instancio.ofList(Person.class).size(countOfFakePerson).create();
    }

    public List<Person> generateDefaultPersonList() {
        return Instancio.ofList(Person.class).create();
    }

    public PersonRequest generateFakePersonRequest() {
        return Instancio.of(PersonRequest.class).create();
    }

    public PersonResponse generateFakePersonResponse() {
        return Instancio.of(PersonResponse.class).create();
    }

    public List<PersonResponse> generateFakePersonResponseList(Integer countOfFakePersonResponses) {
        return Instancio.ofList(PersonResponse.class).size(countOfFakePersonResponses).create();
    }

    /////
    //Account Types
    /////

    public Account generateDefaultAccount() {
        return Instancio.of(Account.class).create();
    }

    public List<Account> generateDefaultAccountList() {
        return Instancio.ofList(Account.class).create();
    }

    public AccountRequest generateFakeAccountRequest() {
        return Instancio.of(AccountRequest.class).create();

    }

    public AccountResponse generateDefaultAccountResponse() {
        return Instancio.of(AccountResponse.class).create();
    }

    public List<AccountResponse> generateFakeAccountResponseList(int count) {
        return Instancio.ofList(AccountResponse.class).size(count).create();
    }

    public AssociatePeopleWithAccountRequest generateFakeAssociatePeopleWithAccountRequest() {
        return Instancio.of(AssociatePeopleWithAccountRequest.class).create();
    }
}
