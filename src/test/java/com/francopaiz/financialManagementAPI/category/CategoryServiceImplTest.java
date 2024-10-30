package com.francopaiz.financialManagementAPI.category;
import com.francopaiz.financialManagementAPI.model.Category;
import com.francopaiz.financialManagementAPI.repository.category.CategoryRepository;
import com.francopaiz.financialManagementAPI.service.category.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    void setUp() {
        // Establecemos el perfil de Spring como "postgres" para los tests de formato de ID
        ReflectionTestUtils.setField(categoryService, "profile", "postgres");
    }

    @Test
    public void testGetCategories() {
        // Configuramos el mock para el repositorio
        List<Category> categories = new ArrayList<>();
        Category category = new Category();
        category.setName("Food");
        categories.add(category);
        when(categoryRepository.getCategories()).thenReturn(categories);

        // Ejecutamos el método y verificamos el resultado
        List<Category> result = categoryService.getCategories();
        assertEquals(1, result.size());
        assertEquals("Food", result.get(0).getName());
        verify(categoryRepository, times(1)).getCategories();
    }

    @Test
    public void testFindCategoryById_ValidId() {
        // Configuramos el mock para el repositorio
        String id = "1";
        Category category = new Category();
        category.setName("Utilities");
        when(categoryRepository.findCategoryById(id)).thenReturn(Optional.of(category));

        // Ejecutamos el método y verificamos el resultado
        Category result = categoryService.findCategoryById(id);
        assertNotNull(result);
        assertEquals("Utilities", result.getName());
        verify(categoryRepository, times(1)).findCategoryById(id);
    }

    @Test
    public void testFindCategoryById_InvalidIdFormat() {
        // Ejecutamos el método y verificamos que lanza una excepción por formato de ID inválido
        String invalidId = "invalid_id";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            categoryService.findCategoryById(invalidId);
        });
        assertTrue(exception.getMessage().contains("Formato de ID no válido para PostgreSQL"));
        verify(categoryRepository, never()).findCategoryById(anyString());
    }

    @Test
    public void testCreateCategory() {
        // Configuramos el mock para el repositorio
        Category category = new Category();
        category.setName("Health");
        when(categoryRepository.createCategory(category)).thenReturn(category);

        // Ejecutamos el método y verificamos el resultado
        Category result = categoryService.createCategory(category);
        assertNotNull(result);
        assertEquals("Health", result.getName());
        verify(categoryRepository, times(1)).createCategory(category);
    }

    @Test
    public void testUpdateCategory_ValidId() {
        // Configuramos el mock para el repositorio
        String id = "1";
        Category existingCategory = new Category();
        existingCategory.setName("Entertainment");

        Category updatedCategory = new Category();
        updatedCategory.setName("Travel");

        when(categoryRepository.findCategoryById(id)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.updateCategory(existingCategory)).thenReturn(updatedCategory);

        // Ejecutamos el método y verificamos el resultado
        Category result = categoryService.updateCategory(id, updatedCategory);
        assertNotNull(result);
        assertEquals("Travel", result.getName());
        verify(categoryRepository, times(1)).findCategoryById(id);
        verify(categoryRepository, times(1)).updateCategory(existingCategory);
    }

    @Test
    public void testUpdateCategory_CategoryNotFound() {
        // Configuramos el mock para el repositorio
        String id = "2";
        Category updatedCategory = new Category();
        updatedCategory.setName("Education");

        when(categoryRepository.findCategoryById(id)).thenReturn(Optional.empty());

        // Ejecutamos el método y verificamos que lanza una excepción por categoría no encontrada
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            categoryService.updateCategory(id, updatedCategory);
        });
        assertEquals("Categoría no encontrada", exception.getMessage());
        verify(categoryRepository, times(1)).findCategoryById(id);
        verify(categoryRepository, never()).updateCategory(any(Category.class));
    }

    @Test
    public void testDeleteCategory_ValidId() {
        // Configuramos el mock para el repositorio
        String id = "1";
        doNothing().when(categoryRepository).deleteCategory(id);

        // Ejecutamos el método
        categoryService.deleteCategory(id);
        verify(categoryRepository, times(1)).deleteCategory(id);
    }

    @Test
    public void testDeleteCategory_InvalidIdFormat() {
        // Ejecutamos el método y verificamos que lanza una excepción por formato de ID inválido
        String invalidId = "invalid_id";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            categoryService.deleteCategory(invalidId);
        });
        assertTrue(exception.getMessage().contains("Formato de ID no válido para PostgreSQL"));
        verify(categoryRepository, never()).deleteCategory(anyString());
    }
}
