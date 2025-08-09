package com.github.guilhermecustodionieto.finance_api.services;

import com.github.guilhermecustodionieto.finance_api.config.SecurityConfig;
import com.github.guilhermecustodionieto.finance_api.dtos.authentication.AuthenticationDTO;
import com.github.guilhermecustodionieto.finance_api.dtos.authentication.RecoveryJwtTokenDTO;
import com.github.guilhermecustodionieto.finance_api.dtos.authentication.UserAuthenticationDTO;
import com.github.guilhermecustodionieto.finance_api.dtos.user.UserCreationDTO;
import com.github.guilhermecustodionieto.finance_api.dtos.user.UserUpdatingDTO;
import com.github.guilhermecustodionieto.finance_api.entities.User;
import com.github.guilhermecustodionieto.finance_api.entities.UserDetailsImpl;
import com.github.guilhermecustodionieto.finance_api.entities.Wallet;
import com.github.guilhermecustodionieto.finance_api.exceptions.generics.DataIntegrityViolationException;
import com.github.guilhermecustodionieto.finance_api.exceptions.generics.EntityNotFoundException;
import com.github.guilhermecustodionieto.finance_api.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository repository;
    private final WalletService walletService;
    private final PasswordEncoder passwordEncoder;

    private AuthenticationManager authenticationManager;
    private JwtTokenService jwtTokenService;
    private SecurityConfig securityConfig;

    public UserService(UserRepository repository, WalletService walletService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtTokenService jwtTokenService, SecurityConfig securityConfig) {
        this.repository = repository;
        this.walletService = walletService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.securityConfig = securityConfig;
    }

    public RecoveryJwtTokenDTO authenticateUser(AuthenticationDTO authenticationDTO){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(authenticationDTO.email(), authenticationDTO.password());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return new RecoveryJwtTokenDTO(jwtTokenService.generateToken(userDetails));
    }


    public List<User> findAll(){
        return repository.findAll();
    }

    public UserAuthenticationDTO findById(UUID id){
        Optional<User> optionalUser = repository.findById(id);

        if(optionalUser.isEmpty()){
            throw new EntityNotFoundException("User", "id: " + id);
        }

        User user = optionalUser.get();

        return new UserAuthenticationDTO(user.getId(), user.getName(), user.getEmail(), user.getPhone(), user.getWallet());
    }

    public User findCompleteUserById(UUID id){
        Optional<User> optionalUser = repository.findById(id);

        if(optionalUser.isEmpty()){
            throw new EntityNotFoundException("User", "id: " + id);
        }

        return optionalUser.get();
    }

    public User findByEmail(String email){
        User user = repository.findByEmail(email);

        if(user == null){
            throw new EntityNotFoundException("User", "email: " + email);
        }

        return user;
    }

    @Transactional
    public UserAuthenticationDTO create(UserCreationDTO userCreationDTO){
        if(!userCreationDTO.password().equals(userCreationDTO.confirmPassword())){
            throw new DataIntegrityViolationException("Passwords do not match");
        }

        if(repository.existsByEmail(userCreationDTO.email())){
            throw new DataIntegrityViolationException("Email is already in use");
        }

        Wallet wallet = walletService.create();

        User user = new User();
        user.setName(userCreationDTO.name());
        user.setEmail(userCreationDTO.email());
        user.setWallet(wallet);
        user.setPhone(userCreationDTO.phone());

        user.setPassword(passwordEncoder.encode(userCreationDTO.password()));

        repository.save(user);

        return new UserAuthenticationDTO(user.getId(), user.getName(), user.getEmail(), user.getPhone(), user.getWallet());
    }

    public UserAuthenticationDTO update(UUID id, UserUpdatingDTO userUpdatingDTO){
        User user = findCompleteUserById(id);

        if (userUpdatingDTO.name() != null) {
            user.setName(userUpdatingDTO.name());
        }

        if (userUpdatingDTO.email() != null) {
            user.setEmail(userUpdatingDTO.email());
        }

        if(userUpdatingDTO.password() != null){
            if(!(userUpdatingDTO.password().equals(userUpdatingDTO.confirmPassword()))){
                    throw new DataIntegrityViolationException("The passwords do not match");
            }

            user.setPassword(userUpdatingDTO.password());
        }

        if(userUpdatingDTO.phone() != null){
            user.setPhone(userUpdatingDTO.phone());
        }

        repository.save(user);
        return new UserAuthenticationDTO(user.getId(), user.getName(), user.getEmail(), user.getPhone(), user.getWallet());
    }

    public void delete(UUID id){
        User user = findCompleteUserById(id);
        repository.delete(user);
    }
}
