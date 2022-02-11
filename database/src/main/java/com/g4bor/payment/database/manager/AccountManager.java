package com.g4bor.payment.database.manager;

import com.g4bor.payment.database.converter.AccountConverter;
import com.g4bor.payment.database.exception.ErrorMsg;
import com.g4bor.payment.database.exception.EntityNotFoundException;
import com.g4bor.payment.database.model.Account;
import com.g4bor.payment.database.model.Wallet;
import com.g4bor.payment.database.repository.AccountRepository;
import com.g4bor.payment.entity.model.AccountDTO;
import com.g4bor.payment.entity.model.AccountRegistrationDTO;
import com.g4bor.payment.entity.model.Currency;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccountManager {

    private final AccountRepository accountRepository;
    private final AccountConverter accountConverter;

    public AccountManager(AccountRepository accountRepository, AccountConverter accountConverter) {
        this.accountRepository = accountRepository;
        this.accountConverter = accountConverter;
    }

    public Account createAccount(AccountRegistrationDTO registrationDTO) {
        Account account = accountConverter.registrationDtoToEntity(registrationDTO);
        return accountRepository.save(account);
    }

    public Account addWallet(UUID accountId, Currency currency) throws NoSuchElementException, IllegalArgumentException {
        Account account = accountRepository.findAccountByAccountId(accountId);
        if (null == account) {
            throw new EntityNotFoundException(ErrorMsg.ACCOUNT_NOT_FOUND.formatMessage(accountId.toString()));
        }

        List<String> existingCurrencies = account.getWallets().stream()
                .map(item -> item.getCurrency().getIsoCode())
                .collect(Collectors.toList());
        if (existingCurrencies.contains(currency.getIsoCode())) {
            throw new IllegalArgumentException(ErrorMsg.ALREADY_USED_CURRENCY.formatMessage(currency.getIsoCode()));
        }

        account.addWallet(new Wallet(currency, 0.0));
        return accountRepository.save(account);
    }

    public Account getAccountById(UUID accountId) {
        Account account = accountRepository.findAccountByAccountId(accountId);
        if (null == account) {
            throw new EntityNotFoundException(ErrorMsg.ACCOUNT_NOT_FOUND.formatMessage(accountId.toString()));
        }
        return account;
    }

    public List<Account> getAccounts() {
        return new ArrayList<>(accountRepository.findAll());
    }

    public Set<Wallet> getWalletsForAccount(UUID accountId) {
        Account account = accountRepository.findAccountByAccountId(accountId);
        if (null == account) {
            throw new EntityNotFoundException(ErrorMsg.ACCOUNT_NOT_FOUND.formatMessage(accountId.toString()));
        }
        return new HashSet<Wallet>(account.getWallets());
    }

    public Account updateAccount(AccountDTO accountDto) {
        Account originalAccount = accountRepository.findAccountByAccountId(accountDto.getAccountId());
        if (null == originalAccount) {
            throw new EntityNotFoundException(ErrorMsg.ACCOUNT_NOT_FOUND.formatMessage(accountDto.getAccountId().toString()));
        }

        Account updatedAccount = accountConverter.dtoToEntity(accountDto);
        return accountRepository.save(updatedAccount);
    }

    public Account changeUsername(UUID accountId, String username) {
        Account account = accountRepository.findAccountByAccountId(accountId);
        account.setUsername(username);
        return accountRepository.save(account);
    }

    public void deleteById(UUID accountId) {
        if (null == accountId) {
            throw new IllegalArgumentException("AccountId needs to be provided for delete operation!");
        }
        accountRepository.deleteById(accountId);
    }

    public Account deleteWallet(UUID accountId, Long walletId) {
        Account account = accountRepository.findAccountByAccountId(accountId);
        if (null == account) {
            throw new EntityNotFoundException(ErrorMsg.ACCOUNT_NOT_FOUND.formatMessage(accountId.toString()));
        }

        boolean isRemoved = account.removeWallet(walletId);
        if (!isRemoved) {
            throw new EntityNotFoundException(ErrorMsg.WALLET_NOT_FOUND.formatMessage(walletId.toString()));
        }
        return accountRepository.save(account);
    }
}


