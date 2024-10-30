package com.francopaiz.financialManagementAPI.repository.category.postgres;

import com.francopaiz.financialManagementAPI.model.Category;
import com.francopaiz.financialManagementAPI.model.postgres.CategoryPostgres;
import com.francopaiz.financialManagementAPI.repository.category.CategoryRepository;
import com.francopaiz.financialManagementAPI.caster.CategoryCaster;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementación del repositorio de categorías para PostgreSQL.
 * Esta clase se encarga de la persistencia de datos relacionados
 * con las categorías en una base de datos PostgreSQL.
 */
@Profile("postgres") // Activa esta implementación cuando el perfil 'postgres' está activo.
@Repository // Indica que esta clase es un repositorio de acceso a datos.
@RequiredArgsConstructor
public class CategoryRepositoryPostgres implements CategoryRepository {

    private final CategoryRepositoryJpa categoryRepositoryJpa; // Repositorio JPA para operaciones CRUD.
    private final CategoryCaster categoryCaster; // Utilidad para convertir entre entidades.

    /*public CategoryRepositoryPostgres(CategoryRepositoryJpa categoryRepositoryJpa, CategoryCaster categoryCaster) {
        this.categoryRepositoryJpa = categoryRepositoryJpa;
        this.categoryCaster = categoryCaster;
    }*/

    /**
     * Crea una nueva categoría en la base de datos.
     *
     * @param category La categoría a crear.
     * @return La categoría creada.
     */
    @Override
    public Category createCategory(Category category) {
        CategoryPostgres categoryPostgres = categoryCaster.categoryToCategoryPostgres(category); // Convierte a CategoryPostgres.
        CategoryPostgres newCategory = categoryRepositoryJpa.save(categoryPostgres); // Guarda la categoría en la base de datos.
        return categoryCaster.categoryPostgresToCategory(newCategory); // Convierte y retorna la categoría creada.
    }

    /**
     * Recupera todas las categorías de la base de datos.
     *
     * @return Una lista de categorías.
     */
    @Override
    public List<Category> getCategories() {
        return categoryRepositoryJpa.findAll().stream() // Obtiene todas las categorías de la base de datos.
                .map(categoryCaster::categoryPostgresToCategory) // Convierte cada categoría a Category.
                .collect(Collectors.toList()); // Recoge las categorías en una lista.
    }

    /**
     * Encuentra una categoría por su ID.
     *
     * @param idCategory El ID de la categoría a buscar.
     * @return Un Optional que contiene la categoría si se encuentra, o vacío si no.
     */
    @Override
    public Optional<Category> findCategoryById(String idCategory) {
        Optional<CategoryPostgres> categoryPostgres = categoryRepositoryJpa.findById(Long.parseLong(idCategory)); // Busca la categoría por ID.
        return categoryPostgres.map(categoryCaster::categoryPostgresToCategory); // Convierte el resultado a un Optional de Category.
    }

    /**
     * Actualiza una categoría existente en la base de datos.
     *
     * @param category La categoría a actualizar.
     * @return La categoría actualizada.
     */
    @Override
    public Category updateCategory(Category category) {
        CategoryPostgres categoryPostgres = categoryCaster.categoryToCategoryPostgres(category); // Convierte a CategoryPostgres.
        CategoryPostgres newCategory = categoryRepositoryJpa.save(categoryPostgres); // Guarda la categoría actualizada.
        return categoryCaster.categoryPostgresToCategory(newCategory); // Convierte y retorna la categoría actualizada.
    }

    /**
     * Elimina una categoría de la base de datos por su ID.
     *
     * @param idCategory El ID de la categoría a eliminar.
     */
    @Override
    public void deleteCategory(String idCategory) {
        categoryRepositoryJpa.deleteById(Long.parseLong(idCategory)); // Elimina la categoría por ID.
    }
}
