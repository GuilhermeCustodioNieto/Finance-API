package com.github.guilhermecustodionieto.finance_api.controllers;

import com.github.guilhermecustodionieto.finance_api.dtos.authentication.AuthenticationDTO;
import com.github.guilhermecustodionieto.finance_api.dtos.authentication.RecoveryJwtTokenDTO;
import com.github.guilhermecustodionieto.finance_api.dtos.authentication.UserAuthenticationDTO;
import com.github.guilhermecustodionieto.finance_api.dtos.user.UserCreationDTO;
import com.github.guilhermecustodionieto.finance_api.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<RecoveryJwtTokenDTO> login(@RequestBody @Valid AuthenticationDTO authenticationDTO){
        RecoveryJwtTokenDTO recoveryJwtTokenDTO = userService.authenticateUser(authenticationDTO);

        return ResponseEntity.ok().body(recoveryJwtTokenDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<RecoveryJwtTokenDTO> create(@Valid @RequestBody UserCreationDTO userCreationDTO){
        userService.create(userCreationDTO);
        AuthenticationDTO authenticationDTO = new AuthenticationDTO(userCreationDTO.email(), userCreationDTO.password());
        RecoveryJwtTokenDTO recoveryJwtTokenDTO = userService.authenticateUser(authenticationDTO);

        return ResponseEntity.ok().body(recoveryJwtTokenDTO);
    }
}
