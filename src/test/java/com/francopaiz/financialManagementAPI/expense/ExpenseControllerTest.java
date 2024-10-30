package com.francopaiz.financialManagementAPI.expense;

import com.francopaiz.financialManagementAPI.controller.expense.ExpenseController;
import com.francopaiz.financialManagementAPI.model.Category;
import com.francopaiz.financialManagementAPI.model.Expense;
import com.francopaiz.financialManagementAPI.service.category.CategoryService;
import com.francopaiz.financialManagementAPI.service.expense.ExpenseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ExpenseControllerTest {

    @Mock
    private ExpenseService expenseService;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private ExpenseController expenseController;

    private Expense expense;
    private Category category;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        category = new Category();
        category.setId("1");
        category.setName("Food");

        expense = new Expense();
        expense.setId("1");
        expense.setAmount(BigDecimal.valueOf(100.00));
        expense.setCategory(category);
    }

    @Test
    void testFindAll() {
        // Arrange
        List<Expense> expenses = Arrays.asList(expense);
        when(expenseService.getExpenses()).thenReturn(expenses);

        // Act
        List<Expense> result = expenseController.findAll();

        // Assert
        assertEquals(1, result.size());
        assertEquals(BigDecimal.valueOf(100.00), result.get(0).getAmount());
        verify(expenseService, times(1)).getExpenses();
    }

    @Test
    void testFindById() {
        // Arrange
        when(expenseService.findExpenseById("1")).thenReturn(expense);

        // Act
        Expense result = expenseController.findById("1");

        // Assert
        assertEquals(BigDecimal.valueOf(100.00), result.getAmount());
        verify(expenseService, times(1)).findExpenseById("1");
    }

    @Test
    void testSaveWithCategory() {
        // Arrange
        when(expenseService.createExpense(any(Expense.class))).thenReturn(expense);

        // Act
        Expense result = expenseController.save("1", expense);

        // Assert
        assertEquals(BigDecimal.valueOf(100.00), result.getAmount());
        assertEquals("Food", result.getCategory().getName());
        verify(categoryService, never()).findCategoryById(anyString());
    }

    @Test
    void testSaveWithoutCategory() {
        // Arrange
        expense.setCategory(null);
        when(categoryService.findCategoryById("1")).thenReturn(category);
        when(expenseService.createExpense(any(Expense.class))).thenReturn(expense);

        // Act
        Expense result = expenseController.save("1", expense);

        // Assert
        assertEquals(BigDecimal.valueOf(100.00), result.getAmount());
        assertEquals("Food", result.getCategory().getName());
        verify(categoryService, times(1)).findCategoryById("1");
        verify(expenseService, times(1)).createExpense(any(Expense.class));
    }

    @Test
    void testUpdate() {
        // Arrange
        when(expenseService.updateExpense(eq("1"), any(Expense.class))).thenReturn(expense);

        // Act
        Expense result = expenseController.update("1", expense);

        // Assert
        assertEquals(BigDecimal.valueOf(100.00), result.getAmount());
        verify(expenseService, times(1)).updateExpense(eq("1"), any(Expense.class));
    }

    @Test
    void testDeleteById() {
        // Act
        expenseController.deleteById("1");

        // Assert
        verify(expenseService, times(1)).deleteExpense("1");
    }

    @Test
    void testFindExpensesForAuthenticatedUser() {
        // Arrange
        List<Expense> expenses = Arrays.asList(expense);
        when(expenseService.findExpensesForAuthenticatedUser()).thenReturn(expenses);

        // Act
        List<Expense> result = expenseController.findIncomesForAuthenticatedUser();

        // Assert
        assertEquals(1, result.size());
        assertEquals(BigDecimal.valueOf(100.00), result.get(0).getAmount());
        verify(expenseService, times(1)).findExpensesForAuthenticatedUser();
    }
}
