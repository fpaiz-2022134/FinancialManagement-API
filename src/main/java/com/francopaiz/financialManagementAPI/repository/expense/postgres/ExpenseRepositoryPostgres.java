package com.francopaiz.financialManagementAPI.repository.expense.postgres;

import com.francopaiz.financialManagementAPI.model.Expense;
import com.francopaiz.financialManagementAPI.model.User;
import com.francopaiz.financialManagementAPI.model.postgres.ExpensePostgres;
import com.francopaiz.financialManagementAPI.repository.expense.ExpenseRepository;
import com.francopaiz.financialManagementAPI.caster.ExpenseCaster;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementación del repositorio de gastos para PostgreSQL.
 * Esta clase se encarga de la persistencia de datos relacionados
 * con los gastos en una base de datos PostgreSQL.
 */
@Profile("postgres") // Activa esta implementación cuando el perfil 'postgres' está activo.
@Repository // Indica que esta clase es un repositorio de acceso a datos.
@RequiredArgsConstructor
public class ExpenseRepositoryPostgres implements ExpenseRepository {

    private final ExpenseRepositoryJpa expenseRepositoryJpa; // Repositorio JPA para operaciones CRUD.
    private final ExpenseCaster expenseCaster; // Utilidad para convertir entre entidades.

   /* public ExpenseRepositoryPostgres(ExpenseRepositoryJpa expenseRepositoryJpa, ExpenseCaster expenseCaster) {
        this.expenseRepositoryJpa = expenseRepositoryJpa;
        this.expenseCaster = expenseCaster;
    }*/

    /**
     * Crea un nuevo gasto en la base de datos.
     *
     * @param expense El gasto a crear.
     * @return El gasto creado.
     */
    @Override
    public Expense createExpense(Expense expense) {
        ExpensePostgres expensePostgres = expenseCaster.expenseToExpensePostgres(expense); // Convierte a ExpensePostgres.
        ExpensePostgres newExpense = expenseRepositoryJpa.save(expensePostgres); // Guarda el gasto en la base de datos.
        return expenseCaster.expensePostgresToExpense(newExpense); // Convierte y retorna el gasto creado.
    }

    /**
     * Recupera todos los gastos de la base de datos.
     *
     * @return Una lista de gastos.
     */
    @Override
    public List<Expense> getExpenses() {
        return expenseRepositoryJpa.findAll().stream() // Obtiene todos los gastos de la base de datos.
                .map(expenseCaster::expensePostgresToExpense) // Convierte cada gasto a Expense.
                .collect(Collectors.toList()); // Recoge los gastos en una lista.
    }

    /**
     * Encuentra un gasto por su ID.
     *
     * @param idExpense El ID del gasto a buscar.
     * @return Un Optional que contiene el gasto si se encuentra, o vacío si no.
     */
    @Override
    public Optional<Expense> findExpenseById(String idExpense) {
        Optional<ExpensePostgres> expensePostgres = expenseRepositoryJpa.findById(Long.parseLong(idExpense)); // Busca el gasto por ID.
        return expensePostgres.map(expenseCaster::expensePostgresToExpense); // Convierte el resultado a un Optional de Expense.
    }

    /**
     * Actualiza un gasto existente en la base de datos.
     *
     * @param expense El gasto a actualizar.
     * @return El gasto actualizado.
     */
    @Override
    public Expense updateExpense(Expense expense) {
        ExpensePostgres expensePostgres = expenseCaster.expenseToExpensePostgres(expense); // Convierte a ExpensePostgres.
        ExpensePostgres newExpense = expenseRepositoryJpa.save(expensePostgres); // Guarda el gasto actualizado.
        return expenseCaster.expensePostgresToExpense(newExpense); // Convierte y retorna el gasto actualizado.
    }

    /**
     * Elimina un gasto de la base de datos por su ID.
     *
     * @param idExpense El ID del gasto a eliminar.
     */
    @Override
    public void deleteExpense(String idExpense) {
        expenseRepositoryJpa.deleteById(Long.parseLong(idExpense)); // Elimina el gasto por ID.
    }

    /**
     * Encuentra los gastos asociados a un usuario específico.
     *
     * @param user El usuario cuyos gastos se van a buscar.
     * @return Una lista de objetos Expense que pertenecen al usuario.
     */
    @Override
    public List<Expense> findByUser(User user) {
        return expenseRepositoryJpa.findByUser_Id(Long.valueOf(user.getId())).stream() // Obtiene los gastos del usuario.
                .map(expenseCaster::expensePostgresToExpense) // Convierte cada gasto a Expense.
                .collect(Collectors.toList()); // Recoge los gastos en una lista.
    }

    /**
     * Busca los gastos de un usuario dentro de un rango de fechas específico.
     *
     * @param user El usuario cuyos gastos se van a buscar.
     * @param startDate La fecha de inicio del rango.
     * @param endDate La fecha de fin del rango.
     * @return Una lista de objetos Expense que pertenecen al usuario dentro del rango de fechas.
     */
    @Override
    public List<Expense> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate) {
        return expenseRepositoryJpa.findByUser_IdAndDateBetween(Long.valueOf(user.getId()), startDate, endDate).stream() // Obtiene los gastos del usuario en el rango de fechas.
                .map(expenseCaster::expensePostgresToExpense) // Convierte cada gasto a Expense.
                .collect(Collectors.toList()); // Recoge los gastos en una lista.
    }
}
