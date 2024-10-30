package com.francopaiz.financialManagementAPI.service.category;

import com.francopaiz.financialManagementAPI.model.Category;
import com.francopaiz.financialManagementAPI.repository.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Value("${spring.profiles.active}")
    private String profile;

    @Override
    public List<Category> getCategories() {
        return categoryRepository.getCategories();
    }

    @Override
    public Category findCategoryById(String id) {
        // Valida el formato del ID antes de buscar la categoría.
        validateIdFormat(id);
        return categoryRepository.findCategoryById(id).orElse(null);
    }

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.createCategory(category);
    }

    @Override
    public Category updateCategory(String id, Category category) {
        // Valida el formato del ID antes de actualizar la categoría.
        validateIdFormat(id);

        Category existingCategory = categoryRepository.findCategoryById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));

        if (category.getName() != null) {
            existingCategory.setName(category.getName());
        }

        return categoryRepository.updateCategory(existingCategory);
    }

    @Override
    public void deleteCategory(String id) {
        // Valida el formato del ID antes de eliminar la categoría.
        validateIdFormat(id);
        categoryRepository.deleteCategory(id);
    }

    /**
     * Valida el formato del ID de la categoría para PostgreSQL.
     *
     * @param id ID de la categoría a validar.
     */
    private void validateIdFormat(String id) {
        if (profile.equals("postgres")) {
            try {
                Long.parseLong(id);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Formato de ID no válido para PostgreSQL: " + id);
            }
        }
    }
}
