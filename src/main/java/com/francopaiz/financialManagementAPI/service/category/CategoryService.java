package com.francopaiz.financialManagementAPI.service.category;

import com.francopaiz.financialManagementAPI.model.Category;
import java.util.List;

public interface CategoryService {
    /**
     * Obtiene todas las categorías disponibles.
     * @return Una lista de categorías.
     */
    List<Category> getCategories();

    /**
     * Busca una categoría por su ID.
     * @param id El ID de la categoría.
     * @return La categoría si es encontrada.
     */
    Category findCategoryById(String id);

    /**
     * Crea una nueva categoría.
     * @param category La categoría a crear.
     * @return La categoría creada.
     */
    Category createCategory(Category category);

    /**
     * Actualiza una categoría existente.
     * @param id El ID de la categoría a actualizar.
     * @param category La categoría con los datos actualizados.
     * @return La categoría actualizada.
     */
    Category updateCategory(String id, Category category);

    /**
     * Elimina una categoría por su ID.
     * @param id El ID de la categoría a eliminar.
     */
    void deleteCategory(String id);
}
