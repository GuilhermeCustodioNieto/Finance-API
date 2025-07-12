package com.github.guilhermecustodionieto.finance_api.controllers.transaction;

import com.github.guilhermecustodionieto.finance_api.dtos.transaction.RecipeDTO;
import com.github.guilhermecustodionieto.finance_api.entities.transaction.Recipe;
import com.github.guilhermecustodionieto.finance_api.services.transaction.RecipeService;
import jakarta.persistence.Column;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("recipes")
public class RecipeController {
    private RecipeService service;

    public RecipeController(RecipeService service) {
        this.service = service;
    }

    @GetMapping("")
    public ResponseEntity<List<Recipe>> findAll(){
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping("/origin")
    public ResponseEntity<List<Recipe>> findByOrigin(@RequestBody String origin){
        return ResponseEntity.ok().body(service.findByOrigin(origin));
    }

    @GetMapping("{id}")
    public ResponseEntity<Recipe> findById(@PathVariable UUID id){
        return ResponseEntity.ok().body(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Recipe> create(@RequestBody RecipeDTO recipeDTO){
        Recipe recipe = service.create(recipeDTO);
        return ResponseEntity.ok().body(recipe);
    }

    @PatchMapping("{id}")
    public ResponseEntity<Recipe> update(@PathVariable UUID id, @RequestBody RecipeDTO recipeDTO){
        Recipe recipe = service.update(id, recipeDTO);
        return ResponseEntity.ok().body(recipe);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}
