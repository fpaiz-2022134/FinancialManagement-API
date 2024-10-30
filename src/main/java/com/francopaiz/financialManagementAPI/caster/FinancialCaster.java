package com.francopaiz.financialManagementAPI.caster;

import com.francopaiz.financialManagementAPI.model.FinancialSummary;
import com.francopaiz.financialManagementAPI.model.mongo.FinancialMongo;
import com.francopaiz.financialManagementAPI.model.postgres.FinancialPostgres;
import org.springframework.stereotype.Component;

/**
 * Clase utilitaria para realizar conversiones entre diferentes representaciones
 * de un resumen financiero, específicamente entre FinancialSummary, FinancialMongo y FinancialPostgres.
 */
@Component
public class FinancialCaster {

    /**
     * Convierte un objeto FinancialSummary a un objeto FinancialMongo.
     *
     * @param financialSummary El objeto FinancialSummary que se va a convertir.
     * @return Un objeto FinancialMongo que representa el resumen financiero.
     */
    public FinancialMongo financialSummaryToFinancialMongo(FinancialSummary financialSummary) {
        FinancialMongo financialMongo = new FinancialMongo();
        financialMongo.setTotalExpenses(financialSummary.getTotalExpenses());
        financialMongo.setTotalIncome(financialSummary.getTotalIncome());
        financialMongo.setBalance(financialSummary.getBalance());
        financialMongo.setExpensesByCategory(financialSummary.getExpensesByCategory());
        return financialMongo;
    }

    /**
     * Convierte un objeto FinancialMongo a un objeto FinancialSummary.
     *
     * @param financialMongo El objeto FinancialMongo que se va a convertir.
     * @return Un objeto FinancialSummary que representa el resumen financiero.
     */
    public FinancialSummary financialMongoToFinancialSummary(FinancialMongo financialMongo) {
        FinancialSummary financialSummary = new FinancialSummary();
        financialSummary.setTotalExpenses(financialMongo.getTotalExpenses());
        financialSummary.setTotalIncome(financialMongo.getTotalIncome());
        financialSummary.setBalance(financialMongo.getBalance());
        financialSummary.setExpensesByCategory(financialMongo.getExpensesByCategory());
        return financialSummary;
    }

    /**
     * Convierte un objeto FinancialSummary a un objeto FinancialPostgres.
     *
     * @param financialSummary El objeto FinancialSummary que se va a convertir.
     * @return Un objeto FinancialPostgres que representa el resumen financiero.
     */
    public FinancialPostgres financialSummaryToFinancialPostgres(FinancialSummary financialSummary) {
        FinancialPostgres financialPostgres = new FinancialPostgres();
        financialPostgres.setTotalExpenses(financialSummary.getTotalExpenses());
        financialPostgres.setTotalIncome(financialSummary.getTotalIncome());
        financialPostgres.setBalance(financialSummary.getBalance());
        financialPostgres.setExpensesByCategory(financialSummary.getExpensesByCategory());
        return financialPostgres;
    }

    /**
     * Convierte un objeto FinancialPostgres a un objeto FinancialSummary.
     *
     * @param financialPostgres El objeto FinancialPostgres que se va a convertir.
     * @return Un objeto FinancialSummary que representa el resumen financiero.
     */
    public FinancialSummary financialPostgresToFinancialSummary(FinancialPostgres financialPostgres) {
        FinancialSummary financialSummary = new FinancialSummary();
        financialSummary.setTotalExpenses(financialPostgres.getTotalExpenses());
        financialSummary.setTotalIncome(financialPostgres.getTotalIncome());
        financialSummary.setBalance(financialPostgres.getBalance());
        financialSummary.setExpensesByCategory(financialPostgres.getExpensesByCategory());
        return financialSummary;
    }

    /**
     * Convierte un objeto FinancialMongo a un objeto FinancialPostgres.
     *
     * @param financialMongo El objeto FinancialMongo que se va a convertir.
     * @return Un objeto FinancialPostgres que representa el resumen financiero.
     */
    public FinancialPostgres financialMongoToFinancialPostgres(FinancialMongo financialMongo) {
        FinancialPostgres financialPostgres = new FinancialPostgres();
        financialPostgres.setTotalExpenses(financialMongo.getTotalExpenses());
        financialPostgres.setTotalIncome(financialMongo.getTotalIncome());
        financialPostgres.setBalance(financialMongo.getBalance());
        financialPostgres.setExpensesByCategory(financialMongo.getExpensesByCategory());
        return financialPostgres;
    }

    /**
     * Convierte un objeto FinancialPostgres a un objeto FinancialMongo.
     *
     * @param financialPostgres El objeto FinancialPostgres que se va a convertir.
     * @return Un objeto FinancialMongo que representa el resumen financiero.
     */
    public FinancialMongo financialPostgresToFinancialMongo(FinancialPostgres financialPostgres) {
        FinancialMongo financialMongo = new FinancialMongo();
        financialMongo.setTotalExpenses(financialPostgres.getTotalExpenses());
        financialMongo.setTotalIncome(financialPostgres.getTotalIncome());
        financialMongo.setBalance(financialPostgres.getBalance());
        financialMongo.setExpensesByCategory(financialPostgres.getExpensesByCategory());
        return financialMongo;
    }
}
