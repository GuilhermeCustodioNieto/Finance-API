package com.github.guilhermecustodionieto.finance_api.dtos.transaction;

import com.github.guilhermecustodionieto.finance_api.entities.transaction.enums.TypeTransactionCategory;

import java.math.BigDecimal;
import java.util.Date;

public record RecipeDTO(BigDecimal value, Date date, String description, String transactionCategory, Boolean isRecurring, String origin, TypeTransactionCategory typeTransactionCategory) {
}
