package com.github.guilhermecustodionieto.finance_api.services.transaction;

import com.github.guilhermecustodionieto.finance_api.entities.transaction.Transaction;
import com.github.guilhermecustodionieto.finance_api.entities.transaction.TransactionHistory;
import com.github.guilhermecustodionieto.finance_api.exceptions.generics.EntityNotFoundException;
import com.github.guilhermecustodionieto.finance_api.repositories.TransactionHistoryReporitory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionHistoryService {
    private final TransactionHistoryReporitory reporitory;

    public TransactionHistoryService(TransactionHistoryReporitory reporitory) {
        this.reporitory = reporitory;
    }

    public List<TransactionHistory> findAll(){
        return reporitory.findAll();
    }

    public TransactionHistory findById(UUID id){
        Optional<TransactionHistory> transactionHistory = reporitory.findById(id);
        if(transactionHistory.isEmpty()){
            throw new EntityNotFoundException("Transaction History", "id : " + id);
        }
        return transactionHistory.get();
    }

    public TransactionHistory create(){
        TransactionHistory transactionHistory = new TransactionHistory();
        return reporitory.save(transactionHistory);
    }

    public TransactionHistory update(UUID id, Transaction transaction){
        TransactionHistory transactionHistory = findById(id);
        transactionHistory.addNewTransaction(transaction);
        transactionHistory.setLastTransaction(new Date());
        return reporitory.save(transactionHistory);
    }

    public void delete(UUID id){
        TransactionHistory transactionHistory = findById(id);
        reporitory.delete(transactionHistory);
    }

    public void removeTransactionFromHistory(UUID historyId, Transaction transaction) {
        TransactionHistory history = findById(historyId);
        history.removeTransaction(transaction);
        history.setLastTransaction(new Date());
        reporitory.save(history);
    }

}
