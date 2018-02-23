package com.z4ventures.budd.repositories;

import com.z4ventures.budd.models.Account;
import org.springframework.data.repository.Repository;

import java.util.Collection;
import java.util.Optional;

public interface AccountRepo extends Repository<Account, Long> {
    Optional<Account> findByUsername(String username);
    Account save(Account account);
    int deleteAccountById(Long id);
    Optional<Account> findById(Long userId);
    //Account saveOrUpdate(Account account);
}
