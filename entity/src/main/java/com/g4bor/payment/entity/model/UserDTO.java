package com.g4bor.payment.entity.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(access = AccessLevel.PUBLIC, setterPrefix = "with")
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private UUID accountId;

}
