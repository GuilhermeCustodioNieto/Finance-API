package com.github.guilhermecustodionieto.finance_api.dtos.transaction;

import com.github.guilhermecustodionieto.finance_api.entities.transaction.TransactionCategory;
import com.github.guilhermecustodionieto.finance_api.entities.transaction.enums.PaymentFormat;
import com.github.guilhermecustodionieto.finance_api.entities.transaction.enums.TypeTransactionCategory;

import java.math.BigDecimal;
import java.util.Date;

public record WasteDTO(BigDecimal value, Date date, String description, String transactionCategory, Boolean isRecurring, TypeTransactionCategory typeTransactionCategory, PaymentFormat paymentFormat, Integer installments) {
}
