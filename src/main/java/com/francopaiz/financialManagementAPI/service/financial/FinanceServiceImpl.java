package com.francopaiz.financialManagementAPI.service.financial;

import com.francopaiz.financialManagementAPI.model.*;
import com.francopaiz.financialManagementAPI.repository.expense.ExpenseRepository;
import com.francopaiz.financialManagementAPI.repository.financial.FinanceRepository;
import com.francopaiz.financialManagementAPI.repository.income.IncomeRepository;
import com.francopaiz.financialManagementAPI.service.expense.ExpenseService;
import com.francopaiz.financialManagementAPI.service.income.IncomeService;
import jdk.swing.interop.SwingInterOpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FinanceServiceImpl implements FinanceService {

    @Autowired
    private FinanceRepository financialRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private ExpenseService expenseService;

    @Value("${spring.profiles.active}")
    private String profile;

    @Override
    public FinancialSummary generateSummary(User user, LocalDate from, LocalDate to) {
        System.out.println("Usuario que cae en generate: " + user);

        List<Income> incomes;
        List<Expense> expenses;
        if (profile.equals("mongo")){
             incomes = incomeService.findIncomesForAuthenticatedUser();
            expenses = expenseService.findExpensesForAuthenticatedUser();
        } else {
            expenses = expenseRepository.findByUserAndDateBetween(user, from, to);
            incomes = incomeRepository.findByUserAndDateBetween(user, from, to);
        }




        // PRUEBAS DE DEBUG
        System.out.println("Gastos: " + expenses);
        System.out.println("Ingresos: "+incomes);


        BigDecimal totalExpenses = expenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalIncomes = incomes.stream()
                .map(Income::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<Category, BigDecimal> expensesByCategory = expenses.stream()
                .collect((Collectors.groupingBy(Expense::getCategory,
                        Collectors.reducing(BigDecimal.ZERO, Expense:: getAmount, BigDecimal::add))));


        BigDecimal balance = totalIncomes.subtract(totalExpenses);

        FinancialSummary summary = new FinancialSummary(totalExpenses, totalIncomes, balance, expensesByCategory);
        summary.setTotalExpenses(totalExpenses);
        summary.setTotalIncome(totalIncomes);
        summary.setBalance(balance);
        summary.setExpensesByCategory(expensesByCategory);


        return summary;
    }
}
