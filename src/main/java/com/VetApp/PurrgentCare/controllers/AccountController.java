package com.VetApp.PurrgentCare.controllers;

import com.VetApp.PurrgentCare.dtos.AccountDto;
import com.VetApp.PurrgentCare.models.Account;

import com.VetApp.PurrgentCare.services.AccountServiceInterface;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController // Specifies that this is a Rest API
public class AccountController {

    private static final String BASE_URL = "/accounts";

    @Autowired // You don't need to initialize the object.
    private AccountServiceInterface accountService;


    private ModelMapper mapper;

    @GetMapping(BASE_URL + "/{id}") // PathVariable is used to get ID from the URL
    @ResponseStatus(HttpStatus.OK)
    private AccountDto getAccount(@PathVariable("id") Integer id) {
        var account = accountService.getAccount(id);
        return mapper.map(account, AccountDto.class);
    }

    @GetMapping(BASE_URL)
    @ResponseStatus(HttpStatus.OK)
    private List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }
    @PostMapping(BASE_URL)
    @ResponseStatus(HttpStatus.CREATED)
        private Account addAccount(@RequestBody Account account) {
         return accountService.addAccount(account);
        }

    @Deprecated
        // TODO: Need to create this with another feature.
    @DeleteMapping(BASE_URL + "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
        private void deleteAccount(@PathVariable("id") Integer accountId) {
            accountService.deleteAccount(accountId);

        }

    @PutMapping(BASE_URL + "/{id}")
    @ResponseStatus(HttpStatus.CREATED)
        private Account updateAccount(@PathVariable("id")  Integer accountId, @RequestBody Account account ) {
            return accountService.updateAccount(account, accountId);

        }

    @PutMapping(BASE_URL + "/status/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    private Account accountToggle(@PathVariable("id")  Integer accountId) {
        return accountService.accountToggle(accountId);

    }

}
