package com.github.guilhermecustodionieto.finance_api.services.transaction;

import com.github.guilhermecustodionieto.finance_api.dtos.transaction.WasteDTO;
import com.github.guilhermecustodionieto.finance_api.entities.transaction.TransactionCategory;
import com.github.guilhermecustodionieto.finance_api.entities.transaction.Waste;
import com.github.guilhermecustodionieto.finance_api.entities.transaction.enums.PaymentFormat;
import com.github.guilhermecustodionieto.finance_api.exceptions.generics.EntityNotFoundException;
import com.github.guilhermecustodionieto.finance_api.repositories.WasteRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

public class WasteService {
    private WasteRepository wasteRepository;
    private TransactionCategoryService transactionCategoryService;

    public WasteService(WasteRepository wasteRepository, TransactionCategoryService transactionCategoryService) {
        this.wasteRepository = wasteRepository;
        this.transactionCategoryService = transactionCategoryService;
    }

    List<Waste> findAll(){
       return wasteRepository.findAll();
    }

    Waste findById(UUID id){
        var waste = wasteRepository.findById(id);
        if(waste.isEmpty()){
            throw new EntityNotFoundException("Waste", id.toString());
        }
        return waste.get();

    }

    List<Waste> findByDescription(String description){
        return wasteRepository.findByDescriptionContainingIgnoreCase(description);
    }

    List<Waste> findByPaymentFormat(PaymentFormat paymentFormat){
        return wasteRepository.findByPaymentFormat(paymentFormat);
    }

    Waste create(WasteDTO wasteDTO){
        Date date = wasteDTO.date() == null ? new Date() : wasteDTO.date();

        List<TransactionCategory> transactionCategory = transactionCategoryService.findByName(wasteDTO.transactionCategory());

        if(transactionCategory.isEmpty()){
            throw new EntityNotFoundException("Waste", wasteDTO.transactionCategory());
        }

        Waste waste = new Waste(wasteDTO.value(), date, wasteDTO.isRecurring(), transactionCategory.get(0), wasteDTO.paymentFormat(), wasteDTO.installments());
        return wasteRepository.save(waste);
    }

    Waste update(UUID id,WasteDTO wasteDTO){
        Waste waste = findById(id);

        if(wasteDTO.transactionCategory() != null){
            List<TransactionCategory> transactionCategory = transactionCategoryService.findByName(wasteDTO.transactionCategory());

            if(transactionCategory.isEmpty()){
                throw new EntityNotFoundException("Waste", wasteDTO.transactionCategory());
            }

            waste.setCategory(transactionCategory.get(0));
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

    void delete(UUID id){
        Waste waste = findById(id);

        wasteRepository.delete(waste);
    }

}
