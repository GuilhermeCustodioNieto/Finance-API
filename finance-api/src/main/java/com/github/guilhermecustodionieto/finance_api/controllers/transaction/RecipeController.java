package com.github.guilhermecustodionieto.finance_api.controllers.transaction;

import com.github.guilhermecustodionieto.finance_api.dtos.transaction.RecipeDTO;
import com.github.guilhermecustodionieto.finance_api.entities.transaction.Recipe;
import com.github.guilhermecustodionieto.finance_api.services.transaction.RecipeService;
import jakarta.persistence.Column;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("recipe")
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
    public ResponseEntity<List<Recipe>> findByOrigin(@RequestParam String origin){
        System.out.println();
        return ResponseEntity.ok().body(service.findByOrigin(origin));
    }

    @GetMapping("/description")
    public ResponseEntity<List<Recipe>> findByDescription(@RequestParam String description){
        return ResponseEntity.ok().body(service.findByDescription(description));
    }

    @GetMapping("{id}")
    public ResponseEntity<Recipe> findById(@PathVariable UUID id){
        return ResponseEntity.ok().body(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Recipe> create(@RequestBody RecipeDTO recipeDTO, @RequestParam UUID walletId){
        Recipe recipe = service.create(recipeDTO, walletId);
        return ResponseEntity.ok().body(recipe);
    }

    @PatchMapping("{id}")
    public ResponseEntity<Recipe> update(@PathVariable UUID id, @RequestBody RecipeDTO recipeDTO){
        Recipe recipe = service.update(id, recipeDTO);
        return ResponseEntity.ok().body(recipe);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id, @RequestParam UUID walletId){
        service.delete(id, walletId);
        return ResponseEntity.ok().build();
    }

}
