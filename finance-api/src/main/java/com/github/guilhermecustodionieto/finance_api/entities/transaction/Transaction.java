package com.github.guilhermecustodionieto.finance_api.entities.transaction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.github.guilhermecustodionieto.finance_api.entities.transaction.enums.TypeTransactionCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;


@Entity
@Table(name = "tb_transaction")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column
    private UUID id;
    @Column
    private BigDecimal value;
    @Column
    private Date date;

    @Column
    private String description;

    @Column
    private Boolean isRecurring;

    @Column
    @Enumerated(EnumType.STRING)
    private TypeTransactionCategory typeTransactionCategory;

    @ManyToOne()
    @JoinColumn(name = "category_id", nullable = false)
    private TransactionCategory transactionCategory;

    @ManyToOne
    @JoinColumn(name = "transaction_history_id")
    @JsonBackReference
    private TransactionHistory transactionHistory;

    public Transaction(BigDecimal value, Date date, String description, Boolean isRecurring, TypeTransactionCategory typeTransactionCategory, TransactionCategory transactionCategory, TransactionHistory transactionHistory) {
        this.value = value;
        this.date = date;
        this.description = description;
        this.isRecurring = isRecurring;
        this.typeTransactionCategory = typeTransactionCategory;
        this.transactionCategory = transactionCategory;
        this.transactionHistory = transactionHistory;
    }

}
