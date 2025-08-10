package com.github.guilhermecustodionieto.finance_api.controllers.transaction;

import com.github.guilhermecustodionieto.finance_api.entities.transaction.TransactionHistory;
import com.github.guilhermecustodionieto.finance_api.services.transaction.TransactionHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/transaction-history")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "TransactionHistory", description = "Operações relacionadas ao histórico de transações")
public class TransactionHistoryController {

    private final TransactionHistoryService transactionHistoryService;

    public TransactionHistoryController(TransactionHistoryService transactionHistoryService) {
        this.transactionHistoryService = transactionHistoryService;
    }

    @Operation(
            summary = "Busca um histórico de transação pelo ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Histórico de transação encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TransactionHistory.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Histórico de transação não encontrado", content = @Content)
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<TransactionHistory> findById(
            @Parameter(description = "ID do histórico de transação", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID id) {
        TransactionHistory transactionHistory = transactionHistoryService.findById(id);
        return ResponseEntity.ok().body(transactionHistory);
    }
}
