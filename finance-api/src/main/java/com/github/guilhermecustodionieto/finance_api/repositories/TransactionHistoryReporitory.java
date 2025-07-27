package com.github.guilhermecustodionieto.finance_api.repositories;

import com.github.guilhermecustodionieto.finance_api.entities.transaction.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TransactionHistoryReporitory extends JpaRepository<TransactionHistory, UUID> {

}
