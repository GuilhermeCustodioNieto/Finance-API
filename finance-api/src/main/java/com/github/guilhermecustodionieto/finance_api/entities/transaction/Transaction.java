package com.github.guilhermecustodionieto.finance_api.entities.transaction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.github.guilhermecustodionieto.finance_api.entities.transaction.enums.TypeTransactionCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

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

    public Transaction(BigDecimal value, Date date, Boolean isRecurring, TransactionCategory category) {
        this.value = value;
        this.date = date;
        this.isRecurring = isRecurring;
        this.transactionCategory = category;
    }

    public Transaction(BigDecimal value, Date date, String description, Boolean isRecurring, TypeTransactionCategory typeTransactionCategory, TransactionCategory category) {
        this.value = value;
        this.date = date;
        this.description = description;
        this.isRecurring = isRecurring;
        this.typeTransactionCategory = typeTransactionCategory;
        this.transactionCategory = category;
    }
}
