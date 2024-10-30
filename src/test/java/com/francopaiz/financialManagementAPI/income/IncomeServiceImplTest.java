package com.francopaiz.financialManagementAPI.income;


import com.francopaiz.financialManagementAPI.model.Income;
import com.francopaiz.financialManagementAPI.model.User;
import com.francopaiz.financialManagementAPI.repository.income.IncomeRepository;
import com.francopaiz.financialManagementAPI.repository.usuario.UserRepository;
import com.francopaiz.financialManagementAPI.service.income.IncomeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
class IncomeServiceImplTest {

    @Mock
    private IncomeRepository incomeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private IncomeServiceImpl incomeService;

    private User user;
    private Income income;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId("1");

        income = new Income();
        income.setUser(user);
        income.setAmount(BigDecimal.valueOf(1000.0));
        income.setDate(LocalDate.now());

        // Configuración de contexto de seguridad
        SecurityContextHolder.setContext(securityContext);

        // Simular el valor de profile
        ReflectionTestUtils.setField(incomeService, "profile", "postgres");
    }

    @Test
    void createIncome_ShouldReturnCreatedIncome() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);
        when(userRepository.findUserById("1")).thenReturn(Optional.of(user));
        when(incomeRepository.createIncome(income)).thenReturn(income);

        Income result = incomeService.createIncome(income);

        assertNotNull(result);
        assertEquals(user, result.getUser());
        verify(incomeRepository, times(1)).createIncome(income);
    }

    @Test
    void findIncomeById_ShouldReturnIncomeWhenExists() {
        String incomeId = "123";
        when(incomeRepository.findIncomeById(incomeId)).thenReturn(Optional.of(income));

        Income result = incomeService.findIncomeById(incomeId);

        assertNotNull(result);
        assertEquals(income, result);
        verify(incomeRepository, times(1)).findIncomeById(incomeId);
    }

    @Test
    void findIncomeById_ShouldReturnNullWhenIncomeDoesNotExist() {
        String incomeId = "456";
        when(incomeRepository.findIncomeById(incomeId)).thenReturn(Optional.empty());

        Income result = incomeService.findIncomeById(incomeId);

        assertNull(result);
        verify(incomeRepository, times(1)).findIncomeById(incomeId);
    }

    @Test
    void updateIncome_ShouldUpdateAndReturnIncome() {
        String incomeId = "123";
        Income updatedIncome = new Income();
        updatedIncome.setAmount(BigDecimal.valueOf(2000.0));

        when(incomeRepository.findIncomeById(incomeId)).thenReturn(Optional.of(income));
        when(incomeRepository.updateIncome(any(Income.class))).thenReturn(income);

        Income result = incomeService.updateIncome(incomeId, updatedIncome);

        assertNotNull(result);
        assertEquals(updatedIncome.getAmount(), result.getAmount());
        verify(incomeRepository, times(1)).updateIncome(any(Income.class));
    }

    @Test
    void deleteIncome_ShouldDeleteIncome() {
        String incomeId = "123";
        doNothing().when(incomeRepository).deleteIncome(incomeId);

        incomeService.deleteIncome(incomeId);

        verify(incomeRepository, times(1)).deleteIncome(incomeId);
    }

    @Test
    void findIncomesForAuthenticatedUser_ShouldReturnListOfIncomes() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);
        when(userRepository.findUserById("1")).thenReturn(Optional.of(user));
        when(incomeRepository.findByUser(user)).thenReturn(List.of(income));

        List<Income> result = incomeService.findIncomesForAuthenticatedUser();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(income, result.get(0));
        verify(incomeRepository, times(2)).findByUser(user);
    }
}
