package com.github.guilhermecustodionieto.finance_api.dtos.transaction.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserUpdatingDTO(
        String name,

        @Email(message = "Invalid email format")
        String email,

        String password,
        String confirmPassword,

        @Pattern(regexp = "\\+?\\d{10,15}", message = "Phone must be a valid number")
        String phone
) {
}
