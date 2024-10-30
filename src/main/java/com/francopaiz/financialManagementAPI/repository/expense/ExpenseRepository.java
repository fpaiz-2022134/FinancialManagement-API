package com.francopaiz.financialManagementAPI.repository.expense;

import com.francopaiz.financialManagementAPI.model.Expense;
import com.francopaiz.financialManagementAPI.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz para el repositorio de gastos.
 * Define las operaciones CRUD que se pueden realizar sobre la entidad Expense.
 */
public interface ExpenseRepository {

    /**
     * Crea un nuevo gasto en el repositorio.
     *
     * @param expense El objeto Expense a crear.
     * @return El gasto creado.
     */
    Expense createExpense(Expense expense);

    /**
     * Obtiene todos los gastos del repositorio.
     *
     * @return Una lista de objetos Expense.
     */
    List<Expense> getExpenses();

    /**
     * Busca un gasto por su ID.
     *
     * @param idExpense El ID del gasto a buscar.
     * @return Un Optional que contiene el gasto si se encuentra, o vacío si no.
     */
    Optional<Expense> findExpenseById(String idExpense);

    /**
     * Actualiza un gasto existente en el repositorio.
     *
     * @param expense El objeto Expense con los datos actualizados.
     * @return El gasto actualizado.
     */
    Expense updateExpense(Expense expense);

    /**
     * Elimina un gasto del repositorio por su ID.
     *
     * @param idExpense El ID del gasto a eliminar.
     */
    void deleteExpense(String idExpense);

    /**
     * Busca todos los gastos asociados a un usuario específico.
     *
     * @param user El usuario cuyos gastos se van a buscar.
     * @return Una lista de objetos Expense que pertenecen al usuario.
     */
    List<Expense> findByUser(User user);

    /**
     * Busca los gastos de un usuario dentro de un rango de fechas específico.
     *
     * @param user El usuario cuyos gastos se van a buscar.
     * @param startDate La fecha de inicio del rango.
     * @param endDate La fecha de fin del rango.
     * @return Una lista de objetos Expense que pertenecen al usuario dentro del rango de fechas.
     */
    List<Expense> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate);
}
