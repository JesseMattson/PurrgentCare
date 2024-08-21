package com.VetApp.PurrgentCare.services;

import com.VetApp.PurrgentCare.dtos.AccountRequest;
import com.VetApp.PurrgentCare.dtos.AccountResponse;
import com.VetApp.PurrgentCare.dtos.AssociatePeopleWithAccountRequest;

import java.util.List;

public interface AccountServiceInterface {


    AccountResponse getAccount(Integer accountId);

    List<AccountResponse> getAllAccounts();

    AccountResponse addAccount(AccountRequest accountRequest);

    void deleteAccount(Integer accountId);

    AccountResponse updateAccount(AccountRequest accountRequest, Integer accountId);

    AccountResponse accountToggle(Integer accountId);

    AccountResponse associatePeople(AssociatePeopleWithAccountRequest associatePeopleWithAccountRequest);
}

