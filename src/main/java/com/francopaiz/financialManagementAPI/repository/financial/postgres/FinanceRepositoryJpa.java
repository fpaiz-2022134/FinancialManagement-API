package com.francopaiz.financialManagementAPI.repository.financial.postgres;

import com.francopaiz.financialManagementAPI.model.postgres.FinancialPostgres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinanceRepositoryJpa extends JpaRepository<FinancialPostgres, Long> {
}
