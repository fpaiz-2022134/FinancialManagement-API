package com.francopaiz.financialManagementAPI.repository.income;

import com.francopaiz.financialManagementAPI.model.Income;
import com.francopaiz.financialManagementAPI.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz para el repositorio de ingresos.
 * Define las operaciones CRUD que se pueden realizar sobre la entidad Income.
 */
public interface IncomeRepository {



    /**
     * Crea un nuevo ingreso en el repositorio.
     *
     * @param income El objeto Income a crear.
     * @return El ingreso creado.
     */
    Income createIncome(Income income);

    /**
     * Obtiene todos los ingresos del repositorio.
     *
     * @return Una lista de objetos Income.
     */
    List<Income> getIncomes();

    /**
     * Busca un ingreso por su ID.
     *
     * @param idIncome El ID del ingreso a buscar.
     * @return Un Optional que contiene el ingreso si se encuentra, o vacío si no.
     */
    Optional<Income> findIncomeById(String idIncome);

    /**
     * Actualiza un ingreso existente en el repositorio.
     *
     * @param income El objeto Income con los datos actualizados.
     * @return El ingreso actualizado.
     */
    Income updateIncome(Income income);

    /**
     * Elimina un ingreso del repositorio por su ID.
     *
     * @param idIncome El ID del ingreso a eliminar.
     */
    void deleteIncome(String idIncome);

    /**
     * Busca todos los ingresos asociados a un usuario específico.
     *
     * @param user El usuario cuyos ingresos se van a buscar.
     * @return Una lista de objetos Income que pertenecen al usuario.
     */
    List<Income> findByUser(User user);

    /**
     * Busca los ingresos de un usuario dentro de un rango de fechas específico.
     *
     * @param user El usuario cuyos ingresos se van a buscar.
     * @param startDate La fecha de inicio del rango.
     * @param endDate La fecha de fin del rango.
     * @return Una lista de objetos Income que pertenecen al usuario dentro del rango de fechas.
     */
    List<Income> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate);
}
