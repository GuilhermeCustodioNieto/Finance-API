package com.github.guilhermecustodionieto.finance_api.entities.transaction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.github.guilhermecustodionieto.finance_api.entities.transaction.enums.TypeTransactionCategory;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_transaction_category")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public class TransactionCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    @Enumerated(value = EnumType.STRING)
    private TypeTransactionCategory typeTransactionCategory;
    @OneToMany(mappedBy = "transactionCategory")
    @JsonBackReference
    private List<Transaction> transactions;

    public TransactionCategory(String name, TypeTransactionCategory typeTransactionCategory) {
        this.name = name;
        this.typeTransactionCategory = typeTransactionCategory;
    }


}
