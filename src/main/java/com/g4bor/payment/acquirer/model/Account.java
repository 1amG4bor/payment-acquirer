package com.g4bor.payment.acquirer.model;

import com.g4bor.payment.acquirer.exception.RecordNotFoundException;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ACCOUNTS")
public class Account {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "accountId", updatable = false, nullable = false)
    private UUID accountId;

    @Column(nullable = false, unique = true, updatable = false)
    private String username;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @OneToMany(targetEntity = Wallet.class, cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Wallet> wallets = new HashSet<>();

    public Account(String username, String firstName, String lastName) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void addWallet(Wallet wallet) {
        this.wallets.add(wallet);
    }

    public boolean removeWallet(Long id) {
        Wallet walletToRemove = this.wallets.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RecordNotFoundException(""));

        return this.getWallets().remove(walletToRemove);
    }
}
