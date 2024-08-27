package com.VetApp.PurrgentCare;

import com.VetApp.PurrgentCare.dtos.*;
import com.VetApp.PurrgentCare.models.Account;
import com.VetApp.PurrgentCare.models.Person;
import com.VetApp.PurrgentCare.models.Pet;
import org.instancio.Instancio;
import org.instancio.Model;
import org.instancio.generators.Generators;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

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

        return Person.builder()
                .id(generateRandomInteger())
                .name(generateRandomString())
                .build();
    }

    public List<Person> generateFakePersonList(Integer countOfFakePerson) {
        List<Person> fakePeopleList = new ArrayList<>(List.of());
        var i = 1;
        while (i <= countOfFakePerson) {
            var fakePerson = generateFakePerson();
            fakePeopleList.add(fakePerson);
            i++;
        }
        return fakePeopleList;
    }

    public List<Person> generateDefaultPersonList() {
        final var numberFakePersons = new Random().nextInt(1, 5);
        return generateFakePersonList(numberFakePersons);
    }

    public PersonRequest generateFakePersonRequest() {
        return PersonRequest.builder().name(generateRandomString()).build();
    }

    public PersonResponse generateFakePersonResponse() {

        return PersonResponse.builder().
                id(generateRandomInteger()).
                name(generateRandomString()).
                build();
    }

    public List<PersonResponse> generateFakePersonResponseList(Integer countOfFakePersonResponses) {
        List<PersonResponse> fakePersonResponseList = new ArrayList<>(List.of());
        var i = 1;
        while (i <= countOfFakePersonResponses) {
            var fakePersonResponse = generateFakePersonResponse();
            fakePersonResponseList.add(fakePersonResponse);
            i++;
        }
        return fakePersonResponseList;
    }

    /////
    //Account Types
    /////

    public Account generateFakeAccount(Integer fakeAccountId, Boolean fakeActive, Date
            fakeDateCreated, List<Pet> fakePets, List<Person> fakeAccountHolders) {
        return Account.builder().id(fakeAccountId).active(fakeActive).dateCreated(fakeDateCreated).pets(fakePets).accountHolders(makeListMutable(fakeAccountHolders)).build();
    }

    public Account generateDefaultAccount() {
        return Account.builder()
                .id(generateRandomInteger())
                .active(generateRandomBoolean())
                .dateCreated(generateRandomDate())
                .pets(generateDefaultPetList())
                .accountHolders(generateDefaultPersonList())
                .build();
    }

    public List<Account> generateFakeAccountList(Integer countOfFakeAccounts) {
        List<Account> fakeAccountList = new ArrayList<>(List.of());
        var i = 1;
        while (i <= countOfFakeAccounts) {
            var fakeAccount = generateFakeAccount(generateRandomInteger(), generateRandomBoolean(), generateRandomDate(), generateDefaultPetList(), generateFakePersonList(generateRandomInteger()));
            fakeAccountList.add(fakeAccount);
            i++;
        }
        return fakeAccountList;
    }

    public List<Account> generateDefaultAccountList() {
        final var numberFakeAccounts = new Random().nextInt(1, 5);
        return generateFakeAccountList(numberFakeAccounts);
    }

    public AccountRequest generateFakeAccountRequest() {
        return AccountRequest.builder().active(generateRandomBoolean()).dateCreated(generateRandomDate()).build();

    }

    public AccountResponse generateFakeAccountResponse(Integer fakeId, Boolean fakeActive, Date
            fakeDateCreated, List<Pet> fakePets, List<Person> fakeAccountHolders) {
        return AccountResponse.builder()
                .id(fakeId)
                .active(fakeActive)
                .dateCreated(fakeDateCreated)
                .pets(fakePets)
                .accountHolders(fakeAccountHolders)
                .build();
    }

    public AccountResponse generateDefaultAccountResponse() {
        return AccountResponse.builder()
                .id(generateRandomInteger())
                .active(generateRandomBoolean())
                .dateCreated(generateRandomDate())
                .pets(generateDefaultPetList())
                .accountHolders(generateDefaultPersonList())
                .build();
    }

    public List<AccountResponse> generateFakeAccountResponseList(int count) {
        var fakeAccountResponses = new ArrayList<AccountResponse>();
        for (var i = 0; i < count; i++) {
            fakeAccountResponses.add(generateDefaultAccountResponse());
        }
        return fakeAccountResponses;
    }

    public AssociatePeopleWithAccountRequest generateFakeAssociatePeopleWithAccountRequest(Integer
                                                                                                   fakeAccountId, List<Integer> fakePersonIds) {
        final var request = new AssociatePeopleWithAccountRequest();
        request.accountId = fakeAccountId;
        request.personIds = fakePersonIds;
        return request;
    }

    /////
    //Helper Methods
    /////
    public <T> List<T> makeListMutable(List<? extends T> inputList) {
        return new ArrayList<>(inputList);
    }

}
