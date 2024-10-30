package com.francopaiz.financialManagementAPI.repository.financial;

import com.francopaiz.financialManagementAPI.model.FinancialSummary;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz para el repositorio de finanzas.
 * Define las operaciones CRUD que se pueden realizar sobre la entidad FinancialSummary.
 */
public interface FinanceRepository {

    /**
     * Crea un nuevo objeto FinancialSummary en el repositorio.
     *
     * @param financialSummary El objeto FinancialSummary a crear.
     * @return El objeto FinancialSummary creado.
     */
    FinancialSummary createFinance(FinancialSummary financialSummary);

    /**
     * Obtiene todas las finanzas del repositorio.
     *
     * @return Una lista de objetos FinancialSummary.
     */
    List<FinancialSummary> getFinances();

    /**
     * Busca una finanza por su ID.
     *
     * @param idFinance El ID de la finanza a buscar.
     * @return Un Optional que contiene la finanza si se encuentra, o vacío si no.
     */
    Optional<FinancialSummary> findFinanceById(String idFinance);

    /**
     * Actualiza una finanza existente en el repositorio.
     *
     * @param financialSummary El objeto FinancialSummary con los datos actualizados.
     * @return La finanza actualizada.
     */
    FinancialSummary updateFinance(FinancialSummary financialSummary);

    /**
     * Elimina una finanza del repositorio por su ID.
     *
     * @param idFinance El ID de la finanza a eliminar.
     */
    void deleteFinance(String idFinance);
}
