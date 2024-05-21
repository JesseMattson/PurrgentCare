package com.VetApp.PurrgentCare.services;

import com.VetApp.PurrgentCare.models.Account;
import com.VetApp.PurrgentCare.models.Person;
import com.VetApp.PurrgentCare.repositories.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImplementation implements AccountServiceInterface {
    private final AccountRepository accountRepository;

    public AccountServiceImplementation(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
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
    public void addAccount(Account account) {
        accountRepository.save(account);
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

}
//    private static Account buildDefaultAccount() {
//        final var defaultAccount = new Account();
//        defaultAccount.setName("default");
//        return defaultAccount;
//    }
//}
