package com.francopaiz.financialManagementAPI.model.mongo;

import com.francopaiz.financialManagementAPI.model.User;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Document(collection = "incomes")
public class IncomeMongo{
    @Id
    private String id;
    private String source;
    private BigDecimal amount;
    private LocalDate date;
    private UserMongo user;


}


