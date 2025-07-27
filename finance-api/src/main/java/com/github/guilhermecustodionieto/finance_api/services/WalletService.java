package com.github.guilhermecustodionieto.finance_api.services;

import com.github.guilhermecustodionieto.finance_api.entities.Wallet;
import com.github.guilhermecustodionieto.finance_api.entities.transaction.TransactionHistory;
import com.github.guilhermecustodionieto.finance_api.exceptions.generics.EntityNotFoundException;
import com.github.guilhermecustodionieto.finance_api.repositories.WalletRepository;
import com.github.guilhermecustodionieto.finance_api.services.transaction.TransactionHistoryService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WalletService {
    private final WalletRepository repository;
    private final TransactionHistoryService transactionHistoryService;

    public WalletService(WalletRepository repository, TransactionHistoryService transactionHistoryService) {
        this.repository = repository;
        this.transactionHistoryService = transactionHistoryService;
    }

    public List<Wallet> findAll(){
        return repository.findAll();
    }

    public Wallet findById(UUID id){
        Optional<Wallet> wallet = repository.findById(id);
        if(wallet.isEmpty()){
            throw new EntityNotFoundException("Wallet", "id: " + id);
        }

        return wallet.get();
    }

    public Wallet create(){
        TransactionHistory transactionHistory = transactionHistoryService.create();
        Wallet wallet = new Wallet(transactionHistory);
        return repository.save(wallet);
    }

    public Wallet deposit(UUID id, BigDecimal value){
        Wallet wallet = findById(id);
        wallet.deposit(value);
        return repository.save(wallet);
    }

    public Wallet withdraw(UUID id, BigDecimal value){
        Wallet wallet = findById(id);
        wallet.withdraw(value);
        return repository.save(wallet);
    }

    public Wallet update(UUID id, Wallet beforeWallet){
        Wallet wallet = findById(id);
        wallet.setBalance(beforeWallet.getBalance());
        wallet.setTransactionHistory(beforeWallet.getTransactionHistory());
        return repository.save(wallet);
    }


    public void delete(UUID id){
        Wallet wallet = findById(id);
        repository.delete(wallet);
    }
}
