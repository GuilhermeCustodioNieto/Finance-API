package com.github.guilhermecustodionieto.finance_api.entities.transaction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.github.guilhermecustodionieto.finance_api.entities.Wallet;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_transaction_history")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString
public class TransactionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column
    private Date lastTransaction;

    @OneToMany(mappedBy = "transactionHistory", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Transaction> transactionList;

    @OneToOne(mappedBy = "transactionHistory")
    @JsonBackReference
    private Wallet wallet;

    public TransactionHistory() {
        this.lastTransaction = new Date();
        this.transactionList = new ArrayList<>();
    }

    public void addNewTransaction(Transaction transaction){
        transactionList.add(transaction);
    }

    public void removeTransaction(Transaction transaction){
        transactionList.remove(transaction);
    }
}
