package com.github.guilhermecustodionieto.finance_api.controllers;

import com.github.guilhermecustodionieto.finance_api.dtos.authentication.UserAuthenticationDTO;
import com.github.guilhermecustodionieto.finance_api.dtos.user.UserCreationDTO;
import com.github.guilhermecustodionieto.finance_api.dtos.user.UserUpdatingDTO;
import com.github.guilhermecustodionieto.finance_api.entities.User;
import com.github.guilhermecustodionieto.finance_api.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Usuários", description = "Operações de gerenciamento de usuários")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Lista todos os usuários",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "Lista retornada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = User.class)
                    )
            )
    )
    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok().body(userService.findAll());
    }

    @Operation(
            summary = "Busca um usuário pelo ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuário encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserAuthenticationDTO.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
            }
    )
    @GetMapping("{id}")
    public ResponseEntity<UserAuthenticationDTO> findById(
            @Parameter(description = "ID do usuário", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok().body(userService.findById(id));
    }

    @Operation(
            summary = "Atualiza dados de um usuário",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuário atualizado com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserAuthenticationDTO.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
            }
    )
    @PatchMapping("{id}")
    public ResponseEntity<UserAuthenticationDTO> update(
            @Parameter(description = "ID do usuário a ser atualizado", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID id,
            @RequestBody UserUpdatingDTO userUpdatingDTO
    ) {
        UserAuthenticationDTO user = userService.update(id, userUpdatingDTO);
        return ResponseEntity.ok().body(user);
    }

    @Operation(
            summary = "Exclui um usuário pelo ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário excluído com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
            }
    )
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID do usuário a ser excluído", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID id
    ) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }
}
