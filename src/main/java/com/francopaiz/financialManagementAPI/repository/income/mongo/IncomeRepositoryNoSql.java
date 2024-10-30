package com.francopaiz.financialManagementAPI.repository.income.mongo;

import com.francopaiz.financialManagementAPI.model.Expense;
import com.francopaiz.financialManagementAPI.model.Income;
import com.francopaiz.financialManagementAPI.model.User;
import com.francopaiz.financialManagementAPI.model.mongo.IncomeMongo;
import com.francopaiz.financialManagementAPI.model.mongo.UserMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IncomeRepositoryNoSql extends MongoRepository <IncomeMongo, String> {
    List<IncomeMongo> findByUser(UserMongo user);

    List<IncomeMongo> findByUserAndDateBetween(UserMongo user, LocalDate startDate, LocalDate endDate);
}
