package com.github.guilhermecustodionieto.finance_api.entities.transaction;

import com.github.guilhermecustodionieto.finance_api.entities.transaction.enums.PaymentFormat;
import com.github.guilhermecustodionieto.finance_api.entities.transaction.enums.TypeTransactionCategory;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "tb_waste")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Waste extends Transaction{
    @Column
    @Enumerated(EnumType.STRING)
    private PaymentFormat paymentFormat;

    @Column
    private Integer installments;

    public Waste(BigDecimal value, Date date, Boolean isRecurring, TransactionCategory category, PaymentFormat paymentFormat, Integer installments) {
        super(value, date, isRecurring, category);
        this.paymentFormat = paymentFormat;
        this.installments = installments;
    }

    public Waste(BigDecimal value, Date date, String description, Boolean isRecurring, TypeTransactionCategory typeTransactionCategory, TransactionCategory category, PaymentFormat paymentFormat, Integer installments) {
        super(value, date, description, isRecurring, typeTransactionCategory, category);
        this.paymentFormat = paymentFormat;
        this.installments = installments;
    }
}
