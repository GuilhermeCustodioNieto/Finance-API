package com.github.guilhermecustodionieto.finance_api.services.transaction;

import com.github.guilhermecustodionieto.finance_api.dtos.transaction.RecipeDTO;
import com.github.guilhermecustodionieto.finance_api.entities.transaction.Recipe;
import com.github.guilhermecustodionieto.finance_api.entities.transaction.TransactionCategory;
import com.github.guilhermecustodionieto.finance_api.exceptions.generics.DataIntegrityViolationException;
import com.github.guilhermecustodionieto.finance_api.exceptions.generics.EntityNotFoundException;
import com.github.guilhermecustodionieto.finance_api.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class RecipeService {
    private RecipeRepository repository;
    private TransactionCategoryService transactionCategoryService;

    public RecipeService(RecipeRepository repository, TransactionCategoryService transactionCategoryService) {
        this.repository = repository;
        this.transactionCategoryService = transactionCategoryService;
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

    public Recipe create(RecipeDTO recipeDTO){
        try{

            Date recipeDate = recipeDTO.date() == null ? new Date() : recipeDTO.date();

            List<TransactionCategory> foundCategories = transactionCategoryService.findByName(recipeDTO.transactionCategory());

            if(foundCategories.isEmpty()){

                throw new EntityNotFoundException("Transaction Category", recipeDTO.transactionCategory());
            }

            Recipe recipe = new Recipe(recipeDTO.value(), recipeDate, recipeDTO.description(), recipeDTO.isRecurring(), recipeDTO.typeTransactionCategory(), foundCategories.get(0), recipeDTO.origin());

            return repository.save(recipe);
        } catch (Exception e){
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

            recipe.setCategory(foundCategories.get(0));
        }

        if (recipeDTO.value() != null && recipeDTO.value().compareTo(BigDecimal.ZERO) >= 0) {
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


    public void delete(UUID id){
        Recipe recipe = findById(id);

        repository.delete(recipe);
    }


}
