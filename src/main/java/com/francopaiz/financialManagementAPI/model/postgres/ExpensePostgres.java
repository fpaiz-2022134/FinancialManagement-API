package com.francopaiz.financialManagementAPI.model.postgres;

import com.francopaiz.financialManagementAPI.model.Category;
import com.francopaiz.financialManagementAPI.model.User;
import com.francopaiz.financialManagementAPI.repository.category.CategoryRepository;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@Entity
@Table(name = "expense")
public class ExpensePostgres{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="expense_id")
    private Long id;


    private String description;
    private BigDecimal amount;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryPostgres category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserPostgres user;




}