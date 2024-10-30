package com.francopaiz.financialManagementAPI.repository.financial.postgres;

import com.francopaiz.financialManagementAPI.caster.FinancialCaster;
import com.francopaiz.financialManagementAPI.model.FinancialSummary;
import com.francopaiz.financialManagementAPI.model.postgres.FinancialPostgres;
import com.francopaiz.financialManagementAPI.repository.financial.FinanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementación del repositorio de finanzas para PostgreSQL.
 * Esta clase se encarga de la persistencia de datos relacionados
 * con los resúmenes financieros en una base de datos PostgreSQL.
 */
@Profile("postgres") // Activa esta implementación cuando el perfil 'postgres' está activo.
@Repository // Indica que esta clase es un repositorio de acceso a datos.
@RequiredArgsConstructor
public class FinanceRepositoryPostgres implements FinanceRepository {

    private final FinanceRepositoryJpa financeRepositoryJpa; // Repositorio JPA para operaciones CRUD.
    private final FinancialCaster financialCaster; // Utilidad para convertir entre entidades.

    /**
     * Crea un nuevo resumen financiero en la base de datos.
     *
     * @param financialSummary El resumen financiero a crear.
     * @return El resumen financiero creado.
     */
    @Override
    public FinancialSummary createFinance(FinancialSummary financialSummary) {
        FinancialPostgres financialPostgres = financialCaster.financialSummaryToFinancialPostgres(financialSummary); // Convierte a FinancialSummaryPostgres.
        FinancialPostgres newFinancial = financeRepositoryJpa.save(financialPostgres); // Guarda el resumen financiero.
        return financialCaster.financialPostgresToFinancialSummary(newFinancial); // Convierte y retorna el resumen creado.
    }

    /**
     * Recupera todos los resúmenes financieros de la base de datos.
     *
     * @return Una lista de resúmenes financieros.
     */
    @Override
    public List<FinancialSummary> getFinances() {
        return financeRepositoryJpa.findAll().stream() // Obtiene todos los resúmenes financieros.
                .map(financialCaster::financialPostgresToFinancialSummary) // Convierte cada resumen a FinancialSummary.
                .collect(Collectors.toList()); // Recoge los resúmenes en una lista.
    }

    /**
     * Encuentra un resumen financiero por su ID.
     *
     * @param idFinance El ID del resumen financiero a buscar.
     * @return Un Optional que contiene el resumen financiero si se encuentra, o vacío si no.
     */
    @Override
    public Optional<FinancialSummary> findFinanceById(String idFinance) {
        Optional<FinancialPostgres> financialPostgres = financeRepositoryJpa.findById(Long.parseLong(idFinance)); // Busca el resumen por ID.
        return financialPostgres.map(financialCaster::financialPostgresToFinancialSummary); // Convierte el resultado a un Optional de FinancialSummary.
    }

    /**
     * Actualiza un resumen financiero existente en la base de datos.
     *
     * @param financialSummary El resumen financiero a actualizar.
     * @return El resumen financiero actualizado.
     */
    @Override
    public FinancialSummary updateFinance(FinancialSummary financialSummary) {
        FinancialPostgres financialPostgres = financialCaster.financialSummaryToFinancialPostgres(financialSummary); // Convierte a FinancialSummaryPostgres.
        FinancialPostgres updatedFinancial = financeRepositoryJpa.save(financialPostgres); // Guarda el resumen actualizado.
        return financialCaster.financialPostgresToFinancialSummary(updatedFinancial); // Convierte y retorna el resumen actualizado.
    }

    /**
     * Elimina un resumen financiero de la base de datos por su ID.
     *
     * @param idFinance El ID del resumen financiero a eliminar.
     */
    @Override
    public void deleteFinance(String idFinance) {
        financeRepositoryJpa.deleteById(Long.parseLong(idFinance)); // Elimina el resumen por ID.
    }
}
