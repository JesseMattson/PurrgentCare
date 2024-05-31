package com.VetApp.PurrgentCare.controllers;

import com.VetApp.PurrgentCare.models.Account;

import com.VetApp.PurrgentCare.models.Person;
import com.VetApp.PurrgentCare.services.AccountServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController // Specifies that this is a Rest API
public class AccountController {

    private static final String BASE_URL = "/accounts";

    @Autowired // You don't need to initialize the object.
    AccountServiceInterface accountService;

    @GetMapping(BASE_URL + "/{id}") // PathVariable is used to get ID from the URL
    private Account getAccount(@PathVariable("id") Integer id) {

        return accountService.getAccount(id);
    }

    @GetMapping(BASE_URL)
    private List<Account> getAllAccounts() {

        return accountService.getAllAccounts();
    }
    @PostMapping(BASE_URL)
        private void addAccount(@RequestBody Account account) {
            accountService.addAccount(account);
        }


        // TODO: Need to create this with another feature.
//    @DeleteMapping("/accounts/DeleteAccount/{id}")
//        private void deleteAccount(@PathVariable("id") Integer accountId) {
//            accountService.deleteAccount(accountId);
//
//        }

    @PutMapping(BASE_URL + "/{id}")
        private Account updateAccount(@PathVariable("id")  Integer accountId, @RequestBody Account account ) {
            return accountService.updateAccount(account, accountId);

        }


}