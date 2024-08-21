package com.VetApp.PurrgentCare.controllers;

import com.VetApp.PurrgentCare.dtos.AccountRequest;
import com.VetApp.PurrgentCare.dtos.AccountResponse;
import com.VetApp.PurrgentCare.dtos.AssociatePeopleWithAccountRequest;
import com.VetApp.PurrgentCare.services.AccountServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController // Specifies that this is a Rest API
public class AccountController {

    private static final String BASE_URL = "/accounts";

    @Autowired // You don't need to initialize the object.
    AccountServiceInterface accountService;

    @GetMapping(BASE_URL + "/{id}") // PathVariable is used to get ID from the URL
    @ResponseStatus(HttpStatus.OK)
    private AccountResponse getAccount(@PathVariable("id") Integer id) {

        return accountService.getAccount(id);
    }

    @GetMapping(BASE_URL)
    @ResponseStatus(HttpStatus.OK)
    private List<AccountResponse> getAllAccounts() {

        return accountService.getAllAccounts();
    }

    @PostMapping(BASE_URL)
    @ResponseStatus(HttpStatus.CREATED)
    private AccountResponse addAccount(@RequestBody AccountRequest accountRequest) {
        return accountService.addAccount(accountRequest);
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
    private AccountResponse updateAccount(@PathVariable("id") Integer accountId, @RequestBody AccountRequest accountRequest) {
        return accountService.updateAccount(accountRequest, accountId);

    }

    @PutMapping(BASE_URL + "/status/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    private AccountResponse accountToggle(@PathVariable("id") Integer accountId) {
        return accountService.accountToggle(accountId);

    }

    @PutMapping(BASE_URL + "/associate-people")
    @ResponseStatus(HttpStatus.CREATED)
    private AccountResponse accountAssociatePeople(@RequestBody AssociatePeopleWithAccountRequest associatePeopleWithAccountRequest) {
        return accountService.associatePeople(associatePeopleWithAccountRequest);
    }
}
