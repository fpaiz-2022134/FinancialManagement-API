package com.francopaiz.financialManagementAPI.model.mongo;

import com.francopaiz.financialManagementAPI.model.Category;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Document(collection = "summary")
public class FinancialMongo {

    @Id
    private String id;
    private BigDecimal totalExpenses;
    private BigDecimal totalIncome;
    private BigDecimal balance; //Incomes - expenses
    private Map<Category, BigDecimal> expensesByCategory;



}


