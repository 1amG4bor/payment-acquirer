package com.g4bor.payment.database.converter;


import com.g4bor.payment.database.model.Address;
import com.g4bor.payment.entity.model.AddressDTO;

public class AddressConverter {
    public AddressDTO entityToDTO(Address entity) {
        return AddressDTO.builder()
                .withCountry(entity.getCountry())
                .withRegion(entity.getRegion())
                .withZipCode(entity.getZipCode())
                .withCity(entity.getCity())
                .withStreet(entity.getStreet())
                .withAdditionalInfo(entity.getAdditionalInfo())
                .build();
    }

    public Address dtoToEntity(AddressDTO dto) {
        Address entity = new Address();
        entity.setCountry(dto.getCountry());
        entity.setRegion(dto.getRegion());
        entity.setZipCode(dto.getZipCode());
        entity.setCity(dto.getCity());
        entity.setStreet(dto.getStreet());
        entity.setAdditionalInfo(dto.getAdditionalInfo());
        return entity;
    }
}
