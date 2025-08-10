package com.github.guilhermecustodionieto.finance_api.controllers.transaction;

import com.github.guilhermecustodionieto.finance_api.dtos.transaction.RecipeDTO;
import com.github.guilhermecustodionieto.finance_api.entities.transaction.Recipe;
import com.github.guilhermecustodionieto.finance_api.services.transaction.RecipeService;
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
@RequestMapping("recipe")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Recipe", description = "Operações relacionadas a receitas")
public class RecipeController {
    private RecipeService service;

    public RecipeController(RecipeService service) {
        this.service = service;
    }

    @Operation(summary = "Lista todas as receitas", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de receitas retornada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Recipe.class)))
    })
    @GetMapping("")
    public ResponseEntity<List<Recipe>> findAll(){
        return ResponseEntity.ok().body(service.findAll());
    }

    @Operation(summary = "Busca receitas pela origem", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de receitas filtrada por origem",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Recipe.class)))
    })
    @GetMapping("/origin")
    public ResponseEntity<List<Recipe>> findByOrigin(
            @Parameter(description = "Origem para filtrar receitas", required = true, example = "Brasil")
            @RequestParam String origin){
        return ResponseEntity.ok().body(service.findByOrigin(origin));
    }

    @Operation(summary = "Busca receitas pela descrição", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de receitas filtrada por descrição",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Recipe.class)))
    })
    @GetMapping("/description")
    public ResponseEntity<List<Recipe>> findByDescription(
            @Parameter(description = "Texto para filtrar a descrição", required = true, example = "bolo")
            @RequestParam String description){
        return ResponseEntity.ok().body(service.findByDescription(description));
    }

    @Operation(summary = "Busca uma receita pelo ID", responses = {
            @ApiResponse(responseCode = "200", description = "Receita encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Recipe.class))),
            @ApiResponse(responseCode = "404", description = "Receita não encontrada", content = @Content)
    })
    @GetMapping("{id}")
    public ResponseEntity<Recipe> findById(
            @Parameter(description = "ID da receita", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID id){
        return ResponseEntity.ok().body(service.findById(id));
    }

    @Operation(summary = "Cria uma nova receita", responses = {
            @ApiResponse(responseCode = "200", description = "Receita criada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Recipe.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Recipe> create(
            @RequestBody(description = "Dados da receita a serem criados", required = true,
                    content = @Content(schema = @Schema(implementation = RecipeDTO.class)))
            @org.springframework.web.bind.annotation.RequestBody RecipeDTO recipeDTO,

            @Parameter(description = "ID da carteira onde a receita será registrada", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
            @RequestParam UUID walletId){
        Recipe recipe = service.create(recipeDTO, walletId);
        return ResponseEntity.ok().body(recipe);
    }

    @Operation(summary = "Atualiza uma receita existente", responses = {
            @ApiResponse(responseCode = "200", description = "Receita atualizada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Recipe.class))),
            @ApiResponse(responseCode = "404", description = "Receita não encontrada", content = @Content)
    })
    @PatchMapping("{id}")
    public ResponseEntity<Recipe> update(
            @Parameter(description = "ID da receita a ser atualizada", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID id,

            @RequestBody(description = "Dados da receita para atualização", required = true,
                    content = @Content(schema = @Schema(implementation = RecipeDTO.class)))
            @org.springframework.web.bind.annotation.RequestBody RecipeDTO recipeDTO){
        Recipe recipe = service.update(id, recipeDTO);
        return ResponseEntity.ok().body(recipe);
    }

    @Operation(summary = "Deleta uma receita pelo ID e carteira", responses = {
            @ApiResponse(responseCode = "200", description = "Receita deletada com sucesso", content = @Content),
            @ApiResponse(responseCode = "404", description = "Receita não encontrada", content = @Content)
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID da receita a ser deletada", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID id,

            @Parameter(description = "ID da carteira associada à receita", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
            @RequestParam UUID walletId){
        service.delete(id, walletId);
        return ResponseEntity.ok().build();
    }
}
