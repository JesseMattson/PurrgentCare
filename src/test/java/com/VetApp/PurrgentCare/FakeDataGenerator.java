package com.VetApp.PurrgentCare;

import com.VetApp.PurrgentCare.dtos.*;
import com.VetApp.PurrgentCare.models.Account;
import com.VetApp.PurrgentCare.models.Person;
import com.VetApp.PurrgentCare.models.Pet;
import org.instancio.Instancio;

import java.util.List;

import static org.instancio.Select.field;


public class FakeDataGenerator {
    /////
    //General Types
    /////

    public Integer generateRandomInteger() {
        return Instancio.gen().ints().get();
    }

    /////
    //Pet Types
    /////
    public Pet generateFakePet() {
        return Instancio.of(Pet.class).create();

    }

    /**
     * Generate fake pet based on exisiting fake pet that is passed in.
     *
     * @param fakePet
     * @return Pet object based on original pet with changed params.
     */
    public Pet generateFakePet(Pet fakePet) {
        return Instancio.of(Pet.class)
                .set(field(Pet::getId), fakePet.getId())
                .set(field(Pet::getAccount), fakePet.getAccount())
                .create();
    }


    public List<Pet> generateFakePetList(int count) {
        return Instancio.ofList(Pet.class).size(count).create();
    }

    public PetRequest generateFakePetRequest() {
        return Instancio.of(PetRequest.class).create();
    }

    public PetResponse generateFakePetResponse() {
        return Instancio.of(PetResponse.class).create();
    }

    public PetResponse generateEmptyPetResponse() {
        return Instancio.ofBlank(PetResponse.class)
                .create();
    }

    public List<PetResponse> generateFakePetResponses(int count) {
        return Instancio.ofList(PetResponse.class).size(count).create();
    }


    /////
    //Person Types
    /////

    public Person generateFakePerson() {
        return Instancio.of(Person.class).create();
    }

    /**
     * Generate fake person based on exisiting fake pet that is passed in.
     *
     * @param fakePerson
     * @return Person object based on original pet with changed params.
     */
    public Person generateFakePerson(Person fakePerson) {
        return Instancio.of(Person.class)
                .set(field(Person::getId), fakePerson.getId())
                .set(field(Person::getAccount), fakePerson.getAccount())
                .create();
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

    public Account generateFakeAccount() {
        return Instancio.of(Account.class).create();
    }

    public Account generateFakeAccount(Account fakeAccount) {
        return Instancio.of(Account.class)
                .set(field(Account::getId), fakeAccount.getId())
                .set(field(Account::getPets), fakeAccount.getPets())
                .set(field(Account::getAccountHolders), fakeAccount.getAccountHolders())
                .create();
    }

    public List<Account> generatefakeAccountList() {
        return Instancio.ofList(Account.class).create();
    }

    public AccountRequest generateFakeAccountRequest() {
        return Instancio.of(AccountRequest.class).create();

    }

    public AccountResponse generateFakeAccountResponse() {
        return Instancio.of(AccountResponse.class).create();
    }

    public List<AccountResponse> generateFakeAccountResponseList(int count) {
        return Instancio.ofList(AccountResponse.class).size(count).create();
    }

    public AssociatePeopleWithAccountRequest generateFakeAssociatePeopleWithAccountRequest() {
        return Instancio.of(AssociatePeopleWithAccountRequest.class).create();
    }
}
