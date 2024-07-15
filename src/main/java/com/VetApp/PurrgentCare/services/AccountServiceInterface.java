package com.VetApp.PurrgentCare.services;

import com.VetApp.PurrgentCare.dtos.AccountResponse;
import com.VetApp.PurrgentCare.dtos.AssociatePeopleWithAccountRequest;
import com.VetApp.PurrgentCare.models.Account;
import com.VetApp.PurrgentCare.models.Person;

import java.util.List;

public interface AccountServiceInterface {


    Account getAccount(Integer personId);

    List<Account> getAllAccounts();

    Account addAccount(Account account);

    void deleteAccount(Integer personId);

    Account updateAccount(Account account, Integer personId);

    Account updateAccount(Account newAccount, Integer accountId, List<Person> accountHolders);

    Account accountToggle(Integer accountId);

    AccountResponse associatePeople(AssociatePeopleWithAccountRequest associatePeopleWithAccountRequest);
}

