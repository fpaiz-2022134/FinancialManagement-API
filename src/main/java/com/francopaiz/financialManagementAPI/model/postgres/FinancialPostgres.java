package com.francopaiz.financialManagementAPI.model.postgres;

import com.francopaiz.financialManagementAPI.model.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Entity
@Table(name = "summary")
public class FinancialPostgres {

    @Id // Añade esta línea para declarar el ID
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática del ID
    private Long id; // Puedes usar Long o el tipo que prefieras

    @Getter
    @Setter
    @Column(name = "total_expenses", nullable = false)
    private BigDecimal totalExpenses;

    @Setter
    @Getter
    @Column(name = "total_income", nullable = false)
    private BigDecimal totalIncome;

    @Setter
    @Getter
    @Column(name = "balance", nullable = false)
    private BigDecimal balance; // Incomes - expenses

    // Map cannot be directly persisted, so we'll use @ElementCollection for a map of basic or embeddable types
    @Setter
    @Getter
    @ElementCollection
    @CollectionTable(name = "expenses_by_category", joinColumns = @JoinColumn(name = "summary_id"))
    @MapKeyJoinColumn(name = "category_id") // Assuming Category is an entity
    @Column(name = "expense_amount")
    private Map<Category, BigDecimal> expensesByCategory;



}
