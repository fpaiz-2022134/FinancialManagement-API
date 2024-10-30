package com.francopaiz.financialManagementAPI.repository.category;

import com.francopaiz.financialManagementAPI.model.Category;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz para el repositorio de categorías.
 * Define las operaciones CRUD que se pueden realizar sobre la entidad Category.
 */
public interface CategoryRepository {

    /**
     * Crea una nueva categoría en el repositorio.
     *
     * @param category El objeto Category a crear.
     * @return La categoría creada.
     */
    Category createCategory(Category category);

    /**
     * Obtiene todas las categorías del repositorio.
     *
     * @return Una lista de objetos Category.
     */
    List<Category> getCategories();

    /**
     * Busca una categoría por su ID.
     *
     * @param idCategory El ID de la categoría a buscar.
     * @return Un Optional que contiene la categoría si se encuentra, o vacío si no.
     */
    Optional<Category> findCategoryById(String idCategory);

    /**
     * Actualiza una categoría existente en el repositorio.
     *
     * @param category El objeto Category con los datos actualizados.
     * @return La categoría actualizada.
     */
    Category updateCategory(Category category);

    /**
     * Elimina una categoría del repositorio por su ID.
     *
     * @param idCategory El ID de la categoría a eliminar.
     */
    void deleteCategory(String idCategory);
}
