package com.VetApp.PurrgentCare.services;

import com.VetApp.PurrgentCare.dtos.AccountResponse;
import com.VetApp.PurrgentCare.dtos.AssociatePeopleWithAccountRequest;
import com.VetApp.PurrgentCare.models.Account;
import com.VetApp.PurrgentCare.models.Person;
import com.VetApp.PurrgentCare.repositories.AccountRepository;
import com.VetApp.PurrgentCare.repositories.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImplementation implements AccountServiceInterface {
    private final AccountRepository accountRepository;

    private final PersonRepository personRepository;

    private final ModelMapper mapper;


    public AccountServiceImplementation(AccountRepository accountRepository, PersonRepository personRepository, ModelMapper mapper) {
        this.accountRepository = accountRepository;
        this.personRepository = personRepository;
        this.mapper = mapper;
    }

    @Override
    public Account getAccount(Integer accountId) {

        final var account = accountRepository.findById(accountId);
        if (account.isPresent()) {
            return account.get();
        }
        return new Account();
    }


    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account addAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public void deleteAccount(Integer accountId) {
        accountRepository.deleteById(accountId);
    }

    @Override
    public Account updateAccount(Account newAccount, Integer accountId) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(accountId)));
        account.setActive(newAccount.getActive());
        account.setDateCreated(newAccount.getDateCreated());
        return accountRepository.save(account);
    }

    @Override
    public Account accountToggle(Integer accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(accountId)));
        account.setActive(!account.getActive());

        return accountRepository.save(account);
    }

    @Override
    public AccountResponse associatePeople(AssociatePeopleWithAccountRequest associatePeopleWithAccountRequest) {
        var personIds = associatePeopleWithAccountRequest.personIds;
        var accountId = associatePeopleWithAccountRequest.accountId;
        var people = personRepository.findAllById(personIds);
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(accountId)));
        //Had to add this loop to set the parent relationship of each child; check for duplicate children; add any new children to the parent.
        for (Person person : people) {
            person.setAccount(account);
            if (!account.getAccountHolders().contains(person)) {
                account.getAccountHolders().add(person);
            }
        }
        //TODO can delete this if everything is good
        //account.setAccountHolders(people);
        Account updatedAccount = accountRepository.save(account);
        AccountResponse accountResponse = mapper.map(updatedAccount, AccountResponse.class);
        return accountResponse;
    }

}

