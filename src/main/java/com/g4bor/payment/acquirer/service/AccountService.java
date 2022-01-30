package com.g4bor.payment.acquirer.service;

import com.g4bor.payment.acquirer.converter.AccountConverter;
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

    private static final String ACCOUNT_NOT_FOUND_ERROR = "No account found with the given ID: '%s'";
    private static final String EXISTING_ACCOUNT_ERROR = "Account is already exist with the given username: '%s'";
    private static final String WALLET_NOT_FOUND_ERROR =
            "No wallet found in the given currency: '%s', for the account with id: '%s'";
    private static final String INVALID_CURRENCY_ERROR = "'%s' is an unsupported currency code!";
    private static final String ALREADY_USED_CURRENCY_ERROR =
            "Only one wallet can exist in a given currency and there is already one in '%s' currency.";

    AccountRepository accountRepository;
    AccountConverter accountConverter;

    public AccountService(AccountRepository accountRepository, AccountConverter accountConverter) {
        this.accountRepository = accountRepository;
        this.accountConverter = accountConverter;
    }

    public Account createAccount(AccountCreationDTO accountDTO) {
        Account existingAccount = accountRepository.findAccountByUsername(accountDTO.getUsername());
        if (null != existingAccount) {
            throw new IllegalArgumentException(String.format(EXISTING_ACCOUNT_ERROR, accountDTO.getUsername()));
        }
        Account account = accountConverter.creationDtoToAccount(accountDTO);
        return accountRepository.save(account);
    }

    public Account getAccountByUsername(String username) {
        Account account = accountRepository.findAccountByUsername(username);
        if (null == account) {
            throw new RecordNotFoundException(String.format(ACCOUNT_NOT_FOUND_ERROR, username));
        }
        return account;
    }

    public Account getAccountById(String id) {
        Account account = accountRepository.findAccountByAccountId(UUID.fromString(id));
        if (null == account) {
            throw new RecordNotFoundException(String.format(ACCOUNT_NOT_FOUND_ERROR, id));
        }
        return account;
    }

    public Set<WalletDTO> getWalletsForAccount(String id) {
        Account account = accountRepository.findAccountByAccountId(UUID.fromString(id));
        if (null == account) {
            throw new RecordNotFoundException(String.format(ACCOUNT_NOT_FOUND_ERROR, id));
        }
        return account.getWallets().stream()
                .map(wallet -> new WalletDTO(wallet.getCurrency(), wallet.getBalance()))
                .collect(Collectors.toSet());
    }

    public Double getBalanceByAccountIdAndCurrency(String id, String currencyISO) {
        Account account = accountRepository.findAccountByAccountId(UUID.fromString(id));
        if (null == account) {
            throw new RecordNotFoundException(String.format(ACCOUNT_NOT_FOUND_ERROR, id));
        }

        Wallet wallet = account.getWallets().stream()
                .filter(i -> i.getCurrency().getIsoCode().equals(currencyISO))
                .findFirst()
                .orElseThrow(() -> new RecordNotFoundException(String.format(WALLET_NOT_FOUND_ERROR, currencyISO, id)));

        return wallet.getBalance();
    }

    public Account addWallet(String id, String currencyISO) throws NoSuchElementException, IllegalArgumentException {
        Currency newCurrency = Currency.resolve(currencyISO);
        if (null == newCurrency) {
            throw new NoSuchElementException(String.format(INVALID_CURRENCY_ERROR, currencyISO));
        }

        Account account = accountRepository.findAccountByAccountId(UUID.fromString(id));
        if (null == account) {
            throw new RecordNotFoundException(String.format(ACCOUNT_NOT_FOUND_ERROR, id));
        }

        List<String> existingCurrencies = account.getWallets().stream()
                .map(item -> item.getCurrency().getIsoCode())
                .collect(Collectors.toList());
        if (existingCurrencies.contains(currencyISO)) {
            throw new IllegalArgumentException(String.format(ALREADY_USED_CURRENCY_ERROR, currencyISO));
        }

        account.addWallet(new Wallet(newCurrency, 0.0));
        return accountRepository.save(account);
    }
}
