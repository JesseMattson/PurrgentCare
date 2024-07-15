package com.VetApp.PurrgentCare.dtos;

import java.util.List;

public class AssociatePeopleWithAccountRequest {
    public Integer accountId;
    public List<Integer> personIds;
}

    /* TODO:  we should be getting a list of PersonIDs from the front end.
        We don't have a way to get a list of persons from a list of PersonIDs
        We don't have a way to remove a person from an account.
        We would need to be able to pass a list of persons and update the account
        We don't have a way to add the list of accountHolders (to be added) to those already on the account
*/