package com.github.guilhermecustodionieto.finance_api.dtos.authentication;

import com.github.guilhermecustodionieto.finance_api.entities.Wallet;

import java.util.UUID;

public record UserAuthenticationDTO(
        UUID id, String name, String email, String phone, Wallet wallet
) {
}
