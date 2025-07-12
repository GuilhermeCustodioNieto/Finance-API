package com.github.guilhermecustodionieto.finance_api.services.transaction;

import com.github.guilhermecustodionieto.finance_api.dtos.transaction.TransactionCategoryDTO;
import com.github.guilhermecustodionieto.finance_api.entities.transaction.TransactionCategory;
import com.github.guilhermecustodionieto.finance_api.entities.transaction.enums.TypeTransactionCategory;
import com.github.guilhermecustodionieto.finance_api.exceptions.generics.DataIntegrityViolationException;
import com.github.guilhermecustodionieto.finance_api.exceptions.generics.EntityNotFoundException;
import com.github.guilhermecustodionieto.finance_api.repositories.TransactionCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TransactionCategoryService {
    private TransactionCategoryRepository repository;

    public TransactionCategoryService(TransactionCategoryRepository repository) {
        this.repository = repository;
    }

    public List<TransactionCategory> findAll(){
        return repository.findAll();
    }

    public List<TransactionCategory> findByName(String name){
        return repository.findByName(name);
    }

    public TransactionCategory findById(UUID id) throws EntityNotFoundException{
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Transaction Category", id.toString()));
    }

    public TransactionCategory create(TransactionCategoryDTO transactionCategoryDTO){
        try{
            TransactionCategory transactionCategory = new TransactionCategory(
                    transactionCategoryDTO.name(),
                    transactionCategoryDTO.typeTransactionCategory());

            return this.repository.save(transactionCategory);
        } catch (Exception e){
            throw new DataIntegrityViolationException("Transaction Category");
        }
    }

    public TransactionCategory update(TransactionCategoryDTO transactionCategoryDTO, UUID id) throws EntityNotFoundException{
        TransactionCategory transactionCategory = findById(id);
        if(transactionCategoryDTO.name() != null) transactionCategory.setName(transactionCategoryDTO.name());
        if(transactionCategoryDTO.typeTransactionCategory()!= null) transactionCategory.setTypeTransactionCategory(transactionCategoryDTO.typeTransactionCategory());

        return repository.save(transactionCategory);
    }

    public void delete(UUID id) throws EntityNotFoundException{
        TransactionCategory transactionCategory = findById(id);
        repository.delete(transactionCategory);
    }

}
