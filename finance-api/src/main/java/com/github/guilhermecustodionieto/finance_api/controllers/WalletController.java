package com.github.guilhermecustodionieto.finance_api.controllers;

import com.github.guilhermecustodionieto.finance_api.entities.Wallet;
import com.github.guilhermecustodionieto.finance_api.services.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("wallet")
public class WalletController {
    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping
    public ResponseEntity<List<Wallet>> findAll(){
        return ResponseEntity.ok().body(walletService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Wallet> findById(@PathVariable UUID id){
        return ResponseEntity.ok().body(walletService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Wallet> create(){
        return ResponseEntity.ok().body(walletService.create());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        walletService.delete(id);
        return ResponseEntity.ok().build();
    }
}
