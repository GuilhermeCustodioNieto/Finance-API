package com.github.guilhermecustodionieto.finance_api.services.transaction;

import com.github.guilhermecustodionieto.finance_api.dtos.transaction.RecipeDTO;
import com.github.guilhermecustodionieto.finance_api.entities.Wallet;
import com.github.guilhermecustodionieto.finance_api.entities.transaction.Recipe;
import com.github.guilhermecustodionieto.finance_api.entities.transaction.TransactionCategory;
import com.github.guilhermecustodionieto.finance_api.entities.transaction.TransactionHistory;
import com.github.guilhermecustodionieto.finance_api.exceptions.generics.DataIntegrityViolationException;
import com.github.guilhermecustodionieto.finance_api.exceptions.generics.EntityNotFoundException;
import com.github.guilhermecustodionieto.finance_api.repositories.RecipeRepository;
import com.github.guilhermecustodionieto.finance_api.services.WalletService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class RecipeService {
    private final RecipeRepository repository;
    private final TransactionCategoryService transactionCategoryService;
    private final TransactionHistoryService transactionHistoryService;
    private final WalletService walletService;

    public RecipeService(RecipeRepository repository, TransactionCategoryService transactionCategoryService, TransactionHistoryService transactionHistoryService, WalletService walletService) {
        this.repository = repository;
        this.transactionCategoryService = transactionCategoryService;
        this.transactionHistoryService = transactionHistoryService;
        this.walletService = walletService;
    }

    public List<Recipe> findAll(){
        return repository.findAll();
    }

    public List<Recipe> findByOrigin(String origin){
        return repository.findByOriginContainingIgnoreCase(origin);
    }

    public List<Recipe> findByDescription(String description){
        return repository.findByDescriptionContainingIgnoreCase(description);
    }

    public Recipe findById(UUID id){
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Recipe", id.toString()));
    }

    @Transactional
    public Recipe create(RecipeDTO recipeDTO, UUID walletId){
        try{

            Date recipeDate = recipeDTO.date() == null ? new Date() : recipeDTO.date();

            List<TransactionCategory> foundCategories = transactionCategoryService.findByName(recipeDTO.transactionCategory());

            if(foundCategories.isEmpty()){

                throw new EntityNotFoundException("Transaction Category", recipeDTO.transactionCategory());
            }

            Wallet wallet = walletService.findById(walletId);

            Recipe recipe = new Recipe(recipeDTO.value(), recipeDate, recipeDTO.description(), recipeDTO.isRecurring(), recipeDTO.typeTransactionCategory(), foundCategories.get(0), wallet.getTransactionHistory(), recipeDTO.origin());

            walletService.deposit(walletId, recipeDTO.value());

            TransactionHistory transactionHistory = wallet.getTransactionHistory();
            transactionHistory.getTransactionList().add(recipe);
            recipe.setTransactionHistory(transactionHistory);

            return repository.save(recipe);
        } catch (Exception e){
            e.printStackTrace();
            throw new DataIntegrityViolationException("Recipe");

        }

    }

    public Recipe update(UUID id, RecipeDTO recipeDTO) {
        Recipe recipe = findById(id);

        if (recipeDTO.transactionCategory() != null) {
            List<TransactionCategory> foundCategories = transactionCategoryService.findByName(recipeDTO.transactionCategory());

            if (foundCategories.isEmpty()) {
                throw new EntityNotFoundException("Transaction Category", recipeDTO.transactionCategory());
            }

            recipe.setTransactionCategory(foundCategories.get(0));
        }

        if (recipeDTO.value() != null && recipeDTO.value().compareTo(BigDecimal.ZERO) >= 0) {
            Wallet wallet = recipe.getTransactionHistory().getWallet();
            wallet.withdraw(recipe.getValue());
            wallet.deposit(recipeDTO.value());
            walletService.update(wallet.getId(), wallet);
            recipe.setValue(recipeDTO.value());
        }

        if (recipeDTO.date() != null) {
            recipe.setDate(recipeDTO.date());
        }

        if (recipeDTO.origin() != null && !recipeDTO.origin().isBlank()) {
            recipe.setOrigin(recipeDTO.origin());
        }

        if (recipeDTO.isRecurring() != null) {
            recipe.setIsRecurring(recipeDTO.isRecurring());
        }

        if (recipeDTO.description() != null && !recipeDTO.description().isBlank()) {
            recipe.setDescription(recipeDTO.description());
        }

        if (recipeDTO.typeTransactionCategory() != null) {
            recipe.setTypeTransactionCategory(recipeDTO.typeTransactionCategory());
        }

        return repository.save(recipe);
    }


    public void delete(UUID id, UUID transactionHistoryId){
        Recipe recipe = findById(id);
        transactionHistoryService.removeTransactionFromHistory(transactionHistoryId, recipe);
        repository.delete(recipe);
    }


}
