package com.github.guilhermecustodionieto.finance_api.controllers;

import com.github.guilhermecustodionieto.finance_api.dtos.authentication.AuthenticationDTO;
import com.github.guilhermecustodionieto.finance_api.dtos.authentication.RecoveryJwtTokenDTO;
import com.github.guilhermecustodionieto.finance_api.dtos.user.UserCreationDTO;
import com.github.guilhermecustodionieto.finance_api.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@Tag(name = "Autenticação", description = "Endpoints para login e registro de usuários")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Operation(
            summary = "Realiza login do usuário",
            description = "Recebe email e senha e retorna um token JWT para autenticação.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Login realizado com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RecoveryJwtTokenDTO.class),
                                    examples = @ExampleObject(value = "{ \"token\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...\" }")
                            )
                    ),
                    @ApiResponse(responseCode = "401", description = "Credenciais inválidas", content = @Content)
            }
    )
    @PostMapping("/login")
    public ResponseEntity<RecoveryJwtTokenDTO> login(
            @Valid @RequestBody AuthenticationDTO authenticationDTO
    ) {
        RecoveryJwtTokenDTO recoveryJwtTokenDTO = userService.authenticateUser(authenticationDTO);
        return ResponseEntity.ok().body(recoveryJwtTokenDTO);
    }

    @Operation(
            summary = "Registra um novo usuário",
            description = "Cria um novo usuário com email e senha, e retorna um token JWT já autenticado.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuário registrado e autenticado com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RecoveryJwtTokenDTO.class),
                                    examples = @ExampleObject(value = "{ \"token\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...\" }")
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
            }
    )
    @PostMapping("/register")
    public ResponseEntity<RecoveryJwtTokenDTO> create(
            @Valid @RequestBody UserCreationDTO userCreationDTO
    ) {
        userService.create(userCreationDTO);
        AuthenticationDTO authenticationDTO = new AuthenticationDTO(userCreationDTO.email(), userCreationDTO.password());
        RecoveryJwtTokenDTO recoveryJwtTokenDTO = userService.authenticateUser(authenticationDTO);
        return ResponseEntity.ok().body(recoveryJwtTokenDTO);
    }
}
