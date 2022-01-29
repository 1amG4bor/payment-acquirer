package com.g4bor.payment.acquirer.repository;

import com.g4bor.payment.acquirer.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    Account findAccountByAccountId(UUID accountId);
    Account findAccountByUsername(String username);
}
