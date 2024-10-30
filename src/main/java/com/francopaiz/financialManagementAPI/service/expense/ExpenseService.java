package com.francopaiz.financialManagementAPI.service.expense;

import com.francopaiz.financialManagementAPI.model.Expense;
import com.francopaiz.financialManagementAPI.model.User;

import java.util.List;

public interface ExpenseService {
    /**
     * Obtiene todos los gastos registrados.
     * @return Lista de todos los gastos.
     */
    List<Expense> getExpenses();

    /**
     * Busca un gasto por su ID.
     * @param id El ID del gasto.
     * @return El gasto encontrado o null si no se encuentra.
     */
    Expense findExpenseById(String id);

    /**
     * Crea un nuevo gasto.
     * @param expense El objeto Expense a crear.
     * @return El gasto creado.
     */
    Expense createExpense(Expense expense);

    /**
     * Actualiza un gasto existente.
     * @param id El ID del gasto a actualizar.
     * @param expense Los datos actualizados del gasto.
     * @return El gasto actualizado.
     */
    Expense updateExpense(String id, Expense expense);

    /**
     * Elimina un gasto por su ID.
     * @param id El ID del gasto a eliminar.
     */
    void deleteExpense(String id);

    /**
     * Busca los gastos asociados a un usuario específico.
     * @param user El usuario asociado a los gastos.
     * @return Lista de gastos del usuario.
     */
    List<Expense> findByUser(User user);

    /**
     * Busca los gastos del usuario autenticado.
     * @return Lista de gastos del usuario autenticado.
     */
    List<Expense> findExpensesForAuthenticatedUser();
}