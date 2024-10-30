package com.francopaiz.financialManagementAPI.service.expense;

import com.francopaiz.financialManagementAPI.model.Expense;
import com.francopaiz.financialManagementAPI.model.User;
import com.francopaiz.financialManagementAPI.repository.expense.ExpenseRepository;
import com.francopaiz.financialManagementAPI.repository.usuario.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${spring.profiles.active}")
    private String profile;

    @Override
    public List<Expense> getExpenses() {
        return expenseRepository.getExpenses();
    }

    @Override
    public Expense findExpenseById(String id) {
        // Validar el formato del ID antes de buscar el gasto.
        validateIdFormat(id);
        return expenseRepository.findExpenseById(id).orElse(null);
    }

    @Override
    public Expense createExpense(Expense expense) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String idUser = ((User) userDetails).getId();

        System.out.println("Id del autenticado: " + idUser);
        User authenticatedUser = userRepository.findUserById(idUser)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // Asignar el usuario autenticado al gasto
        expense.setUser(authenticatedUser);
        System.out.println(authenticatedUser);

        if (expense.getDate() == null) {
            expense.setDate(LocalDate.now());
        }

        try {
            return expenseRepository.createExpense(expense);
        } catch (Exception e) {
            e.printStackTrace(); // o usa un logger para registrar el error
            throw new RuntimeException("Error al crear el gasto", e);

        }
    }

    @Override
    public Expense updateExpense(String id, Expense expense) {
        // Validar el formato del ID antes de actualizar el gasto.
        validateIdFormat(id);

        Expense existingExpense = expenseRepository.findExpenseById(id)
                .orElseThrow(() -> new IllegalArgumentException("Gasto no encontrado"));

        if (expense.getDescription() != null) {
            existingExpense.setDescription(expense.getDescription());
        }

        if (expense.getAmount() != null) {
            existingExpense.setAmount(expense.getAmount());
        }

        if (expense.getDate() != null) {
            existingExpense.setDate(expense.getDate());
        }

        if (expense.getUser() != null) {
            existingExpense.setUser(expense.getUser());
        }

        if (expense.getCategory() != null) {
            existingExpense.setCategory(expense.getCategory());
        }

        return expenseRepository.updateExpense(existingExpense);
    }

    @Override
    public void deleteExpense(String id) {
        // Validar el formato del ID antes de eliminar el gasto.
        validateIdFormat(id);
        expenseRepository.deleteExpense(id);
    }

    @Override
    public List<Expense> findByUser(User user) {
        return expenseRepository.findByUser(user);
    }

    @Override
    public List<Expense> findExpensesForAuthenticatedUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String idUser = ((User) userDetails).getId();

        System.out.println(idUser);

        // Buscar el usuario autenticado
        User authenticatedUser = (User) userRepository.findUserById(idUser)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        System.out.println(authenticatedUser);
        // Buscar todos los gastos de este usuario
        System.out.println("hola 1");
        System.out.println(expenseRepository.findByUser(authenticatedUser));
        System.out.println("hola 2");
        try{
            return expenseRepository.findByUser(authenticatedUser);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al encontrar el ingreso", e);
        }
    }

    /**
     * Valida el formato del ID de gasto para PostgreSQL.
     *
     * @param id ID del gasto a validar.
     */
    private void validateIdFormat(String id) {
        if (profile.equals("postgres")) {
            try {
                Long.parseLong(id);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Formato de ID no válido para PostgreSQL: " + id);
            }
        }
    }
}
