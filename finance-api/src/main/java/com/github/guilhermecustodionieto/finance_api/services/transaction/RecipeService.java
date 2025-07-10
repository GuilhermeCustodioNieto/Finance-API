package com.github.guilhermecustodionieto.finance_api.services.transaction;

import com.github.guilhermecustodionieto.finance_api.dtos.transaction.RecipeDTO;
import com.github.guilhermecustodionieto.finance_api.entities.transaction.Recipe;
import com.github.guilhermecustodionieto.finance_api.exceptions.generics.DataIntegrityViolationException;
import com.github.guilhermecustodionieto.finance_api.exceptions.generics.EntityNotFoundException;
import com.github.guilhermecustodionieto.finance_api.repositories.RecipeRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class RecipeService {
    private RecipeRepository repository;

    public RecipeService(RecipeRepository repository) {
        this.repository = repository;
    }

    public List<Recipe> findAll(){
        return repository.findAll();
    }

    public List<Recipe> findByOrigin(String origin){
        return repository.findByOrigin(origin);
    }

    public Recipe findById(UUID id){
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Recipe", id.toString()));
    }

    public Recipe create(RecipeDTO recipeDTO){
        Date recipeDate = recipeDTO.date() == null ? new Date() : recipeDTO.date();

        try{
            Recipe recipe = new Recipe(
                    recipeDTO.value(),
                    recipeDate,
                    recipeDTO.isRecurring(),
                    recipeDTO.category(),
                    recipeDTO.origin()
            );

            return repository.save(recipe);
        } catch (Exception e){
            throw new DataIntegrityViolationException("Recipe");
        }

    }

    public Recipe update(UUID id, RecipeDTO recipeDTO){
        Recipe recipe = findById(id);

        if(recipeDTO.value() != null) recipe.setValue(recipeDTO.value());
        if(recipeDTO.date() != null) recipe.setDate(recipeDTO.date());
        if(recipeDTO.category() != null) recipe.setCategory(recipeDTO.category());
        if(recipeDTO.origin() != null) recipe.setOrigin(recipeDTO.origin());
        if(recipeDTO.isRecurring() != null) recipe.setIsRecurring(recipeDTO.isRecurring());
        if(recipeDTO.description() != null) recipe.setDescription(recipeDTO.description());

        return repository.save(recipe);
    }

    public void delete(UUID id){
        Recipe recipe = findById(id);

        repository.delete(recipe);
    }


}
