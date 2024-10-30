package com.francopaiz.financialManagementAPI.repository.category.mongo;


import com.francopaiz.financialManagementAPI.model.Category;
import com.francopaiz.financialManagementAPI.model.mongo.CategoryMongo;
import com.francopaiz.financialManagementAPI.caster.CategoryCaster;
import com.francopaiz.financialManagementAPI.repository.category.CategoryRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;

import com.francopaiz.financialManagementAPI.repository.category.mongo.CategoryRepositoryNoSql;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementación del repositorio de categorías utilizando MongoDB.
 * Esta clase se encarga de la persistencia de datos en una base de datos NoSQL
 * y se activa solo cuando el perfil "mongo" está en uso.
 */
@Profile("mongo")
/*
@RequiredArgsConstructor
*/
@Repository
public class CategoryRepositoryMongo implements CategoryRepository {

    // Repositorio NoSQL para operaciones de base de datos
    private final CategoryRepositoryNoSql categoryRepositoryNoSql;

    // Utilidad para la conversión entre objetos Category y CategoryMongo
    private final CategoryCaster categoryCaster;

    public CategoryRepositoryMongo(CategoryRepositoryNoSql categoryRepositoryNoSql, CategoryCaster categoryCaster) {
        this.categoryRepositoryNoSql = categoryRepositoryNoSql;
        this.categoryCaster = categoryCaster;
    }

    /**
     * Crea una nueva categoría en la base de datos.
     *
     * @param category Objeto Category que representa la categoría a crear.
     * @return El objeto Category creado.
     */
    public Category createCategory(Category category) {
        // Convierte el objeto Category a CategoryMongo para persistencia
        CategoryMongo categoryMongo = categoryCaster.categoryToCategoryMongo(category);
        // Guarda la categoría en la base de datos y obtiene el nuevo objeto
        CategoryMongo newCategory = categoryRepositoryNoSql.save(categoryMongo);
        // Convierte el objeto CategoryMongo guardado de nuevo a Category y lo retorna
        return categoryCaster.categoryMongoToCategory(newCategory);
    }

    /**
     * Obtiene una lista de todas las categorías en la base de datos.
     *
     * @return Lista de objetos Category.
     */
    public List<Category> getCategories() {
        // Busca todas las categorías en la base de datos y las convierte a objetos Category
        return categoryRepositoryNoSql.findAll().stream()
                .map(categoryCaster::categoryMongoToCategory)
                .collect(Collectors.toList());
    }

    /**
     * Busca una categoría por su ID.
     *
     * @param idCategory ID de la categoría a buscar.
     * @return Un Optional que puede contener el objeto Category si se encuentra, o vacío si no.
     */
    public Optional<Category> findCategoryById(String idCategory) {
        // Busca la categoría en la base de datos y convierte el resultado a Category
        Optional<CategoryMongo> categoryMongo = categoryRepositoryNoSql.findById(idCategory);
        return categoryMongo.map(categoryCaster::categoryMongoToCategory);
    }

    /**
     * Actualiza la información de una categoría existente.
     *
     * @param category Objeto Category que contiene la información actualizada de la categoría.
     * @return El objeto Category actualizado.
     */
    public Category updateCategory(Category category) {
        // Convierte el objeto Category a CategoryMongo para la actualización
        CategoryMongo categoryMongo = categoryCaster.categoryToCategoryMongo(category);
        // Guarda la categoría actualizada en la base de datos y obtiene el nuevo objeto
        CategoryMongo newCategory = categoryRepositoryNoSql.save(categoryMongo);
        // Convierte el objeto CategoryMongo guardado de nuevo a Category y lo retorna
        return categoryCaster.categoryMongoToCategory(newCategory);
    }

    /**
     * Elimina una categoría de la base de datos por su ID.
     *
     * @param idCategory ID de la categoría a eliminar.
     */
    public void deleteCategory(String idCategory) {
        // Elimina la categoría de la base de datos utilizando su ID
        categoryRepositoryNoSql.deleteById(idCategory);
    }
}
