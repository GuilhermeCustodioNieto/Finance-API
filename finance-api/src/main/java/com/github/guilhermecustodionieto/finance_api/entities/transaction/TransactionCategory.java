package com.github.guilhermecustodionieto.finance_api.entities.transaction;

import com.github.guilhermecustodionieto.finance_api.entities.transaction.enums.TypeTransactionCategory;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "tb_transaction-category")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public class TransactionCategory {
    @Id
    private UUID id;
    private String name;
    @Enumerated(value = EnumType.STRING)
    private TypeTransactionCategory typeTransactionCategory;

    public TransactionCategory(String name, TypeTransactionCategory typeTransactionCategory) {
        this.name = name;
        this.typeTransactionCategory = typeTransactionCategory;
    }
}
