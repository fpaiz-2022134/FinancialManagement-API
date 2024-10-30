package com.francopaiz.financialManagementAPI.repository.expense.mongo;

import com.francopaiz.financialManagementAPI.caster.UserCaster;
import com.francopaiz.financialManagementAPI.model.Expense;
import com.francopaiz.financialManagementAPI.model.User;
import com.francopaiz.financialManagementAPI.model.mongo.ExpenseMongo;
import com.francopaiz.financialManagementAPI.model.mongo.UserMongo;
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
 * Implementación del repositorio de gastos para MongoDB.
 * Esta clase se encarga de la persistencia de datos relacionados
 * con los gastos en una base de datos MongoDB.
 */
@Profile("mongo") // Activa esta implementación cuando el perfil 'mongo' está activo.
@Repository // Indica que esta clase es un repositorio de acceso a datos.
public class ExpenseRepositoryMongo implements ExpenseRepository {

    private final ExpenseRepositoryNoSql expenseRepositoryNoSql; // Repositorio NoSQL para operaciones CRUD.
    private final ExpenseCaster expenseCaster; // Utilidad para convertir entre entidades.
    private final UserCaster userCaster;
    public ExpenseRepositoryMongo(ExpenseRepositoryNoSql expenseRepositoryNoSql, ExpenseCaster expenseCaster, UserCaster userCaster) {
        this.expenseRepositoryNoSql = expenseRepositoryNoSql;
        this.expenseCaster = expenseCaster;
        this.userCaster = userCaster;
    }

    /**
     * Crea un nuevo gasto en la base de datos.
     *
     * @param expense El gasto a crear.
     * @return El gasto creado.
     */
    @Override
    public Expense createExpense(Expense expense) {
        ExpenseMongo expenseMongo = expenseCaster.expenseToExpenseMongo(expense); // Convierte a ExpenseMongo.
        ExpenseMongo newExpense = expenseRepositoryNoSql.save(expenseMongo); // Guarda el gasto en la base de datos.
        return expenseCaster.expenseMongoToExpense(newExpense); // Convierte y retorna el gasto creado.
    }

    /**
     * Recupera todos los gastos de la base de datos.
     *
     * @return Una lista de gastos.
     */
    @Override
    public List<Expense> getExpenses() {
        return expenseRepositoryNoSql.findAll().stream() // Obtiene todos los gastos de la base de datos.
                .map(expenseCaster::expenseMongoToExpense) // Convierte cada gasto a Expense.
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
        Optional<ExpenseMongo> expenseMongo = expenseRepositoryNoSql.findById(idExpense); // Busca el gasto por ID.
        return expenseMongo.map(expenseCaster::expenseMongoToExpense); // Convierte el resultado a un Optional de Expense.
    }

    /**
     * Actualiza un gasto existente en la base de datos.
     *
     * @param expense El gasto a actualizar.
     * @return El gasto actualizado.
     */
    @Override
    public Expense updateExpense(Expense expense) {
        ExpenseMongo expenseMongo = expenseCaster.expenseToExpenseMongo(expense); // Convierte a ExpenseMongo.
        ExpenseMongo newExpense = expenseRepositoryNoSql.save(expenseMongo); // Guarda el gasto actualizado.
        return expenseCaster.expenseMongoToExpense(newExpense); // Convierte y retorna el gasto actualizado.
    }

    /**
     * Elimina un gasto de la base de datos por su ID.
     *
     * @param idExpense El ID del gasto a eliminar.
     */
    @Override
    public void deleteExpense(String idExpense) {
        expenseRepositoryNoSql.deleteById(idExpense); // Elimina el gasto por ID.
    }

    @Override
    public List<Expense> findByUser(User user) {
        UserMongo userMongo = userCaster.userToUserMongo(user);
        return expenseRepositoryNoSql.findByUser(userMongo).stream()
                .map(expenseCaster::expenseMongoToExpense)
                .collect(Collectors.toList());
    }

    @Override
    public List<Expense> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate) {
        System.out.println("User que cae en expense: " + user);
        UserMongo userMongo = userCaster.userToUserMongo(user);

        System.out.println(expenseRepositoryNoSql.findByUserAndDateBetween(userMongo, startDate, endDate));
        return expenseRepositoryNoSql.findByUserAndDateBetween(userMongo, startDate, endDate).stream() // Obtiene los gastos del usuario en el rango de fechas.
                .map(expenseCaster::expenseMongoToExpense) // Convierte cada gasto a Expense.
                .collect(Collectors.toList()); // Recoge los gastos en una lista.

    }
}
