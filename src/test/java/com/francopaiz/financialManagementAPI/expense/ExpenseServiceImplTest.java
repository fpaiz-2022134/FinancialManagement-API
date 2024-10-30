package com.francopaiz.financialManagementAPI.expense;

import com.francopaiz.financialManagementAPI.model.Expense;
import com.francopaiz.financialManagementAPI.model.User;
import com.francopaiz.financialManagementAPI.repository.expense.ExpenseRepository;
import com.francopaiz.financialManagementAPI.repository.usuario.UserRepository;
import com.francopaiz.financialManagementAPI.service.expense.ExpenseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExpenseServiceImplTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private ExpenseServiceImpl expenseService;

    private User authenticatedUser;

    @BeforeEach
    void setUp() {
        authenticatedUser = new User();
        authenticatedUser.setId("1");
        authenticatedUser.setName("Test User");
        authenticatedUser.setUsername("testuser"); // Agregar si es necesario para el test
        authenticatedUser.setEmail("testuser@example.com"); // Agregar si es necesario para el test

/*
        when(securityContext.getAuthentication()).thenReturn(authentication);

*/


        SecurityContextHolder.setContext(securityContext);
/*
        when(authentication.getPrincipal()).thenReturn(authenticatedUser);
*/

        // Asignar valor de prueba para 'profile'
        ReflectionTestUtils.setField(expenseService, "profile", "postgres");
    }

    @Test
    public void testGetExpenses() {
        Expense expense1 = new Expense();
        Expense expense2 = new Expense();
        when(expenseRepository.getExpenses()).thenReturn(List.of(expense1, expense2));

        List<Expense> expenses = expenseService.getExpenses();

        assertEquals(2, expenses.size());
        verify(expenseRepository, times(1)).getExpenses();
    }

    @Test
    public void testFindExpenseById() {
        Expense expense = new Expense();
        expense.setId("123");
        when(expenseRepository.findExpenseById("123")).thenReturn(Optional.of(expense));

        Expense foundExpense = expenseService.findExpenseById("123");

        assertNotNull(foundExpense);
        assertEquals("123", foundExpense.getId());
        verify(expenseRepository, times(1)).findExpenseById("123");
    }


    @Test
    public void testUpdateExpense() {
        Expense existingExpense = new Expense();
        existingExpense.setId("123");
        existingExpense.setDescription("Descripción antigua");
        existingExpense.setAmount(BigDecimal.valueOf(50.0));

        when(expenseRepository.findExpenseById("123")).thenReturn(Optional.of(existingExpense));
        Expense updatedExpense = new Expense();
        updatedExpense.setDescription("Descripción actualizada");
        updatedExpense.setAmount(BigDecimal.valueOf(75.0));

        when(expenseRepository.updateExpense(existingExpense)).thenReturn(existingExpense); // Añadido para evitar NullPointerException

        Expense result = expenseService.updateExpense("123", updatedExpense);

        assertEquals("Descripción actualizada", result.getDescription());
        assertEquals(BigDecimal.valueOf(75.0), result.getAmount());
        verify(expenseRepository, times(1)).updateExpense(existingExpense);
    }

    @Test
    public void testDeleteExpense() {
        String expenseId = "123";
        doNothing().when(expenseRepository).deleteExpense(expenseId);

        expenseService.deleteExpense(expenseId);

        verify(expenseRepository, times(1)).deleteExpense(expenseId);
    }


}
