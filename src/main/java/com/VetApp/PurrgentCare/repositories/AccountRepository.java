package com.VetApp.PurrgentCare.repositories;

import com.VetApp.PurrgentCare.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository
    extends JpaRepository<Account, Integer> {
}
