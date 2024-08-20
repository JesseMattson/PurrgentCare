package com.VetApp.PurrgentCare;

import com.VetApp.PurrgentCare.dtos.*;
import com.VetApp.PurrgentCare.models.Account;
import com.VetApp.PurrgentCare.models.Person;
import com.VetApp.PurrgentCare.models.Pet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class FakeDataGenerator {
    /////
    //General Types
    /////
    public Boolean generateRandomBoolean() {
        return new Random().nextBoolean();
    }

    public Integer generateRandomInteger() {
        return new Random().nextInt(1000);
    }

    public String generateRandomString() {
        return new Random().ints(97, 123)
                .limit(1000)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public Date generateRandomDate() {
        long minDay = LocalDate.of(1970, 1, 1).toEpochDay();
        long maxDay = LocalDate.now().toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay - 1);
        return new Date(randomDay);

    }

    /////
    //Pet Types
    /////
    public Pet generateFakePet() {
        return Pet.builder()
                .id(generateRandomInteger())
                .name(generateRandomString())
                .type(generateRandomString())
                .age(generateRandomInteger())
                .gender(generateRandomString())
                .build();
    }

    public List<Pet> generateFakePetList(int count) {
        var fakePetList = new ArrayList<Pet>();
        for (int i = 0; i < count; i++) {
            fakePetList.add(generateFakePet());
        }
        return fakePetList;
    }

    public List<Pet> generateDefaultPetList() {
        var fakeNumberOfPets = new Random().nextInt(1, 5);
        return generateFakePetList(fakeNumberOfPets);
    }

    public PetRequest generateFakePetRequest() {
        return PetRequest.builder().name(generateRandomString()).type(generateRandomString()).age(generateRandomInteger()).gender(generateRandomString()).build();
    }

    public PetResponse generateFakePetResponse() {
        return PetResponse.builder().id(generateRandomInteger()).name(generateRandomString()).type(generateRandomString()).age(generateRandomInteger()).gender(generateRandomString()).build();
    }

    public List<PetResponse> generateFakePetResponses(int count) {
        var fakePetResponses = new ArrayList<PetResponse>();
        for (var i = 0; i < count; i++) {
            fakePetResponses.add(generateFakePetResponse());
        }
        return fakePetResponses;
    }

    public List<PetResponse> generateDefaultFakePetResponses() {
        return generateFakePetResponses(5);
    }

    /////
    //Person Types
    /////
    public Person generatePerson(Integer fakePersonId) {
        return Person.builder().id(fakePersonId).build();
    }

    public List<Person> generatePersonList(Integer countOfFakePerson) {
        List<Person> fakePeopleList = new ArrayList<>(List.of());
        var i = 1;
        while (i <= countOfFakePerson) {
            var fakePerson = generatePerson(generateRandomInteger());
            fakePeopleList.add(fakePerson);
            i++;
        }
        return fakePeopleList;
    }

    public List<Person> generateDefaultPersonList() {
        final var numberFakePersons = new Random().nextInt(1, 5);
        return generatePersonList(numberFakePersons);
    }

    /////
    //Account Types
    /////
    public Account generateFakeAccount(Integer fakeAccountId, Boolean fakeActive, Date fakeDateCreated, List<Pet> fakePets, List<Person> fakeAccountHolders) {
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

    public List<Account> generateAccountList(Integer countOfFakeAccounts) {
        List<Account> fakeAccountList = new ArrayList<>(List.of());
        var i = 1;
        while (i <= countOfFakeAccounts) {
            var fakeAccount = generateFakeAccount(generateRandomInteger(), generateRandomBoolean(), generateRandomDate(), generateDefaultPetList(), generatePersonList(generateRandomInteger()));
            fakeAccountList.add(fakeAccount);
            i++;
        }
        return fakeAccountList;
    }

    public List<Account> generateDefaultAccountList() {
        final var numberFakeAccounts = new Random().nextInt(1, 5);
        return generateAccountList(numberFakeAccounts);
    }

    public AccountRequest generateFakeAccountRequest() {
        return AccountRequest.builder().active(generateRandomBoolean()).dateCreated(generateRandomDate()).build();

    }

    public AccountResponse generateFakeAccountResponse(Integer fakeId, Boolean fakeActive, Date fakeDateCreated, List<Pet> fakePets, List<Person> fakeAccountHolders) {
        final var accountResponse = new AccountResponse();
        accountResponse.setId(fakeId);
        accountResponse.setActive(fakeActive);
        accountResponse.setDateCreated(fakeDateCreated);
        accountResponse.setPets(fakePets);
        accountResponse.setAccountHolders(fakeAccountHolders);
        return accountResponse;
    }

    public AccountResponse generateDefaultAccountResponse() {
        final var accountResponse = new AccountResponse();
        accountResponse.setId(generateRandomInteger());
        accountResponse.setActive(generateRandomBoolean());
        accountResponse.setDateCreated(generateRandomDate());
        accountResponse.setPets(generateDefaultPetList());
        accountResponse.setAccountHolders(generateDefaultPersonList());
        return accountResponse;
    }

    public List<AccountResponse> generateFakeAccountResponses(int count) {
        var fakeAccountResponses = new ArrayList<AccountResponse>();
        for (var i = 0; i < count; i++) {
            fakeAccountResponses.add(generateDefaultAccountResponse());
        }
        return fakeAccountResponses;
    }

    public AssociatePeopleWithAccountRequest generateAssociatePeopleWithAccountRequest(Integer fakeAccountId, List<Integer> fakePersonIds) {
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
