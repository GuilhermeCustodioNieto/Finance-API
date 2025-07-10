package com.github.guilhermecustodionieto.finance_api.controllers.transaction;

import com.github.guilhermecustodionieto.finance_api.dtos.transaction.TransactionCategoryDTO;
import com.github.guilhermecustodionieto.finance_api.entities.transaction.TransactionCategory;
import com.github.guilhermecustodionieto.finance_api.exceptions.generics.EntityNotFoundException;
import com.github.guilhermecustodionieto.finance_api.services.transaction.TransactionCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("transaction-category")
public class TransactionCategoryController {
    private TransactionCategoryService service;

    public TransactionCategoryController(TransactionCategoryService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<List<TransactionCategory>> findAll(){
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<TransactionCategory> findById(@PathVariable("id") UUID id){
        TransactionCategory transactionCategory = service.findById(id);
        return ResponseEntity.ok().body(transactionCategory);
    }

    @PostMapping
    public ResponseEntity<TransactionCategory> create(@RequestBody TransactionCategoryDTO transactionCategoryDTO){
        return ResponseEntity.ok().body(service.create(transactionCategoryDTO));
    }

    @PatchMapping("{id}")
    public ResponseEntity<TransactionCategory> update(@RequestBody TransactionCategoryDTO transactionCategoryDTO, @PathVariable UUID id){
        return ResponseEntity.ok().body(service.update(transactionCategoryDTO, id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}

