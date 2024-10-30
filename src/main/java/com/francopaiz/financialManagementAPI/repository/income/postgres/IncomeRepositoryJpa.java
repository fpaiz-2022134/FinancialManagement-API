package com.francopaiz.financialManagementAPI.repository.income.postgres;

import com.francopaiz.financialManagementAPI.model.Income;
import com.francopaiz.financialManagementAPI.model.User;
import com.francopaiz.financialManagementAPI.model.postgres.ExpensePostgres;
import com.francopaiz.financialManagementAPI.model.postgres.IncomePostgres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Interfaz para el repositorio de ingresos utilizando JPA.
 * Define las operaciones CRUD que se pueden realizar sobre la entidad Income.
 */
@Repository
public interface IncomeRepositoryJpa extends JpaRepository<IncomePostgres, Long> {

    /**
     * Busca todos los ingresos asociados a un usuario específico.
     *
     * @param userId El usuario cuyos ingresos se van a buscar.
     * @return Una lista de objetos Income que pertenecen al usuario.
     */
    List<IncomePostgres> findByUser_Id(Long userId);

    /**
     * Busca los ingresos de un usuario dentro de un rango de fechas específico.
     *
     * @param userId El usuario cuyos ingresos se van a buscar.
     * @param startDate La fecha de inicio del rango.
     * @param endDate La fecha de fin del rango.
     * @return Una lista de objetos Income que pertenecen al usuario dentro del rango de fechas.
     */
    List<IncomePostgres> findByUser_IdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate);
}
