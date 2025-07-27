package com.github.guilhermecustodionieto.finance_api.controllers.transaction;

import com.github.guilhermecustodionieto.finance_api.entities.transaction.TransactionHistory;
import com.github.guilhermecustodionieto.finance_api.services.transaction.TransactionHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/transaction-history")
public class TransactionHistoryController {
    private final TransactionHistoryService transactionHistoryService;

    public TransactionHistoryController(TransactionHistoryService transactionHistoryService) {
        this.transactionHistoryService = transactionHistoryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionHistory> findById(@PathVariable UUID id){
        TransactionHistory transactionHistory = transactionHistoryService.findById(id);
        return ResponseEntity.ok().body(transactionHistory);
    }

}
