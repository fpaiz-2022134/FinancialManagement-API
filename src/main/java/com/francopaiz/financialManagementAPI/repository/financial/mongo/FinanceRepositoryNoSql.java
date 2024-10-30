package com.francopaiz.financialManagementAPI.repository.financial.mongo;

import com.francopaiz.financialManagementAPI.model.FinancialSummary;
import com.francopaiz.financialManagementAPI.model.mongo.FinancialMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinanceRepositoryNoSql extends MongoRepository<FinancialMongo, String> {

}
