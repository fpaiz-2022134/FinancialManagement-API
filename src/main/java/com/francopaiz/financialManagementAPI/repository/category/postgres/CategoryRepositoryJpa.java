package com.francopaiz.financialManagementAPI.repository.category.postgres;

import com.francopaiz.financialManagementAPI.model.postgres.CategoryPostgres;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepositoryJpa extends JpaRepository<CategoryPostgres, Long> {
}
