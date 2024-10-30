package com.francopaiz.financialManagementAPI.repository.expense.postgres;


import com.francopaiz.financialManagementAPI.model.Expense;
import com.francopaiz.financialManagementAPI.model.User;
import com.francopaiz.financialManagementAPI.model.postgres.ExpensePostgres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Interfaz para el repositorio de gastos utilizando JPA.
 * Define las operaciones CRUD que se pueden realizar sobre la entidad Expense.
 */
@Repository
public interface ExpenseRepositoryJpa extends JpaRepository<ExpensePostgres, Long> {

    /**
     * Busca todos los gastos asociados a un usuario específico.
     *
     * @param userId El usuario cuyos gastos se van a buscar.
     * @return Una lista de objetos Expense que pertenecen al usuario.
     */
    List<ExpensePostgres> findByUser_Id(Long userId);

    /**
     * Busca los gastos de un usuario dentro de un rango de fechas específico.
     *
     * @param userId El usuario cuyos gastos se van a buscar.
     * @param startDate La fecha de inicio del rango.
     * @param endDate La fecha de fin del rango.
     * @return Una lista de objetos Expense que pertenecen al usuario dentro del rango de fechas.
     */
    List<ExpensePostgres> findByUser_IdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate);
}
