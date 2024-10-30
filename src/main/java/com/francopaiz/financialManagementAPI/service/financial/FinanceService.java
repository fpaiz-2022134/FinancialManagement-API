package com.francopaiz.financialManagementAPI.service.financial;

import com.francopaiz.financialManagementAPI.model.FinancialSummary;
import com.francopaiz.financialManagementAPI.model.User;

import java.time.LocalDate;

public interface FinanceService {
    /*
        List<FinancialSummary> findAll();
        FinancialSummary findById(String id);*/
    FinancialSummary generateSummary(User user, LocalDate from, LocalDate to);

}