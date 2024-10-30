package com.francopaiz.financialManagementAPI.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
public class Expense {

    private String id;
    private String description;
    private BigDecimal amount;
    private LocalDate date;
    private Category category;

    private User user;




}
