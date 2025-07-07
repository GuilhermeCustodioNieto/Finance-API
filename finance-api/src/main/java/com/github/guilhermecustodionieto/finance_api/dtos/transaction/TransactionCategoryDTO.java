package com.github.guilhermecustodionieto.finance_api.dtos.transaction;

import com.github.guilhermecustodionieto.finance_api.entities.transaction.enums.TypeTransactionCategory;

public record TransactionCategoryDTO(String name, TypeTransactionCategory typeTransactionCategory ) {
}
