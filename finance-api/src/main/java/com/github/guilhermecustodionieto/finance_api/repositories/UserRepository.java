package com.github.guilhermecustodionieto.finance_api.repositories;

import com.github.guilhermecustodionieto.finance_api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);
}
