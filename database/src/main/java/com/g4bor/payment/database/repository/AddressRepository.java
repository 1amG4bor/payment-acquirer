package com.g4bor.payment.database.repository;

import com.g4bor.payment.database.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    Address findAddressById(Long id);
}
