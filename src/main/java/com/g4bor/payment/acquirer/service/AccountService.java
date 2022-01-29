package com.g4bor.payment.acquirer.service;

import com.g4bor.payment.acquirer.converter.AccountConverter;
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

    private static final String INVALID_CURRENCY_ERROR = "The given currency code is not supported!";
    private static final String ALREADY_USED_CURRENCY_ERROR = "A wallet is already exist with this currency." +
            " Only one wallet can exist in a given currency!";
    AccountRepository accountRepository;
    AccountConverter accountConverter;

    public AccountService(AccountRepository accountRepository, AccountConverter accountConverter) {
        this.accountRepository = accountRepository;
        this.accountConverter = accountConverter;
    }

    public Account createAccount(AccountCreationDTO accountDTO) {
        Account account = accountConverter.creationDtoToAccount(accountDTO);
        return accountRepository.save(account);
    }

    public Account getAccountByUsername(String username) {
        return accountRepository.findAccountByUsername(username);
    }

    public Account getAccountById(String id) {
        return accountRepository.findAccountByAccountId(UUID.fromString(id));
    }

    public Set<WalletDTO> getWalletsForAccount(String id) {
        Account account = accountRepository.findAccountByAccountId(UUID.fromString(id));
        if (null == account) {
            return null;
        }
        return account.getWallets().stream()
                .map(wallet -> new WalletDTO(wallet.getCurrency(), wallet.getBalance()))
                .collect(Collectors.toSet());
    }

    public Double getBalanceByAccountIdAndCurrency(String id, String currencyISO) {
        Account account = accountRepository.findAccountByAccountId(UUID.fromString(id));
        if (null == account) {
            return null;
        }

        return account.getWallets().stream()
                .filter(i -> i.getCurrency().getIsoCode().equals(currencyISO))
                .findFirst()
                .orElseGet(null)
                .getBalance();
    }

    public Account addWallet(String id, String currencyISO) throws NoSuchElementException, IllegalArgumentException {
        Currency newCurrency = Currency.resolve(currencyISO);
        if (null == newCurrency) {
            throw new NoSuchElementException(INVALID_CURRENCY_ERROR);
        }

        Account account = accountRepository.findAccountByAccountId(UUID.fromString(id));
        if (null == account) {
            return null;
        }

        List<String> existingCurrencies = account.getWallets().stream()
                .map(item -> item.getCurrency().getIsoCode())
                .collect(Collectors.toList());
        if (existingCurrencies.contains(currencyISO)) {
            throw new IllegalArgumentException(ALREADY_USED_CURRENCY_ERROR);
        }

        account.addWallet(new Wallet(newCurrency, 0.0));
        return accountRepository.save(account);
    }
}
