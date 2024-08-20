package com.VetApp.PurrgentCare.services;

import com.VetApp.PurrgentCare.dtos.AccountRequest;
import com.VetApp.PurrgentCare.dtos.AccountResponse;
import com.VetApp.PurrgentCare.dtos.AssociatePeopleWithAccountRequest;

import java.util.List;

public interface AccountServiceInterface {


    AccountResponse getAccount(Integer personId);

    List<AccountResponse> getAllAccounts();

    AccountResponse addAccount(AccountRequest accountRequest);

    void deleteAccount(Integer personId);

    AccountResponse updateAccount(AccountRequest accountRequest, Integer personId);

    AccountResponse accountToggle(Integer accountId);

    AccountResponse associatePeople(AssociatePeopleWithAccountRequest associatePeopleWithAccountRequest);
}

