package com.github.guilhermecustodionieto.finance_api.controllers.transaction;

import com.github.guilhermecustodionieto.finance_api.dtos.transaction.WasteDTO;
import com.github.guilhermecustodionieto.finance_api.entities.transaction.Waste;
import com.github.guilhermecustodionieto.finance_api.entities.transaction.enums.PaymentFormat;
import com.github.guilhermecustodionieto.finance_api.services.transaction.WasteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/waste")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Waste", description = "Operações relacionadas a gastos")
public class WasteController {

    private final WasteService wasteService;

    public WasteController(WasteService wasteService) {
        this.wasteService = wasteService;
    }

    @Operation(summary = "Lista todos os gastos", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de gastos retornada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Waste.class)))
    })
    @GetMapping
    public ResponseEntity<List<Waste>> findAll(){
        return ResponseEntity.ok().body(wasteService.findAll());
    }

    @Operation(summary = "Busca um gasto pelo ID", responses = {
            @ApiResponse(responseCode = "200", description = "Gasto encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Waste.class))),
            @ApiResponse(responseCode = "404", description = "Gasto não encontrado", content = @Content)
    })
    @GetMapping("{id}")
    public ResponseEntity<Waste> findById(
            @Parameter(description = "ID do gasto", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID id){
        return ResponseEntity.ok().body(wasteService.findById(id));
    }

    @Operation(summary = "Busca gastos pela descrição", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de gastos filtrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Waste.class)))
    })
    @GetMapping("/description")
    public ResponseEntity<List<Waste>> findByDescription(
            @Parameter(description = "Texto para filtrar a descrição", required = true, example = "aluguel")
            @RequestParam String description){
        return ResponseEntity.ok().body(wasteService.findByDescription(description));
    }

    @Operation(summary = "Busca gastos pelo formato de pagamento", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de gastos filtrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Waste.class)))
    })
    @GetMapping("/payment-format")
    public ResponseEntity<List<Waste>> findByPaymentFormat(
            @Parameter(description = "Formato de pagamento para filtro", required = true, example = "CASH")
            @RequestParam PaymentFormat paymentFormat){
        return ResponseEntity.ok().body(wasteService.findByPaymentFormat(paymentFormat));
    }

    @Operation(summary = "Cria um novo gasto", responses = {
            @ApiResponse(responseCode = "200", description = "Gasto criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Waste.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Waste> create(
            @RequestBody(description = "Dados do gasto a serem criados", required = true,
                    content = @Content(schema = @Schema(implementation = WasteDTO.class)))
            @org.springframework.web.bind.annotation.RequestBody WasteDTO wasteDTO,

            @Parameter(description = "ID da carteira onde o gasto será registrado", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
            @RequestParam UUID walletId){
        return ResponseEntity.ok().body(wasteService.create(wasteDTO, walletId));
    }

    @Operation(summary = "Atualiza um gasto existente", responses = {
            @ApiResponse(responseCode = "200", description = "Gasto atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Waste.class))),
            @ApiResponse(responseCode = "404", description = "Gasto não encontrado", content = @Content)
    })
    @PatchMapping("{id}")
    public ResponseEntity<Waste> update(
            @Parameter(description = "ID do gasto a ser atualizado", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID id,

            @RequestBody(description = "Dados do gasto para atualização", required = true,
                    content = @Content(schema = @Schema(implementation = WasteDTO.class)))
            @org.springframework.web.bind.annotation.RequestBody WasteDTO wasteDTO){
        return ResponseEntity.ok().body(wasteService.update(id, wasteDTO));
    }

    @Operation(summary = "Deleta um gasto pelo ID e carteira", responses = {
            @ApiResponse(responseCode = "200", description = "Gasto deletado com sucesso", content = @Content),
            @ApiResponse(responseCode = "404", description = "Gasto não encontrado", content = @Content)
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID do gasto a ser deletado", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID id,

            @Parameter(description = "ID da carteira associada ao gasto", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
            @RequestParam UUID walletId){
        wasteService.delete(id, walletId);
        return ResponseEntity.ok().build();
    }
}
