package com.github.guilhermecustodionieto.finance_api.controllers.transaction;

import com.github.guilhermecustodionieto.finance_api.dtos.transaction.TransactionCategoryDTO;
import com.github.guilhermecustodionieto.finance_api.entities.transaction.TransactionCategory;
import com.github.guilhermecustodionieto.finance_api.services.transaction.TransactionCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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
@RequestMapping("transaction-category")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Transaction Category", description = "Operações relacionadas a categorias de transação")
public class TransactionCategoryController {
    private TransactionCategoryService service;

    public TransactionCategoryController(TransactionCategoryService service) {
        this.service = service;
    }

    @Operation(summary = "Lista todas as categorias de transação", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de categorias retornada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TransactionCategory.class)))
    })
    @GetMapping("/")
    public ResponseEntity<List<TransactionCategory>> findAll(){
        return ResponseEntity.ok().body(service.findAll());
    }

    @Operation(summary = "Busca uma categoria pelo ID", responses = {
            @ApiResponse(responseCode = "200", description = "Categoria encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TransactionCategory.class))),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada", content = @Content)
    })
    @GetMapping("{id}")
    public ResponseEntity<TransactionCategory> findById(
            @Parameter(description = "ID da categoria", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable("id") UUID id){
        TransactionCategory transactionCategory = service.findById(id);
        return ResponseEntity.ok().body(transactionCategory);
    }

    @Operation(summary = "Cria uma nova categoria de transação", responses = {
            @ApiResponse(responseCode = "200", description = "Categoria criada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TransactionCategory.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<TransactionCategory> create(
            @RequestBody(description = "Dados da categoria a serem criados", required = true,
                    content = @Content(schema = @Schema(implementation = TransactionCategoryDTO.class)))
            @org.springframework.web.bind.annotation.RequestBody TransactionCategoryDTO transactionCategoryDTO){
        return ResponseEntity.ok().body(service.create(transactionCategoryDTO));
    }

    @Operation(summary = "Atualiza uma categoria existente", responses = {
            @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TransactionCategory.class))),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada", content = @Content)
    })
    @PatchMapping("{id}")
    public ResponseEntity<TransactionCategory> update(
            @RequestBody(description = "Dados da categoria para atualização", required = true,
                    content = @Content(schema = @Schema(implementation = TransactionCategoryDTO.class)))
            @org.springframework.web.bind.annotation.RequestBody TransactionCategoryDTO transactionCategoryDTO,

            @Parameter(description = "ID da categoria a ser atualizada", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID id){
        return ResponseEntity.ok().body(service.update(transactionCategoryDTO, id));
    }

    @Operation(summary = "Deleta uma categoria pelo ID", responses = {
            @ApiResponse(responseCode = "200", description = "Categoria deletada com sucesso", content = @Content),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada", content = @Content)
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID da categoria a ser deletada", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
