package com.github.guilhermecustodionieto.finance_api.controllers.transaction;

import com.github.guilhermecustodionieto.finance_api.dtos.transaction.WasteDTO;
import com.github.guilhermecustodionieto.finance_api.entities.transaction.Recipe;
import com.github.guilhermecustodionieto.finance_api.entities.transaction.Waste;
import com.github.guilhermecustodionieto.finance_api.entities.transaction.enums.PaymentFormat;
import com.github.guilhermecustodionieto.finance_api.services.transaction.RecipeService;
import com.github.guilhermecustodionieto.finance_api.services.transaction.WasteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/wastes")
public class WasteController {
    private WasteService wasteService;

    public WasteController(WasteService wasteService) {
        this.wasteService = wasteService;
    }

    @GetMapping
    public ResponseEntity<List<Waste>> findAll(){
        return ResponseEntity.ok().body(wasteService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Waste> findById(@PathVariable UUID id){
        return ResponseEntity.ok().body(wasteService.findById(id));
    }

    @GetMapping("/description")
    public ResponseEntity<List<Waste>> findByDescription(@RequestParam String description){
        return ResponseEntity.ok().body(wasteService.findByDescription(description));
    }

    @GetMapping("/payment-format")
    public ResponseEntity<List<Waste>> findByPaymentFormat(@RequestParam PaymentFormat paymentFormat){
        return ResponseEntity.ok().body(wasteService.findByPaymentFormat(paymentFormat));
    }

    @PostMapping
    public ResponseEntity<Waste> create(@RequestBody WasteDTO wasteDTO){
        return ResponseEntity.ok().body(wasteService.create(wasteDTO));
    }

    @PatchMapping("{id}")
    public ResponseEntity<Waste> update(@PathVariable UUID id, @RequestBody WasteDTO wasteDTO){
        return ResponseEntity.ok().body(wasteService.update(id, wasteDTO));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        wasteService.delete(id);
        return ResponseEntity.ok().build();
    }
}
