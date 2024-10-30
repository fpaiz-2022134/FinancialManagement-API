package com.francopaiz.financialManagementAPI.model.postgres;

import com.francopaiz.financialManagementAPI.model.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "incomes")
public class IncomePostgres {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_income")
    private Long id;


    @Column(name = "source", nullable = false)
    private String source;


    @Column(name = "amount", nullable = false)
    private BigDecimal amount;


    @Column(name = "date", nullable = false)
    private LocalDate date;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserPostgres user;




}