package com.francopaiz.financialManagementAPI.category;

import com.francopaiz.financialManagementAPI.model.Category;
import com.francopaiz.financialManagementAPI.service.category.CategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryService categoryService;

    @Test
    public void testGetCategories() {
        // Configuramos el mock para devolver una lista de categorías
        List<Category> categories = new ArrayList<>();
        Category category = new Category();
        category.setName("Food");
        categories.add(category);
        when(categoryService.getCategories()).thenReturn(categories);

        // Ejecutamos el método y verificamos el resultado
        List<Category> result = categoryService.getCategories();
        assertEquals(1, result.size());
        assertEquals("Food", result.get(0).getName());
        verify(categoryService, times(1)).getCategories();
    }

    @Test
    public void testFindCategoryById() {
        // Configuramos el mock para devolver una categoría específica
        String id = "1";
        Category category = new Category();
        category.setName("Utilities");
        when(categoryService.findCategoryById(id)).thenReturn(category);

        // Ejecutamos el método y verificamos el resultado
        Category result = categoryService.findCategoryById(id);
        assertNotNull(result);
        assertEquals("Utilities", result.getName());
        verify(categoryService, times(1)).findCategoryById(id);
    }

    @Test
    public void testFindCategoryById_NotFound() {
        // Configuramos el mock para devolver null si la categoría no existe
        String id = "2";
        when(categoryService.findCategoryById(id)).thenReturn(null);

        // Ejecutamos el método y verificamos el resultado
        Category result = categoryService.findCategoryById(id);
        assertNull(result);
        verify(categoryService, times(1)).findCategoryById(id);
    }

    @Test
    public void testCreateCategory() {
        // Configuramos el mock para devolver la categoría creada
        Category category = new Category();
        category.setName("Health");
        when(categoryService.createCategory(category)).thenReturn(category);

        // Ejecutamos el método y verificamos el resultado
        Category result = categoryService.createCategory(category);
        assertNotNull(result);
        assertEquals("Health", result.getName());
        verify(categoryService, times(1)).createCategory(category);
    }

    @Test
    public void testUpdateCategory() {
        // Configuramos el mock para devolver la categoría actualizada
        String id = "1";
        Category category = new Category();
        category.setName("Travel");
        when(categoryService.updateCategory(id, category)).thenReturn(category);

        // Ejecutamos el método y verificamos el resultado
        Category result = categoryService.updateCategory(id, category);
        assertNotNull(result);
        assertEquals("Travel", result.getName());
        verify(categoryService, times(1)).updateCategory(id, category);
    }

    @Test
    public void testDeleteCategory() {
        // Configuramos el mock para que no haga nada al eliminar
        String id = "1";
        doNothing().when(categoryService).deleteCategory(id);

        // Ejecutamos el método y verificamos la interacción
        categoryService.deleteCategory(id);
        verify(categoryService, times(1)).deleteCategory(id);
    }
}
