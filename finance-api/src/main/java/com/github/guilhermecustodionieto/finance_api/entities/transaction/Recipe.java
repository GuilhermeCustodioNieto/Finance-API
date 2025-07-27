    package com.github.guilhermecustodionieto.finance_api.entities.transaction;

    import com.github.guilhermecustodionieto.finance_api.entities.transaction.enums.TypeTransactionCategory;
    import jakarta.persistence.*;
    import lombok.*;

    import java.math.BigDecimal;
    import java.util.Date;

    @Entity
    @Table(name = "tb_recipe")
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public class Recipe extends Transaction{
        @Column
        private String origin;

        public Recipe(BigDecimal value, Date date, String description, Boolean isRecurring, TypeTransactionCategory typeTransactionCategory, TransactionCategory transactionCategory, TransactionHistory transactionHistory, String origin) {
            super(value, date, description, isRecurring, typeTransactionCategory, transactionCategory, transactionHistory);
            this.origin = origin;
        }

    }
