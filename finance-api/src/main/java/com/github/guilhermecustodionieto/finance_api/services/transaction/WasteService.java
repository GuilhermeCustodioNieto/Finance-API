package com.github.guilhermecustodionieto.finance_api.services.transaction;

import com.github.guilhermecustodionieto.finance_api.dtos.transaction.WasteDTO;
import com.github.guilhermecustodionieto.finance_api.entities.Wallet;
import com.github.guilhermecustodionieto.finance_api.entities.transaction.TransactionCategory;
import com.github.guilhermecustodionieto.finance_api.entities.transaction.TransactionHistory;
import com.github.guilhermecustodionieto.finance_api.entities.transaction.Waste;
import com.github.guilhermecustodionieto.finance_api.entities.transaction.enums.PaymentFormat;
import com.github.guilhermecustodionieto.finance_api.exceptions.generics.EntityNotFoundException;
import com.github.guilhermecustodionieto.finance_api.repositories.WasteRepository;
import com.github.guilhermecustodionieto.finance_api.services.WalletService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class WasteService {
    private final WasteRepository wasteRepository;
    private final TransactionCategoryService transactionCategoryService;
    private final TransactionHistoryService transactionHistoryService;
    private final WalletService walletService;

    public WasteService(WasteRepository wasteRepository, TransactionCategoryService transactionCategoryService, TransactionHistoryService transactionHistoryService, WalletService walletService) {
        this.wasteRepository = wasteRepository;
        this.transactionCategoryService = transactionCategoryService;
        this.transactionHistoryService = transactionHistoryService;
        this.walletService = walletService;
    }

    public List<Waste> findAll(){
       return wasteRepository.findAll();
    }

    public Waste findById(UUID id){
        var waste = wasteRepository.findById(id);
        if(waste.isEmpty()){
            throw new EntityNotFoundException("Waste", id.toString());
        }
        return waste.get();

    }

    public List<Waste> findByDescription(String description){
        return wasteRepository.findByDescriptionContainingIgnoreCase(description);
    }

    public List<Waste> findByPaymentFormat(PaymentFormat paymentFormat){
        return wasteRepository.findByPaymentFormat(paymentFormat);
    }

    @Transactional
    public Waste create(WasteDTO wasteDTO, UUID walletId){
        var date = wasteDTO.date() == null ? new Date() : wasteDTO.date();

        List<TransactionCategory> transactionCategory = transactionCategoryService.findByName(wasteDTO.transactionCategory());

        if(transactionCategory.isEmpty()){
            throw new EntityNotFoundException("Waste", wasteDTO.transactionCategory());
        }

        Wallet wallet = walletService.findById(walletId);
        walletService.withdraw(walletId, wasteDTO.value());
        Waste waste = new Waste(wasteDTO.value(), date, wasteDTO.description(), wasteDTO.isRecurring(), wasteDTO.typeTransactionCategory(), transactionCategory.get(0), wallet.getTransactionHistory(), wasteDTO.paymentFormat(), wasteDTO.installments());


        TransactionHistory transactionHistory = wallet.getTransactionHistory();
        transactionHistory.getTransactionList().add(waste);
        waste.setTransactionHistory(transactionHistory);

        return wasteRepository.save(waste);
    }

    public Waste update(UUID id,WasteDTO wasteDTO){
        Waste waste = findById(id);

        if(wasteDTO.transactionCategory() != null){
            List<TransactionCategory> transactionCategory = transactionCategoryService.findByName(wasteDTO.transactionCategory());

            if(transactionCategory.isEmpty()){
                throw new EntityNotFoundException("Waste", wasteDTO.transactionCategory());
            }

            waste.setTransactionCategory(transactionCategory.get(0));
        }

        if(wasteDTO.typeTransactionCategory() != null){
            waste.setTypeTransactionCategory(wasteDTO.typeTransactionCategory());
        }

        if(wasteDTO.isRecurring() != null){
            waste.setIsRecurring(wasteDTO.isRecurring());
        }

        if(wasteDTO.installments() != null){
            waste.setInstallments(wasteDTO.installments());
        }

        if(wasteDTO.date() != null){
            waste.setDate(wasteDTO.date());
        }

        if(wasteDTO.value() != null){
            Wallet wallet = waste.getTransactionHistory().getWallet();
            wallet.deposit(waste.getValue());
            wallet.withdraw(wasteDTO.value());
            walletService.update(wallet.getId(), wallet);
            waste.setValue(wasteDTO.value());
        }

        if(wasteDTO.description() != null){
            waste.setDescription(wasteDTO.description());
        }

        if(wasteDTO.paymentFormat() != null){
            waste.setPaymentFormat(wasteDTO.paymentFormat());
        }

        return wasteRepository.save(waste);
    }

    public void delete(UUID id, UUID transactionHistoryId){
        Waste waste = findById(id);
        transactionHistoryService.removeTransactionFromHistory(transactionHistoryId, waste);

        wasteRepository.delete(waste);
    }

}
