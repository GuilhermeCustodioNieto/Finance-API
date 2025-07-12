    package com.github.guilhermecustodionieto.finance_api.entities.transaction;

    import com.github.guilhermecustodionieto.finance_api.entities.transaction.enums.TypeTransactionCategory;
    import jakarta.persistence.*;
    import lombok.*;

    import java.math.BigDecimal;
    import java.util.Date;
    import java.util.UUID;

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

        public Recipe(BigDecimal value, Date date, String description, Boolean isRecurring, TypeTransactionCategory typeTransactionCategory, TransactionCategory category, String origin) {
            super(value, date, description, isRecurring, typeTransactionCategory, category);
            this.origin = origin;
        }

        public Recipe(BigDecimal value, Date date, Boolean isRecurring, TransactionCategory category, String origin) {
            super(value, date, isRecurring, category);
            this.origin = origin;
        }
    }
