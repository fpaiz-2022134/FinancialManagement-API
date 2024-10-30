package com.francopaiz.financialManagementAPI.repository.financial.mongo;

import com.francopaiz.financialManagementAPI.model.FinancialSummary; // Asegúrate de tener la clase FinancialSummary definida
import com.francopaiz.financialManagementAPI.model.mongo.FinancialMongo; // Asegúrate de tener la clase FinancialMongo definida
import com.francopaiz.financialManagementAPI.caster.FinancialCaster; // Asegúrate de tener la clase FinancialCaster definida
import com.francopaiz.financialManagementAPI.repository.financial.FinanceRepository; // Asegúrate de tener la interfaz FinanceRepository definida
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementación del repositorio de finanzas utilizando MongoDB.
 * Esta clase se encarga de la persistencia de datos en una base de datos NoSQL
 * y se activa solo cuando el perfil "mongo" está en uso.
 */
@Profile("mongo")
@Repository
public class FinanceRepositoryMongo implements FinanceRepository {

    // Repositorio NoSQL para operaciones de base de datos
    private final FinanceRepositoryNoSql financeRepositoryNoSql;

    // Utilidad para la conversión entre objetos FinancialSummary y FinancialMongo
    private final FinancialCaster financeCaster;

    public FinanceRepositoryMongo(FinanceRepositoryNoSql financeRepositoryNoSql, FinancialCaster financeCaster) {
        this.financeRepositoryNoSql = financeRepositoryNoSql;
        this.financeCaster = financeCaster;
    }

    /**
     * Crea un nuevo objeto FinancialSummary en la base de datos.
     *
     * @param financialSummary Objeto FinancialSummary que representa el elemento a crear.
     * @return El objeto FinancialSummary creado.
     */
    @Override
    public FinancialSummary createFinance(FinancialSummary financialSummary) {
        // Convierte el objeto FinancialSummary a FinancialMongo para persistencia
        FinancialMongo financialMongo = financeCaster.financialSummaryToFinancialMongo(financialSummary);
        // Guarda el objeto en la base de datos y obtiene el nuevo objeto
        FinancialMongo newFinance = financeRepositoryNoSql.save(financialMongo);
        // Convierte el objeto FinancialMongo guardado de nuevo a FinancialSummary y lo retorna
        return financeCaster.financialMongoToFinancialSummary(newFinance);
    }

    /**
     * Obtiene una lista de todas las finanzas en la base de datos.
     *
     * @return Lista de objetos FinancialSummary.
     */
    @Override
    public List<FinancialSummary> getFinances() {
        // Busca todas las finanzas en la base de datos y las convierte a objetos FinancialSummary
        return financeRepositoryNoSql.findAll().stream()
                .map(financeCaster::financialMongoToFinancialSummary)
                .collect(Collectors.toList());
    }

    /**
     * Busca una finanza por su ID.
     *
     * @param idFinance ID de la finanza a buscar.
     * @return Un Optional que puede contener el objeto FinancialSummary si se encuentra, o vacío si no.
     */
    @Override
    public Optional<FinancialSummary> findFinanceById(String idFinance) {
        // Busca la finanza en la base de datos y convierte el resultado a FinancialSummary
        Optional<FinancialMongo> financialMongo = financeRepositoryNoSql.findById(idFinance);
        return financialMongo.map(financeCaster::financialMongoToFinancialSummary);
    }

    /**
     * Actualiza la información de una finanza existente.
     *
     * @param financialSummary Objeto FinancialSummary que contiene la información actualizada de la finanza.
     * @return El objeto FinancialSummary actualizado.
     */
    @Override
    public FinancialSummary updateFinance(FinancialSummary financialSummary) {
        // Convierte el objeto FinancialSummary a FinancialMongo para la actualización
        FinancialMongo financialMongo = financeCaster.financialSummaryToFinancialMongo(financialSummary);
        // Guarda la finanza actualizada en la base de datos y obtiene el nuevo objeto
        FinancialMongo newFinance = financeRepositoryNoSql.save(financialMongo);
        // Convierte el objeto FinancialMongo guardado de nuevo a FinancialSummary y lo retorna
        return financeCaster.financialMongoToFinancialSummary(newFinance);
    }

    /**
     * Elimina una finanza de la base de datos por su ID.
     *
     * @param idFinance ID de la finanza a eliminar.
     */
    @Override
    public void deleteFinance(String idFinance) {
        // Elimina la finanza de la base de datos utilizando su ID
        financeRepositoryNoSql.deleteById(idFinance);
    }
}
