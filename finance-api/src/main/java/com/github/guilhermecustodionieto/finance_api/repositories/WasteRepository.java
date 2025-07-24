package com.github.guilhermecustodionieto.finance_api.repositories;

import com.github.guilhermecustodionieto.finance_api.entities.transaction.Recipe;
import com.github.guilhermecustodionieto.finance_api.entities.transaction.Waste;
import com.github.guilhermecustodionieto.finance_api.entities.transaction.enums.PaymentFormat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WasteRepository extends JpaRepository<Waste, UUID> {
    List<Waste> findByDescriptionContainingIgnoreCase(String description);
    List<Waste> findByPaymentFormat(PaymentFormat paymentFormat);
}
