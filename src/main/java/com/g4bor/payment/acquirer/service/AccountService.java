package com.g4bor.payment.acquirer.service;

import com.g4bor.payment.acquirer.converter.AccountConverter;
import com.g4bor.payment.acquirer.exception.CustomError;
import com.g4bor.payment.acquirer.exception.RecordNotFoundException;
import com.g4bor.payment.acquirer.model.Account;
import com.g4bor.payment.acquirer.model.Currency;
import com.g4bor.payment.acquirer.model.DTO.AccountCreationDTO;
import com.g4bor.payment.acquirer.model.DTO.WalletDTO;
import com.g4bor.payment.acquirer.model.Wallet;
import com.g4bor.payment.acquirer.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountService {

    AccountRepository accountRepository;
    AccountConverter accountConverter;

    public AccountService(AccountRepository accountRepository, AccountConverter accountConverter) {
        this.accountRepository = accountRepository;
        this.accountConverter = accountConverter;
    }

    // region > Create
    public Account createAccount(AccountCreationDTO accountDTO) {
        Account existingAccount = accountRepository.findAccountByUsername(accountDTO.getUsername());
        if (null != existingAccount) {
            throw new IllegalArgumentException(CustomError.EXISTING_ACCOUNT.formatMessage(accountDTO.getUsername()));
        }
        Account account = accountConverter.creationDtoToAccount(accountDTO);
        return accountRepository.save(account);
    }

    public Account addWallet(String id, String currencyISO) throws NoSuchElementException, IllegalArgumentException {
        Currency newCurrency = Currency.resolve(currencyISO);
        if (null == newCurrency) {
            throw new NoSuchElementException(CustomError.INVALID_CURRENCY.formatMessage(currencyISO));
        }

        Account account = accountRepository.findAccountByAccountId(UUID.fromString(id));
        if (null == account) {
            throw new RecordNotFoundException(CustomError.ACCOUNT_NOT_FOUND.formatMessage(id));
        }

        List<String> existingCurrencies = account.getWallets().stream()
                .map(item -> item.getCurrency().getIsoCode())
                .collect(Collectors.toList());
        if (existingCurrencies.contains(currencyISO)) {
            throw new IllegalArgumentException(CustomError.ALREADY_USED_CURRENCY.formatMessage(currencyISO));
        }

        account.addWallet(new Wallet(newCurrency, 0.0));
        return accountRepository.save(account);
    }
    // endregion

    // region > Read
    public Account getAccountByUsername(String username) {
        Account account = accountRepository.findAccountByUsername(username);
        if (null == account) {
            throw new RecordNotFoundException(CustomError.ACCOUNT_NOT_FOUND.formatMessage(username));
        }
        return account;
    }

    public Account getAccountById(String id) {
        Account account = accountRepository.findAccountByAccountId(UUID.fromString(id));
        if (null == account) {
            throw new RecordNotFoundException(CustomError.ACCOUNT_NOT_FOUND.formatMessage(id));
        }
        return account;
    }

    public Set<WalletDTO> getWalletsForAccount(String id) {
        Account account = accountRepository.findAccountByAccountId(UUID.fromString(id));
        if (null == account) {
            throw new RecordNotFoundException(CustomError.ACCOUNT_NOT_FOUND.formatMessage(id));
        }
        return account.getWallets().stream()
                .map(wallet -> new WalletDTO(wallet.getCurrency(), wallet.getBalance()))
                .collect(Collectors.toSet());
    }

    public Double getBalanceByAccountIdAndCurrency(String id, String currencyISO) {
        Account account = accountRepository.findAccountByAccountId(UUID.fromString(id));
        if (null == account) {
            throw new RecordNotFoundException(CustomError.ACCOUNT_NOT_FOUND.formatMessage(id));
        }

        Wallet wallet = account.getWallets().stream()
                .filter(i -> i.getCurrency().getIsoCode().equals(currencyISO))
                .findFirst()
                .orElseThrow(() -> new RecordNotFoundException(CustomError.NO_WALLET_FOR_ACCOUNT.formatMessage(currencyISO, id)));

        return wallet.getBalance();
    }
    // endregion

    // region > Update
    public Account updateAccount(Account account) {
        Account originalAccount = accountRepository.findAccountByAccountId(account.getAccountId());
        if (null == originalAccount) {
            throw new RecordNotFoundException(CustomError.ACCOUNT_NOT_FOUND.formatMessage(account.getAccountId().toString()));
        }
        return accountRepository.save(account);
    }

    // endregion

    // region > Delete
    public void deleteAccountById(String id) {
        accountRepository.deleteById(UUID.fromString(id));
    }

    public void deleteWallet(String accountId, Long walletId) {
        Account account = accountRepository.findAccountByAccountId(UUID.fromString(accountId));
        if (null == account) {
            throw new RecordNotFoundException(CustomError.ACCOUNT_NOT_FOUND.formatMessage(accountId));
        }

        boolean isRemoved = account.removeWallet(walletId);
        if (!isRemoved) {
            throw new RecordNotFoundException(CustomError.WALLET_NOT_FOUND.formatMessage(walletId.toString()));
        }
        accountRepository.save(account);
    }
    // endregion
}
