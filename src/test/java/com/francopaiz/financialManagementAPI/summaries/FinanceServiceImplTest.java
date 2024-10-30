package com.francopaiz.financialManagementAPI.summaries;

import com.francopaiz.financialManagementAPI.model.*;
import com.francopaiz.financialManagementAPI.repository.expense.ExpenseRepository;
import com.francopaiz.financialManagementAPI.repository.financial.FinanceRepository;
import com.francopaiz.financialManagementAPI.repository.income.IncomeRepository;
import com.francopaiz.financialManagementAPI.service.expense.ExpenseService;
import com.francopaiz.financialManagementAPI.service.financial.FinanceServiceImpl;
import com.francopaiz.financialManagementAPI.service.income.IncomeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FinanceServiceImplTest {

    @Mock
    private FinanceRepository financialRepository;

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private IncomeRepository incomeRepository;

    @Mock
    private IncomeService incomeService;

    @Mock
    private ExpenseService expenseService;

    @InjectMocks
    private FinanceServiceImpl financeService;

    private User user;
    private List<Income> incomes;
    private List<Expense> expenses;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId("1");

        // Crear instancia de la categoría
        Category foodCategory = new Category();
        foodCategory.setId("1");
        foodCategory.setName("Food");

        // Definir ingresos y gastos simulados
        Income income = new Income();
        income.setAmount(new BigDecimal("1000.00"));
        income.setDate(LocalDate.of(2023, 1, 1));
        income.setUser(user);

        Expense expense = new Expense();
        expense.setAmount(new BigDecimal("500.00"));
        expense.setDate(LocalDate.of(2023, 1, 1));
        expense.setCategory(foodCategory);  // Asignar la categoría de comida
        expense.setUser(user);

        incomes = Collections.singletonList(income);
        expenses = Collections.singletonList(expense);
    }

    @Test
    void testGenerateSummaryWithMongoProfile() {
        // Configurar el perfil para "mongo"
        ReflectionTestUtils.setField(financeService, "profile", "mongo");

        // Configurar comportamiento de mocks
        when(incomeService.findIncomesForAuthenticatedUser()).thenReturn(incomes);
        when(expenseService.findExpensesForAuthenticatedUser()).thenReturn(expenses);

        // Ejecutar el método
        FinancialSummary summary = financeService.generateSummary(user, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31));

        // Verificar resultados
        assertEquals(new BigDecimal("500.00"), summary.getTotalExpenses());
        assertEquals(new BigDecimal("1000.00"), summary.getTotalIncome());
        assertEquals(new BigDecimal("500.00"), summary.getBalance());

        // Verificar interacciones
        verify(incomeService, times(1)).findIncomesForAuthenticatedUser();
        verify(expenseService, times(1)).findExpensesForAuthenticatedUser();
    }

    @Test
    void testGenerateSummaryWithDefaultProfile() {
        // Configurar el perfil para el caso por defecto (diferente de "mongo")
        ReflectionTestUtils.setField(financeService, "profile", "default");

        // Configurar comportamiento de mocks
        when(expenseRepository.findByUserAndDateBetween(any(User.class), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(expenses);
        when(incomeRepository.findByUserAndDateBetween(any(User.class), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(incomes);

        // Ejecutar el método
        FinancialSummary summary = financeService.generateSummary(user, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31));

        // Verificar resultados
        assertEquals(new BigDecimal("500.00"), summary.getTotalExpenses());
        assertEquals(new BigDecimal("1000.00"), summary.getTotalIncome());
        assertEquals(new BigDecimal("500.00"), summary.getBalance());

        // Verificar interacciones
        verify(expenseRepository, times(1)).findByUserAndDateBetween(user, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31));
        verify(incomeRepository, times(1)).findByUserAndDateBetween(user, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31));
    }
}