package com.francopaiz.financialManagementAPI.caster;


import com.francopaiz.financialManagementAPI.model.Category;
import com.francopaiz.financialManagementAPI.model.mongo.CategoryMongo;
import com.francopaiz.financialManagementAPI.model.postgres.CategoryPostgres;
import org.springframework.stereotype.Component;

/**
 * Clase utilitaria para realizar conversiones entre diferentes representaciones
 * de una categoría, específicamente entre Category, CategoryPostgres y CategoryMongo.
 */
@Component
public class CategoryCaster {

    /**
     * Convierte un objeto Category a un objeto CategoryPostgres.
     *
     * @param category El objeto Category que se va a convertir.
     * @return Un objeto CategoryPostgres que representa la categoría.
     */
    public CategoryPostgres categoryToCategoryPostgres(Category category) {
        CategoryPostgres categoryPostgres = new CategoryPostgres();
        categoryPostgres.setId((category.getId() != null && !category.getId().isEmpty())
                ? Long.parseLong(category.getId()) : null);
        categoryPostgres.setName(category.getName());
        return categoryPostgres;
    }

    /**
     * Convierte un objeto CategoryPostgres a un objeto Category.
     *
     * @param categoryPostgres El objeto CategoryPostgres que se va a convertir.
     * @return Un objeto Category que representa la categoría.
     */
    public Category categoryPostgresToCategory(CategoryPostgres categoryPostgres) {
        Category category = new Category();
        category.setId(String.valueOf(categoryPostgres.getId()));
        category.setName(categoryPostgres.getName());
        return category;
    }

    /**
     * Convierte un objeto Category a un objeto CategoryMongo.
     *
     * @param category El objeto Category que se va a convertir.
     * @return Un objeto CategoryMongo que representa la categoría.
     */
    public CategoryMongo categoryToCategoryMongo(Category category) {
        CategoryMongo categoryMongo = new CategoryMongo();
        categoryMongo.setId(category.getId());
        categoryMongo.setName(category.getName());
        return categoryMongo;
    }

    /**
     * Convierte un objeto CategoryMongo a un objeto Category.
     *
     * @param categoryMongo El objeto CategoryMongo que se va a convertir.
     * @return Un objeto Category que representa la categoría.
     */
    public Category categoryMongoToCategory(CategoryMongo categoryMongo) {
        Category category = new Category();
        category.setId(categoryMongo.getId());
        category.setName(categoryMongo.getName());
        return category;
    }
}

