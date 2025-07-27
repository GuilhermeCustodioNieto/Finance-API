package com.github.guilhermecustodionieto.finance_api.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.github.guilhermecustodionieto.finance_api.entities.transaction.TransactionHistory;
import com.github.guilhermecustodionieto.finance_api.exceptions.generics.InsufficientBalanceException;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "tb_wallet")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@ToString
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_history_id", referencedColumnName = "id")
    @JsonManagedReference
    private TransactionHistory transactionHistory;

    public Wallet(TransactionHistory transactionHistory) {
        this.transactionHistory = transactionHistory;
    }

    public void deposit(BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit value must be positive");
        }
        this.balance = this.balance.add(value);
    }

    public void withdraw(BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdraw value must be positive");
        }
        if (this.balance.compareTo(value) < 0) {
            throw new InsufficientBalanceException("The value " + value + " is greater than the current balance " + balance);
        }
        this.balance = this.balance.subtract(value);
    }
}
