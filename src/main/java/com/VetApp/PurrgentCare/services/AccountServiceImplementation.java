package com.VetApp.PurrgentCare.services;

import com.VetApp.PurrgentCare.dtos.AccountRequest;
import com.VetApp.PurrgentCare.dtos.AccountResponse;
import com.VetApp.PurrgentCare.dtos.AssociatePeopleWithAccountRequest;
import com.VetApp.PurrgentCare.models.Account;
import com.VetApp.PurrgentCare.repositories.AccountRepository;
import com.VetApp.PurrgentCare.repositories.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountServiceImplementation implements AccountServiceInterface {
    private final AccountRepository accountRepository;

    private final PersonRepository personRepository;
    @Autowired
    private final ModelMapper mapper;


    public AccountServiceImplementation(AccountRepository accountRepository, PersonRepository personRepository, ModelMapper mapper) {
        this.accountRepository = accountRepository;
        this.personRepository = personRepository;
        this.mapper = mapper;
    }

    @Override
    public AccountResponse getAccount(Integer accountId) {

        final var account = accountRepository.findById(accountId);
        if (account.isPresent()) {
            AccountResponse accountResponse =  mapper.map(account, AccountResponse.class);
            return accountResponse;
        }
        return new AccountResponse();
    }


    @Override
    public List<AccountResponse> getAllAccounts() {
        final List<Account> accounts = accountRepository.findAll();
        final List<AccountResponse> accountResponses = new ArrayList<>();
        for (Account account : accounts) {
            AccountResponse accountResponse = mapper.map(account, AccountResponse.class);
            accountResponses.add(accountResponse);
        }
        return accountResponses;
    }

    @Override
    public AccountResponse addAccount(AccountRequest accountRequest) {
        final Account account = mapper.map(accountRequest, Account.class);
        accountRepository.save(account);
        return mapper.map(account, AccountResponse.class);
    }

    @Override
    public void deleteAccount(Integer accountId) {
        accountRepository.deleteById(accountId);
    }

    @Override
    public AccountResponse updateAccount(AccountRequest accountRequest, Integer accountId) {
        final Account newAccount = mapper.map(accountRequest, Account.class);
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(accountId)));
        account.setActive(newAccount.getActive());
        account.setDateCreated(newAccount.getDateCreated());
        account = accountRepository.save(account);
        return mapper.map(account, AccountResponse.class);
    }

    @Override
    public AccountResponse accountToggle(Integer accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(accountId)));
        account.setActive(!account.getActive());
        accountRepository.save(account);
        return mapper.map(account, AccountResponse.class);
    }

    @Override
    public AccountResponse associatePeople(AssociatePeopleWithAccountRequest associatePeopleWithAccountRequest) {
        var personIds = associatePeopleWithAccountRequest.personIds;
        var accountId = associatePeopleWithAccountRequest.accountId;
        var people = personRepository.findAllById(personIds);
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(accountId)));
        account.addAccountHolders(people);
        Account updatedAccount = accountRepository.save(account);
        AccountResponse accountResponse = mapper.map(updatedAccount, AccountResponse.class);
        return accountResponse;
    }

}
