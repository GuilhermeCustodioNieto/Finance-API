package com.github.guilhermecustodionieto.finance_api.repositories;

import com.github.guilhermecustodionieto.finance_api.entities.transaction.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RecipeRepository extends JpaRepository<Recipe, UUID> {
    List<Recipe> findByOriginContainingIgnoreCase(String origin);
    List<Recipe> findByDescriptionContainingIgnoreCase(String description);
}
