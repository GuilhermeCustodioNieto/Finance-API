package com.github.guilhermecustodionieto.finance_api.controllers;

import com.github.guilhermecustodionieto.finance_api.dtos.transaction.user.UserAuthenticationDTO;
import com.github.guilhermecustodionieto.finance_api.dtos.transaction.user.UserCreationDTO;
import com.github.guilhermecustodionieto.finance_api.dtos.transaction.user.UserUpdatingDTO;
import com.github.guilhermecustodionieto.finance_api.entities.User;
import com.github.guilhermecustodionieto.finance_api.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll(){
        return ResponseEntity.ok().body(userService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<UserAuthenticationDTO> findById(@PathVariable UUID id){
        return ResponseEntity.ok().body(userService.findById(id));
    }

    @PostMapping
    public ResponseEntity<UserAuthenticationDTO> create(@Valid @RequestBody UserCreationDTO userCreationDTO){
        UserAuthenticationDTO user = userService.create(userCreationDTO);

        return ResponseEntity.ok().body(user);
    }

    @PatchMapping("{id}")
    public ResponseEntity<UserAuthenticationDTO> update(@PathVariable UUID id ,@RequestBody UserUpdatingDTO userUpdatingDTO){
        UserAuthenticationDTO user = userService.update(id, userUpdatingDTO);

        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        userService.delete(id);
        return ResponseEntity.ok().build();
    }
}
