package com.github.guilhermecustodionieto.finance_api.controllers;

import com.github.guilhermecustodionieto.finance_api.entities.Wallet;
import com.github.guilhermecustodionieto.finance_api.services.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("wallet")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Wallet", description = "Operações relacionadas a carteiras financeiras")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @Operation(
            summary = "Lista todas as carteiras",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de carteiras retornada com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Wallet.class)
                            )
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<Wallet>> findAll() {
        return ResponseEntity.ok().body(walletService.findAll());
    }

    @Operation(
            summary = "Busca uma carteira pelo ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Carteira encontrada",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Wallet.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Carteira não encontrada", content = @Content)
            }
    )
    @GetMapping("{id}")
    public ResponseEntity<Wallet> findById(
            @Parameter(description = "ID da carteira", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID id) {
        return ResponseEntity.ok().body(walletService.findById(id));
    }

    @Operation(
            summary = "Cria uma nova carteira",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Carteira criada com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Wallet.class)
                            )
                    )
            }
    )
    @PostMapping
    public ResponseEntity<Wallet> create() {
        return ResponseEntity.ok().body(walletService.create());
    }

    @Operation(
            summary = "Exclui uma carteira pelo ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Carteira excluída com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Carteira não encontrada", content = @Content)
            }
    )
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID da carteira a ser excluída", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID id) {
        walletService.delete(id);
        return ResponseEntity.ok().build();
    }
}
