package com.VetApp.PurrgentCare;

import com.VetApp.PurrgentCare.dtos.AccountResponse;
import com.VetApp.PurrgentCare.dtos.AssociatePeopleWithAccountRequest;
import com.VetApp.PurrgentCare.models.Account;
import com.VetApp.PurrgentCare.models.Person;
import com.VetApp.PurrgentCare.models.Pet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class FakeDataGenerator {
    public Boolean generateRandomBoolean() {
        return new Random().nextBoolean();
    }

    public Integer generateRandomInteger() {
        return new Random().nextInt(1000);
    }

    public AssociatePeopleWithAccountRequest generateAssociatePeopleWithAccountRequest(Integer fakeAccountId, List<Integer> fakePersonIds) {
        final var request = new AssociatePeopleWithAccountRequest();
        request.accountId = fakeAccountId;
        request.personIds = fakePersonIds;
        return request;
    }

    public Person generatePerson(Integer fakePersonId) {
        return Person.builder()
                .id(fakePersonId)
                .build();
    }

    public Account generateAccount(Integer fakeAccountId, Boolean fakeActive, Date fakeDateCreated, List<Pet> fakePets, List<Person> fakeAccountHolders) {
        return Account.builder()
                .id(fakeAccountId)
                .active(fakeActive)
                .dateCreated(fakeDateCreated)
                .pets(fakePets)
                .accountHolders(makeListMutable(fakeAccountHolders))
                .build();
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
