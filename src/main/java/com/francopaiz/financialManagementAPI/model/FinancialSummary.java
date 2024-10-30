package com.francopaiz.financialManagementAPI.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.Map;

@Data
@AllArgsConstructor // Genera un constructor con todos los argumentos
@NoArgsConstructor  // Genera el constructor sin argumentos
public class FinancialSummary {

    private BigDecimal totalExpenses;
    private BigDecimal totalIncome;
    private BigDecimal balance;
    //Incomes - expenses
    private Map<Category, BigDecimal> expensesByCategory;

}

