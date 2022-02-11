package com.g4bor.payment.integration.acquire_service.service;

import com.g4bor.payment.database.manager.AccountManager;
import com.g4bor.payment.database.model.Account;
import com.g4bor.payment.database.model.Wallet;
import com.g4bor.payment.entity.model.AccountDTO;
import com.g4bor.payment.entity.model.AccountRegistrationDTO;
import com.g4bor.payment.entity.model.Currency;
import com.g4bor.payment.entity.model.WalletDTO;
import com.g4bor.payment.database.converter.AccountConverter;
import com.g4bor.payment.database.converter.WalletConverter;
import com.g4bor.payment.database.exception.ErrorMsg;
import com.g4bor.payment.integration.acquire_service.exception.RecordNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private final AccountManager accountManager;
    private final AccountConverter accountConverter;
    private final WalletConverter walletConverter;

    public AccountService(AccountManager accountManager,
                          AccountConverter accountConverter,
                          WalletConverter walletConverter) {
        this.accountManager = accountManager;
        this.accountConverter = accountConverter;
        this.walletConverter = walletConverter;
    }

    // region > Create
    public AccountDTO createAccount(AccountRegistrationDTO registrationDTO) {
        Account account = accountManager.createAccount(registrationDTO);
        return accountConverter.entityToDTO(account);
    }

    public AccountDTO addWallet(String id, String currencyISO) throws NoSuchElementException, IllegalArgumentException {
        Currency newCurrency = Currency.resolve(currencyISO);
        if (null == newCurrency) {
            throw new NoSuchElementException(ErrorMsg.INVALID_CURRENCY.formatMessage(currencyISO));
        }

        Account account = accountManager.addWallet(UUID.fromString(id), newCurrency);
        return accountConverter.entityToDTO(account);
    }
    // endregion

    // region > Read
    public AccountDTO getAccountById(String id) {
        Account account = accountManager.getAccountById(UUID.fromString(id));
        return accountConverter.entityToDTO(account);
    }

    public List<AccountDTO> getAccounts() {
        List<Account> accounts = accountManager.getAccounts();
        return accounts.stream()
                .map(accountConverter::entityToDTO)
                .collect(Collectors.toList());
    }

    public Set<WalletDTO> getWalletsForAccount(String id) {
        Set<Wallet> wallets = accountManager.getWalletsForAccount(UUID.fromString(id));
        return wallets.stream()
                .map(walletConverter::entityToDTO)
                .collect(Collectors.toSet());
    }

    public Double getBalanceByIdAndCurrency(String id, String currencyISO) {
        Set<Wallet> allWallets = accountManager.getWalletsForAccount(UUID.fromString(id));

        Wallet wallet = allWallets.stream()
                .filter(i -> i.getCurrency().getIsoCode().equals(currencyISO))
                .findFirst()
                .orElseThrow(() -> new RecordNotFoundException(ErrorMsg.NO_WALLET_FOR_ACCOUNT.formatMessage(currencyISO, id)));

        return wallet.getBalance();
    }
    // endregion

    // region > Update
    public AccountDTO updateAccount(AccountDTO accountDto) {
        Account updatedAccount = accountManager.updateAccount(accountDto);
        return accountConverter.entityToDTO(updatedAccount);
    }

    public AccountDTO changeUsername(String id, String username) {
        Account account = accountManager.changeUsername(UUID.fromString(id), username);
        return accountConverter.entityToDTO(account);
    }
    // endregion

    // region > Delete
    public void deleteAccountById(String id) {
        accountManager.deleteById(UUID.fromString(id));
    }

    public AccountDTO deleteWallet(String id, Long walletId) {
        Account account = accountManager.deleteWallet(UUID.fromString(id), walletId);
        return accountConverter.entityToDTO(account);
    }
    // endregion
}
