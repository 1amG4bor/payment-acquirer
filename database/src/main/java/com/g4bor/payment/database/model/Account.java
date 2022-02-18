package com.g4bor.payment.database.model;

import com.g4bor.payment.database.exception.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PUBLIC, setterPrefix = "with")
@Entity
@Table(name = "ACCOUNTS")
public class Account {
    private static final String WALLET_NOT_FOUND = "No wallet found with the given ID: '%s'";

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "accountId", updatable = false, nullable = false)
    private UUID accountId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private String idNumber;

    @OneToOne(targetEntity = Address.class, cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    private Address address;

    @OneToMany(targetEntity = Wallet.class, cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Wallet> wallets = new HashSet<>();

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

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
                .orElseThrow(() -> new EntityNotFoundException(String.format(WALLET_NOT_FOUND, id)));

        return this.getWallets().remove(walletToRemove);
    }
}
