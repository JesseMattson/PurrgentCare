package com.VetApp.PurrgentCare;

import com.VetApp.PurrgentCare.dtos.AccountResponse;
import com.VetApp.PurrgentCare.dtos.AssociatePeopleWithAccountRequest;
import com.VetApp.PurrgentCare.models.Account;
import com.VetApp.PurrgentCare.models.Person;
import com.VetApp.PurrgentCare.models.Pet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.mockito.Mockito.mock;

public class FakeDataGenerator {
    public Boolean generateRandomBoolean() {
        return new Random().nextBoolean();
    }

    public Integer generateRandomInteger() {
        return new Random().nextInt(1000);
    }

    public Date generateRandomDate() {
        long minDay = LocalDate.of(1970, 1, 1).toEpochDay();
        long maxDay = LocalDate.now().toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay - 1);
        return new Date(randomDay);

    }

    public AssociatePeopleWithAccountRequest generateAssociatePeopleWithAccountRequest(Integer fakeAccountId, List<Integer> fakePersonIds) {
        final var request = new AssociatePeopleWithAccountRequest();
        request.accountId = fakeAccountId;
        request.personIds = fakePersonIds;
        return request;
    }

    public Person generatePerson(Integer fakePersonId) {
        return Person.builder().id(fakePersonId).build();
    }

    public Pet generatePet(Integer fakePetId) {
        return Pet.builder().id(fakePetId).build();
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

    public List<Pet> generatePetList(Integer countOfFakePets) {
        List<Pet> fakePetList = new ArrayList<>(List.of());
        var i = 1;
        while (i <= countOfFakePets) {
            var fakePet = generatePet(generateRandomInteger());
            fakePetList.add(fakePet);
            i++;
        }
        return fakePetList;
    }

    public List<Account> generateAccountList(Integer countOfFakeAccounts) {
        List<Account> fakeAccountList = new ArrayList<>(List.of());
        var i = 1;
        while (i <= countOfFakeAccounts) {
            var fakeAccount = generateAccount(generateRandomInteger(), generateRandomBoolean(), generateRandomDate(), generatePetList((generateRandomInteger())), generatePersonList(generateRandomInteger()));
            fakeAccountList.add(fakeAccount);
            i++;
        }
        return fakeAccountList;
    }


    public Account generateAccount(Integer fakeAccountId, Boolean fakeActive, Date fakeDateCreated, List<Pet> fakePets, List<Person> fakeAccountHolders) {
        return Account.builder().id(fakeAccountId).active(fakeActive).dateCreated(fakeDateCreated).pets(fakePets).accountHolders(makeListMutable(fakeAccountHolders)).build();
    }

    public AccountResponse generateAccountResponse(Boolean fakeActive, Date fakeDateCreated, List<Pet> fakePets, List<Person> fakeAccountHolders) {
        final var accountResponse = new AccountResponse();
        accountResponse.active = fakeActive;
        accountResponse.dateCreated = fakeDateCreated;
        accountResponse.pets = fakePets;
        accountResponse.accountHolders = fakeAccountHolders;
        return accountResponse;
    }

    public <T> List<T> makeListMutable(List<? extends T> inputList) {
        return new ArrayList<>(inputList);
    }

}
